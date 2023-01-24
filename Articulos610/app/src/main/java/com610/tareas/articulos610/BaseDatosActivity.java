package com.grover101.misarticulos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class BaseDatosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String respuesta2;
    public static AlmacenArticulos almacen = new AlmacenArticulosJson();
    public static List<String> temporal;
    public static ImageLoader lectorImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_datos);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RequestQueue colaPeticiones= Volley.newRequestQueue(getApplicationContext());

        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<>(40);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });

        RequestQueue colaPeticiones2= Volley.newRequestQueue(getApplicationContext());
        StringRequest peticion = new StringRequest(Request.Method.GET, "https://mis-articulos-d3e65-default-rtdb.firebaseio.com/Articulos.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String respuesta) {
                Log.i("Respondio","OnResponse"+respuesta);
                try {
                    recuperaEvento2(1,respuesta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Respondio","OnError");
            }
        }
        );

        colaPeticiones2.add(peticion);
    }

    public void recuperaEvento2(Integer n, String respuesta) throws InterruptedException, ExecutionException, TimeoutException {
        respuesta2 = respuesta;
        MiTarea2 tarea = new MiTarea2();
        tarea.execute(n);
    }

    class MiTarea2 extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog progreso;
        public MiTarea2() {
        }
        @Override
        protected void onCancelled() {
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int res = 1;
            temporal = almacen.listaArticulos(1,respuesta2);
            return res;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Integer... porc) {
        }
        @Override
        protected void onPostExecute(Integer res) {
            AdaptadorArticulos adaptador = new AdaptadorArticulos(getApplicationContext(), temporal);
            recyclerView.setAdapter(adaptador);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        }
    }
}