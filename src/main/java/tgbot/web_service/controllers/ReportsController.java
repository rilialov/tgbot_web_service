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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<Integer> pageNumbers;
        if (currentPage < response.getTotalPages()) {
            if (currentPage > 1) {
                pageNumbers = IntStream.rangeClosed(currentPage - 1, currentPage + 1).boxed().collect(Collectors.toList());
            } else pageNumbers = IntStream.rangeClosed(currentPage, currentPage + 1).boxed().collect(Collectors.toList());
        } else {
            if (response.getTotalPages() == 1) {
                pageNumbers = IntStream.rangeClosed(currentPage, currentPage).boxed().collect(Collectors.toList());
            } else pageNumbers = IntStream.rangeClosed(currentPage - 1, currentPage).boxed().collect(Collectors.toList());
        }
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("page", currentPage);
        List<Integer> sizes = Arrays.asList(10, 15, 20);
        model.addAttribute("sizes", sizes);
        model.addAttribute("pageSize", pageSize);
        return "report/reportsList";
    }
}
