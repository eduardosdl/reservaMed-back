package com.api.reservamed.model;

public enum TypeConsult {
    // Enum constants
    ROUTINE_CHECKUP("Consulta de Rotina/Check-up"),
    URGENT_CARE("Consulta de Urgência/Emergência"),
    SPECIALIZED("Consulta Especializada"),
    PEDIATRIC("Consulta Pediátrica");

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
