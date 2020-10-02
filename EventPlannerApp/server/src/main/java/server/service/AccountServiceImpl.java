package server.service;

import lib.dto.AccountDto;
import lib.service.AccountService;
import server.convert.AccountConverter;
import server.dao.AccountDao;
import server.dao.EventDao;
import server.dao.impl.AccountDaoImpl;
import server.dao.impl.EventDaoImpl;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.stream.Collectors;

public class AccountServiceImpl extends UnicastRemoteObject implements AccountService {

    private final AccountDao accountDao;
    private final EventDao eventDao;

    public AccountServiceImpl() throws RemoteException{
        var entityManagerFactory = Persistence.createEntityManagerFactory("java2_planificatorPU");
        var entityManager = entityManagerFactory.createEntityManager();
        accountDao = new AccountDaoImpl(entityManager);
        eventDao = new EventDaoImpl(entityManagerFactory.createEntityManager());
    }

    @Override
    public void persist(AccountDto accountDto) throws RemoteException{
        var account = AccountConverter.convert(accountDto);
        var events = accountDto.getEventsIds().stream()
                .map(eventDao::getById)
                .collect(Collectors.toSet());
        account.setEvents(events);
        accountDao.persist(account);
    }

    @Override
    public void remove(AccountDto accountDto) throws RemoteException{
        var account = AccountConverter.convert(accountDto);
        var events = accountDto.getEventsIds().stream()
                .map(eventDao::getById)
                .collect(Collectors.toSet());
        account.setEvents(events);
        accountDao.remove(account);
    }

    @Override
    public Collection<AccountDto> findByUsernameAndPassword(String username, String password) throws RemoteException {
        return accountDao.findByUsernameAndPassword(username,password).stream()
                .map(AccountConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEvents(AccountDto accountDto) throws RemoteException {
        var account = AccountConverter.convert(accountDto);
        var events = accountDto.getEventsIds().stream()
                .map(eventDao::getById)
                .collect(Collectors.toSet());
        account.setEvents(events);
        accountDao.updateEvents(account);
    }
}
