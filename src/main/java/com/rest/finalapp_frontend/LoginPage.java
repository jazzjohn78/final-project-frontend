package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.UserDto;
import com.rest.finalapp_frontend.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class LoginPage extends VerticalLayout {

    private MainView mainView;

    private BackendService backendService = BackendService.getInstance();

    private TextField userName = new TextField("Username");
    private Button login = new Button("Log in");
    private Button signUp = new Button("Sign up");

    public LoginPage(MainView mainView) {
        this.mainView = mainView;

        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(login, signUp);
        setAlignItems(Alignment.CENTER);
        add(userName, buttons);

        login.addClickListener(event -> loginUser(userName.getValue()));
        signUp.addClickListener(event -> createUser(userName.getValue()));
    }

    private void loginUser(String username) {
        if (username == "") {
            Notification.show("Please enter username first", 3000, Notification.Position.TOP_CENTER);
            return;
        }
        UserDto user = backendService.getUser(username);
        if (user != null) {
            Notification.show("Logged in as: " + user.getName(), 3000, Notification.Position.TOP_CENTER);
            mainView.executeLogin(user);
        }
    }

    private void createUser(String username) {
        if (username == "") {
            Notification.show("Please enter username first", 3000, Notification.Position.TOP_CENTER);
        }
        backendService.createUser(username);
    }
}
