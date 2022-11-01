package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgbot.users.service.GetAllUsersResponse;
import tgbot.web_service.service.UserClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    private final UserClient userClient;

    public UsersController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public String usersList(Optional<Integer> page, Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        GetAllUsersResponse response = userClient.getAllUsers(currentPage, pageSize);
        model.addAttribute("users", response.getUsersList());
        model.addAttribute("totalPages", response.getTotalPages());

        List<Integer> pageNumbersList = ControllersUtils.getPageNumbersList(currentPage, response.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbersList);
        model.addAttribute("page", currentPage);

        List<Integer> sizes = Arrays.asList(10, 15, 20);
        model.addAttribute("sizes", sizes);
        model.addAttribute("pageSize", pageSize);
        return "user/usersList";
    }

}
