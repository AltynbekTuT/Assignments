package com.fulfillment.facade;

import com.fulfillment.exception.FulfillmentException;
import com.fulfillment.subsystemA.InventoryManager;
import com.fulfillment.subsystemB.PackingStation;
import com.fulfillment.subsystemC.PaymentGateway;

import java.io.*;
import java.nio.file.*;

public class FulfillmentFacade {
    private final InventoryManager inventoryManager = new InventoryManager();
    private final PackingStation packingStation = new PackingStation();
    private final PaymentGateway paymentGateway = new PaymentGateway();
    private final String chainLogPath = "data/chain_status.txt";

    public FulfillmentFacade() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Главная цепочка (A -> B -> C)
    public boolean executeFulfillment(String orderId, String itemId, int quantity, double amount, 
                                      boolean forceFailB, boolean forceFailC) {
        
        System.out.println("\n=== ЗАПУСК ЦЕПОЧКИ ДЛЯ ЗАКАЗА: " + orderId + " ===");

        // Шаг 1: Подсистема A (Бронирование товара)
        try {
            inventoryManager.reserveStock(orderId, itemId, quantity);
            saveChainStatus(orderId, "STEP_A_COMMITTED");
            System.out.println("-> Шаг A пройден: Товар забронирован.");
        } catch (Exception e) {
            saveChainStatus(orderId, "FAILED_AT_A");
            System.out.println("❌ Сбой на Шаге A: " + e.getMessage());
            throw e;
        }

        // Шаг 2: Подсистема B (Упаковка на складе)
        try {
            packingStation.packOrder(orderId, forceFailB);
            saveChainStatus(orderId, "STEP_B_COMMITTED");
            System.out.println("-> Шаг B пройден: Заказ упакован.");
        } catch (Exception e) {
            System.out.println("❌ Сбой на Шаге B: " + e.getMessage());
            System.out.println("⚠️  ОТКАТ: Компенсация Подсистемы A...");
            
            // Откат глубины 1: Отмена A
            inventoryManager.cancelReservation(orderId);
            saveChainStatus(orderId, "COMPENSATED_A_ONLY");
            throw new FulfillmentException("Откат выполнен (Отменена бронь в A). Причина: " + e.getMessage());
        }

        // Шаг 3: Подсистема C (Оплата и Доставка)
        try {
            paymentGateway.processPaymentAndShip(orderId, amount, forceFailC);
            saveChainStatus(orderId, "COMPLETED_SUCCESSFULLY");
            System.out.println("-> Шаг C пройден: Оплата и доставка оформлены.");
            System.out.println("✅ ЦЕПОЧКА УСПЕШНО ЗАВЕРШЕНА!");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Сбой на Шаге C: " + e.getMessage());
            System.out.println("⚠️  ДВУХШАГОВЫЙ ОТКАТ: Компенсация B, затем A...");

            // Откат глубины 2: Откат B, затем откат A
            packingStation.rollbackPacking(orderId);
            inventoryManager.cancelReservation(orderId);
            
            saveChainStatus(orderId, "COMPENSATED_B_THEN_A");
            throw new FulfillmentException("Двухшаговый откат выполнен (Откатаны B и A). Причина: " + e.getMessage());
        }
    }

    private void saveChainStatus(String orderId, String status) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(chainLogPath, true))) {
            writer.println(orderId + "=" + status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}