package com.hotel.infra;

import com.hotel.domains.enums.TipoQuarto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoQuartoConverter implements AttributeConverter<TipoQuarto, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TipoQuarto tipoQuarto) {
        return tipoQuarto == null ? null : tipoQuarto.getId();
    }

    @Override
    public TipoQuarto convertToEntityAttribute(Integer dbValue) {
        return TipoQuarto.toEnum(dbValue);
    }
}
