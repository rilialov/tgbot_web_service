package tgbot.web_service.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import tgbot.web_service.model.Task;

import java.util.List;

public class TaskClient {

    private WebClient webClient;

    private static final String TASKS_URI = "/tasks/";

    public void setParameters(String address) {
        webClient = WebClient.create(address);
    }

    public Task getTask(String id) {
        return webClient
                .get()
                .uri(TASKS_URI + id)
                .retrieve()
                .bodyToMono(Task.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex))
                .block();
    }

    public List<Task> getAllTasks() {
        return webClient
                .get()
                .uri(TASKS_URI)
                .retrieve()
                .bodyToFlux(Task.class)
                .collectList()
                .block();
    }

    public Task createTask(Task task) {
        return webClient
                .post()
                .uri(TASKS_URI)
                .body(Mono.just(task), Task.class)
                .retrieve()
                .bodyToMono(Task.class)
                .block();
    }

    public void updateTask(String id, Task task) {
        webClient
                .put()
                .uri(TASKS_URI + id)
                .body(Mono.just(task), Task.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteTask(long id) {
        webClient
                .delete()
                .uri(TASKS_URI + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
