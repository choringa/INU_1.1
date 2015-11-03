package asynktasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import co.com.instrasoft.inu_11.StatisticsActivity;
import conexion.ConexionConfig;
import conexion.ManejadorConexion;
import mundo.UsuarioPuntaje;


/**
 * Created by Choringa on 22/04/15.
 */
public class DarPuntajes extends AsyncTask<Void, Void, ArrayList<UsuarioPuntaje>> {

    //-------------------
    // ATRIBUTOS
    //-------------------

    /**
     * Atributo del contexto de donde viene
     */
    private StatisticsActivity statsAct;

    private ArrayList<UsuarioPuntaje> lista;

    private static final String TAG = "DarPuntajes";


    //-------------------
    // CONSTRUCTOR
    //-------------------

    /**
     * Constructor
     */
    public DarPuntajes(StatisticsActivity statsAct, ArrayList<UsuarioPuntaje> lista) {
        this.statsAct = statsAct;
        this.lista = lista;
    }



    @Override
    protected ArrayList<UsuarioPuntaje> doInBackground(Void... params) {
        return darPuntajes();
    }

    @Override
    protected void onCancelled() {
        statsAct.quitarProgressDialog();
    }

    @Override
    protected void onPostExecute(ArrayList<UsuarioPuntaje> result) {
        statsAct.cambiarLista(result);
    }



    private ArrayList<UsuarioPuntaje> darPuntajes() {
        String[] nombres = new String[] { };
        String[] params = new String[] { };
        ManejadorConexion server = new ConexionConfig();
        try {
            ArrayList<SoapObject> respObjetos = server.consumirServicioLista(ConexionConfig.METHOD_DAR_USUARIOS_PUNTAJE, nombres, params);
            for (SoapObject i : respObjetos){
                Log.i(TAG, "0:" + i.getProperty(0) + " ,1: " + i.getProperty(1));
                UsuarioPuntaje temp = new UsuarioPuntaje(Integer.parseInt(i.getProperty("idUsuario").toString()), Integer.parseInt(i.getProperty("points").toString()), i.getProperty("nombreUsuario").toString());
                lista.add(temp);
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lista;
    }



}
