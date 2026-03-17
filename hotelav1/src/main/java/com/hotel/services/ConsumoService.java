package com.hotel.services;

import com.hotel.domains.Consumo;
import com.hotel.domains.Reserva;
import com.hotel.domains.dtos.ConsumoDTO;
import com.hotel.mappers.ConsumoMapper;
import com.hotel.repositories.ConsumoRepository;
import com.hotel.repositories.ReservaRepository;
import com.hotel.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ConsumoService {
    private final ConsumoRepository consumoRepo;
    private final ReservaRepository reservaRepo;

    public ConsumoService(ConsumoRepository consumoRepo, ReservaRepository reservaRepo) {
        this.consumoRepo = consumoRepo;
        this.reservaRepo = reservaRepo;
    }

    @Transactional(readOnly = true)
    public List<ConsumoDTO> findAll() {
        return ConsumoMapper.toDtoList(consumoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public ConsumoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return consumoRepo.findById(id)
                .map(ConsumoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Consumo não encontrado: id=" + id));
    }

    @Transactional
    public ConsumoDTO create(ConsumoDTO consumoDTO) {
        if (consumoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do consumo são obrigatórios");
        }

        Integer reservaId = consumoDTO.getReservaId();
        if (reservaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A Reserva é obrigatória");
        }

        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new ObjectNotFoundException("Reserva não encontrada: id=" + reservaId));

        consumoDTO.setId(null);
        Consumo consumo;
        try {
            consumo = ConsumoMapper.toEntity(consumoDTO, reserva);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return ConsumoMapper.toDto(consumoRepo.save(consumo));
    }

    @Transactional
    public ConsumoDTO update(Integer id, ConsumoDTO consumoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (consumoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do consumo são obrigatórios");
        }

        Consumo consumo = consumoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Consumo não encontrado: id=" + id));

        Integer reservaId = consumoDTO.getReservaId();
        if (reservaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A Reserva é obrigatória");
        }

        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new ObjectNotFoundException("Reserva não encontrada: id=" + reservaId));

        ConsumoMapper.copyToEntity(consumoDTO, consumo, reserva);

        return ConsumoMapper.toDto(consumoRepo.save(consumo));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Consumo consumo = consumoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Consumo não encontrado: id=" + id));

        consumoRepo.delete(consumo);
    }
}
