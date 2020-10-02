package server.dao;

import server.model.Event;
import server.model.Reminder;

import java.util.Collection;

public interface ReminderDao {
    void persist(Reminder reminder);
    void remove(Reminder reminder);
    Collection<Reminder> findByEventId(int eventId);
    Reminder getById(int id);
    void updateEvent(Reminder reminder, Event event);
}
