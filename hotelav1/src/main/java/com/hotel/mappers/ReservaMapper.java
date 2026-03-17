package com.hotel.mappers;

import com.hotel.domains.Hospede;
import com.hotel.domains.Quarto;
import com.hotel.domains.Reserva;
import com.hotel.domains.dtos.ReservaDTO;
import com.hotel.domains.enums.StatusReserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ReservaMapper {
    private ReservaMapper() {}

    public static ReservaDTO toDto(Reserva e) {
        if (e == null) return null;

        Integer hospedeId = (e.getHospede() == null) ? null : e.getHospede().getId();
        int statusInt = e.getStatusReserva().getId();

        List<Integer> quartoIds = new ArrayList<>();
        if (e.getQuartos() != null) {
            for (Quarto q : e.getQuartos()) {
                quartoIds.add(q.getId());
            }
        }

        return new ReservaDTO(
                e.getId(),
                e.getDataCheckIn(),
                e.getDataCheckOut(),
                statusInt,
                hospedeId,
                quartoIds
        );
    }

    public static List<ReservaDTO> toDtoList(Collection<Reserva> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ReservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<ReservaDTO> toDtoPage(Page<Reserva> page) {
        List<ReservaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Reserva toEntity(ReservaDTO dto, Hospede hospede, List<Quarto> quartos) {
        if (dto == null) return null;

        Reserva e = new Reserva();
        e.setId(dto.getId());
        e.setDataCheckIn(dto.getDataCheckIn());
        e.setDataCheckOut(dto.getDataCheckOut());
        e.setStatusReserva(StatusReserva.toEnum(dto.getStatusReserva()));
        e.setHospede(hospede);
        e.setQuartos(quartos != null ? quartos : new ArrayList<>());

        return e;
    }

    public static Reserva toEntity(ReservaDTO dto, Function<Integer, Hospede> hospedeResolver, Function<Integer, Quarto> quartoResolver) {
        if (dto == null) return null;
        Hospede hospede = (dto.getHospedeId() == null) ? null : hospedeResolver.apply(dto.getHospedeId());
        List<Quarto> quartos = new ArrayList<>();
        if (dto.getQuartoIds() != null) {
            for (Integer quartoId : dto.getQuartoIds()) {
                quartos.add(quartoResolver.apply(quartoId));
            }
        }
        return toEntity(dto, hospede, quartos);
    }

    public static void copyToEntity(ReservaDTO dto, Reserva target, Hospede hospede, List<Quarto> quartos) {
        if (dto == null || target == null) return;
        target.setDataCheckIn(dto.getDataCheckIn());
        target.setDataCheckOut(dto.getDataCheckOut());
        target.setStatusReserva(StatusReserva.toEnum(dto.getStatusReserva()));
        target.setHospede(hospede);
        target.setQuartos(quartos != null ? quartos : new ArrayList<>());
    }

    public static void copyToEntity(ReservaDTO dto, Reserva target, Function<Integer, Hospede> hospedeResolver, Function<Integer, Quarto> quartoResolver) {
        if (dto == null || target == null) return;
        Hospede hospede = (dto.getHospedeId() == null) ? null : hospedeResolver.apply(dto.getHospedeId());
        List<Quarto> quartos = new ArrayList<>();
        if (dto.getQuartoIds() != null) {
            for (Integer quartoId : dto.getQuartoIds()) {
                quartos.add(quartoResolver.apply(quartoId));
            }
        }
        copyToEntity(dto, target, hospede, quartos);
    }
}
