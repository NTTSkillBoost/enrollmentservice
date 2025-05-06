-- Criação da tb_enrollment
CREATE TABLE tb_enrollment (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    course_id UUID NOT NULL,
    student_id UUID NOT NULL,
    enrollment_date DATE NOT NULL,
    activity_type VARCHAR(50) NOT NULL CHECK (activity_type IN ('ACTIVE', 'CANCELED', 'COMPLETED')),
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date timestamp DEFAULT CURRENT_DATE,
    last_modified_by varchar(255),
    last_modified_date timestamp DEFAULT CURRENT_DATE
);
