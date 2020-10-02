package lib.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AccountDto implements Serializable {

    private int id;
    private String username;
    private String password;
    private Set<Integer> eventsIds=new HashSet<>();

    public AccountDto(String username, String password, Set<Integer> eventsIds) {
        this.username = username;
        this.password = password;
        this.eventsIds = eventsIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getEventsIds() {
        return eventsIds;
    }

    public void setEventsIds(Set<Integer> eventsIds) {
        this.eventsIds = eventsIds;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", eventsIds=" + eventsIds +
                '}';
    }
}
