package co.com.instrasoft.inu_11;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import asynktasks.DarPregunta;
import mundo.Question;
import mundo.Usuario;


public class ProfileActivity extends ActionBarActivity {

    /**
     * Atributo de usuario cargado en extras
     */
    private Usuario usuario;

    private View mPerfilFormView;

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String usuarioString = getIntent().getExtras().getString("usuarioVerificado");
        System.out.println("usuarioString desde profile:" + usuarioString);
        String[] usuarioArray = usuarioString.split(";");
        usuario = new Usuario(Integer.parseInt(usuarioArray[0]), usuarioArray[1], usuarioArray[2], Integer.parseInt(usuarioArray[3]), Integer.parseInt(usuarioArray[4]), usuarioString);
        TextView nombreView = (TextView)findViewById(R.id.text_name);
        nombreView.setText(usuario.getName());
        mPerfilFormView = findViewById(R.id.perfilForm);
        mProgressView = findViewById(R.id.progress_perfil);

        final Button iniciarQuiz = (Button)findViewById(R.id.btnStartQuiz);
        iniciarQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        final Button statistics = (Button)findViewById(R.id.btnStatistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatistics();
            }
        });


        Button logout = (Button)findViewById(R.id.btnLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_start_quiz_menu:
                startQuiz();
                return true;
            case R.id.action_statistics_menu:
                showStatistics();
                return true;
            case R.id.action_log_out_menu:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo para empezar la actividad de quiz
     */
    public void startQuiz(){
        System.out.println("Inicia a pedir un nuevo quiz");
        try {
            showProgress(true);
            Question pregunta = new DarPregunta(usuario.getId(), usuario.getType_user(), this, null).execute().get();
            if(pregunta != null) {
                Intent quizIntent = new Intent(this, QuizActivity.class);
                quizIntent.putExtra("usuarioVerificado", usuario);
                quizIntent.putExtra("pregunta", pregunta);
                startActivity(quizIntent);
            }
            else{
                Toast toast = Toast.makeText(this, getString(R.string.no_questions), Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para ver las estadisticas del usuario
     */
    public void showStatistics(){
        Toast toast = Toast.makeText(this, "Sorry. Not available yet.", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Metodo que termina la sesion del usuario actual
     */
    public void logout(){
        Intent inte = new Intent(this, LoginActivity.class);
        startActivity(inte);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mPerfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mPerfilFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPerfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mPerfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
