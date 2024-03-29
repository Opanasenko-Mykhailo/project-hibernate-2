package dao;

import domain.Film;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;

public class FilmDAO extends GenericDAO<Film> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {
        Query query = getCurrentSession().createQuery("select f from Film f" +
                " where f.id not in (select distinct film.id from Inventory )", Film.class);
        query.setMaxResults(1);
        return (Film) query.getSingleResult();
    }
}
