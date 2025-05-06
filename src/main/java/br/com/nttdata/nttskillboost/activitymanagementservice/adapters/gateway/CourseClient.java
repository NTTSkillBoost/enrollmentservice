package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class CourseClient {

    private final WebClient webClient;

    public CourseClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8882/api/v1/courses").build();
    }

    @Retry(name = "courseService")
    @CircuitBreaker(name = "courseService", fallbackMethod = "fallbackExistsById")
    @Bulkhead(name = "courseService")
    public boolean existsById(UUID courseId) {
        try {
            webClient.get()
                    .uri("/{id}", courseId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        }
    }

    // Fallback se o Circuit Breaker abrir ou falhar
    public boolean fallbackExistsById(UUID employeeId, Throwable t) {
        // ⚠️ Atenção: aqui é fallback! Você pode registrar alerta, retornar falso ou outro comportamento
        return false;
    }
}