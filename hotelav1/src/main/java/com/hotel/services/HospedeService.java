package com.hotel.services;

import com.hotel.domains.Hospede;
import com.hotel.domains.dtos.HospedeDTO;
import com.hotel.mappers.HospedeMapper;
import com.hotel.repositories.HospedeRepository;
import com.hotel.repositories.ReservaRepository;
import com.hotel.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class HospedeService {
    private final HospedeRepository hospedeRepo;
    private final ReservaRepository reservaRepo;

    public HospedeService(HospedeRepository hospedeRepo, ReservaRepository reservaRepo) {
        this.hospedeRepo = hospedeRepo;
        this.reservaRepo = reservaRepo;
    }

    @Transactional(readOnly = true)
    public List<HospedeDTO> findAll() {
        return HospedeMapper.toDtoList(hospedeRepo.findAll());
    }

    @Transactional(readOnly = true)
    public HospedeDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return hospedeRepo.findById(id)
                .map(HospedeMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Hóspede não encontrado: id=" + id));
    }

    @Transactional
    public HospedeDTO create(HospedeDTO hospedeDTO) {
        if (hospedeDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do hóspede são obrigatórios");
        }

        hospedeDTO.setId(null);
        Hospede hospede;
        try {
            hospede = HospedeMapper.toEntity(hospedeDTO);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return HospedeMapper.toDto(hospedeRepo.save(hospede));
    }

    @Transactional
    public HospedeDTO update(Integer id, HospedeDTO hospedeDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (hospedeDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do hóspede são obrigatórios");
        }

        Hospede hospede = hospedeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Hóspede não encontrado: id=" + id));

        HospedeMapper.copyToEntity(hospedeDTO, hospede);

        return HospedeMapper.toDto(hospedeRepo.save(hospede));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Hospede hospede = hospedeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Hóspede não encontrado: id=" + id));

        if (reservaRepo.existsByHospede_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Hóspede possui Reservas associadas e não pode ser removido: id=" + id
            );
        }

        hospedeRepo.delete(hospede);
    }
}
