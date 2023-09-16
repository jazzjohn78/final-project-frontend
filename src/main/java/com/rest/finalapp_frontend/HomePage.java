package com.rest.finalapp_frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class HomePage extends VerticalLayout {

    private MainView mainView;
    private PlayerForm playerForm;
    private TeamManageForm teamManageForm;


    public HomePage(MainView mainView) {
        this.mainView = mainView;
        this.playerForm = new PlayerForm(mainView);
        this.teamManageForm = new TeamManageForm(mainView);

        add(playerForm, teamManageForm);
    }

    public PlayerForm getPlayerForm() {
        return playerForm;
    }

    public TeamManageForm getTeamManageForm() {
        return teamManageForm;
    }
}
