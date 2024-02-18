package dao;

import domain.Actor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;


public class ActorDAO extends GenericDAO<Actor>{
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
