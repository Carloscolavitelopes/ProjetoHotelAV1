package com.hotel.mappers;

import com.hotel.domains.Quarto;
import com.hotel.domains.dtos.QuartoDTO;
import com.hotel.domains.enums.TipoQuarto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class QuartoMapper {
    private QuartoMapper() {}

    public static QuartoDTO toDto(Quarto e) {
        if (e == null) return null;
        int tipoQuartoInt = e.getTipoQuarto().getId();
        return new QuartoDTO(
                e.getId(),
                e.getNumero(),
                tipoQuartoInt,
                e.getPrecoDiaria(),
                e.getDescricao()
        );
    }

    public static Quarto toEntity(QuartoDTO dto) {
        if (dto == null) return null;
        Quarto e = new Quarto();
        e.setId(dto.getId());
        e.setNumero(trim(dto.getNumero()));
        e.setTipoQuarto(TipoQuarto.toEnum(dto.getTipoQuarto()));
        e.setPrecoDiaria(dto.getPrecoDiaria());
        e.setDescricao(trim(dto.getDescricao()));
        return e;
    }

    public static void copyToEntity(QuartoDTO dto, Quarto target) {
        if (dto == null || target == null) return;
        target.setNumero(trim(dto.getNumero()));
        target.setTipoQuarto(TipoQuarto.toEnum(dto.getTipoQuarto()));
        target.setPrecoDiaria(dto.getPrecoDiaria());
        target.setDescricao(trim(dto.getDescricao()));
    }

    public static List<QuartoDTO> toDtoList(Collection<Quarto> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(QuartoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<QuartoDTO> toDtoPage(Page<Quarto> page) {
        List<QuartoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
