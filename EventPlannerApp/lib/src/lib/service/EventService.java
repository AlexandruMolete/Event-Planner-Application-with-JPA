package lib.service;

import lib.dto.AccountDto;
import lib.dto.EventDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Collection;

public interface EventService extends Remote {
    void persist(EventDto eventDto) throws RemoteException;
    void remove(EventDto eventDto) throws RemoteException;
    Collection<EventDto> findByDate(int accountId, LocalDate date) throws RemoteException;
    Collection<EventDto> findByAccountId(int accountId) throws RemoteException;
    Collection<EventDto> findByAccountIdUnordered(int accountId) throws RemoteException;
    void updateAccount(EventDto eventDto, AccountDto accountDto) throws RemoteException;
    void updateReminders(EventDto eventDto) throws RemoteException;
}
