package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgbot.users.service.GetAllTeamsResponse;
import tgbot.users.service.GetTeamResponse;
import tgbot.users.service.TeamDTO;
import tgbot.web_service.service.TeamClient;

@Controller
@RequestMapping(value = "/teams")
public class TeamsController {

    private final TeamClient teamClient;

    public TeamsController(TeamClient teamClient) {
        this.teamClient = teamClient;
    }

    @GetMapping
    public String teamsList(Model model) {
        GetAllTeamsResponse allTeams = teamClient.getAllTeams();
        model.addAttribute("teams", allTeams.getTeamsList());
        return "team/teamsList";
    }

    @GetMapping("/new")
    public String createTeam(Model model) {
        TeamDTO teamDTO = new TeamDTO();
        model.addAttribute("title", "Create Team");
        model.addAttribute("team", teamDTO);
        return "team/teamForm";
    }

    @GetMapping("/edit/{id}")
    public String updateTeam(@PathVariable("id") Long teamID, Model model) {
        GetTeamResponse response = teamClient.getTeamById(teamID);
        TeamDTO teamDTO = response.getTeamDTO();
        model.addAttribute("title", "Update Team");
        model.addAttribute("team", teamDTO);
        return "team/teamForm";
    }

    @PostMapping
    public String saveTeam(TeamDTO teamDTO) {
        teamClient.saveTeam(teamDTO);
        return "redirect:/teams";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable("id") Long teamID) {
        teamClient.deleteTeamById(teamID);
        return "redirect:/teams";
    }
}
