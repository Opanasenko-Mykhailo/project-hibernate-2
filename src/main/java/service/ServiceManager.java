package service;

import dao.*;
import domain.*;
import domain.enums.Feature;
import domain.enums.Rating;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ServiceManager {
    private final SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public ServiceManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.actorDAO = new ActorDAO(sessionFactory);
        this.addressDAO = new AddressDAO(sessionFactory);
        this.categoryDAO = new CategoryDAO(sessionFactory);
        this.cityDAO = new CityDAO(sessionFactory);
        this.countryDAO = new CountryDAO(sessionFactory);
        this.customerDAO = new CustomerDAO(sessionFactory);
        this.filmDAO = new FilmDAO(sessionFactory);
        this.filmTextDAO = new FilmTextDAO(sessionFactory);
        this.inventoryDAO = new InventoryDAO(sessionFactory);
        this.languageDAO = new LanguageDAO(sessionFactory);
        this.paymentDAO = new PaymentDAO(sessionFactory);
        this.rentalDAO = new RentalDAO(sessionFactory);
        this.staffDAO = new StaffDAO(sessionFactory);
        this.storeDAO = new StoreDAO(sessionFactory);
    }

    public void newFilmWasMade() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0, 20).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 8);
            List<Actor> actors = actorDAO.getItems(0, 21);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.PG13);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.BEHIND_THE_SCENES));
            film.setLength((short) 22);
            film.setReplacementCost(BigDecimal.TWO);
            film.setRentalRate(BigDecimal.ZERO);
            film.setLanguage(language);
            film.setDescription("new scary film");
            film.setTitle("scary my-movie");
            film.setRentalDuration((byte) 45);
            film.setOriginalLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.now());
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setId(film.getId());
            filmText.setDescription("new scary film");
            filmText.setTitle("scary my-movie");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

    public void rentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDAO.getFirstAvailableFilmForRent();
            Store store = storeDAO.getItems(0, 1).get(0);
            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getManagerStaff();

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(233.33));
            payment.setStaff(staff);
            paymentDAO.save(payment);
            session.getTransaction().commit();
        }
    }

    public void returnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    public Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);

            City city = cityDAO.getByName("Bhopal");

            Address address = new Address();
            address.setAddress("Test, 23");
            address.setPhone("38-066-6666-666");
            address.setCity(city);
            address.setDistrict("Test");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setIsActive(true);
            customer.setEmail("test@gmail.com");
            customer.setAddress(address);
            customer.setStore(store);
            customer.setFirstName("Tom");
            customer.setLastName("Cruz");
            customerDAO.save(customer);

            session.getTransaction().commit();
            return customer;
        }
    }
}
