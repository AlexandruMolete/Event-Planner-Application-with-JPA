package client.controller;

import lib.dto.AccountDto;
import lib.dto.EventDto;
import lib.service.EventService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.util.Collection;

public class EventController {

    private final EventService eventService;

    private EventController(){
        try {
            var registry = LocateRegistry.getRegistry("localhost",4545);
            eventService = (EventService)registry.lookup("eventService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final class SingletonHolder{
        public static final EventController INSTANCE = new EventController();
    }

    public static EventController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void persist(EventDto eventDto){
        try {
            eventService.persist(eventDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void remove(EventDto eventDto){
        try {
            eventService.remove(eventDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<EventDto> findByDate(int accountId, LocalDate date){
        try{
            return eventService.findByDate(accountId,date);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<EventDto> findByAccountId(int accountId){
        try {
            return eventService.findByAccountId(accountId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<EventDto> findByAccountIdUnordered(int accountId){
        try {
            return eventService.findByAccountIdUnordered(accountId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(EventDto eventDto, AccountDto accountDto){
        try {
            eventService.updateAccount(eventDto,accountDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateReminders(EventDto eventDto){
        try {
            eventService.updateReminders(eventDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
