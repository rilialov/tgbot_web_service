package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgbot.web_service.model.Task;
import tgbot.web_service.service.TaskClient;

import java.util.List;

@Controller
public class TasksController {

    private final TaskClient taskClient;

    public TasksController(TaskClient taskClient) {
        this.taskClient = taskClient;
    }

    @GetMapping("/tasksList")
    public String tasksList(Model model) {
        List<Task> allTasks = taskClient.getAllTasks();
        for (Task task : allTasks) {
            System.out.println(task.getTaskName());
        }
        model.addAttribute("tasks", taskClient.getAllTasks());
        return "task/tasksList";
    }
}
