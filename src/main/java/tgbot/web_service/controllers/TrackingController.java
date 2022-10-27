package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgbot.web_service.service.Response;
import tgbot.web_service.service.TrackingClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/tracking")
public class TrackingController {

    private final TrackingClient trackingClient;

    public TrackingController(TrackingClient trackingClient) {
        this.trackingClient = trackingClient;
    }

    @GetMapping
    public String trackingList(Optional<Integer> page, Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Response response = trackingClient.getAllTracking(currentPage-1, pageSize);
        model.addAttribute("trackings", response.getContent());
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
        return "tracking/trackingList";
    }
}
