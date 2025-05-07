package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.repository;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, UUID> {
    Enrollment findByCourseId(UUID courseId);
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
