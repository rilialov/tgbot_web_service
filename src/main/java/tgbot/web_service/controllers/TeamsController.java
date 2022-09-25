package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgbot.users.service.GetAllTeamsResponse;
import tgbot.web_service.service.TeamClient;

@Controller
public class TeamsController {

    private final TeamClient teamClient;

    public TeamsController(TeamClient teamClient) {
        this.teamClient = teamClient;
    }

    @GetMapping("/teamsList")
    public String teamsList(Model model) {
        GetAllTeamsResponse allTeams = teamClient.getAllTeams();
        model.addAttribute("teams", allTeams.getTeamsList());
        return "team/teamsList";
    }

}
