package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.PlayerDto;
import com.rest.finalapp_frontend.domain.PlayerLogDto;
import com.rest.finalapp_frontend.domain.TeamDto;
import com.rest.finalapp_frontend.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Date;

public class PlayerForm extends FormLayout {

    private MainView mainView;
    private PlayerDto playerDto;
    private BackendService backendService = BackendService.getInstance();

    private TextField playerName = new TextField("Player Name");
    private ComboBox<String> playerRank = new ComboBox<>("Player Rank");
    private ComboBox<String> playerRole = new ComboBox<>("Player Role");
    private TextField playerTeam = new TextField("Player Team");
    private Button edit = new Button("Edit");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button joinMyTeam = new Button("Join My Team");
    private Button findTeam = new Button("Find a Team");
    private Button leaveTeam = new Button("Leave this Team");
    private Span teamListHeader = new Span("Select a team to join it");
    private Grid<TeamDto> teams = new Grid<>(TeamDto.class);
    HorizontalLayout buttons = new HorizontalLayout(edit, save, delete);
    HorizontalLayout teamButtons = new HorizontalLayout(joinMyTeam, findTeam, leaveTeam);
    VerticalLayout teamList = new VerticalLayout(teamListHeader, teams);

    public PlayerForm(MainView mainView) {
        this.mainView = mainView;

        this.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
        Span title = new Span("PLAYER INFO");
        title.getElement().getStyle().set("font-size", "20px");
        playerName.setReadOnly(true);
        playerRank.setReadOnly(true);
        playerRole.setReadOnly(true);
        playerTeam.setReadOnly(true);
        playerRank.setItems(backendService.getPlayerRanks().stream()
                .map(playerRankDto -> playerRankDto.getName()));
        playerRole.setItems(backendService.getPlayerRoles().stream()
                .map(playerRoleDto -> playerRoleDto.getName()));
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setVisible(false);
        joinMyTeam.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        findTeam.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        teamListHeader.getElement().getStyle().set("font-size", "14px");
        teams.setColumns("name", "description");
        teamList.setVisible(false);
        add(title, playerName, playerRank, playerRole, playerTeam, buttons, teamButtons, teamList);
        refresh();

        edit.addClickListener(event -> editPlayer());
        save.addClickListener(event -> savePlayer());
        delete.addClickListener(event -> deletePlayer());
        joinMyTeam.addClickListener(event -> addToMyTeam());
        findTeam.addClickListener(event -> showTeams());
        leaveTeam.addClickListener(event -> leaveThisTeam());
        teams.asSingleSelect().addValueChangeListener(event -> addToTeam(teams.asSingleSelect().getValue()));
    }

    public void loadPlayer(Long playerId) {
        playerDto = backendService.getPlayer(playerId);
        playerName.setValue(playerDto.getName());
        playerRank.setValue(playerDto.getRank());
        playerRole.setValue(playerDto.getRole());
        refresh();
    }

    private void editPlayer() {
        edit.setVisible(false);
        save.setVisible(true);
        playerName.setReadOnly(false);
        playerRank.setReadOnly(false);
        playerRole.setReadOnly(false);
    }

