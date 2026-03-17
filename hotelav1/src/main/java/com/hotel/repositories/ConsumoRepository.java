package com.hotel.repositories;

import com.hotel.domains.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Integer> {
    boolean existsByReserva_Id(Integer reservaId);
}
