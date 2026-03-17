package com.hotel.services;

import com.hotel.domains.Hospede;
import com.hotel.domains.Quarto;
import com.hotel.domains.Reserva;
import com.hotel.domains.dtos.ReservaDTO;
import com.hotel.mappers.ReservaMapper;
import com.hotel.repositories.ConsumoRepository;
import com.hotel.repositories.HospedeRepository;
import com.hotel.repositories.PagamentoRepository;
import com.hotel.repositories.QuartoRepository;
import com.hotel.repositories.ReservaRepository;
import com.hotel.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepo;
    private final HospedeRepository hospedeRepo;
    private final QuartoRepository quartoRepo;
    private final ConsumoRepository consumoRepo;
    private final PagamentoRepository pagamentoRepo;

    public ReservaService(ReservaRepository reservaRepo, HospedeRepository hospedeRepo,
                          QuartoRepository quartoRepo, ConsumoRepository consumoRepo,
                          PagamentoRepository pagamentoRepo) {
        this.reservaRepo = reservaRepo;
        this.hospedeRepo = hospedeRepo;
        this.quartoRepo = quartoRepo;
        this.consumoRepo = consumoRepo;
        this.pagamentoRepo = pagamentoRepo;
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> findAll() {
        return ReservaMapper.toDtoList(reservaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public ReservaDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return reservaRepo.findById(id)
                .map(ReservaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Reserva não encontrada: id=" + id));
    }

    @Transactional
    public ReservaDTO create(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da reserva são obrigatórios");
        }

        Integer hospedeId = reservaDTO.getHospedeId();
        if (hospedeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Hóspede é obrigatório");
        }

        Hospede hospede = hospedeRepo.findById(hospedeId)
                .orElseThrow(() -> new ObjectNotFoundException("Hóspede não encontrado: id=" + hospedeId));

        List<Quarto> quartos = resolverQuartos(reservaDTO.getQuartoIds());

        reservaDTO.setId(null);
        Reserva reserva;
        try {
            reserva = ReservaMapper.toEntity(reservaDTO, hospede, quartos);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return ReservaMapper.toDto(reservaRepo.save(reserva));
    }

    @Transactional
    public ReservaDTO update(Integer id, ReservaDTO reservaDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (reservaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da reserva são obrigatórios");
        }

        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Reserva não encontrada: id=" + id));

        Integer hospedeId = reservaDTO.getHospedeId();
        if (hospedeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Hóspede é obrigatório");
        }

        Hospede hospede = hospedeRepo.findById(hospedeId)
                .orElseThrow(() -> new ObjectNotFoundException("Hóspede não encontrado: id=" + hospedeId));

        List<Quarto> quartos = resolverQuartos(reservaDTO.getQuartoIds());

        ReservaMapper.copyToEntity(reservaDTO, reserva, hospede, quartos);

        return ReservaMapper.toDto(reservaRepo.save(reserva));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Reserva não encontrada: id=" + id));

        if (consumoRepo.existsByReserva_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Reserva possui Consumos associados e não pode ser removida: id=" + id
            );
        }

        if (pagamentoRepo.existsByReserva_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Reserva possui Pagamentos associados e não pode ser removida: id=" + id
            );
        }

        reservaRepo.delete(reserva);
    }

    private List<Quarto> resolverQuartos(List<Integer> quartoIds) {
        List<Quarto> quartos = new ArrayList<>();
        if (quartoIds != null) {
            for (Integer quartoId : quartoIds) {
                Quarto quarto = quartoRepo.findById(quartoId)
                        .orElseThrow(() -> new ObjectNotFoundException("Quarto não encontrado: id=" + quartoId));
                quartos.add(quarto);
            }
        }
        return quartos;
    }
}
