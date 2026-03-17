package com.hotel.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_consumo",
        sequenceName = "seq_consumo",
        allocationSize = 1
)
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_consumo")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String descricao;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valorUnitario;

    @NotNull
    @Column(nullable = false)
    private Integer quantidade;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime dataHoraConsumo = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idreserva", nullable = false)
    @JsonBackReference
    private Reserva reserva;

    public Consumo() {
        this.valorUnitario = BigDecimal.ZERO;
        this.quantidade = 1;
    }

    public Consumo(Integer id, String descricao, BigDecimal valorUnitario, Integer quantidade, LocalDateTime dataHoraConsumo, Reserva reserva) {
        this.id = id;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario != null ? valorUnitario : BigDecimal.ZERO;
        this.quantidade = quantidade != null ? quantidade : 1;
        this.dataHoraConsumo = dataHoraConsumo;
        this.reserva = reserva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataHoraConsumo() {
        return dataHoraConsumo;
    }

    public void setDataHoraConsumo(LocalDateTime dataHoraConsumo) {
        this.dataHoraConsumo = dataHoraConsumo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public BigDecimal getValorTotal() {
        return valorUnitario.multiply(new BigDecimal(quantidade));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Consumo consumo = (Consumo) o;
        return Objects.equals(id, consumo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
