package com.ejemplo.tarea;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepositorio repositorio;
    private final NotificadorService notificador;

    // Spring entrega aquí el repositorio y el notificador (inyección por constructor)
    public TareaService(TareaRepositorio repositorio, NotificadorService notificador) {
        this.repositorio = repositorio;
        this.notificador = notificador;
    }

    public List<Tarea> listar() {
        return repositorio.buscarTodas();
    }

    public Optional<Tarea> buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    // Regla: el título es obligatorio y debe tener al menos 3 caracteres (Paso 7)
    public Optional<Tarea> crear(Tarea nueva) {
        if (nueva.getTitulo() == null || nueva.getTitulo().trim().length() < 3) {
            return Optional.empty();
        }
        nueva.setId(0); // el id siempre lo asigna el repositorio
        Tarea guardada = repositorio.guardar(nueva);
        notificador.notificador("Tarea creada: " + guardada.getTitulo()); // Ejercicio 3
        return Optional.of(guardada);
    }

    public Optional<Tarea> actualizar(int id, Tarea datos) {
        Optional<Tarea> existente = repositorio.buscarPorId(id);
        if (existente.isEmpty()) {
            return Optional.empty();
        }
        Tarea tarea = existente.get();
        tarea.setTitulo(datos.getTitulo());
        tarea.setCompletada(datos.isCompletada());
        return Optional.of(tarea);
    }

    public boolean eliminar(int id) {
        return repositorio.eliminar (id);
    }

    public List<Tarea> pendientes() {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea t : repositorio.buscarTodas()) {
            if (!t.isCompletada()) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    // Ejercicio 1 (básico): "Total: 3, completadas: 1, pendientes: 2"
    public String resumen() {
        int total = 0;
        int completadas = 0;
        for (Tarea t : repositorio.buscarTodas()) {
            total++;
            if (t.isCompletada()) {
                completadas++;
            }
        }
        int pendientes = total - completadas;
        return "Total: " + total + ", completadas: " + completadas + ", pendientes: " + pendientes;
    }

    // Ejercicio 2 (intermedio): título contiene el texto, sin distinguir mayúsculas
    public List<Tarea> buscarPorTitulo(String texto) {
        List<Tarea> resultado = new ArrayList<>();
        String buscado = texto.toLowerCase();
        for (Tarea t : repositorio.buscarTodas()) {
            if (t.getTitulo().toLowerCase().contains(buscado)) {
                resultado.add(t);
            }
        }
        return resultado;
    }
}