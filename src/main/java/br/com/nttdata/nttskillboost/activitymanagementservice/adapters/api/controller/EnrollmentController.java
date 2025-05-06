package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.controller;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.EnrollmentRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.EnrollmentResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.mapper.EnrollmentMapper;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.CreateEnrollmentService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.DeleteEnrollmentService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.GetEnrollmentService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.UpdateEnrollmentService;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.EnrollmentStatus;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/activities")
@RequiredArgsConstructor
public class EnrollmentController {

    private final CreateEnrollmentService createEnrollmentService;
    private final GetEnrollmentService getEnrollmentService;
    private final UpdateEnrollmentService updateEnrollmentService;
    private final DeleteEnrollmentService deleteEnrollmentService;
    private final EnrollmentMapper enrollmentMapper;

    @Retry(name = "enrollmentService", fallbackMethod = "fallbackCreate")
    @CircuitBreaker(name = "enrollmentService", fallbackMethod = "fallbackCreate")
    @Bulkhead(name = "enrollmentService")
    @PostMapping
    public ResponseEntity<EnrollmentResponse> create(@Valid @RequestBody EnrollmentRequest dto) {
        Enrollment enrollment = enrollmentMapper.toDomain(dto);
        Enrollment created = createEnrollmentService.create(enrollment);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/v1/enrollments/%s", created.getId()));
        EnrollmentResponse response = enrollmentMapper.toResponse(created);
        return ResponseEntity.created(location).body(response);
    }

    // 🔎 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> findById(@PathVariable UUID id) {
        Enrollment enrollmentById = getEnrollmentService.findById(id);
        if (enrollmentById != null) {
            EnrollmentResponse byId = enrollmentMapper.toResponse(enrollmentById);
            return ResponseEntity.ok(byId);
        }

        return ResponseEntity.notFound().build();
    }

    // 🔎 Listar todos
    @GetMapping
    public List<EnrollmentResponse> listAll() {
        List<Enrollment> cours = getEnrollmentService.findAll();
        return cours.stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    // 🔄 Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> update(@PathVariable UUID id, @Valid @RequestBody EnrollmentRequest dto) {
        Enrollment enrollment = enrollmentMapper.toDomain(dto);
        Enrollment update = updateEnrollmentService.update(id, enrollment);
        if (update != null) {
            EnrollmentResponse response = enrollmentMapper.toResponse(update);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // ❌ Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (deleteEnrollmentService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<EnrollmentResponse> fallbackCreate(EnrollmentRequest dto, Throwable t) {
        Enrollment fallback = new Enrollment();
        fallback.setId(UUID.randomUUID());
        fallback.setStudentId(UUID.randomUUID());
        fallback.setEnrollmentDate(LocalDate.now());
        fallback.setEnrollmentStatus(EnrollmentStatus.ACTIVE);

        EnrollmentResponse response = enrollmentMapper.toResponse(fallback);

        if (t instanceof BulkheadFullException || t instanceof CallNotPermittedException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
