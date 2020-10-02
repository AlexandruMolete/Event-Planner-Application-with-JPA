package client.controller;

import lib.dto.AccountDto;
import lib.service.AccountService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Collection;

public class AccountController {

    private final AccountService accountService;

    private AccountController(){
        try{
            var registry = LocateRegistry.getRegistry("localhost", 4545);
            accountService = (AccountService)registry.lookup("accountService");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final class SingletonHolder{
        public static final AccountController INSTANCE = new AccountController();
    }

    public static AccountController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void persist(AccountDto accountDto){
        try{
            accountService.persist(accountDto);
        }catch (RemoteException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void remove(AccountDto accountDto){
        try{
            accountService.remove(accountDto);
        }catch (RemoteException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<AccountDto> findByUsernameAndPassword(String username, String password){
        try {
            return accountService.findByUsernameAndPassword(username,password);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateEvents(AccountDto accountDto){
        try {
            accountService.updateEvents(accountDto);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
