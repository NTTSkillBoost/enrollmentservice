package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourseClient {

    private final WebClient.Builder webClientBuilder;
    private final EurekaClient eurekaClient;

    @Retry(name = "courseService")
    @CircuitBreaker(name = "courseService", fallbackMethod = "fallbackExistsById")
    @Bulkhead(name = "courseService")
    public boolean existsById(UUID courseId) {
        try {
            InstanceInfo instance = eurekaClient.getNextServerFromEureka("COURSE-SERVICE", false);
            String baseUrl = instance.getHomePageUrl(); // Exemplo: http://localhost:8881/

            WebClient clientWebClient = webClientBuilder
                    .baseUrl(baseUrl + "api/v1/courses")
                    .build();

            clientWebClient.get()
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