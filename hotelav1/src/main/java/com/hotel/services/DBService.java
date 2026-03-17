package com.hotel.services;

import com.hotel.domains.*;
import com.hotel.domains.enums.MeioPagamento;
import com.hotel.domains.enums.StatusReserva;
import com.hotel.domains.enums.TipoQuarto;
import com.hotel.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DBService {
    @Autowired
    private HospedeRepository hospedeRepo;

    @Autowired
    private QuartoRepository quartoRepo;

    @Autowired
    private ReservaRepository reservaRepo;

    @Autowired
    private ConsumoRepository consumoRepo;

    @Autowired
    private PagamentoRepository pagamentoRepo;

    @Transactional
    public void initDB() {
        Hospede hospede01 = new Hospede(null, "João Silva", "12345678901", "11999998888", "joao@email.com");
        Hospede hospede02 = new Hospede(null, "Maria Oliveira", "98765432100", "21988887777", "maria@email.com");

        Quarto quarto01 = new Quarto(null, "101", TipoQuarto.SOLTEIRO, new BigDecimal("150.00"), "Quarto solteiro com vista para o jardim");
        Quarto quarto02 = new Quarto(null, "202", TipoQuarto.CASAL, new BigDecimal("280.00"), "Quarto casal com varanda");
        Quarto quarto03 = new Quarto(null, "301", TipoQuarto.SUITE_MASTER, new BigDecimal("550.00"), "Suíte master com jacuzzi e vista para o mar");

        hospedeRepo.save(hospede01);
        hospedeRepo.save(hospede02);
        quartoRepo.save(quarto01);
        quartoRepo.save(quarto02);
        quartoRepo.save(quarto03);

        Reserva reserva01 = new Reserva(null, LocalDate.of(2026, 3, 20), LocalDate.of(2026, 3, 25), StatusReserva.CONFIRMADA, hospede01);
        reserva01.addQuarto(quarto01);
        reserva01.addQuarto(quarto02);

        Reserva reserva02 = new Reserva(null, LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 15), StatusReserva.PENDENTE, hospede02);
        reserva02.addQuarto(quarto03);

        reservaRepo.save(reserva01);
        reservaRepo.save(reserva02);

        Consumo consumo01 = new Consumo(null, "Frigobar - Água mineral", new BigDecimal("8.00"), 2, LocalDateTime.now(), reserva01);
        Consumo consumo02 = new Consumo(null, "Room Service - Jantar", new BigDecimal("95.00"), 1, LocalDateTime.now(), reserva01);

        consumoRepo.save(consumo01);
        consumoRepo.save(consumo02);

        Pagamento pagamento01 = new Pagamento(null, new BigDecimal("500.00"), LocalDate.now(), MeioPagamento.CARTAO_CREDITO, reserva01);
        Pagamento pagamento02 = new Pagamento(null, new BigDecimal("250.00"), LocalDate.now(), MeioPagamento.PIX, reserva02);

        pagamentoRepo.save(pagamento01);
        pagamentoRepo.save(pagamento02);
    }
}
