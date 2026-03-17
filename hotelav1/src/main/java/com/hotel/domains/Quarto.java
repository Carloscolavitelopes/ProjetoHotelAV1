package com.hotel.domains;

import com.hotel.domains.enums.TipoQuarto;
import com.hotel.infra.TipoQuartoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_quarto",
        sequenceName = "seq_quarto",
        allocationSize = 1
)
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_quarto")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 10, unique = true)
    private String numero;

    @Convert(converter = TipoQuartoConverter.class)
    @Column(name = "tipo_quarto", nullable = false)
    private TipoQuarto tipoQuarto;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal precoDiaria;

    @Column(length = 300)
    private String descricao;

    public Quarto() {
        this.precoDiaria = BigDecimal.ZERO;
    }

    public Quarto(Integer id, String numero, TipoQuarto tipoQuarto, BigDecimal precoDiaria, String descricao) {
        this.id = id;
        this.numero = numero;
        this.tipoQuarto = tipoQuarto;
        this.precoDiaria = precoDiaria != null ? precoDiaria : BigDecimal.ZERO;
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

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quarto quarto = (Quarto) o;
        return Objects.equals(id, quarto.id) && Objects.equals(numero, quarto.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero);
    }
}
