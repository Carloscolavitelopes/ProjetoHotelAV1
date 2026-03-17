package com.hotel.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class QuartoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Número do quarto é obrigatório")
    @Size(max = 10, message = "Número do quarto deve ter no máximo 10 caracteres")
    private String numero;

    @Min(value = 0, message = "Tipo de quarto inválido: use 0 (SOLTEIRO), 1 (CASAL), 2 (SUITE_MASTER)")
    @Max(value = 2, message = "Tipo de quarto inválido: use 0 (SOLTEIRO), 1 (CASAL), 2 (SUITE_MASTER)")
    private int tipoQuarto;

    @NotNull(message = "Preço da diária é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Preço da diária deve ter no máximo 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Preço da diária não pode ser negativo")
    private BigDecimal precoDiaria;

    @Size(max = 300, message = "Descrição deve ter no máximo 300 caracteres")
    private String descricao;

    public QuartoDTO() {}

    public QuartoDTO(Integer id, String numero, int tipoQuarto, BigDecimal precoDiaria, String descricao) {
        this.id = id;
        this.numero = numero;
        this.tipoQuarto = tipoQuarto;
        this.precoDiaria = precoDiaria;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(int tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public BigDecimal getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(BigDecimal precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "QuartoDTO{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipoQuarto=" + tipoQuarto +
                ", precoDiaria=" + precoDiaria +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
