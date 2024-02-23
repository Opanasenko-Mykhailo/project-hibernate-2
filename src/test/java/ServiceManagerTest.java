import domain.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import service.MySessionFactory;
import service.ServiceManager;

import static org.junit.jupiter.api.Assertions.*;

class ServiceManagerTest {

    private static SessionFactory sessionFactory;
    private static ServiceManager serviceManager;

    @BeforeAll
    static void setUpAll() {
        sessionFactory = MySessionFactory.getSessionFactory();
        serviceManager = new ServiceManager(sessionFactory);
    }

    @AfterAll
    static void tearDownAll() {
        sessionFactory.close();
    }

    @Test
    void newFilmWasMade() {
        assertDoesNotThrow(() -> serviceManager.newFilmWasMade());
    }

    @Test
    void rentInventory() {
        Customer customer = serviceManager.createCustomer();
        assertNotNull(customer);

        assertDoesNotThrow(() -> serviceManager.rentInventory(customer));
    }

    @Test
    void returnInventoryToStore() {
        assertDoesNotThrow(() -> serviceManager.returnInventoryToStore());
    }

    @Test
    void createCustomer() {
        Customer customer = serviceManager.createCustomer();
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals("Tom", customer.getFirstName());
        assertEquals("Cruz", customer.getLastName());
        assertEquals("test@gmail.com", customer.getEmail());
        assertTrue(customer.getIsActive());
        assertNotNull(customer.getStore());
        assertNotNull(customer.getAddress());
    }
}
