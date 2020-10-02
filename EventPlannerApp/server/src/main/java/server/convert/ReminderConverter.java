package server.convert;

import lib.dto.ReminderDto;
import server.model.Reminder;

import java.util.Optional;

public final class ReminderConverter {

    private ReminderConverter(){

    }

    public static Reminder convert(ReminderDto reminderDto){
        var reminder = new Reminder();
        Optional.ofNullable(reminderDto.getEvent())
                .ifPresent(eventDto -> {
                    reminder.setEvent(EventConverter.convert(eventDto));
                });
        reminder.setId(reminderDto.getId());
        reminder.setTime(reminderDto.getTime());
        return reminder;
    }

    public static ReminderDto convert(Reminder reminder){
        var reminderDto = new ReminderDto(
                reminder.getTime(),
                null
        );
        Optional.ofNullable(reminder.getEvent())
                .ifPresent(event -> {
                    reminderDto.setEvent(EventConverter.convert(event));
                });
        reminderDto.setId(reminder.getId());
        return reminderDto;
    }
}
