package asynktasks;

import android.os.AsyncTask;

import co.com.instrasoft.inu_11.QuizActivity;
import conexion.ConexionConfig;
import conexion.ManejadorConexion;


/**
 * Created by Choringa on 22/04/15.
 */
public class DarPuntajeUsuario extends AsyncTask<Void, Void, Integer> {

    //-------------------
    // ATRIBUTOS
    //-------------------

    /**
     * Atributos requeridos para pedir pregunta
     */
    private int idUser;
    /**
     * Atributo del contexto de donde viene
     */
    private QuizActivity quizAct;


    //-------------------
    // CONSTRUCTOR
    //-------------------

    /**
     * Constructor
     * @param idUser el id del usuario
     */
    public DarPuntajeUsuario(int idUser, QuizActivity quizAct) {
        this.idUser = idUser;
        this.quizAct = quizAct;
    }



    @Override
    protected Integer doInBackground(Void... params) {
        return darPuntaje();
    }

    @Override
    protected void onCancelled() {
        quizAct.cambiarTextoPuntos("0");
    }

    @Override
    protected void onPostExecute(Integer result) {
        quizAct.cambiarTextoPuntos(result+"");
    }

    public int darPuntaje(){
        int respuesta = 0;
        String[] nombres = new String[] {ConexionConfig.PARAM_ID_USER};
        String[] params = new String[] { idUser+""};
        ManejadorConexion server = new ConexionConfig();
        try {
            String sapo = server.consumirServicio(ConexionConfig.METHOD_DAR_PUNTAJE, nombres, params).toString();
            respuesta = Integer.parseInt(sapo);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return respuesta;
    }

}
