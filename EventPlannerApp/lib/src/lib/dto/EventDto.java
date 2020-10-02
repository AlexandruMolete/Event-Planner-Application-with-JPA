package lib.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class EventDto implements Serializable {

    private int id;
    private String title;
    private LocalDate date;
    private LocalTime timestamp;
    private AccountDto account;
    private Set<Integer> remindersIds=new HashSet<>();

    public EventDto(String title, LocalDate date, LocalTime timestamp, AccountDto account, Set<Integer> remindersIds) {
        this.title = title;
        this.date = date;
        this.timestamp = timestamp;
        this.account = account;
        this.remindersIds = remindersIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public Set<Integer> getRemindersIds() {
        return remindersIds;
    }

    public void setRemindersIds(Set<Integer> remindersIds) {
        this.remindersIds = remindersIds;
    }

    @Override
    public String toString() {
        return "Title: "+this.title+"   Date: "+this.date+"   Timestamp: "+this.timestamp+"   Reminders: "+this.remindersIds.size();
    }
}
