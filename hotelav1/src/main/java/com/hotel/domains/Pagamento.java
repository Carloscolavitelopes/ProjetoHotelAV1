package com.hotel.domains;

import com.hotel.domains.enums.MeioPagamento;
import com.hotel.infra.MeioPagamentoConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_pagamento",
        sequenceName = "seq_pagamento",
        allocationSize = 1
)
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pagamento")
    private Integer id;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valorPago;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataPagamento = LocalDate.now();

    @Convert(converter = MeioPagamentoConverter.class)
    @Column(name = "meio_pagamento", nullable = false)
    private MeioPagamento meioPagamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idreserva", nullable = false)
    @JsonBackReference
    private Reserva reserva;

    public Pagamento() {
        this.valorPago = BigDecimal.ZERO;
    }

    public Pagamento(Integer id, BigDecimal valorPago, LocalDate dataPagamento, MeioPagamento meioPagamento, Reserva reserva) {
        this.id = id;
        this.valorPago = valorPago != null ? valorPago : BigDecimal.ZERO;
        this.dataPagamento = dataPagamento;
        this.meioPagamento = meioPagamento;
        this.reserva = reserva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
