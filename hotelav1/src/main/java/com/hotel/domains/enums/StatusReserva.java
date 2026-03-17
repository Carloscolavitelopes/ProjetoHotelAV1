package com.hotel.domains.enums;

public enum StatusReserva {
    PENDENTE(0, "PENDENTE"), CONFIRMADA(1, "CONFIRMADA"), CHECK_IN(2, "CHECK_IN"), CHECK_OUT(3, "CHECK_OUT"), CANCELADA(4, "CANCELADA");

    private Integer id;
    private String descricao;

    StatusReserva(Integer id, String descricao) {
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

    public static StatusReserva toEnum(Integer id) {
        if (id == null) return null;
        for (StatusReserva status : StatusReserva.values()) {
            if (id.equals(status.getId())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de reserva inválido!");
    }
}
