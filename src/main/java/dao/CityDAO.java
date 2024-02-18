package dao;

import domain.City;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;

public class CityDAO extends GenericDAO<City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        Query query = getCurrentSession().createQuery("select c from City c where c.city = :NAME" , City.class);
        query.setParameter("NAME", name);
        query.setMaxResults(1);
        return (City) query.getSingleResult();
    }
}
