package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.mapper;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.EnrollmentRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.EnrollmentResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.EnrollmentStatus;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public Enrollment toDomain(EnrollmentRequest dto) {
        return Enrollment.builder()
                .courseId(dto.getCourseId())
                .studentId(dto.getStudentId())
                .enrollmentDate(dto.getEnrollmentDate())
                .enrollmentStatus(EnrollmentStatus.getEnrollmentStatus(dto.getEnrollmentStatus()))
                .build();
    }

    public EnrollmentResponse toResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .courseId(enrollment.getCourseId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .studentId(enrollment.getStudentId())
                .enrollmentStatus(enrollment.getEnrollmentStatus())
                .build();
    }
}