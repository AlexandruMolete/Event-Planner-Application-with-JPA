package server.convert;

import lib.dto.EventDto;
import server.model.Event;
import server.model.Reminder;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public final class EventConverter {

    private EventConverter(){

    }

    public static Event convert(EventDto eventDto){
        var event = new Event();
        Optional.ofNullable(eventDto.getAccount())
                .ifPresent(accountDto -> {
                    event.setAccount(AccountConverter.convert(accountDto));
                });
        event.setId(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setDate(eventDto.getDate());
        event.setTimestamp(eventDto.getTimestamp());
        return event;
    }

    public static EventDto convert(Event event){
        var eventDto = new EventDto(
                event.getTitle(),
                event.getDate(),
                event.getTimestamp(),
                null,
                event.getReminders().stream().map(Reminder::getId).collect(Collectors.toSet())
        );
        Optional.ofNullable(event.getAccount())
                .ifPresent(account -> {
                    eventDto.setAccount(AccountConverter.convert(account));
                });
        eventDto.setId(event.getId());
        return eventDto;
    }
}
