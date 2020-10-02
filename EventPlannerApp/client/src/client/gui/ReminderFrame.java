package client.gui;

import client.controller.EventController;
import client.controller.ReminderController;
import lib.dto.EventDto;
import lib.dto.ReminderDto;

import javax.swing.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReminderFrame extends JFrame{
    private JList remindersList;
    private JButton removeReminderButton;
    private JTextField hourField;
    private JTextField minuteField;
    private JTextField secondField;
    private JButton addReminderButton;
    private JLabel eventLabel;
    private JLabel addReminderLabel;
    private JLabel timeLabel;
    private JLabel hourLabel;
    private JLabel minuteLabel;
    private JLabel secondLabel;
    private JLabel eventTitleLabel;
    private JPanel mainPanel;
    private EventDto eventDto;
    private DefaultListModel<ReminderDto> listModel;

    public ReminderFrame(EventDto eventDto){
        this.eventDto = eventDto;
        listModel = new DefaultListModel<>();
        remindersList.setModel(listModel);
        printReminders();
        eventTitleLabel.setText(this.eventDto.getTitle());
        hourField.setText("0");
        minuteField.setText("0");
        secondField.setText("0");
        addReminderButton.addActionListener(e->createReminder());
        removeReminderButton.addActionListener(e->removeReminder());
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void printReminders(){
        updateEventAndReminders();
        var reminders = ReminderController.getInstance().findByEventId(this.eventDto.getId());
        listModel.clear();
        reminders.forEach(listModel::addElement);
    }

    private void createReminder(){
        var hour = Integer.parseInt(hourField.getText());
        var minute = Integer.parseInt(minuteField.getText());
        var second = Integer.parseInt(secondField.getText());
        var time = LocalTime.of(hour,minute,second);
        var newReminder = new ReminderDto(
                time,
                this.eventDto
        );
        ReminderController.getInstance().persist(newReminder);
        printReminders();
        hourField.setText("0");
        minuteField.setText("0");
        secondField.setText("0");
    }

    private void removeReminder(){
        ReminderDto selectedReminder = (ReminderDto)remindersList.getSelectedValue();
        if(selectedReminder != null){
            ReminderController.getInstance().remove(selectedReminder);
            printReminders();
        }else{
            JOptionPane.showMessageDialog(null,"Please select a reminder.");
        }
    }

    private void updateEventAndReminders(){
        var collection = ReminderController.getInstance().findByEventId(this.eventDto.getId()).stream()
                .map(ReminderDto::getId).collect(Collectors.toSet());
        this.eventDto.setRemindersIds(collection);
        EventController.getInstance().updateReminders(this.eventDto);
        var listOfReminders = new ArrayList<>(ReminderController.getInstance().findByEventId(this.eventDto.getId()));
        for(ReminderDto reminderDto : listOfReminders){
            ReminderController.getInstance().updateEvent(reminderDto,this.eventDto);
        }
    }
}
