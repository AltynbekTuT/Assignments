package main.java.com.fulfillment.facade;

import com.fulfillment.subsystemA.*;
import com.fulfillment.subsystemB.*;
import com.fulfillment.subsystemC.*;

public class FulfillmentAdminFacade {
    private final InventoryManager inventoryManager = new InventoryManager();
    private final PackingStation packingStation = new PackingStation();
    private final PaymentGateway paymentGateway = new PaymentGateway();

    // Метод инспекции состояния заказа по всем 3 подсистемам на диске
    public void printFullOrderReport(String orderId) {
        System.out.println("\n--- ИНСПЕКЦИЯ СОСТОЯНИЯ ЗАКАЗА: " + orderId + " ---");
        
        InventoryEntity entityA = inventoryManager.getStockInfo(orderId);
        WarehouseEntity entityB = packingStation.getPackingInfo(orderId);
        ShippingEntity entityC = paymentGateway.getShippingInfo(orderId);

        System.out.println("Подсистема A (Инвентарь): " + (entityA != null ? entityA.getStatus() : "НЕТ ДАННЫХ"));
        System.out.println("Подсистема B (Склад):     " + (entityB != null ? entityB.getStatus() : "НЕТ ДАННЫХ"));
        System.out.println("Подсистема C (Доставка):  " + (entityC != null ? entityC.getStatus() : "НЕТ ДАННЫХ"));
    }
}
