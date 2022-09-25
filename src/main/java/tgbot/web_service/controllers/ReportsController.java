package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgbot.web_service.service.ReportClient;

@Controller
public class ReportsController {

    private final ReportClient reportClient;

    public ReportsController(ReportClient reportClient) {
        this.reportClient = reportClient;
    }

    @GetMapping("/reportsList")
    public String reportsList(Model model) {
        model.addAttribute("reports", reportClient.getAllReports());
        return "report/reportsList";
    }
}
