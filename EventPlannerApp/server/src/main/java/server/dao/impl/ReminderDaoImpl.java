package server.dao.impl;

import server.dao.ReminderDao;
import server.model.Event;
import server.model.Reminder;

import javax.persistence.EntityManager;
import java.util.Collection;

public class ReminderDaoImpl implements ReminderDao {

    private final EntityManager entityManager;

    public ReminderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Reminder reminder){
        entityManager.getTransaction().begin();
        entityManager.persist(reminder);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Reminder reminder){
        var reminderToBeRemoved = getById(reminder.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(reminderToBeRemoved);
        entityManager.getTransaction().commit();
    }

    @Override
    public Collection<Reminder> findByEventId(int eventId){
        var query = entityManager
                .createQuery("select r from Reminder r join r.event e where e.id = :id",Reminder.class);
        query.setParameter("id", eventId);
        return query.getResultList();
    }

    @Override
    public Reminder getById(int id){
        return entityManager.find(Reminder.class,id);
    }

    @Override
    public void updateEvent(Reminder reminder, Event event) {
        var reminderToBeModified = getById(reminder.getId());
        entityManager.getTransaction().begin();
        reminderToBeModified.setEvent(event);
        entityManager.getTransaction().commit();
    }
}
