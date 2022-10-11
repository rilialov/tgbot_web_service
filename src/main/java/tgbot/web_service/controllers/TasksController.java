package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgbot.web_service.model.Task;
import tgbot.web_service.service.TaskClient;

@Controller
@RequestMapping(value = "/tasks")
public class TasksController {

    private final TaskClient taskClient;

    public TasksController(TaskClient taskClient) {
        this.taskClient = taskClient;
    }

    @GetMapping
    public String tasksList(Model model) {
        model.addAttribute("tasks", taskClient.getAllTasks());
        return "task/tasksList";
    }

    @GetMapping("/new")
    public String createTask(Model model) {
        Task task = new Task();
        model.addAttribute("title", "Create Task");
        model.addAttribute("task", task);
        return "task/taskForm";
    }

    @GetMapping("/edit/{id}")
    public String updateTask(@PathVariable("id") Long taskID, Model model) {
        Task task = taskClient.getTask(String.valueOf(taskID));
        model.addAttribute("title", "Update Task");
        model.addAttribute("task", task);
        return "task/taskForm";
    }

    @PostMapping
    public String saveTask(Task task, Long id) {
        if (id == 0) {
            taskClient.createTask(task);
        } else {
            taskClient.updateTask(String.valueOf(id), task);
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long taskID) {
        taskClient.deleteTask(taskID);
        return "redirect:/tasks";
    }
}
