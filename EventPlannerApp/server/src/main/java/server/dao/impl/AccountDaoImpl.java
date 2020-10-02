package server.dao.impl;

import server.dao.AccountDao;
import server.model.Account;

import javax.persistence.EntityManager;
import java.util.Collection;

public class AccountDaoImpl implements AccountDao {

    private final EntityManager entityManager;

    public AccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Account account){
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Account account){
        var accountToBeRemoved = getById(account.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(accountToBeRemoved);
        entityManager.getTransaction().commit();
    }

    @Override
    public Account getById(int id){
        return entityManager.find(Account.class,id);
    }

    @Override
    public Collection<Account> findByUsernameAndPassword(String username, String password){
        var query = entityManager
                .createQuery("select a from Account a where a.username= :username and a.password= :password",Account.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultList();
    }

    @Override
    public void updateEvents(Account account) {
        var accountToBeUpdated = getById(account.getId());
        entityManager.getTransaction().begin();
        accountToBeUpdated.setEvents(account.getEvents());
        entityManager.getTransaction().commit();
    }
}
