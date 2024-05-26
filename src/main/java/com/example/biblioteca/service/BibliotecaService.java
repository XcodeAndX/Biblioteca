package com.example.biblioteca.service;

import com.example.biblioteca.Dto.Libro;
import com.example.biblioteca.Dto.Prestamo;
import com.example.biblioteca.Dto.Reserva;
import com.example.biblioteca.Repository.LibroRepository;
import com.example.biblioteca.Repository.PrestamoRepository;
import com.example.biblioteca.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BibliotecaService {



    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ReservaRepository reservaRepository;


    //Prestamos
    public void agregarPrestamo(Long idLibro, Long idUsuario) {
        Libro libro = libroRepository.findById(idLibro).orElse(null);
        if (libro != null && libro.isDisponible()) {
            libro.setDisponible(false);
            libroRepository.save(libro);

            Prestamo prestamo = new Prestamo(idLibro, idUsuario);
            prestamoRepository.save(prestamo);
        } else {
            throw new RuntimeException("El libro no está disponible para préstamo.");
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
                notificarReserva(libro);
            }
        } else {
            throw new RuntimeException("Préstamo no encontrado.");
        }
    }

    //Libros
    public void agregarLibro(Libro libro) {
        libroRepository.save(libro);
    }

    public Libro buscarPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }


    //Reservas
    public void agregarReserva(Long idLibro, Long idUsuario) {
        Libro libro = libroRepository.findById(idLibro).orElse(null);
        if (libro != null && !libro.isDisponible()) {
            Reserva reserva = new Reserva(idLibro, idUsuario);
            reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("El libro está disponible o no existe.");
        }
    }

    private void notificarReserva(Libro libro) {
        List<Reserva> reservas = reservaRepository.findByIdLibroAndNotificadoFalse(libro.getId());
        for (Reserva reserva : reservas) {
            // Lógica para notificar al usuario, por ejemplo, enviar un correo electrónico
            reserva.setNotificado(true);
            reservaRepository.save(reserva);
        }
    }
}
