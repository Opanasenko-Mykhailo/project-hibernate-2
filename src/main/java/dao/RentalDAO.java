package dao;

import domain.Rental;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;

public class RentalDAO extends GenericDAO<Rental>{
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyUnreturnedRental() {
        Query query = getCurrentSession().createQuery("select r from Rental r where r.returnDate is null", Rental.class);
        query.setMaxResults(1);
        return (Rental) query.getSingleResult();
    }
}
