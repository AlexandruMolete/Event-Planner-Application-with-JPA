package server.dao.impl;

import server.dao.EventDao;
import server.model.Account;
import server.model.Event;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collection;

public class EventDaoImpl implements EventDao {
    private final EntityManager entityManager;


    public EventDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Event event){
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Event event){
        var eventToBeRemoved = getById(event.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(eventToBeRemoved);
        entityManager.getTransaction().commit();
    }

    @Override
    public Collection<Event> findByDate(int accountId, LocalDate date){
        var query = entityManager.createQuery("select e from Event e join e.account a where a.id = :accountId and e.date = :date order by e.date, e.timestamp",Event.class);
        query.setParameter("accountId",accountId);
        query.setParameter("date",date);
        return query.getResultList();
    }

    @Override
    public Event getById(int id){
        return entityManager.find(Event.class,id);
    }

    @Override
    public Collection<Event> findByAccountId(int accountId) {
        var query = entityManager
                .createQuery("select e from Event e join e.account a where a.id= :accountId order by e.date, e.timestamp",Event.class);
        query.setParameter("accountId",accountId);
        return query.getResultList();
    }

    @Override
    public Collection<Event> findByAccountIdUnordered(int accountId) {
        var query = entityManager.createQuery("select e from Event e join e.account a where a.id= :accountId",Event.class);
        query.setParameter("accountId",accountId);
        return query.getResultList();
    }

    @Override
    public void updateAccount(Event event, Account account) {
        var eventToBeModified = getById(event.getId());
        entityManager.getTransaction().begin();
        eventToBeModified.setAccount(account);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateReminders(Event event) {
        var eventToBeModified = getById(event.getId());
        entityManager.getTransaction().begin();
        eventToBeModified.setReminders(event.getReminders());
        entityManager.getTransaction().commit();
    }
}
