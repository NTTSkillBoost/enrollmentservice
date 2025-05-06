package br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_enrollment")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Activity Entity", description = "Operações relacionadas a o funcionário")
public class Enrollment extends AuditDomain {

    @Schema(description = "ID do Employee", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @Column(name = "course_id")
    private UUID courseId;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @Column(name = "student_id")
    private UUID studentId;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    @Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

}