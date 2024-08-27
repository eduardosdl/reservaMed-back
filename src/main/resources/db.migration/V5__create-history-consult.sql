CREATE TABLE history_consult (
    id_patient BIGINT NOT NULL,
    id_doctor BIGINT NOT NULL,
    type_consult VARCHAR(255),
    date TIMESTAMP,
    date_cancellation TIMESTAMP,
    cancellation_reason VARCHAR(255),
    status VARCHAR(50),
    diagnostic TEXT,
    prescription TEXT,
    PRIMARY KEY (id_patient, id_doctor, date)
);
