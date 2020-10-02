package server.service;

import lib.dto.AccountDto;
import lib.dto.EventDto;
import lib.service.EventService;
import server.convert.AccountConverter;
import server.convert.EventConverter;
import server.dao.EventDao;
import server.dao.ReminderDao;
import server.dao.impl.EventDaoImpl;
import server.dao.impl.ReminderDaoImpl;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class EventServiceImpl extends UnicastRemoteObject implements EventService {

    private final EventDao eventDao;
    private final ReminderDao reminderDao;

    public EventServiceImpl() throws RemoteException{
        var entityManagerFactory = Persistence.createEntityManagerFactory("java2_planificatorPU");
        var entityManager = entityManagerFactory.createEntityManager();
        eventDao = new EventDaoImpl(entityManager);
        reminderDao = new ReminderDaoImpl(entityManagerFactory.createEntityManager());
    }

    @Override
    public void persist(EventDto eventDto) throws RemoteException{
        var event = EventConverter.convert(eventDto);
        var reminders = eventDto.getRemindersIds().stream()
                .map(reminderDao::getById)
                .collect(Collectors.toSet());
        event.setReminders(reminders);
        eventDao.persist(event);
    }

    @Override
    public void remove(EventDto eventDto) throws RemoteException{
        var event = EventConverter.convert(eventDto);
        var reminders = eventDto.getRemindersIds().stream()
                .map(reminderDao::getById)
                .collect(Collectors.toSet());
        event.setReminders(reminders);
        eventDao.remove(event);
    }

    @Override
    public Collection<EventDto> findByDate(int accountId, LocalDate date) throws RemoteException{
        return eventDao.findByDate(accountId,date).stream()
                .map(EventConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EventDto> findByAccountId(int accountId) throws RemoteException {
        return eventDao.findByAccountId(accountId).stream()
                .map(EventConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EventDto> findByAccountIdUnordered(int accountId) throws RemoteException {
        return eventDao.findByAccountIdUnordered(accountId).stream()
                .map(EventConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAccount(EventDto eventDto, AccountDto accountDto) throws RemoteException {
        var event = EventConverter.convert(eventDto);
        var account = AccountConverter.convert(accountDto);
        var accountEvents = accountDto.getEventsIds().stream()
                .map(eventDao::getById)
                .collect(Collectors.toSet());
        account.setEvents(accountEvents);
        event.setAccount(account);
        var reminders = eventDto.getRemindersIds().stream()
                .map(reminderDao::getById)
                .collect(Collectors.toSet());
        event.setReminders(reminders);
        eventDao.updateAccount(event,account);
    }

    @Override
    public void updateReminders(EventDto eventDto) throws RemoteException {
        var event = EventConverter.convert(eventDto);
        var reminders = eventDto.getRemindersIds().stream()
                .map(reminderDao::getById)
                .collect(Collectors.toSet());
        event.setReminders(reminders);
        eventDao.updateReminders(event);
    }
}
