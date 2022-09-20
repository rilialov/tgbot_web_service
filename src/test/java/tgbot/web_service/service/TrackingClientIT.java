package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.web_service.config.ManagementServiceClientsConfiguration;
import tgbot.web_service.model.Task;
import tgbot.web_service.model.Tracking;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ManagementServiceClientsConfiguration.class)
class TrackingClientIT {

    @Autowired
    private TrackingClient trackingClient;

    @Autowired
    private TaskClient taskClient;

    @Test
    void getTracking() {
        Tracking tracking = trackingClient.getTracking("1");

        assertEquals("Adding connection to users service", tracking.getTrackingNote());
    }

    @Test
    void getAllTracking() {
        List<Tracking> allTracking = trackingClient.getAllTracking();

        assertNotNull(allTracking);
        assertTrue(allTracking.size() > 0);
    }

    @Test
    void createTracking() {
        Task task = taskClient.getTask("1");
        Tracking tracking = new Tracking("Test Note", task, 1L);

        Tracking created = trackingClient.createTracking(tracking);

        assertEquals("Test Note", created.getTrackingNote());
        assertEquals(1L, created.getUser());
    }

    @Test
    void updateTracking() {
        Tracking tracking = trackingClient.getTracking("2");

        tracking.setTrackingNote("Changed Note");
        trackingClient.updateTracking("2", tracking);

        Tracking updated = trackingClient.getTracking("2");
        assertEquals("Changed Note", updated.getTrackingNote());
    }

    @Test
    void deleteTracking() {
        trackingClient.deleteTracking(7L);

        Tracking deleted = trackingClient.getTracking("7");
        assertNull(deleted);
    }
}