package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.web_service.config.ManagementServiceClientsConfiguration;
import tgbot.web_service.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ManagementServiceClientsConfiguration.class)
class TaskClientIT {

    private static final Logger logger = LoggerFactory.getLogger(TaskClientIT.class);

    @Autowired
    private TaskClient taskClient;

    @Test
    void getTask() {
        Task task = taskClient.getTask("1");

        assertEquals("Create Service connected to telegram", task.getTaskName());
        logger.info(task.toString());
    }

    @Test
    void getAllTask() {
        Response response = taskClient.getAllTasks(0, 10);
        List<Task> allTasks = response.getContent();

        assertNotNull(allTasks);
        assertTrue(allTasks.size() > 0);

        for (int i = 0; i < allTasks.size(); i++) {
            logger.info(String.valueOf(allTasks.get(i)));
        }
    }

    @Test
    void createTask() {
        Response response = taskClient.getAllTasks(0, 10);
        List<Task> allTasks = response.getContent();
        assertNotNull(allTasks);
        for (int i = 0; i < allTasks.size(); i++) {
            logger.info(String.valueOf(allTasks.get(i)));
        }

        Task task = new Task("Test Task Name", "Test Task Note");

        Task created = taskClient.createTask(task);

        assertEquals("Test Task Name", created.getTaskName());
        assertEquals("Test Task Note", created.getTaskNote());

        Response updatedResponse = taskClient.getAllTasks(0, 10);
        List<Task> updatedTasks = updatedResponse.getContent();
        assertNotNull(updatedTasks);
        logger.info("Updated List:");
        for (int i = 0; i < updatedTasks.size(); i++) {
            logger.info(String.valueOf(updatedTasks.get(i)));
        }
    }

    @Test
    void updateTask() {
        Task task = taskClient.getTask("2");
        logger.info(task.toString());

        task.setTaskNote("Changed Note");
        taskClient.updateTask("2", task);

        Task updated = taskClient.getTask("2");
        assertEquals("Changed Note", updated.getTaskNote());

        logger.info("Updated Task:");
        logger.info(updated.toString());
    }

    @Test
    void deleteTask() {
        Response response = taskClient.getAllTasks(0, 10);
        List<Task> allTasks = response.getContent();
        assertNotNull(allTasks);
        for (int i = 0; i < allTasks.size(); i++) {
            logger.info(String.valueOf(allTasks.get(i)));
        }

        taskClient.deleteTask(4);

        Task deleted = taskClient.getTask("4");
        assertNull(deleted);

        Response updatedResponse = taskClient.getAllTasks(0, 10);
        List<Task> updatedTasks = updatedResponse.getContent();
        assertNotNull(updatedTasks);
        logger.info("Updated List:");
        for (int i = 0; i < updatedTasks.size(); i++) {
            logger.info(String.valueOf(updatedTasks.get(i)));
        }
    }
}