package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.PlayerDto;
import com.rest.finalapp_frontend.domain.UserDto;
import com.rest.finalapp_frontend.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PlayerForm extends FormLayout {
    private UserDto userDto;
    private PlayerDto playerDto;
    private BackendService backendService = BackendService.getInstance();

    private TextField playerName = new TextField("Player Name");
    private TextField playerRank = new TextField("Player Rank");
    private TextField playerRole = new TextField("Player Role");
    private TextField playerTeam = new TextField("Player Team");
    private Button edit = new Button("Edit");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    public PlayerForm() {
        this.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
        Span title = new Span("PLAYER INFO");
        title.getElement().getStyle().set("font-size", "20px");
        playerName.setReadOnly(true);
        playerRank.setReadOnly(true);
        playerRole.setReadOnly(true);
        playerTeam.setReadOnly(true);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setVisible(false);
        VerticalLayout fields = new VerticalLayout(playerName, playerRole, playerRank, playerTeam);
        HorizontalLayout buttons = new HorizontalLayout(edit, save, delete);
        add(title, playerName, playerRank, playerRole, playerTeam, buttons);

        edit.addClickListener(event -> editPlayer());
        save.addClickListener(event -> savePlayer());
        delete.addClickListener(event -> deletePlayer());
    }

    public void loadPlayer(UserDto userDto) {
        this.userDto = userDto;
        if(this.userDto.getPlayerId() != null) {
            this.playerDto = backendService.getPlayer(userDto.getPlayerId());
            playerName.setValue(this.playerDto.getName());
            playerRank.setValue(this.playerDto.getRank());
            playerRole.setValue(this.playerDto.getRole());
        }
    }

    private void editPlayer() {
        edit.setVisible(false);
        save.setVisible(true);
        playerName.setReadOnly(false);
        playerRank.setReadOnly(false);
        playerRole.setReadOnly(false);
    }

    private void savePlayer() {
        edit.setVisible(true);
        save.setVisible(false);
        playerName.setReadOnly(true);
        playerRank.setReadOnly(true);
        playerRole.setReadOnly(true);

        if(this.userDto.getPlayerId() == null) {
            PlayerDto createdPlayerDto = backendService.createPlayer(new PlayerDto(
                    null,
                    playerName.getValue(),
                    playerRank.getValue(),
                    playerRole.getValue()
            ));
            this.userDto.setPlayerId(createdPlayerDto.getId());
            backendService.updateUser(this.userDto);
            Notification.show("Player info added", 3000, Notification.Position.TOP_CENTER);
        } else {
            this.playerDto.setName(playerName.getValue());
            this.playerDto.setRank(playerRank.getValue());
            this.playerDto.setRole(playerRole.getValue());
            backendService.updatePlayer(this.playerDto);
            Notification.show("Player info updated", 3000, Notification.Position.TOP_CENTER);
        }
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

        this.userDto.setPlayerId(null);
        backendService.updateUser(this.userDto);
        backendService.deletePlayer(this.playerDto.getId());
        this.playerDto = null;
        Notification.show("Player info deleted", 3000, Notification.Position.TOP_CENTER);
    }
}
