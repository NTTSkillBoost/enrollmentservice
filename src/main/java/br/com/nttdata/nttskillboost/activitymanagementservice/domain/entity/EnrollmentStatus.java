package br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity;

import lombok.Getter;

@Getter
public enum EnrollmentStatus {
    ACTIVE("ACTIVE"), // Work In Progress
    CANCELED("CANCELED"), // Completed
    COMPLETED("COMPLETED");   // Archived
    // Add more statuses as needed

    private final String type;

    private EnrollmentStatus(String type) {
        this.type = type;
    }

    public static EnrollmentStatus getEnrollmentStatus(String type) {
        for (EnrollmentStatus enrollmentStatus : EnrollmentStatus.values()) {
            if (enrollmentStatus.name().equalsIgnoreCase(type)) {
                return enrollmentStatus;
            }
        }
        throw new IllegalArgumentException("No constant with text " + type + " found");
    }
}
