package com.hotel.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ConsumoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;

    @NotNull(message = "Valor unitário é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor unitário deve ter no máximo 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Valor unitário não pode ser negativo")
    private BigDecimal valorUnitario;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade;

    @NotNull(message = "Reserva é obrigatória")
    private Integer reservaId;

    public ConsumoDTO() {}

    public ConsumoDTO(Integer id, String descricao, BigDecimal valorUnitario, Integer quantidade, Integer reservaId) {
        this.id = id;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.reservaId = reservaId;
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

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }
}
