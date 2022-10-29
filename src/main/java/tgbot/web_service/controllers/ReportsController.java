package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgbot.web_service.service.ReportClient;
import tgbot.web_service.service.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/reports")
public class ReportsController {

    private final ReportClient reportClient;

    public ReportsController(ReportClient reportClient) {
        this.reportClient = reportClient;
    }

    @GetMapping
    public String reportsList(Optional<Integer> page, Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Response response = reportClient.getAllReports(currentPage-1, pageSize);
        model.addAttribute("reports", response.getContent());
        model.addAttribute("totalPages", response.getTotalPages());

        List<Integer> pageNumbersList = ControllersUtils.getPageNumbersList(currentPage, response.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbersList);
        model.addAttribute("page", currentPage);

        List<Integer> sizes = Arrays.asList(10, 15, 20);
        model.addAttribute("sizes", sizes);
        model.addAttribute("pageSize", pageSize);
        return "report/reportsList";
    }
}
