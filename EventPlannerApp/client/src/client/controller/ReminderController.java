package client.controller;

import lib.dto.EventDto;
import lib.dto.ReminderDto;
import lib.service.ReminderService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Collection;

public class ReminderController {

    private final ReminderService reminderService;

    private ReminderController(){
        try {
            var registry = LocateRegistry.getRegistry("localhost",4545);
            reminderService = (ReminderService)registry.lookup("reminderService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final class SingletonHolder{
        public static final ReminderController INSTANCE = new ReminderController();
    }

    public static ReminderController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void persist(ReminderDto reminderDto){
        try {
            reminderService.persist(reminderDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void remove(ReminderDto reminderDto){
        try {
            reminderService.remove(reminderDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<ReminderDto> findByEventId(int eventId){
        try {
            return reminderService.findByEventId(eventId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateEvent(ReminderDto reminderDto, EventDto eventDto){
        try {
            reminderService.updateEvent(reminderDto,eventDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
