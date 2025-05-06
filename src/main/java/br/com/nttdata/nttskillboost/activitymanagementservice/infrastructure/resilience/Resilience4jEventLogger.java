package br.com.nttdata.nttskillboost.activitymanagementservice.infrastructure.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Resilience4jEventLogger {

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final BulkheadRegistry bulkheadRegistry;

    private static final Logger logger = LoggerFactory.getLogger(Resilience4jEventLogger.class);

    @PostConstruct
    public void subscribeToEvents() {

        Retry retry = retryRegistry.retry("employeeService");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("employeeService");
        Bulkhead bulkhead = bulkheadRegistry.bulkhead("employeeService");

        retry.getEventPublisher().onRetry(event ->
                logger.info("Retry - Tentativa {} para chamada. Motivo: {}", event.getNumberOfRetryAttempts(), event.getLastThrowable())
        );

        circuitBreaker.getEventPublisher().onStateTransition(event ->
                logger.info("CircuitBreaker - Mudança de estado: {}", event.getStateTransition())
        );

        bulkhead.getEventPublisher().onCallRejected(event ->
                logger.info("Bulkhead - Chamada rejeitada por limite de concorrência.")
        );
    }
}