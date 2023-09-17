package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.PlayerDto;
import com.rest.finalapp_frontend.domain.TeamDto;
import com.rest.finalapp_frontend.domain.TeamLogDto;
import com.rest.finalapp_frontend.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TeamManageForm extends FormLayout {

    private MainView mainView;
    private TeamDto teamDto;
    private BackendService backendService = BackendService.getInstance();

    private TextField teamName = new TextField("Team Name");
    private TextField teamDescription = new TextField("Team Description");
    private Button edit = new Button("Edit Team");
    private Button save = new Button("Save Team");
    private Button delete = new Button("Delete Team");
    private Grid<PlayerDto> players = new Grid<>(PlayerDto.class);

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
        Span rosterHeader = new Span("Team Roster");
        rosterHeader.getElement().getStyle().set("font-size", "14px");
        players.setColumns("name", "rank", "role");
        add(title, teamName, teamDescription, buttons, rosterHeader, players);

        edit.addClickListener(event -> editTeam());
        save.addClickListener(event -> saveTeam());
        delete.addClickListener(event -> deleteTeam());
    }

    public TeamDto getTeamDto() {
        return teamDto;
    }

    public void loadTeam(Long teamId) {
        teamDto = backendService.getTeam(teamId);
        teamName.setValue(teamDto.getName());
        teamDescription.setValue(teamDto.getDescription());
        refresh();
    }

    private void editTeam() {
        edit.setVisible(false);
        save.setVisible(true);
        teamName.setReadOnly(false);
        teamDescription.setReadOnly(false);
    }

    private void saveTeam() {
        if (teamName.getValue() == "") {
            Notification.show("Team name cannot be empty", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        edit.setVisible(true);
        save.setVisible(false);
        teamName.setReadOnly(true);
        teamDescription.setReadOnly(true);

        if (teamDto == null) {
            teamDto = backendService.createTeam(new TeamDto(
                    null,
                    teamName.getValue(),
                    teamDescription.getValue(),
                    new ArrayList<>()
            ));
            mainView.getUser().setTeamId(teamDto.getId());
            backendService.updateUser(mainView.getUser());
            backendService.createTeamLog(new TeamLogDto(null, teamDto.getId(), "create", "Team created [name: " + teamDto.getName() + ", description: " + teamDto.getDescription() + "]"));
            Notification.show("Team created", 3000, Notification.Position.TOP_CENTER);
        } else {
            teamDto.setName(teamName.getValue());
            teamDto.setDescription(teamDescription.getValue());
            backendService.updateTeam(teamDto);
            backendService.createTeamLog(new TeamLogDto(null, teamDto.getId(), "update", "Team updated [name: " + teamDto.getName() + ", description: " + teamDto.getDescription() + "]"));
            Notification.show("Team info updated", 3000, Notification.Position.TOP_CENTER);
        }
        mainView.getHomePage().getPlayerForm().refresh();
    }

    private void deleteTeam() {
        teamName.setValue("");
        teamDescription.setValue("");
        edit.setVisible(true);
        save.setVisible(false);
        teamName.setReadOnly(true);
        teamDescription.setReadOnly(true);

        mainView.getUser().setTeamId(null);
        backendService.updateUser(mainView.getUser());
        backendService.deleteTeam(teamDto.getId());
        backendService.createTeamLog(new TeamLogDto(null, teamDto.getId(), "delete", "Team deleted [name: " + teamDto.getName() + ", description: " + teamDto.getDescription() + "]"));
        teamDto = null;
        Notification.show("Team deleted", 3000, Notification.Position.TOP_CENTER);
        mainView.getHomePage().getPlayerForm().refresh();
    }

    public void refresh() {
        players.setItems(teamDto.getPlayersIds().stream()
                .map(playerId -> backendService.getPlayer(playerId))
                .collect(Collectors.toList())
        );
    }
}
