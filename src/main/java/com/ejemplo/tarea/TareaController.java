package com.ejemplo.tarea;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    private final TareaService servicio;

    public TareaController(TareaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Tarea> listar() {
        return servicio.listar();
    }

    @GetMapping("/pendientes")
    public List<Tarea> pendientes() {
        return servicio.pendientes();
    }

    // Ejercicio 1
    @GetMapping("/resumen")
    public String resumen() {
        return servicio.resumen();
    }

    // Ejercicio 2
    @GetMapping("/buscar/{texto}")
    public List<Tarea> buscar(@PathVariable String texto) {
        return servicio.buscarPorTitulo(texto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtener(@PathVariable int id) {
        Optional<Tarea> tarea = servicio.buscarPorId(id);
        if (tarea.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarea.get());
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea nueva) {
        Optional<Tarea> creada = servicio.crear(nueva);
        if (creada.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creada.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable int id, @RequestBody Tarea datos) {
        Optional<Tarea> actualizada = servicio.actualizar(id, datos);
        if (actualizada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}