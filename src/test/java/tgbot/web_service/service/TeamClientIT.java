package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.users.service.GetAllTeamsResponse;
import tgbot.users.service.GetBooleanResponse;
import tgbot.users.service.GetTeamResponse;
import tgbot.users.service.TeamDTO;
import tgbot.web_service.config.UsersServiceClientsConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UsersServiceClientsConfiguration.class)
class TeamClientIT {

    @Autowired
    private TeamClient teamClient;

    @Test
    void getTeamById() {
        GetTeamResponse teamResponse = teamClient.getTeamById(1L);
        TeamDTO teamDTO = teamResponse.getTeamDTO();

        assertEquals("Admin Team", teamDTO.getTeamName());
        assertEquals("Grey", teamDTO.getTeamColor());
    }

    @Test
    void getAllTeams() {
        GetAllTeamsResponse allTeamsResponse = teamClient.getAllTeams();
        List<TeamDTO> teamsList = allTeamsResponse.getTeamsList();

        assertNotNull(teamsList);
        assertTrue(teamsList.size() > 0);
    }

    @Test
    void saveAndDeleteTeam() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName("New team");
        teamDTO.setTeamColor("New color");

        GetTeamResponse teamResponse = teamClient.saveTeam(teamDTO);
        TeamDTO saved = teamResponse.getTeamDTO();

        assertEquals("New team", saved.getTeamName());
        assertEquals("New color", saved.getTeamColor());

        GetBooleanResponse booleanResponse = teamClient.deleteTeamById(saved.getId());
        assertTrue(booleanResponse.isDeleted());
    }
}