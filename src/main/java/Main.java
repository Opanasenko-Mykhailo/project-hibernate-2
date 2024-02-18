import domain.Customer;
import org.hibernate.SessionFactory;
import service.MySessionFactory;
import service.ServiceManager;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = MySessionFactory.getSessionFactory();
        ServiceManager serviceManager = new ServiceManager(sessionFactory);


        Customer customer = serviceManager.createCustomer();
        serviceManager.returnInventoryToStore();
        serviceManager.rentInventory(customer);
        serviceManager.newFilmWasMade();
    }
}