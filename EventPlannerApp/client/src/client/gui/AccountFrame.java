package client.gui;

import client.controller.AccountController;
import client.controller.EventController;
import client.controller.ReminderController;
import lib.dto.AccountDto;
import lib.dto.ReminderDto;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AccountFrame extends JFrame{
    private JTextField usernameField;
    private JButton removeButton;
    private JButton logInButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private JButton createAccountButton;

    public AccountFrame(){
        logInButton.addActionListener(e->logIn());
        removeButton.addActionListener(e->removeAccount());
        createAccountButton.addActionListener(e-> createAccount());
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createAccount(){
        var username = usernameField.getText();
        var password = Arrays.toString(passwordField.getPassword());
        var existingAccount = AccountController.getInstance().findByUsernameAndPassword(username,password);
        if(existingAccount.isEmpty()){
            var newAccount = new AccountDto(
                    username,
                    password,
                    Collections.emptySet()
            );
            AccountController.getInstance().persist(newAccount);
            JOptionPane.showMessageDialog(null,"Account created. Please, log in.");
        }else{
            JOptionPane.showMessageDialog(null,"Username or password already exist.");
        }
        usernameField.setText("");
        passwordField.setText("");
    }

    private void logIn(){
        var username = usernameField.getText();
        var password = Arrays.toString(passwordField.getPassword());
        var existingAccount = AccountController.getInstance().findByUsernameAndPassword(username,password);
        if(existingAccount.isEmpty()){
            JOptionPane.showMessageDialog(null,"Username or password are incorrect.");

        }else{
            var account = existingAccount.stream().findFirst().get();
            new EventFrame(account);
        }
        usernameField.setText("");
        passwordField.setText("");
    }

    private void removeAccount(){
        var username = usernameField.getText();
        var password = Arrays.toString(passwordField.getPassword());
        var existingAccount = AccountController.getInstance().findByUsernameAndPassword(username,password);
        if(!existingAccount.isEmpty()){
            var account = existingAccount.stream().findFirst().get();
            var events = new ArrayList<>(EventController.getInstance().findByAccountIdUnordered(account.getId()));
            if(!events.isEmpty()) {
                for (lib.dto.EventDto event : events) {
                    var reminders = new ArrayList<>(ReminderController.getInstance().findByEventId(event.getId()));
                    if(!reminders.isEmpty()){
                        for(ReminderDto reminder : reminders){
                            ReminderController.getInstance().remove(reminder);
                        }
                    }
                    EventController.getInstance().remove(event);
                }
            }
            AccountController.getInstance().remove(account);
            JOptionPane.showMessageDialog(null,"Account removed successfully.");
        }else{
            JOptionPane.showMessageDialog(null,"Cannot remove account. Wrong username or password.");
        }
        usernameField.setText("");
        passwordField.setText("");
    }
}
