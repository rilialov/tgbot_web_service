package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgbot.users.service.GetAllUsersResponse;
import tgbot.web_service.service.UserClient;

@Controller
public class UsersController {

    private final UserClient userClient;

    public UsersController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/usersList")
    public String usersList(Model model) {
        GetAllUsersResponse allUsersResponse = userClient.getAllUsers();
        model.addAttribute("users", allUsersResponse.getUsersList());
        return "user/usersList";
    }

}
