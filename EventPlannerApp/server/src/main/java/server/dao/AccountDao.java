package server.dao;

import server.model.Account;

import java.util.Collection;

public interface AccountDao {
    void persist(Account account);
    void remove(Account account);
    Account getById(int id);
    Collection<Account> findByUsernameAndPassword(String username, String password);
    void updateEvents(Account account);
}
