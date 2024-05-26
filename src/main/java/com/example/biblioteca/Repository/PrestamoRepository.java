package com.example.biblioteca.Repository;

import com.example.biblioteca.Dto.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
