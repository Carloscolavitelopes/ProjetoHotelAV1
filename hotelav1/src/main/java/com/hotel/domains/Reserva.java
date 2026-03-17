package com.hotel.domains;

import com.hotel.domains.enums.StatusReserva;
import com.hotel.infra.StatusReservaConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_reserva",
        sequenceName = "seq_reserva",
        allocationSize = 1
)
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reserva")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    @Column(nullable = false)
    private LocalDate dataCheckIn;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    @Column(nullable = false)
    private LocalDate dataCheckOut;

    @Convert(converter = StatusReservaConverter.class)
    @Column(name = "status_reserva", nullable = false)
    private StatusReserva statusReserva;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idhospede", nullable = false)
    @JsonBackReference
    private Hospede hospede;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reserva_quarto",
            joinColumns = @JoinColumn(name = "idreserva"),
            inverseJoinColumns = @JoinColumn(name = "idquarto")
    )
    private List<Quarto> quartos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "reserva",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("dataHoraConsumo ASC")
    private List<Consumo> consumos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "reserva",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("dataPagamento ASC")
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Reserva() {
    }

    public Reserva(Integer id, LocalDate dataCheckIn, LocalDate dataCheckOut, StatusReserva statusReserva, Hospede hospede) {
        this.id = id;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.statusReserva = statusReserva;
        this.hospede = hospede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    public void addQuarto(Quarto q) {
        if (quartos == null) return;
        quartos.add(q);
    }

    public void removeQuarto(Quarto q) {
        if (quartos == null) return;
        quartos.remove(q);
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumo> consumos) {
        this.consumos = consumos;
    }

    public void addConsumo(Consumo c) {
        if (consumos == null) return;
        consumos.add(c);
        c.setReserva(this);
    }

    public void removeConsumo(Consumo c) {
        if (consumos == null) return;
        consumos.remove(c);
        if (c.getReserva() == this) c.setReserva(null);
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void addPagamento(Pagamento p) {
        if (pagamentos == null) return;
        pagamentos.add(p);
        p.setReserva(this);
    }

    public void removePagamento(Pagamento p) {
        if (pagamentos == null) return;
        pagamentos.remove(p);
        if (p.getReserva() == this) p.setReserva(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
