package server.service;

import lib.dto.EventDto;
import lib.dto.ReminderDto;
import lib.service.ReminderService;
import server.convert.AccountConverter;
import server.convert.EventConverter;
import server.convert.ReminderConverter;
import server.dao.EventDao;
import server.dao.ReminderDao;
import server.dao.impl.EventDaoImpl;
import server.dao.impl.ReminderDaoImpl;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReminderServiceImpl extends UnicastRemoteObject implements ReminderService{

    private final ReminderDao reminderDao;
    private final EventDao eventDao;

    public ReminderServiceImpl() throws RemoteException{
        var entityManagerFactory = Persistence.createEntityManagerFactory("java2_planificatorPU");
        var entityManager = entityManagerFactory.createEntityManager();
        reminderDao = new ReminderDaoImpl(entityManager);
        eventDao = new EventDaoImpl(entityManagerFactory.createEntityManager());
    }

    @Override
    public void persist(ReminderDto reminderDto) throws RemoteException{
        var reminder = ReminderConverter.convert(reminderDto);
        reminderDao.persist(reminder);
    }

    @Override
    public void remove(ReminderDto reminderDto) throws RemoteException{
        var reminder = ReminderConverter.convert(reminderDto);
        reminderDao.remove(reminder);
    }

    @Override
    public Collection<ReminderDto> findByEventId(int eventId) throws RemoteException{
        return reminderDao.findByEventId(eventId).stream()
                .map(ReminderConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEvent(ReminderDto reminderDto, EventDto eventDto) throws RemoteException {
        var reminder = ReminderConverter.convert(reminderDto);
        var event = EventConverter.convert(eventDto);
        var account = AccountConverter.convert(eventDto.getAccount());
        var accountEvents = eventDto.getAccount().getEventsIds().stream()
                .map(eventDao::getById)
                .collect(Collectors.toSet());
        account.setEvents(accountEvents);
        event.setAccount(account);
        var eventReminders = eventDto.getRemindersIds().stream()
                .map(reminderDao::getById)
                .collect(Collectors.toSet());
        event.setReminders(eventReminders);
        reminder.setEvent(event);
        reminderDao.updateEvent(reminder,event);
    }
}
