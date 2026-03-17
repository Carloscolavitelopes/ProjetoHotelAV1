package com.hotel.infra;

import com.hotel.domains.enums.StatusReserva;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusReservaConverter implements AttributeConverter<StatusReserva, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusReserva statusReserva) {
        return statusReserva == null ? null : statusReserva.getId();
    }

    @Override
    public StatusReserva convertToEntityAttribute(Integer dbValue) {
        return StatusReserva.toEnum(dbValue);
    }
}
