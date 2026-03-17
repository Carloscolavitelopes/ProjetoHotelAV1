package com.hotel.mappers;

import com.hotel.domains.Consumo;
import com.hotel.domains.Reserva;
import com.hotel.domains.dtos.ConsumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ConsumoMapper {
    private ConsumoMapper() {}

    public static ConsumoDTO toDto(Consumo e) {
        if (e == null) return null;

        Integer reservaId = (e.getReserva() == null) ? null : e.getReserva().getId();

        return new ConsumoDTO(
                e.getId(),
                e.getDescricao(),
                e.getValorUnitario(),
                e.getQuantidade(),
                reservaId
        );
    }

    public static List<ConsumoDTO> toDtoList(Collection<Consumo> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ConsumoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<ConsumoDTO> toDtoPage(Page<Consumo> page) {
        List<ConsumoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Consumo toEntity(ConsumoDTO dto, Reserva reserva) {
        if (dto == null) return null;

        Consumo e = new Consumo();
        e.setId(dto.getId());
        e.setDescricao(trim(dto.getDescricao()));
        e.setValorUnitario(dto.getValorUnitario());
        e.setQuantidade(dto.getQuantidade());
        e.setReserva(reserva);

        return e;
    }

    public static Consumo toEntity(ConsumoDTO dto, Function<Integer, Reserva> reservaResolver) {
        if (dto == null) return null;
        Reserva reserva = (dto.getReservaId() == null) ? null : reservaResolver.apply(dto.getReservaId());
        return toEntity(dto, reserva);
    }

    public static void copyToEntity(ConsumoDTO dto, Consumo target, Reserva reserva) {
        if (dto == null || target == null) return;
        target.setDescricao(trim(dto.getDescricao()));
        target.setValorUnitario(dto.getValorUnitario());
        target.setQuantidade(dto.getQuantidade());
        target.setReserva(reserva);
    }

    public static void copyToEntity(ConsumoDTO dto, Consumo target, Function<Integer, Reserva> reservaResolver) {
        if (dto == null || target == null) return;
        Reserva reserva = (dto.getReservaId() == null) ? null : reservaResolver.apply(dto.getReservaId());
        copyToEntity(dto, target, reserva);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
