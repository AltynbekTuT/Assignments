package main.java.com.fulfillment.main;

import com.fulfillment.facade.FulfillmentAdminFacade;
import com.fulfillment.facade.FulfillmentFacade;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FulfillmentFacade facade = new FulfillmentFacade();
        FulfillmentAdminFacade adminFacade = new FulfillmentAdminFacade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("     E-COMMERCE FULFILLMENT DEMO          ");
            System.out.println("==========================================");
            System.out.println("1. Успешное выполнение цепочки (A -> B -> C)");
            System.out.println("2. Сэмулировать сбой на Шаге B (Откат A)");
            System.out.println("3. Сэмулировать сбой на Шаге C (Откат B, затем A)");
            System.out.println("4. Проверить состояние заказа на диске (Admin)");
            System.out.println("5. Сбросить все файлы данных (Очистка)");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();
            if (choice.equals("0")) break;

            try {
                switch (choice) {
                    case "1":
                        facade.executeFulfillment("ORD-100", "LAPTOP", 1, 1200.0, false, false);
                        break;
                    case "2":
                        facade.executeFulfillment("ORD-200", "PHONE", 2, 800.0, true, false);
                        break;
                    case "3":
                        facade.executeFulfillment("ORD-300", "TABLET", 1, 500.0, false, true);
                        break;
                    case "4":
                        System.out.print("Введите ID заказа (например ORD-100): ");
                        String id = scanner.nextLine();
                        adminFacade.printFullOrderReport(id);
                        break;
                    case "5":
                        resetData();
                        System.out.println("🧹 Файлы данных очищены!");
                        break;
                    default:
                        System.out.println("Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("Результат: " + e.getMessage());
            }
        }
    }

    private static void resetData() {
        String[] files = {"data/inventory.txt", "data/warehouse.txt", "data/shipping.txt", "data/chain_status.txt"};
        for (String f : files) {
            new File(f).delete();
        }
    }
}
