package co.com.instrasoft.inu_11;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import asynktasks.DarPregunta;
import asynktasks.MarcarRespuesta;
import mundo.Question;
import mundo.Usuario;


public class QuizActivity extends ActionBarActivity {

    private TextView textoPreguntaNumero;
    private TextView textoPreguntaPregunta;
    private RadioGroup radioGroupRespuestas;
    private RadioButton rbAnswer1;
    private RadioButton rbAnswer2;
    private RadioButton rbAnswer3;
    private RadioButton rbAnswer4;
    private View mQuizFormView;
    private View mProgressView;

    private Usuario usuario;
    private Question pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuarioVerificado");
        pregunta = (Question) getIntent().getExtras().getSerializable("pregunta");
        mQuizFormView = findViewById(R.id.quizFormView);
        mProgressView = findViewById(R.id.progreso_quiz);

        final Button enviarQuiz = (Button)findViewById(R.id.btnEnviarRespuesta);
        enviarQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarQuiz();
            }
        });

        final Button btnCancelQuiz = (Button) findViewById(R.id.btnCancelQuiz);
        btnCancelQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAPerfil();
            }
        });

        iniciarTextos();
        setearTextos();
    }

    /**
     * Metodo para envair la respuesta del usuario
     */
    private void enviarQuiz() {
        int resp = 0;
        RadioButton respuestaSeleccionada = (RadioButton)findViewById(radioGroupRespuestas.getCheckedRadioButtonId());
        if(respuestaSeleccionada.getText().equals(rbAnswer1.getText())){
            resp = 1;
        }
        else if(respuestaSeleccionada.getText().equals(rbAnswer2.getText())){
            resp = 2;
        }
        else if(respuestaSeleccionada.getText().equals(rbAnswer3.getText())){
            resp = 3;
        }
        else if(respuestaSeleccionada.getText().equals(rbAnswer4.getText())){
            resp = 4;
        }

        if(resp != 0){
            showProgress(true);
            try {
                boolean a = new MarcarRespuesta(this, pregunta.getIdQuestion(), usuario.getId(), resp).execute().get();
                if(a){

                    Question nPregunta = new DarPregunta(usuario.getId(), usuario.getType_user(), null, this).execute().get();
                    if(nPregunta != null){
                        pregunta = nPregunta;
                        setearTextos();
                        Toast toast = Toast.makeText(this, getString(R.string.send_answer_correct), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        new AlertDialog.Builder(this)
                                .setTitle("ALERT")
                                .setMessage(getString(R.string.no_questions))
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                irAPerfil();

                                            }
                                        }).show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(this, getString(R.string.send_answer_incorrect), Toast.LENGTH_LONG);
                    toast.show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        else{
            Toast toast = Toast.makeText(this, "You must choose an answer", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Metodo que redirige a perfil
     */
    private void irAPerfil() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("usuarioVerificado", usuario.getUsuario_string());
        startActivity(intent);
    }

    private void iniciarTextos() {
        textoPreguntaNumero = (TextView) findViewById(R.id.textoPreguntaNumero);
        textoPreguntaPregunta = (TextView) findViewById(R.id.textoPreguntaPregunta);
        radioGroupRespuestas = (RadioGroup) findViewById(R.id.radioGroupRespuestas);
        rbAnswer1 = (RadioButton) findViewById(R.id.rbRespuesta1);
        rbAnswer2 = (RadioButton) findViewById(R.id.rbRespuesta2);
        rbAnswer3 = (RadioButton) findViewById(R.id.rbRespuesta3);
        rbAnswer4 = (RadioButton) findViewById(R.id.rbRespuesta4);


    }

    private void setearTextos(){
        textoPreguntaNumero.setText(getString(R.string.question_number) + " " + pregunta.getIdQuestion());
        textoPreguntaPregunta.setText(pregunta.getQuestion());

        rbAnswer1.setText(pregunta.getAnswer1());
        rbAnswer2.setText(pregunta.getAnswer2());
        rbAnswer3.setText(pregunta.getAnswer3());
        rbAnswer4.setText(pregunta.getAnswer4());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            mQuizFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mQuizFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mQuizFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mQuizFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
