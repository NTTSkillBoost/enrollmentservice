package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.exception;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.exception.BusinessException;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.exception.ResourceAlwaysExists;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BulkheadFullException.class, CallNotPermittedException.class})
    public ResponseEntity<Map<String, String>> handleResilienceExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Serviço indisponível devido à alta concorrência ou falhas.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler({ResourceAlwaysExists.class})
    public ResponseEntity<Map<String, String>> handleResourceAlwaysExists(ResourceAlwaysExists ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ja cadastrado no sistema." + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Map<String, String>> handleBusinessExceptions(BusinessException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Business not found.." + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor.");
    }
}