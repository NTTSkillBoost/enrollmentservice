package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {

    @Schema(description = "ID do Employee", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    private UUID courseId;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    private UUID studentId;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    private EnrollmentStatus enrollmentStatus;
}
