package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.users.service.GetAllTeamsResponse;
import tgbot.users.service.TeamDTO;
import tgbot.web_service.config.UsersServiceClientsConfiguration;

import java.util.List;

@SpringBootTest(classes = UsersServiceClientsConfiguration.class)
class ListToPagesMapConverterTest {

    private static final Logger logger = LoggerFactory.getLogger(ListToPagesMapConverterTest.class);

    @Autowired
    private TeamClient teamClient;

    @Test
    void getTeamsPage() {
        GetAllTeamsResponse allTeamsResponse = teamClient.getAllTeams();
        List<TeamDTO> teamsList = allTeamsResponse.getTeamsList();

        ListToPagesMapConverter<TeamDTO> converter = new ListToPagesMapConverter<>();
        converter.createMap(teamsList, 10);
        logger.info("Total Pages: " + converter.getTotalPages());

        List<TeamDTO> page = converter.getPage(0);
        logger.info("Page 0:");
        for (TeamDTO teamDTO : page) {
            logger.info("Team Name: " + teamDTO.getTeamName());
        }
    }

}