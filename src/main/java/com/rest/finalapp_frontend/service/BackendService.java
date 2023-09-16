package com.rest.finalapp_frontend.service;


import com.rest.finalapp_frontend.domain.PlayerDto;
import com.rest.finalapp_frontend.domain.UserDto;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class BackendService {

    private static BackendService backendService;
    private final RestTemplate restTemplate;
    private String backendApi = "http://localhost:8080/v1/";


    private BackendService() {
        this.restTemplate = new RestTemplate();
    }

    public static BackendService getInstance() {
        if (backendService == null) {
            backendService = new BackendService();
        }
        return backendService;
    }

    /*public boolean authenticateUser(String username) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "users/" + username)
                .build().encode().toUri();
        UserDto userResponse = restTemplate.getForObject(url, UserDto.class);

        System.out.println(userResponse.getName());
        return true;
    }*/

    public UserDto getUser(String username) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "users/" + username)
                .build().encode().toUri();

        try {
            UserDto userResponse = restTemplate.getForObject(url, UserDto.class);
            return userResponse;
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            return null;
        }
    }

    public void createUser(String username) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "users")
                .build().encode().toUri();

        UserDto userDto = new UserDto(null, username, null, null);

        try {
            restTemplate.postForObject(url, userDto, UserDto.class);
            Notification.show("New user created, you can log in now", 3000, Notification.Position.TOP_CENTER);
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
        }
    }

    public void updateUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "users")
                .build().encode().toUri();

        try {
            restTemplate.put(url, userDto);
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
        }
    }

    public PlayerDto getPlayer(Long playerId) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "players/" + playerId)
                .build().encode().toUri();

        try {
            PlayerDto playerResponse = restTemplate.getForObject(url, PlayerDto.class);
            return playerResponse;
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            return null;
        }
    }

    public PlayerDto createPlayer(PlayerDto playerDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "players")
                .build().encode().toUri();

        try {
            PlayerDto createdPlayer = restTemplate.postForObject(url, playerDto, PlayerDto.class);
            return createdPlayer;
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            return null;
        }
    }

    public void updatePlayer(PlayerDto playerDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "players")
                .build().encode().toUri();

        try {
            restTemplate.put(url, playerDto);
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
        }
    }

    public void deletePlayer(Long playerId) {
        URI url = UriComponentsBuilder.fromHttpUrl(this.backendApi + "players/" + playerId)
                .build().encode().toUri();

        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
        }
    }
}
