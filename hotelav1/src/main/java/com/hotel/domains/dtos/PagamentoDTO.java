package com.hotel.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PagamentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "Valor pago é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor pago deve ter no máximo 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Valor pago não pode ser negativo")
    private BigDecimal valorPago;

    @Min(value = 0, message = "Meio de pagamento inválido: use 0 (CARTAO_CREDITO), 1 (PIX), 2 (DINHEIRO)")
    @Max(value = 2, message = "Meio de pagamento inválido: use 0 (CARTAO_CREDITO), 1 (PIX), 2 (DINHEIRO)")
    private int meioPagamento;

    @NotNull(message = "Reserva é obrigatória")
    private Integer reservaId;

    public PagamentoDTO() {}

    public PagamentoDTO(Integer id, BigDecimal valorPago, int meioPagamento, Integer reservaId) {
        this.id = id;
        this.valorPago = valorPago;
        this.meioPagamento = meioPagamento;
        this.reservaId = reservaId;
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

    public int getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(int meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }
}
