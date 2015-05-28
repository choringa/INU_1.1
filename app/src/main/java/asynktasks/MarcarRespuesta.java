package asynktasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import co.com.instrasoft.inu_11.QuizActivity;
import conexion.ConexionConfig;
import conexion.ManejadorConexion;

/**
 * Created by Choringa on 20/04/15.
 */
public class MarcarRespuesta extends AsyncTask<Void, Void, Boolean>{


    //-------------------
    // ATRIBUTOS
    //-------------------

    /**
     * Atributo de dialogo de progreso.
     */
    private ProgressDialog dialogoProgreso;

    /**
     * Atributos requeridos del login
     */
    private int idQuestion, idUser, user_answer;
    /**
     * Atributo del contexto de donde viene
     */
    private QuizActivity quizAct;


    //-------------------
    // CONSTRUCTOR
    //-------------------

    public MarcarRespuesta(QuizActivity quizAct, int idQuestion, int idUser, int user_answer){
        this.idQuestion = idQuestion;
        this.idUser = idUser;
        this.user_answer = user_answer;
        this.quizAct = quizAct;
    }


    //-------------------
    // METODOS
    //-------------------

    @Override
    protected Boolean doInBackground(Void... params) {
        System.out.println("entra a marcar una respuesta");
        return marcarRespuesta();
    }

    @Override
    protected void onCancelled() {
        quizAct.showProgress(false);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        quizAct.showProgress(false);
    }

    public boolean marcarRespuesta() {

        String[] nombres = new String[] {ConexionConfig.PARAM_ID_USER, ConexionConfig.PARAM_ID_QUESTION, ConexionConfig.PARAM_USER_ANSWER};
        String[] params = new String[] { idUser + "", idQuestion +"", user_answer +""};
        ManejadorConexion server = new ConexionConfig();
        try {
            return  Boolean.parseBoolean(server.consumirServicio(ConexionConfig.METHOD_MARCAR_RESPUESTA, nombres, params).toString());
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }


}
