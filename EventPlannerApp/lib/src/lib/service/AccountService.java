package lib.service;

import lib.dto.AccountDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface AccountService extends Remote {
    void persist(AccountDto accountDto) throws RemoteException;
    void remove(AccountDto accountDto) throws RemoteException;
    Collection<AccountDto> findByUsernameAndPassword(String username, String password) throws RemoteException;
    void updateEvents(AccountDto accountDto) throws RemoteException;
}
