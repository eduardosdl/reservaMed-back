CREATE TABLE queue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    queue_position INT NOT NULL,
    doctor_id BIGINT,
    patient_id BIGINT,
    type_consult VARCHAR(255),
    date DATETIME,
    date_cancellation DATETIME,
    cancellation_reason VARCHAR(255),
    status VARCHAR(255),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id)
);
