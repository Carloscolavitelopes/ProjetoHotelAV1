package com.hotel.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_hospede",
        sequenceName = "seq_hospede",
        allocationSize = 1
)
public class Hospede {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hospede")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @NotBlank
    @Column(nullable = false, length = 15)
    private String telefone;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String email;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "hospede",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("dataCheckIn ASC")
    private List<Reserva> reservas = new ArrayList<>();

    public Hospede() {
    }

    public Hospede(Integer id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void addReserva(Reserva r) {
        if (reservas == null) return;
        reservas.add(r);
        r.setHospede(this);
    }

    public void removeReserva(Reserva r) {
        if (reservas == null) return;
        reservas.remove(r);
        if (r.getHospede() == this) r.setHospede(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Hospede hospede = (Hospede) o;
        return Objects.equals(id, hospede.id) && Objects.equals(cpf, hospede.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }
}
