package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.web_service.config.ManagementServiceClientsConfiguration;
import tgbot.web_service.model.Report;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ManagementServiceClientsConfiguration.class)
class ReportClientIT {

    private static final Logger logger = LoggerFactory.getLogger(ReportClientIT.class);

    @Autowired
    private ReportClient reportClient;

    @Test
    void getReport() {
        Report report = reportClient.getReport("2");

        assertEquals(864000L, report.getFullTime());

        logger.info(report.toString());
    }

    @Test
    void getAllReports() {
        List<Report> allReports = reportClient.getAllReports();

        assertNotNull(allReports);
        assertTrue(allReports.size() > 0);

        for (int i = 0; i < allReports.size(); i++) {
            logger.info(String.valueOf(allReports.get(i)));
        }
    }

    @Test
    void createReport() {
        List<Report> allReports = reportClient.getAllReports();
        assertNotNull(allReports);
        for (int i = 0; i < allReports.size(); i++) {
            logger.info(String.valueOf(allReports.get(i)));
        }

        Report report = new Report(LocalDate.now(), 2L);

        Report created = reportClient.createReport(report);

        assertEquals(2L, created.getUser());

        List<Report> updatedReports = reportClient.getAllReports();
        assertNotNull(updatedReports);
        logger.info("Updated List:");
        for (int i = 0; i < updatedReports.size(); i++) {
            logger.info(String.valueOf(updatedReports.get(i)));
        }
    }

    @Test
    void updateReport() {
        Report report = reportClient.getReport("1");
        logger.info(report.toString());

        report.setFullTime(888000L);
        reportClient.updateReport("1", report);

        Report updated = reportClient.getReport("1");
        assertEquals(888000L, updated.getFullTime());

        logger.info("Updated Report:");
        logger.info(updated.toString());
    }

    @Test
    void deleteReport() {
        List<Report> allReports = reportClient.getAllReports();
        assertNotNull(allReports);
        for (int i = 0; i < allReports.size(); i++) {
            logger.info(String.valueOf(allReports.get(i)));
        }

        reportClient.deleteReport(10L);

        Report deleted = reportClient.getReport("10");
        assertNull(deleted);

        List<Report> updatedReports = reportClient.getAllReports();
        assertNotNull(updatedReports);
        logger.info("Updated List:");
        for (int i = 0; i < updatedReports.size(); i++) {
            logger.info(String.valueOf(updatedReports.get(i)));
        }
    }
}