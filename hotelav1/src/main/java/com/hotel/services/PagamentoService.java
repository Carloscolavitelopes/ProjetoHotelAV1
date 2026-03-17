package com.hotel.services;

import com.hotel.domains.Pagamento;
import com.hotel.domains.Reserva;
import com.hotel.domains.dtos.PagamentoDTO;
import com.hotel.mappers.PagamentoMapper;
import com.hotel.repositories.PagamentoRepository;
import com.hotel.repositories.ReservaRepository;
import com.hotel.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepo;
    private final ReservaRepository reservaRepo;

    public PagamentoService(PagamentoRepository pagamentoRepo, ReservaRepository reservaRepo) {
        this.pagamentoRepo = pagamentoRepo;
        this.reservaRepo = reservaRepo;
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAll() {
        return PagamentoMapper.toDtoList(pagamentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return pagamentoRepo.findById(id)
                .map(PagamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));
    }

    @Transactional
    public PagamentoDTO create(PagamentoDTO pagamentoDTO) {
        if (pagamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Integer reservaId = pagamentoDTO.getReservaId();
        if (reservaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A Reserva é obrigatória");
        }

        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new ObjectNotFoundException("Reserva não encontrada: id=" + reservaId));

        pagamentoDTO.setId(null);
        Pagamento pagamento;
        try {
            pagamento = PagamentoMapper.toEntity(pagamentoDTO, reserva);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return PagamentoMapper.toDto(pagamentoRepo.save(pagamento));
    }

    @Transactional
    public PagamentoDTO update(Integer id, PagamentoDTO pagamentoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (pagamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Pagamento pagamento = pagamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));

        Integer reservaId = pagamentoDTO.getReservaId();
        if (reservaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A Reserva é obrigatória");
        }

        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new ObjectNotFoundException("Reserva não encontrada: id=" + reservaId));

        PagamentoMapper.copyToEntity(pagamentoDTO, pagamento, reserva);

        return PagamentoMapper.toDto(pagamentoRepo.save(pagamento));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Pagamento pagamento = pagamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));

        pagamentoRepo.delete(pagamento);
    }
}
