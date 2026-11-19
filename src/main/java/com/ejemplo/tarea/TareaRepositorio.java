package com.ejemplo.tarea;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TareaRepositorio {

    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    public TareaRepositorio() {
        // Inicializar con algunas tareas de ejemplo
        guardar(new Tarea(0, "Agendar Java", false));
        guardar(new Tarea(0, "Practicar Spring Boot", false));
        guardar(new Tarea(0, "Subir el proyecto en Github", false));
    }

    public List<Tarea> buscarTodas() {
        return tareas;
    }

    public Optional<Tarea> buscarPorId(int id) {
        for (Tarea t : tareas) {
            if (t.getId() == id) {
                return Optional.of(t);
            }

        }
        return Optional.empty();
    }

    public Tarea guardar(Tarea tarea) {
        if (tarea.getId() == 0) {
            tarea.setId(siguienteId++);
            tareas.add(tarea);
        }
        return tarea;
    }

    public boolean eliminar(int id) {
        Optional<Tarea> encontrada = buscarPorId(id);
        if (encontrada.isPresent()) {
            tareas.remove(encontrada.get());
            return true;
        }
        return false;
    }
}