package com.hotel.mappers;

import com.hotel.domains.Hospede;
import com.hotel.domains.dtos.HospedeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HospedeMapper {
    private HospedeMapper() {}

    public static HospedeDTO toDto(Hospede e) {
        if (e == null) return null;
        return new HospedeDTO(
                e.getId(),
                e.getNome(),
                e.getCpf(),
                e.getTelefone(),
                e.getEmail()
        );
    }

    public static Hospede toEntity(HospedeDTO dto) {
        if (dto == null) return null;
        Hospede e = new Hospede();
        e.setId(dto.getId());
        e.setNome(dto.getNome() == null ? null : dto.getNome().trim());
        e.setCpf(dto.getCpf() == null ? null : dto.getCpf().trim());
        e.setTelefone(dto.getTelefone() == null ? null : dto.getTelefone().trim());
        e.setEmail(dto.getEmail() == null ? null : dto.getEmail().trim());
        return e;
    }

    public static void copyToEntity(HospedeDTO dto, Hospede target) {
        if (dto == null || target == null) return;
        target.setNome(dto.getNome() == null ? null : dto.getNome().trim());
        target.setCpf(dto.getCpf() == null ? null : dto.getCpf().trim());
        target.setTelefone(dto.getTelefone() == null ? null : dto.getTelefone().trim());
        target.setEmail(dto.getEmail() == null ? null : dto.getEmail().trim());
    }

    public static List<HospedeDTO> toDtoList(Collection<Hospede> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(HospedeMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Hospede> toEntityList(Collection<HospedeDTO> dtos) {
        if (dtos == null) return List.of();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(HospedeMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static Page<HospedeDTO> toDtoPage(Page<Hospede> page) {
        List<HospedeDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
