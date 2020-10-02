package lib.service;

import lib.dto.EventDto;
import lib.dto.ReminderDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface ReminderService extends Remote {
    void persist(ReminderDto reminderDto) throws RemoteException;
    void remove(ReminderDto reminderDto) throws RemoteException;
    Collection<ReminderDto> findByEventId(int eventId) throws RemoteException;
    void updateEvent(ReminderDto reminderDto, EventDto eventDto) throws RemoteException;
}
