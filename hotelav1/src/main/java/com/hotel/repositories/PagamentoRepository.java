package com.hotel.repositories;

import com.hotel.domains.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    boolean existsByReserva_Id(Integer reservaId);
}
