package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.web_service.config.ManagementServiceClientsConfiguration;
import tgbot.web_service.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ManagementServiceClientsConfiguration.class)
class TaskClientIT {

    @Autowired
    private TaskClient taskClient;

    @Test
    void getTask() {
        Task task = taskClient.getTask("1");

        assertEquals("Create Service connected to telegram", task.getTaskName());
    }

    @Test
    void getAllTask() {
        List<Task> allTasks = taskClient.getAllTasks();
        System.out.println(allTasks.get(0));
        assertNotNull(allTasks);
        assertTrue(allTasks.size() > 0);
    }


    @Test
    void createTask() {
        Task task = new Task("Test Task Name", "Test Task Note");

        Task created = taskClient.createTask(task);

        assertEquals("Test Task Name", created.getTaskName());
        assertEquals("Test Task Note", created.getTaskNote());
    }

    @Test
    void updateTask() {
        Task task = taskClient.getTask("2");

        task.setTaskNote("Changed Note");
        taskClient.updateTask("2", task);

        Task updated = taskClient.getTask("2");
        assertEquals("Changed Note", updated.getTaskNote());
    }

    @Test
    void deleteTask() {
        taskClient.deleteTask(4);

        Task deleted = taskClient.getTask("4");
        assertNull(deleted);
    }
}