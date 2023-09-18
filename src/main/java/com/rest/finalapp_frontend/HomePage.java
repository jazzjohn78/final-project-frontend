package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.service.SkinForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class HomePage extends VerticalLayout {

    private MainView mainView;
    private PlayerForm playerForm;
    private TeamManageForm teamManageForm;
    private SkinForm skinForm;


    public HomePage(MainView mainView) {
        this.mainView = mainView;
        this.playerForm = new PlayerForm(mainView);
        this.teamManageForm = new TeamManageForm(mainView);
        this.skinForm = new SkinForm();

        add(playerForm, teamManageForm, skinForm);
    }

    public PlayerForm getPlayerForm() {
        return playerForm;
    }

    public TeamManageForm getTeamManageForm() {
        return teamManageForm;
    }
}
