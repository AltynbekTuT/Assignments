package main.java.com.fulfillment.subsystemA;

import main.java.com.fulfillment.exception.FulfillmentException;

public class OrderValidator {
    public void validateOrder(String itemId, int quantity) {
        if (itemId == null || itemId.trim().isEmpty()) {
            throw new FulfillmentException("Ошибка Подсистемы A: Не указан ID товара!");
        }
        if (quantity <= 0) {
            throw new FulfillmentException("Ошибка Подсистемы A: Количество должно быть больше 0!");
        }
    }
}
