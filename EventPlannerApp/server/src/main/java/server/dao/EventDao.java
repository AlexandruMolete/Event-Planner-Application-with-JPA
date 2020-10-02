package server.dao;

import server.model.Account;
import server.model.Event;

import java.time.LocalDate;
import java.util.Collection;

public interface EventDao {
    void persist(Event event);
    void remove(Event event);
    Collection<Event> findByDate(int accountId, LocalDate date);
    Event getById(int id);
    Collection<Event> findByAccountId(int accountId);
    Collection<Event> findByAccountIdUnordered(int accountId);
    void updateAccount(Event event,Account account);
    void updateReminders(Event event);
}
