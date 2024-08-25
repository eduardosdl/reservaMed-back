package com.api.reservamed.model;

public enum TypeConsult {
    // Enum constants
    ROUTINE_CHECKUP("Consulta de Rotina/Check-up"),
    URGENT_CARE("Consulta de Urgência/Emergência"),
    FOLLOW_UP("Consulta de Seguimento"),
    SPECIALIZED("Consulta Especializada"),
    PRENATAL("Consulta Pré-Natal"),
    PEDIATRIC("Consulta Pediátrica"),
    PSYCHIATRIC("Consulta Psiquiátrica/Psicológica"),
    DENTAL("Consulta Odontológica"),
    GERIATRIC("Consulta Geriátrica"),
    TELEMEDICINE("Consulta de Telemedicina");

    // Field to hold description
    private final String description;

    // Constructor
    TypeConsult(String description) {
        this.description = description;
    }

    // Method to get the description
    public String getDescription() {
        return description;
    }
}
