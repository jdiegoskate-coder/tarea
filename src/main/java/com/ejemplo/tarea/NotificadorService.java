package com.ejemplo.tarea;

import org.springframework.stereotype.Service;

@Service
public class NotificadorService {

    public void notificador(String mensaje) {
        System.out.println("[NOTIFICACION]" + mensaje);
    }
}