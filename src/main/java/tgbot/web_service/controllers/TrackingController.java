package tgbot.web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgbot.web_service.service.TrackingClient;

@Controller
public class TrackingController {

    private final TrackingClient trackingClient;

    public TrackingController(TrackingClient trackingClient) {
        this.trackingClient = trackingClient;
    }

    @GetMapping("/trackingList")
    public String trackingList(Model model) {
        model.addAttribute("trackings", trackingClient.getAllTracking());
        return "tracking/trackingList";
    }
}
