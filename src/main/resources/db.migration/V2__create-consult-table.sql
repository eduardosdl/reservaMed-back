CREATE TABLE consult (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT,
    patient_id BIGINT,
    type_consult VARCHAR(255),
    date DATETIME,
    date_cancellation DATETIME,
    cancellation_reason VARCHAR(255),
    status VARCHAR(255),

    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