    private void savePlayer() {
        if (playerName.getValue() == "") {
            Notification.show("Player name cannot be empty", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        edit.setVisible(true);
        save.setVisible(false);
        playerName.setReadOnly(true);
        playerRank.setReadOnly(true);
        playerRole.setReadOnly(true);

        if(playerDto == null) {
            playerDto = backendService.createPlayer(new PlayerDto(
                    null,
                    playerName.getValue(),
                    playerRank.getValue(),
                    playerRole.getValue(),
                    null
            ));
            mainView.getUser().setPlayerId(playerDto.getId());
            backendService.updateUser(mainView.getUser());
            backendService.createPlayerLog(new PlayerLogDto(null, new Date(), playerDto.getId(), "create", "Player created [name: " + playerDto.getName() + ", rank: " + playerDto.getRank() + ", role: " + playerDto.getRole() + "]"));
            Notification.show("Player info added", 3000, Notification.Position.TOP_CENTER);
        } else {
            playerDto.setName(playerName.getValue());
            playerDto.setRank(playerRank.getValue());
            playerDto.setRole(playerRole.getValue());
            backendService.updatePlayer(playerDto);
            backendService.createPlayerLog(new PlayerLogDto(null, new Date(), playerDto.getId(), "update", "Player updated [name: " + playerDto.getName() + ", rank: " + playerDto.getRank() + ", role: " + playerDto.getRole() + "]"));
            Notification.show("Player info updated", 3000, Notification.Position.TOP_CENTER);
        }
        refresh();
    }

    private void deletePlayer() {
        playerName.setValue("");
        playerRank.setValue("");
        playerRole.setValue("");
        edit.setVisible(true);
        save.setVisible(false);
        playerName.setReadOnly(true);
        playerRank.setReadOnly(true);
        playerRole.setReadOnly(true);

        mainView.getUser().setPlayerId(null);
        backendService.updateUser(mainView.getUser());
        backendService.deletePlayer(playerDto.getId());
        backendService.createPlayerLog(new PlayerLogDto(null, new Date(), playerDto.getId(), "delete", "Player deleted [name: " + playerDto.getName() + ", rank: " + playerDto.getRank() + ", role: " + playerDto.getRole() + "]"));
        playerDto = null;
        Notification.show("Player info deleted", 3000, Notification.Position.TOP_CENTER);
        refresh();
    }

    private void addToMyTeam() {
        playerDto.setTeamId(mainView.getUser().getTeamId());
        mainView.getHomePage().getTeamManageForm().getTeamDto().getPlayersIds().add(playerDto.getId());
        backendService.updateTeam(mainView.getHomePage().getTeamManageForm().getTeamDto());
        Notification.show("Your player has been added to your team", 3000, Notification.Position.TOP_CENTER);
        refresh();
        mainView.getHomePage().getTeamManageForm().refresh();
    }

    private void addToTeam(TeamDto team) {
        playerDto.setTeamId(team.getId());
        team.getPlayersIds().add(playerDto.getId());
        backendService.updateTeam(team);
        teamList.setVisible(false);
        Notification.show("Your player has been added to selected team", 3000, Notification.Position.TOP_CENTER);
        refresh();
        if (mainView.getUser().getTeamId() != null) {
            mainView.getHomePage().getTeamManageForm().loadTeam(mainView.getUser().getTeamId());
        }
    }

    public void showTeams() {
        teamList.setVisible(true);
        teams.setItems(backendService.getTeams());
    }

    private void leaveThisTeam() {
        playerDto.setTeamId(null);
        backendService.updatePlayer(playerDto);
        Notification.show("Your player has left the team", 3000, Notification.Position.TOP_CENTER);
        refresh();
        if (mainView.getUser().getTeamId() != null) {
            mainView.getHomePage().getTeamManageForm().loadTeam(mainView.getUser().getTeamId());
        }
    }

    public void refresh() {
        if (playerDto == null) {
            delete.setVisible(false);
            teamButtons.setVisible(false);
        } else {
            delete.setVisible(true);
            teamButtons.setVisible(true);
            if (playerDto.getTeamId() == null) {
                playerTeam.setValue("");
                if (mainView.getUser().getTeamId() == null) {
                    joinMyTeam.setVisible(false);
                } else {
                    joinMyTeam.setVisible(true);
                }
                findTeam.setVisible(true);
                leaveTeam.setVisible(false);
            } else {
                playerTeam.setValue(backendService.getTeam(playerDto.getTeamId()).getName());
                joinMyTeam.setVisible(false);
                findTeam.setVisible(false);
                leaveTeam.setVisible(true);
            }
        }
    }
}
