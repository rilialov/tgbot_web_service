package tgbot.web_service.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import tgbot.web_service.model.Tracking;

import java.util.List;

public class TrackingClient {

    private WebClient webClient;

    private static final String TRACKING_URI = "/trackings/";

    public void setParameters(String address) {
        webClient = WebClient.create(address);
    }

    public Tracking getTracking(String id) {
        return webClient
                .get()
                .uri(TRACKING_URI + id)
                .retrieve()
                .bodyToMono(Tracking.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex))
                .block();
    }

    public List<Tracking> getAllTracking() {
        return webClient
                .get()
                .uri(TRACKING_URI)
                .retrieve()
                .bodyToFlux(Tracking.class)
                .collectList()
                .block();
    }

    public Tracking createTracking(Tracking tracking) {
        return webClient
                .post()
                .uri(TRACKING_URI)
                .body(Mono.just(tracking), Tracking.class)
                .retrieve()
                .bodyToMono(Tracking.class)
                .block();
    }

    public void updateTracking(String id, Tracking tracking) {
        webClient
                .put()
                .uri(TRACKING_URI + id)
                .body(Mono.just(tracking), Tracking.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteTracking(long id) {
        webClient
                .delete()
                .uri(TRACKING_URI + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
