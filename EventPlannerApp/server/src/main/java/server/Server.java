package server;

import server.service.AccountServiceImpl;
import server.service.EventServiceImpl;
import server.service.ReminderServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        var registry = LocateRegistry.createRegistry(4545);
        registry.rebind("accountService", new AccountServiceImpl());
        registry.rebind("eventService", new EventServiceImpl());
        registry.rebind("reminderService", new ReminderServiceImpl());
    }
}
