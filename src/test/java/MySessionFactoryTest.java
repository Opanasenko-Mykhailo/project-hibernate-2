import domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.MySessionFactory;

import static org.junit.Assert.assertNotNull;

public class MySessionFactoryTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUp() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate-test.cfg.xml")
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterClass
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testSessionFactoryCreation() {
        assertNotNull(sessionFactory);
    }

//    @Test
//    public void testGetSessionFactorySingleton() {
//        SessionFactory sessionFactory1 = MySessionFactory.getSessionFactory();
//        SessionFactory sessionFactory2 = MySessionFactory.getSessionFactory();
//        assertNotNull(sessionFactory1);

//        assertNotNull(sessionFactory2);
//        assert (sessionFactory1 == sessionFactory2);
//    }
}
