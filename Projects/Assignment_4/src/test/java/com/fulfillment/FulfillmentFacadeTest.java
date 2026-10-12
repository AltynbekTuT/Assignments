package com.fulfillment;

import com.fulfillment.exception.FulfillmentException;
import com.fulfillment.facade.FulfillmentAdminFacade;
import com.fulfillment.facade.FulfillmentFacade;
import com.fulfillment.subsystemA.*;
import com.fulfillment.subsystemB.*;
import com.fulfillment.subsystemC.*;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FulfillmentFacadeTest {
    private FulfillmentFacade facade;
    private InventoryRepository repoA;
    private WarehouseRepository repoB;
    private ShippingRepository repoC;

    @BeforeEach
    void setUp() {
        // Очищаем файлы перед каждым тестом (принцип F.I.R.S.T. - Independent/Repeatable)
        new File("data/inventory.txt").delete();
        new File("data/warehouse.txt").delete();
        new File("data/shipping.txt").delete();

        facade = new FulfillmentFacade();
        repoA = new InventoryRepository();
        repoB = new WarehouseRepository();
        repoC = new ShippingRepository();
    }

    // --- DSL Helper (Domain-Specific Testing Language) ---
    private void assertOrderStatusOnDisk(String orderId, InventoryEntity.Status expectedA, 
                                        WarehouseEntity.Status expectedB, ShippingEntity.Status expectedC) {
        InventoryEntity a = repoA.findByOrderId(orderId);
        WarehouseEntity b = repoB.findByOrderId(orderId);
        ShippingEntity c = repoC.findByOrderId(orderId);

        if (expectedA != null) assertEquals(expectedA, a.getStatus());
        if (expectedB != null) assertEquals(expectedB, b.getStatus());
        if (expectedC != null) assertEquals(expectedC, c.getStatus());
    }

    @Test
    @DisplayName("Успешное выполнение всей цепочки A -> B -> C")
    void testSuccessfulChain() {
        facade.executeFulfillment("ORD-1", "ITEM-1", 2, 100.0, false, false);
        assertOrderStatusOnDisk("ORD-1", InventoryEntity.Status.RESERVED, 
                                        WarehouseEntity.Status.PICKED_PACKED, 
                                        ShippingEntity.Status.CONFIRMED_PAID);
    }

    @Test
    @DisplayName("Сбой в Подсистеме B -> Откат Подсистемы A")
    void testCompensationWhenBFails() {
        assertThrows(FulfillmentException.class, () -> 
            facade.executeFulfillment("ORD-2", "ITEM-1", 2, 100.0, true, false)
        );

        // Проверяем, что бронь товара отменена на диске
        assertOrderStatusOnDisk("ORD-2", InventoryEntity.Status.CANCELLED, null, null);
    }

    @Test
    @DisplayName("Сбой в Подсистеме C -> Двухшаговый откат (B, затем A)")
    void testCompensationWhenCFails() {
        assertThrows(FulfillmentException.class, () -> 
            facade.executeFulfillment("ORD-3", "ITEM-1", 2, 100.0, false, true)
        );

        // Проверяем откат обеих подсистем на диске
        assertOrderStatusOnDisk("ORD-3", InventoryEntity.Status.CANCELLED, 
                                        WarehouseEntity.Status.ROLLED_BACK, null);
    }

    @Test
    @DisplayName("Тест вычитывания данных после 'перезапуска' приложения")
    void testPersistenceReloadAfterRestart() {
        facade.executeFulfillment("ORD-4", "ITEM-99", 1, 50.0, false, false);

        // Создаем новые экземпляры репозиториев (имитация перезапуска приложения)
        InventoryRepository newRepoA = new InventoryRepository();
        assertNotNull(newRepoA.findByOrderId("ORD-4"));
        assertEquals(InventoryEntity.Status.RESERVED, newRepoA.findByOrderId("ORD-4").getStatus());
    }
}