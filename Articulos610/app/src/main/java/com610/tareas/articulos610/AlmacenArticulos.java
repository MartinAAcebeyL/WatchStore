package com610.tareas.articulos610;


import java.util.List;

public interface AlmacenArticulos {
    void guardarArticulos(String titulo, String detalle, String fecha, String imagen);
    List<String> listaArticulos(int i, String respuesta);
}