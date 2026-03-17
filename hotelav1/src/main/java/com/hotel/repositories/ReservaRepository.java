package com.hotel.repositories;

import com.hotel.domains.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    boolean existsByHospede_Id(Integer hospedeId);
}
