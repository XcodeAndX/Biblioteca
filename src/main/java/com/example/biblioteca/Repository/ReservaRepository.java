package com.example.biblioteca.Repository;

import com.example.biblioteca.Dto.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByIdLibroAndNotificadoFalse(Long idLibro);
}
