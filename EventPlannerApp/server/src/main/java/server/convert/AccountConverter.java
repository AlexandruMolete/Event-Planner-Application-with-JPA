package server.convert;


import lib.dto.AccountDto;
import server.model.Account;
import server.model.Event;

import java.util.Collections;
import java.util.stream.Collectors;

public final class AccountConverter {

    private AccountConverter(){

    }

    public static Account convert(AccountDto accountDto){
        var account = new Account();
        account.setId(accountDto.getId());
        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        return account;
    }

    public static AccountDto convert(Account account){
        var accountDto = new AccountDto(
                account.getUsername(),
                account.getPassword(),
                account.getEvents().stream().map(Event::getId).collect(Collectors.toSet())
        );
        accountDto.setId(account.getId());
        return accountDto;
    }
}
