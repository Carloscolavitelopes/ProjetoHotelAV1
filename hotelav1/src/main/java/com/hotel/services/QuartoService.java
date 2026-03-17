package com.hotel.services;

import com.hotel.domains.Quarto;
import com.hotel.domains.dtos.QuartoDTO;
import com.hotel.mappers.QuartoMapper;
import com.hotel.repositories.QuartoRepository;
import com.hotel.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class QuartoService {
    private final QuartoRepository quartoRepo;

    public QuartoService(QuartoRepository quartoRepo) {
        this.quartoRepo = quartoRepo;
    }

    @Transactional(readOnly = true)
    public List<QuartoDTO> findAll() {
        return QuartoMapper.toDtoList(quartoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public QuartoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return quartoRepo.findById(id)
                .map(QuartoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Quarto não encontrado: id=" + id));
    }

    @Transactional
    public QuartoDTO create(QuartoDTO quartoDTO) {
        if (quartoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do quarto são obrigatórios");
        }

        quartoDTO.setId(null);
        Quarto quarto;
        try {
            quarto = QuartoMapper.toEntity(quartoDTO);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return QuartoMapper.toDto(quartoRepo.save(quarto));
    }

    @Transactional
    public QuartoDTO update(Integer id, QuartoDTO quartoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (quartoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do quarto são obrigatórios");
        }

        Quarto quarto = quartoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Quarto não encontrado: id=" + id));

        QuartoMapper.copyToEntity(quartoDTO, quarto);

        return QuartoMapper.toDto(quartoRepo.save(quarto));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Quarto quarto = quartoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Quarto não encontrado: id=" + id));

        quartoRepo.delete(quarto);
    }
}
