package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.PlayerRankDto;
import com.rest.finalapp_frontend.domain.PlayerRoleDto;
import com.rest.finalapp_frontend.domain.UserDto;
import com.rest.finalapp_frontend.service.BackendService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route
public class MainView extends VerticalLayout {

    private LoginPage loginPage = new LoginPage(this);
    private HomePage homePage = new HomePage(this);
    private UserDto user;
    private BackendService backendService = BackendService.getInstance();

    public MainView() {
        add(loginPage);
        initRoles();
        initRanks();
    }

    public void executeLogin(UserDto user){
        this.user = user;
        remove(loginPage);
        add(homePage);
        if(user.getPlayerId() != null) {
            homePage.getPlayerForm().loadPlayer(user.getPlayerId());
        }
        if(user.getTeamId() != null) {
            homePage.getTeamManageForm().loadTeam(user.getTeamId());
        }
    }

    public UserDto getUser() {
        return user;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    //administrator dedicated tasks
    private void initRoles() {
        if (backendService.getPlayerRoles().size() == 0) {
            backendService.createPlayerRole(new PlayerRoleDto(null, "In Game Leader"));
            backendService.createPlayerRole(new PlayerRoleDto(null, "Entry Fragger"));
            backendService.createPlayerRole(new PlayerRoleDto(null, "Rifler"));
            backendService.createPlayerRole(new PlayerRoleDto(null, "Sniper"));
            backendService.createPlayerRole(new PlayerRoleDto(null, "Lurker"));
        }
    }

    private void initRanks() {
        if(backendService.getPlayerRanks().size() == 0) {
            backendService.createPlayerRank(new PlayerRankDto(null, "Silver I"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Silver II"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Silver III"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Silver IV"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Silver Elite"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Silver Elite Master"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Gold Nova I"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Gold Nova II"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Gold Nova III"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Gold Nova Master"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Master Guardian I"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Master Guardian II"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Master Guardian Elite"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Distinguished Master Guardian"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Legendary Eagle"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Legendary Eagle Master"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Supreme Master First Class"));
            backendService.createPlayerRank(new PlayerRankDto(null, "Global Elite"));
        }
    }
}
