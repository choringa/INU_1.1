package asynktasks;

import android.os.AsyncTask;

import co.com.instrasoft.inu_11.LoginActivity;
import conexion.ConexionConfig;
import conexion.ManejadorConexion;

/**
 * Created by Choringa on 20/04/15.
 */
public class Login extends AsyncTask<Void, Void, String>{


    //-------------------
    // ATRIBUTOS
    //-------------------

    /**
     * Atributo de dialogo de progreso.
     */

    /**
     * Atributos requeridos del login
     */
    private String username, password;
    /**
     * Atributo del contexto de donde viene
     */
    private LoginActivity loginAct;


    //-------------------
    // CONSTRUCTOR
    //-------------------

    public Login(LoginActivity loginAct, String username, String password){
        this.username = username;
        this.password = password;
        this.loginAct = loginAct;
    }


    //-------------------
    // METODOS
    //-------------------

    @Override
    protected String doInBackground(Void... params) {
        return darUser();
    }

    @Override
    protected void onCancelled() {
        loginAct.showProgress(false);
    }


    @Override
    protected void onPostExecute(String result){
        loginAct.loginResponse(result);
    }

    /**
     * Verifica la autenticidad del usuario
     * @return la respuesta del servidor
     */
    public String darUser() {
        String verif = "";
        String[] nombres = new String[] {ConexionConfig.PARAM_USERNAME, ConexionConfig.PARAM_PASSWORD};
        String[] params = new String[] { username, password};
        ManejadorConexion server = new ConexionConfig();
        try {
            verif = server.consumirServicio(ConexionConfig.METHOD_LOGIN, nombres, params).toString();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return verif;
    }


}
