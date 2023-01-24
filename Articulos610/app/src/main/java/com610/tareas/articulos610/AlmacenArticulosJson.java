package com.grover101.misarticulos;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlmacenArticulosJson  implements AlmacenArticulos {

    private String string; //Almacena puntuaciones en formato JSON
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Articulos>>() {}.getType();
    private String respuesta;

    public AlmacenArticulosJson() {
    }


    @Override
    public void guardarArticulos(String titulo, String detalle, String fecha, String imagen) {

    }

    @Override
    public List<String> listaArticulos(int cantidad,String respuesta) {
        this.respuesta = respuesta; //
        // string = leerString();

        Log.i("Respondio lista ..",respuesta);

        ArrayList<Articulos> listaarticulos;
        if (respuesta == null) {
            listaarticulos = new ArrayList<>();
        } else {
            listaarticulos = gson.fromJson(respuesta, type);
            Log.i("Respondio gson",listaarticulos.toString());
        }

        List<String> salida = new ArrayList<>();
        for (Articulos articulo : listaarticulos) {
            if(articulo == null) {Log.i("Respondio","Es nullo");}else {
                salida.add(articulo.getNombre() + "," + articulo.getDescripcion()+","+articulo.getCosto()+","+articulo.getImagen());
                Log.i("Respondio SAlida", String.valueOf(salida));}
        }
        return salida;
    }
}
