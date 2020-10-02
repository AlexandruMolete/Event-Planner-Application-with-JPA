package client.gui;

import client.controller.AccountController;
import client.controller.EventController;
import client.controller.ReminderController;
import lib.dto.AccountDto;
import lib.dto.EventDto;
import lib.dto.ReminderDto;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class EventFrame extends JFrame{
    private JList eventsList;
    private JButton showTodayEventsButton;
    private JTextField searchYearField;
    private JTextField searchMonthField;
    private JTextField searchDayField;
    private JButton showEventsButton;
    private JButton showAllEventsButton;
    private JTextField insertTitleField;
    private JTextField insertYearField;
    private JTextField insertMonthField;
    private JTextField insertDayField;
    private JTextField insertHourField;
    private JTextField insertMinuteField;
    private JTextField insertSecondField;
    private JButton addEventButton;
    private JButton removeEventButton;
    private JButton viewRemindersButton;
    private JLabel currentUserLabel;
    private JLabel typeDateLabel;
    private JLabel typeYearLabel;
    private JLabel typeMonthLabel;
    private JLabel typeDayLabel;
    private JLabel insertNewEventLabel;
    private JLabel newEventTitleLabel;
    private JLabel newEventDateLabel;
    private JLabel insertYearLabel;
    private JLabel insertMonthLabel;
    private JLabel insertDayLabel;
    private JLabel newEventTimestampLabel;
    private JLabel insertHourLabel;
    private JLabel insertMinuteLabel;
    private JLabel insertSecondLabel;
    private JLabel printCurrentUserLabel;
    private JPanel mainPanel;
    private AccountDto accountDto;
    private DefaultListModel<EventDto> listModel = new DefaultListModel<>();

    public EventFrame(AccountDto accountDto){
        this.accountDto = accountDto;
        listModel = new DefaultListModel<>();
        eventsList.setModel(listModel);
        printEvents();
        showAllEventsButton.addActionListener(e->printEvents());
        showEventsButton.addActionListener(e->printEventsByDate());
        showTodayEventsButton.addActionListener(e->printTodayEvents());
        addEventButton.addActionListener(e->createEvent());
        removeEventButton.addActionListener(e->removeEvent());
        viewRemindersButton.addActionListener(e->openReminderFrame());
        printCurrentUserLabel.setText(this.accountDto.getUsername());
        searchYearField.setText(Integer.valueOf(LocalDate.now().getYear()).toString());
        searchMonthField.setText(Integer.valueOf(LocalDate.now().getMonthValue()).toString());
        searchDayField.setText(Integer.valueOf(LocalDate.now().getDayOfMonth()).toString());
        insertYearField.setText(Integer.valueOf(LocalDate.now().getYear()).toString());
        insertMonthField.setText(Integer.valueOf(LocalDate.now().getMonthValue()).toString());
        insertDayField.setText(Integer.valueOf(LocalDate.now().getDayOfMonth()).toString());
        insertHourField.setText("0");
        insertMinuteField.setText("0");
        insertSecondField.setText("0");
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void printEvents(){
        updateAccountEvents();
        var events = EventController.getInstance().findByAccountId(this.accountDto.getId());
        listModel.clear();
        events.forEach(listModel::addElement);
        notifyEventByReminder(events);
    }

    private void printEventsByDate(){
        updateAccountEvents();
        int year = Integer.parseInt(searchYearField.getText());
        int month = Integer.parseInt(searchMonthField.getText());
        int day = Integer.parseInt(searchDayField.getText());
        var events = EventController.getInstance().findByDate(this.accountDto.getId(), LocalDate.of(year,month,day));
        listModel.clear();
        events.forEach(listModel::addElement);
        notifyEventByReminder(events);
    }

    private void printTodayEvents(){
        updateAccountEvents();
        var today = LocalDate.now();
        var events = EventController.getInstance().findByDate(this.accountDto.getId(),today);
        listModel.clear();
        events.forEach(listModel::addElement);
        notifyEventByReminder(events);
    }

    private void createEvent(){
        int year = Integer.parseInt(insertYearField.getText());
        int month = Integer.parseInt(insertMonthField.getText());
        int day = Integer.parseInt(insertDayField.getText());
        int hour = Integer.parseInt(insertHourField.getText());
        int minute = Integer.parseInt(insertMinuteField.getText());
        int second = Integer.parseInt(insertSecondField.getText());
        var newDate = LocalDate.of(year,month,day);
        var newTimestamp = LocalTime.of(hour,minute,second);
        var newTitle = insertTitleField.getText();
        var newEvent = new EventDto(
                newTitle,
                newDate,
                newTimestamp,
                this.accountDto,
                Collections.emptySet()
        );
        EventController.getInstance().persist(newEvent);
        printEvents();
        insertYearField.setText(Integer.valueOf(LocalDate.now().getYear()).toString());
        insertMonthField.setText(Integer.valueOf(LocalDate.now().getMonthValue()).toString());
        insertDayField.setText(Integer.valueOf(LocalDate.now().getDayOfMonth()).toString());
        insertHourField.setText("0");
        insertMinuteField.setText("0");
        insertSecondField.setText("0");
    }

    private void removeEvent(){
        EventDto selectedEvent = (EventDto)eventsList.getSelectedValue();
        if(selectedEvent != null){
            var reminders = new ArrayList<>(ReminderController.getInstance().findByEventId(selectedEvent.getId()));
            if(!reminders.isEmpty()){
                for(ReminderDto reminder : reminders){
                    ReminderController.getInstance().remove(reminder);
                }
            }
            EventController.getInstance().remove(selectedEvent);
            printEvents();
        }else{
            JOptionPane.showMessageDialog(null,"Please select an event.");
        }
    }

    private void openReminderFrame(){
        EventDto selectedEvent = (EventDto)eventsList.getSelectedValue();
        if(selectedEvent != null){
            new ReminderFrame(selectedEvent);
        }else{
            JOptionPane.showMessageDialog(null,"Please select an event.");
        }
    }

    private void updateAccountEvents(){
        var collection = EventController.getInstance().findByAccountIdUnordered(this.accountDto.getId()).stream()
                .map(EventDto::getId).collect(Collectors.toSet());
        this.accountDto.setEventsIds(collection);
        AccountController.getInstance().updateEvents(this.accountDto);
        var listOfEvents = new ArrayList<>(EventController.getInstance().findByAccountIdUnordered(this.accountDto.getId()));
        for (EventDto eventDto : listOfEvents) {
            EventController.getInstance().updateAccount(eventDto, this.accountDto);
        }
    }

    private void notifyEventByReminder(Collection<EventDto> events){
        var listOfEvents = new ArrayList<>(events);
        if(!listOfEvents.isEmpty()) {
            for (EventDto eventDto : listOfEvents) {
                var reminders = new ArrayList<>(ReminderController.getInstance().findByEventId(eventDto.getId()));
                if(!reminders.isEmpty()){
                    for(ReminderDto reminder : reminders){
                        var message = "The event, " + eventDto.getTitle() + ", will start in less than ";
                        var elapsedSeconds = Duration.between(LocalTime.now(),eventDto.getTimestamp()).toSeconds();
                        var elapsedMinutes = Duration.between(LocalTime.now(),eventDto.getTimestamp()).toMinutes();
                        var elapsedHours = Duration.between(LocalTime.now(),eventDto.getTimestamp()).toHours();
                        if(!eventDto.getDate().isBefore(LocalDate.now())) {
                            var reminderHour = reminder.getTime().getHour();
                            var reminderMinute = reminder.getTime().getMinute();
                            var reminderSecond = reminder.getTime().getSecond();
                            if (elapsedHours < reminderHour) {
                                message = message.concat(Integer.valueOf(reminderHour).toString() + " hours ");
                            } else if (elapsedMinutes < reminderMinute) {
                                message = message.concat(Integer.valueOf(reminderMinute).toString() + " minutes ");
                            } else if (elapsedSeconds < reminderSecond) {
                                message = message.concat(Integer.valueOf(reminderMinute).toString() + " seconds ");
                            }
                            JOptionPane.showMessageDialog(null, message);
                        }
                    }
                }
            }
        }
    }
}
