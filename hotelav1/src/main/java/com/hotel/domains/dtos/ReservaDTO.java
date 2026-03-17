package com.hotel.domains.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class ReservaDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "Data de Check-In é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCheckIn;

    @NotNull(message = "Data de Check-Out é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCheckOut;

    @Min(value = 0, message = "Status inválido: use 0 (PENDENTE), 1 (CONFIRMADA), 2 (CHECK_IN), 3 (CHECK_OUT), 4 (CANCELADA)")
    @Max(value = 4, message = "Status inválido: use 0 (PENDENTE), 1 (CONFIRMADA), 2 (CHECK_IN), 3 (CHECK_OUT), 4 (CANCELADA)")
    private int statusReserva;

    @NotNull(message = "Hóspede é obrigatório")
    private Integer hospedeId;

    @NotNull(message = "Pelo menos um quarto é obrigatório")
    @Size(min = 1, message = "A reserva deve conter pelo menos 1 quarto")
    private List<Integer> quartoIds;

    public ReservaDTO() {}

    public ReservaDTO(Integer id, LocalDate dataCheckIn, LocalDate dataCheckOut, int statusReserva, Integer hospedeId, List<Integer> quartoIds) {
        this.id = id;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.statusReserva = statusReserva;
        this.hospedeId = hospedeId;
        this.quartoIds = quartoIds;
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

    public int getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(int statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Integer getHospedeId() {
        return hospedeId;
    }

    public void setHospedeId(Integer hospedeId) {
        this.hospedeId = hospedeId;
    }

    public List<Integer> getQuartoIds() {
        return quartoIds;
    }

    public void setQuartoIds(List<Integer> quartoIds) {
        this.quartoIds = quartoIds;
    }
}
