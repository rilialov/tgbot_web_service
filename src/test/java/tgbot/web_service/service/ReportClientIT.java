package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.web_service.config.ManagementServiceClientsConfiguration;
import tgbot.web_service.model.Report;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ManagementServiceClientsConfiguration.class)
class ReportClientIT {

    @Autowired
    private ReportClient reportClient;

    @Test
    void getReport() {
        Report report = reportClient.getReport("2");

        assertEquals(864000L, report.getFullTime());
    }

    @Test
    void getAllReports() {
        List<Report> allReports = reportClient.getAllReports();

        assertNotNull(allReports);
        assertTrue(allReports.size() > 0);
    }

    @Test
    void createReport() {
        Report report = new Report(LocalDate.now(), 2L);

        Report created = reportClient.createReport(report);

        assertEquals(2L, created.getUser());
    }

    @Test
    void updateReport() {
        Report report = reportClient.getReport("1");

        report.setFullTime(888000L);
        reportClient.updateReport("1", report);

        Report updated = reportClient.getReport("1");
        assertEquals(888000L, updated.getFullTime());
    }

    @Test
    void deleteReport() {
        reportClient.deleteReport(10L);

        Report deleted = reportClient.getReport("10");
        assertNull(deleted);
    }
}