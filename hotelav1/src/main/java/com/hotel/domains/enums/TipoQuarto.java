package com.hotel.domains.enums;

public enum TipoQuarto {
    SOLTEIRO(0, "SOLTEIRO"), CASAL(1, "CASAL"), SUITE_MASTER(2, "SUITE_MASTER");

    private Integer id;
    private String descricao;

    TipoQuarto(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

    public static TipoQuarto toEnum(Integer id) {
        if (id == null) return null;
        for (TipoQuarto tipo : TipoQuarto.values()) {
            if (id.equals(tipo.getId())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de quarto inválido!");
    }
}
