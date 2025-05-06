package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class EmployeeClient {
    public static final String EMPLOYEE_TYPE_STUDENT = "STUDENT";
    private final WebClient webClient;

    public EmployeeClient(WebClient.Builder builder) {
        //this.webClient = builder.baseUrl("http://employee-service/api/v1/employees").build();
        this.webClient = builder.baseUrl("http://localhost:8881/api/v1/employees").build();
    }

    @Retry(name = "employeeService")
    @CircuitBreaker(name = "employeeService", fallbackMethod = "fallbackExistsById")
    @Bulkhead(name = "employeeService")
    public boolean existsById(UUID employeeId) {
        try {
            webClient.get()
                    .uri("/{id}/person-type/{personType}", employeeId, EMPLOYEE_TYPE_STUDENT)
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