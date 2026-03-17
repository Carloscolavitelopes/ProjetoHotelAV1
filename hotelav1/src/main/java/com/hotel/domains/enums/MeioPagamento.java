package com.hotel.domains.enums;

public enum MeioPagamento {
    CARTAO_CREDITO(0, "CARTAO_CREDITO"), PIX(1, "PIX"), DINHEIRO(2, "DINHEIRO");

    private Integer id;
    private String descricao;

    MeioPagamento(Integer id, String descricao) {
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

    public static MeioPagamento toEnum(Integer id) {
        if (id == null) return null;
        for (MeioPagamento meio : MeioPagamento.values()) {
            if (id.equals(meio.getId())) {
                return meio;
            }
        }
        throw new IllegalArgumentException("Meio de pagamento inválido!");
    }
}
