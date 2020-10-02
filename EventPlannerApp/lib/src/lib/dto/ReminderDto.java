package lib.dto;

import java.io.Serializable;
import java.time.LocalTime;

public class ReminderDto implements Serializable {

    private int id;
    private LocalTime time;
    private EventDto event;

    public ReminderDto(LocalTime time, EventDto event) {
        this.time = time;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Elapsed time: "+this.time;
    }
}
