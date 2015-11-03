package asynktasks;

import android.os.AsyncTask;

import org.ksoap2.serialization.SoapObject;

import co.com.instrasoft.inu_11.ProfileActivity;
import co.com.instrasoft.inu_11.QuizActivity;
import conexion.ConexionConfig;
import conexion.ManejadorConexion;
import mundo.Question;


/**
 * Created by Choringa on 22/04/15.
 */
public class DarPregunta extends AsyncTask<Void, Void, Question> {

    //-------------------
    // ATRIBUTOS
    //-------------------

    /**
     * Atributos requeridos para pedir pregunta
     */
    private int idUser, type_user;
    /**
     * Atributo del contexto de donde viene
     */
    private ProfileActivity profileAct;
    private QuizActivity quizAct;


    //-------------------
    // CONSTRUCTOR
    //-------------------

    /**
     * Constructor
     * @param idUser el id del usuario
     * @param type_user el tipo de usuario al que pertenece
     */
    public DarPregunta(int idUser, int type_user, ProfileActivity profileAct, QuizActivity quizAct) {
        this.idUser = idUser;
        this.type_user = type_user;
        if(profileAct != null)
            this.profileAct = profileAct;
        if(quizAct != null)
            this.quizAct = quizAct;
    }



    @Override
    protected Question doInBackground(Void... params) {
        return darPregunta();
    }

    @Override
    protected void onCancelled() {
        if(profileAct != null)
            profileAct.quitarProgressDialog();
        if(quizAct != null)
            quizAct.quitarProgressDialog();
    }

    @Override
    protected void onPostExecute(Question result) {
        if(profileAct != null)
            profileAct.verifStartQuiz(result);

        if(quizAct != null)
            quizAct.veriSiguientePregunta(result);
    }

    public Question darPregunta() {
        Question verif = null;
        String[] nombres = new String[] {ConexionConfig.PARAM_ID_USER, ConexionConfig.PARAM_USER_TYPE};
        String[] params = new String[] { idUser+"", type_user+""};
        ManejadorConexion server = new ConexionConfig();
        try {
            SoapObject sapo = (SoapObject) server.consumirServicio2(ConexionConfig.METHOD_DAR_PREGUNTA, nombres, params).getProperty(0);
            int idQuestion = Integer.parseInt(sapo.getProperty("idQuestion").toString());
            String question = sapo.getProperty("question").toString();
            String answer1 = sapo.getProperty("answer1").toString();
            String answer2 = sapo.getProperty("answer2").toString();
            String answer3 = sapo.getProperty("answer3").toString();
            String answer4 = sapo.getProperty("answer4").toString();
            int answer = Integer.parseInt(sapo.getProperty("answer").toString());
            int question_user_type = Integer.parseInt(sapo.getProperty("user_type").toString());
            verif = new Question(idQuestion,question,answer1,answer2,answer3,answer4,answer,question_user_type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return verif;
    }
}
