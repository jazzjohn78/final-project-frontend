package com.rest.finalapp_frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class HomePage extends VerticalLayout {

    public PlayerForm playerForm = new PlayerForm();


    public HomePage() {
        add(playerForm);
    }

    public PlayerForm getPlayerForm() {
        return playerForm;
    }
}
