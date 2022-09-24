package tgbot.web_service.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import tgbot.web_service.model.Report;

import java.util.List;

public class ReportClient {

    private WebClient webClient;

    private final String REPORTS_URI = "/reports/";

    public void setParameters(String address) {
        webClient = WebClient.create(address);
    }

    public Report getReport(String id) {
        return webClient
                .get()
                .uri(REPORTS_URI + id)
                .retrieve()
                .bodyToMono(Report.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex))
                .block();
    }

    public List<Report> getAllReports() {
        return webClient
                .get()
                .uri(REPORTS_URI)
                .retrieve()
                .bodyToMono(Response.class)
                .block()
                .getContent();
    }

    public Report createReport(Report report) {
        return webClient
                .post()
                .uri(REPORTS_URI)
                .body(Mono.just(report), Report.class)
                .retrieve()
                .bodyToMono(Report.class)
                .block();
    }

    public void updateReport(String id, Report report) {
        webClient
                .put()
                .uri(REPORTS_URI + id)
                .body(Mono.just(report), Report.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteReport(long id) {
        webClient
                .delete()
                .uri(REPORTS_URI + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
