package com.hotel.mappers;

import com.hotel.domains.Pagamento;
import com.hotel.domains.Reserva;
import com.hotel.domains.dtos.PagamentoDTO;
import com.hotel.domains.enums.MeioPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PagamentoMapper {
    private PagamentoMapper() {}

    public static PagamentoDTO toDto(Pagamento e) {
        if (e == null) return null;

        Integer reservaId = (e.getReserva() == null) ? null : e.getReserva().getId();
        int meioPagamentoInt = e.getMeioPagamento().getId();

        return new PagamentoDTO(
                e.getId(),
                e.getValorPago(),
                meioPagamentoInt,
                reservaId
        );
    }

    public static List<PagamentoDTO> toDtoList(Collection<Pagamento> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(PagamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<PagamentoDTO> toDtoPage(Page<Pagamento> page) {
        List<PagamentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Pagamento toEntity(PagamentoDTO dto, Reserva reserva) {
        if (dto == null) return null;

        Pagamento e = new Pagamento();
        e.setId(dto.getId());
        e.setValorPago(dto.getValorPago());
        e.setMeioPagamento(MeioPagamento.toEnum(dto.getMeioPagamento()));
        e.setReserva(reserva);

        return e;
    }

    public static Pagamento toEntity(PagamentoDTO dto, Function<Integer, Reserva> reservaResolver) {
        if (dto == null) return null;
        Reserva reserva = (dto.getReservaId() == null) ? null : reservaResolver.apply(dto.getReservaId());
        return toEntity(dto, reserva);
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, Reserva reserva) {
        if (dto == null || target == null) return;
        target.setValorPago(dto.getValorPago());
        target.setMeioPagamento(MeioPagamento.toEnum(dto.getMeioPagamento()));
        target.setReserva(reserva);
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, Function<Integer, Reserva> reservaResolver) {
        if (dto == null || target == null) return;
        Reserva reserva = (dto.getReservaId() == null) ? null : reservaResolver.apply(dto.getReservaId());
        copyToEntity(dto, target, reserva);
    }
}
