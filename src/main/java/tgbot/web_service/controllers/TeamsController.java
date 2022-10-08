package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tgbot.users.service.GetAllTeamsResponse;
import tgbot.users.service.GetTeamResponse;
import tgbot.users.service.TeamDTO;
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

    @GetMapping("/createTeam")
    public String createTeam(Model model) {
        model.addAttribute("title", "Create Team");
        model.addAttribute("form", "/saveNewTeam");
        return "team/teamForm";
    }

    @PostMapping("/saveNewTeam")
    public String saveNewTeam(String teamName, String teamColor) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO.setTeamColor(teamColor);
        teamClient.saveTeam(teamDTO);
        return "redirect:/teamsList";
    }

    @GetMapping("/updateTeam")
    public String updateTeam(Long teamID, Model model) {
        model.addAttribute("title", "Update Team");
        model.addAttribute("form", "/saveTeam");
        GetTeamResponse response = teamClient.getTeamById(teamID);
        TeamDTO teamDTO = response.getTeamDTO();
        model.addAttribute("team", teamDTO);
        return "team/teamForm";
    }


}
