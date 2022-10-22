package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgbot.web_service.model.Task;
import tgbot.web_service.service.Response;
import tgbot.web_service.service.TaskClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/tasks")
public class TasksController {

    private final TaskClient taskClient;

    public TasksController(TaskClient taskClient) {
        this.taskClient = taskClient;
    }

    @GetMapping
    public String tasksList(Integer page, Integer size, Model model) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Response response = taskClient.getAllTasks(page-1, size);
        model.addAttribute("tasks", response.getContent());
        model.addAttribute("totalPages", response.getTotalPages());
        List<Integer> pageNumbers;
        if (page < response.getTotalPages()) {
            if (page > 1) {
                pageNumbers = IntStream.rangeClosed(page - 1, page + 1).boxed().collect(Collectors.toList());
            } else pageNumbers = IntStream.rangeClosed(page, page + 1).boxed().collect(Collectors.toList());
        } else {
            if (response.getTotalPages() == 1) {
                pageNumbers = IntStream.rangeClosed(page, page).boxed().collect(Collectors.toList());
            } else pageNumbers = IntStream.rangeClosed(page - 1, page).boxed().collect(Collectors.toList());
        }
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("page", page);
        List<Integer> sizes = Arrays.asList(10, 15, 20);
        model.addAttribute("sizes", sizes);
        model.addAttribute("pageSize", size);
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
