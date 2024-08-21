package com.api.reservamed.model;

import com.api.reservamed.dtos.DadosCadastroPaciente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "paciente")
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private String email;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente requestPaciente){
        this.nome = requestPaciente.nome();
        this.dataNascimento = requestPaciente.dataNascimento();
        this.cpf = requestPaciente.cpf();
        this.telefone = requestPaciente.telefone();
        this.email = requestPaciente.email();
    }

}
