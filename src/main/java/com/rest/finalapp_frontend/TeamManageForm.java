package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.TeamDto;
import com.rest.finalapp_frontend.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TeamManageForm extends FormLayout {

    private MainView mainView;
    private TeamDto teamDto;
    private BackendService backendService = BackendService.getInstance();

    private TextField teamName = new TextField("Team Name");
    private TextField teamDescription = new TextField("Team Description");
    private Button edit = new Button("Edit Team");
    private Button save = new Button("Save Team");
    private Button delete = new Button("Delete Team");

    public TeamManageForm(MainView mainView) {
        this.mainView = mainView;

        this.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
        Span title = new Span("TEAM MANAGEMENT");
        title.getElement().getStyle().set("font-size", "20px");
        teamName.setReadOnly(true);
        teamDescription.setReadOnly(true);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setVisible(false);
        HorizontalLayout buttons = new HorizontalLayout(edit, save, delete);
        add(title, teamName, teamDescription, buttons);

        edit.addClickListener(event -> editTeam());
        save.addClickListener(event -> saveTeam());
        delete.addClickListener(event -> deleteTeam());
    }

    public void loadTeam(Long teamId) {
        this.teamDto = backendService.getTeam(teamId);
        teamName.setValue(teamDto.getName());
        teamDescription.setValue(teamDto.getDescription());
    }

    private void editTeam() {
        edit.setVisible(false);
        save.setVisible(true);
        teamName.setReadOnly(false);
        teamDescription.setReadOnly(false);
    }

    private void saveTeam() {
        edit.setVisible(true);
        save.setVisible(false);
        teamName.setReadOnly(true);
        teamDescription.setReadOnly(true);

        if (this.teamDto == null) {
            this.teamDto = backendService.createTeam(new TeamDto(
                    null,
                    teamName.getValue(),
                    teamDescription.getValue()
            ));
            this.mainView.getUser().setTeamId(this.teamDto.getId());
            backendService.updateUser(this.mainView.getUser());
            Notification.show("Team created", 3000, Notification.Position.TOP_CENTER);
        } else {
            this.teamDto.setName(teamName.getValue());
            this.teamDto.setDescription(teamDescription.getValue());
            backendService.updateTeam(this.teamDto);
            Notification.show("Team info updated", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void deleteTeam() {
        teamName.setValue("");
        teamDescription.setValue("");
        edit.setVisible(true);
        save.setVisible(false);
        teamName.setReadOnly(true);
        teamDescription.setReadOnly(true);

        this.mainView.getUser().setTeamId(null);
        backendService.updateUser(this.mainView.getUser());
        backendService.deleteTeam(this.teamDto.getId());
        this.teamDto = null;
        Notification.show("Team deleted", 3000, Notification.Position.TOP_CENTER);
    }
}
