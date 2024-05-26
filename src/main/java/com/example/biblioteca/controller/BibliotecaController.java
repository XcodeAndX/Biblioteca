package com.example.biblioteca.controller;


import com.example.biblioteca.Dto.Libro;
import com.example.biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @PostMapping("/libros")
    public ResponseEntity<String> agregarLibro(@RequestBody Libro libro) {
        bibliotecaService.agregarLibro(libro);
        return new ResponseEntity<>("Libro agregado correctamente", HttpStatus.CREATED);
    }

    @GetMapping("/libros/{titulo}")
    public ResponseEntity<Libro> buscarLibroPorTitulo(@PathVariable String titulo) {
        Libro libro = bibliotecaService.buscarPorTitulo(titulo);
        if (libro != null) {
            return new ResponseEntity<>(libro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/prestamos")
    public ResponseEntity<String> realizarPrestamo(@RequestParam Long idLibro, @RequestParam Long idUsuario) {
        bibliotecaService.agregarPrestamo(idLibro, idUsuario);
        return new ResponseEntity<>("Préstamo realizado correctamente", HttpStatus.CREATED);
    }

    @PutMapping("/prestamos/{idPrestamo}")
    public ResponseEntity<String> devolverPrestamo(@PathVariable Long idPrestamo) {
        bibliotecaService.devolverPrestamo(idPrestamo);
        return new ResponseEntity<>("Préstamo devuelto correctamente", HttpStatus.OK);
    }

    

}
