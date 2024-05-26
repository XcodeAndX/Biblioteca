package com.example.biblioteca.service;

import com.example.biblioteca.Dto.Libro;
import com.example.biblioteca.Dto.Prestamo;
import com.example.biblioteca.Repository.LibroRepository;
import com.example.biblioteca.Repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BibliotecaService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    public void agregarPrestamo(Long idLibro, Long idUsuario) {
        Libro libro = libroRepository.findById(idLibro).orElse(null);
        if (libro != null && libro.isDisponible()) {
            libro.setDisponible(false);
            libroRepository.save(libro);

            Prestamo prestamo = new Prestamo();
            prestamo.setIdLibro(idLibro);
            prestamo.setIdUsuario(idUsuario);
            prestamo.setFechaPrestamo(new Date());
            prestamoRepository.save(prestamo);
        }
    }

    public void devolverPrestamo(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElse(null);
        if (prestamo != null) {
            prestamo.setFechaDevolucion(new Date());
            prestamoRepository.save(prestamo);

            Libro libro = libroRepository.findById(prestamo.getIdLibro()).orElse(null);
            if (libro != null) {
                libro.setDisponible(true);
                libroRepository.save(libro);
            }
        }
    }
}
