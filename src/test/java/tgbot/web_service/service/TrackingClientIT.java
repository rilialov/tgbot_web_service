package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.web_service.config.ManagementServiceClientsConfiguration;
import tgbot.web_service.model.Task;
import tgbot.web_service.model.Tracking;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ManagementServiceClientsConfiguration.class)
class TrackingClientIT {

    private static final Logger logger = LoggerFactory.getLogger(TrackingClientIT.class);

    @Autowired
    private TrackingClient trackingClient;

    @Autowired
    private TaskClient taskClient;

    @Test
    void getTracking() {
        Tracking tracking = trackingClient.getTracking("1");

        assertEquals("Adding connection to users service", tracking.getTrackingNote());

        logger.info(tracking.toString());
    }

    @Test
    void getAllTracking() {
        List<Tracking> allTracking = trackingClient.getAllTracking();

        assertNotNull(allTracking);
        assertTrue(allTracking.size() > 0);

        for (int i = 0; i < allTracking.size(); i++) {
            logger.info(String.valueOf(allTracking.get(i)));
        }
    }

    @Test
    void createTracking() {
        List<Tracking> allTracking = trackingClient.getAllTracking();
        assertNotNull(allTracking);
        for (int i = 0; i < allTracking.size(); i++) {
            logger.info(String.valueOf(allTracking.get(i)));
        }

        Task task = taskClient.getTask("1");
        Tracking tracking = new Tracking("Test Note", task, 1L);

        Tracking created = trackingClient.createTracking(tracking);

        assertEquals("Test Note", created.getTrackingNote());
        assertEquals(1L, created.getUser());

        List<Tracking> updatedTracking = trackingClient.getAllTracking();
        assertNotNull(updatedTracking);
        logger.info("Updated List:");
        for (int i = 0; i < updatedTracking.size(); i++) {
            logger.info(String.valueOf(updatedTracking.get(i)));
        }
    }

    @Test
    void updateTracking() {
        Tracking tracking = trackingClient.getTracking("2");
        logger.info(tracking.toString());

        tracking.setTrackingNote("Changed Note");
        trackingClient.updateTracking("2", tracking);

        Tracking updated = trackingClient.getTracking("2");
        assertEquals("Changed Note", updated.getTrackingNote());

        logger.info("Updated Tracking:");
        logger.info(updated.toString());
    }

    @Test
    void deleteTracking() {
        List<Tracking> allTracking = trackingClient.getAllTracking();
        assertNotNull(allTracking);
        for (int i = 0; i < allTracking.size(); i++) {
            logger.info(String.valueOf(allTracking.get(i)));
        }

        trackingClient.deleteTracking(7L);

        Tracking deleted = trackingClient.getTracking("7");
        assertNull(deleted);

        List<Tracking> updatedTracking = trackingClient.getAllTracking();
        assertNotNull(updatedTracking);
        logger.info("Updated List:");
        for (int i = 0; i < updatedTracking.size(); i++) {
            logger.info(String.valueOf(updatedTracking.get(i)));
        }
    }
}