package co.com.instrasoft.inu_11;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import asynktasks.DarPregunta;
import asynktasks.DarPuntajeUsuario;
import asynktasks.MarcarRespuesta;
import mundo.Question;
import mundo.Usuario;


public class QuizActivity extends ActionBarActivity {

    private TextView textoPreguntaNumero;
    private TextView textoPreguntaPregunta;
    private TextView tvPoints;
    private RadioGroup radioGroupRespuestas;
    private RadioButton rbAnswer1;
    private RadioButton rbAnswer2;
    private RadioButton rbAnswer3;
    private RadioButton rbAnswer4;
    private View mQuizFormView;
    private View mProgressView;
    private Button enviarQuiz;
    private Button btnCancelQuiz;
    private Button btnNextQuestion;

    private Usuario usuario;
    private Question pregunta;

    //Counter de los segundos
    private int count;
    private Timer timer;

    private ProgressDialog progressDialog;

    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuarioVerificado");
        pregunta = (Question) getIntent().getExtras().getSerializable("pregunta");
        mQuizFormView = findViewById(R.id.quizFormView);
        mProgressView = findViewById(R.id.progreso_quiz);
        tvPoints = (TextView) findViewById(R.id.tvPoints);

        enviarQuiz = (Button) findViewById(R.id.btnEnviarRespuesta);
        enviarQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarQuiz();
            }
        });

        btnCancelQuiz = (Button) findViewById(R.id.btnCancelQuiz);
        btnCancelQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAPerfil();
            }
        });

        btnNextQuestion = (Button) findViewById(R.id.btnNextQuestion);
        btnNextQuestion.setEnabled(false);
        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    siguientePregunta();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tvPoints.setText("Loading points...");
        new DarPuntajeUsuario(usuario.getId(),this).execute();
        iniciarTextos();
        setearTextos();
    }

    /**
     * Metodo que cuenta los segundos que se demora el usuario en contestar la pregunta
     */
    private void startTimer() {
        count = 0;

        //Timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        count++;
//                    }
//                });
                count++;
            }
        }, 1000, 1000);

    }

    /**
     * Metodo para dar la siguiente pregunta
     */
    private void siguientePregunta() throws ExecutionException, InterruptedException {
        if (enviarQuiz.isEnabled()) {
            Toast toast = Toast.makeText(this, "You must send your answer first", Toast.LENGTH_LONG);
            toast.show();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Wait Please");
            progressDialog.setMessage("Retrieving quiz information");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            new DarPregunta(usuario.getId(), usuario.getType_user(), null, this).execute();
        }
    }

    public void quitarProgressDialog(){
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void veriSiguientePregunta(Question nPregunta){

        quitarProgressDialog();
        if (nPregunta != null) {
            pregunta = nPregunta;
            setearTextos();
            radioGroupRespuestas.setEnabled(true);
            btnNextQuestion.setEnabled(false);
            enviarQuiz.setEnabled(true);
            new DarPuntajeUsuario(usuario.getId(),this).execute();

        } else {
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

    /**
     * Metodo para envair la respuesta del usuario
     */
    private void enviarQuiz() {
        int resp = 0;
        RadioButton respuestaSeleccionada = (RadioButton)findViewById(radioGroupRespuestas.getCheckedRadioButtonId());
        if(respuestaSeleccionada != null) {
            if (respuestaSeleccionada.getText().equals(rbAnswer1.getText())) {
                resp = 1;
                if (pregunta.getAnswer() == resp)
                    rbAnswer1.setTextColor(Color.GREEN);
                else
                    rbAnswer1.setTextColor(Color.RED);
            } else if (respuestaSeleccionada.getText().equals(rbAnswer2.getText())) {
                resp = 2;
                if (pregunta.getAnswer() == resp)
                    rbAnswer2.setTextColor(Color.GREEN);
                else
                    rbAnswer2.setTextColor(Color.RED);
            } else if (respuestaSeleccionada.getText().equals(rbAnswer3.getText())) {
                resp = 3;
                if (pregunta.getAnswer() == resp)
                    rbAnswer3.setTextColor(Color.GREEN);
                else
                    rbAnswer3.setTextColor(Color.RED);
            } else if (respuestaSeleccionada.getText().equals(rbAnswer4.getText())) {
                resp = 4;
                if (pregunta.getAnswer() == resp)
                    rbAnswer4.setTextColor(Color.GREEN);
                else
                    rbAnswer4.setTextColor(Color.RED);
            }

            //Pone la respuesta en verde
            if (pregunta.getAnswer() == 1)
                rbAnswer1.setTextColor(Color.GREEN);
            else if (pregunta.getAnswer() == 2)
                rbAnswer2.setTextColor(Color.GREEN);
            else if (pregunta.getAnswer() == 3)
                rbAnswer3.setTextColor(Color.GREEN);
            else if (pregunta.getAnswer() == 4)
                rbAnswer4.setTextColor(Color.GREEN);

            radioGroupRespuestas.setEnabled(false);
            timer.cancel();

            if (resp != 0) {
                    int points = calcularPuntaje(resp);
                    Toast toastP = Toast.makeText(this, points + " POINTS.", Toast.LENGTH_LONG);
                    toastP.show();
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Sending quiz response");
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    new MarcarRespuesta(this, pregunta.getIdQuestion(), usuario.getId(), resp, points).execute();

            }
        }
        else{
            Toast toast = Toast.makeText(this, "You must choose an answer", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void verifMarcarRespuesta(boolean a){
        quitarProgressDialog();
        if (a) {
            Toast toast = Toast.makeText(this, getString(R.string.send_answer_correct), Toast.LENGTH_SHORT);
            toast.show();

            btnNextQuestion.setEnabled(true);
            enviarQuiz.setEnabled(false);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.send_answer_incorrect), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Metodo que calcula el puntaje dependiendo del tiempo que el usuario se ha tomado para contestar la respuesta
     * @param resp la respuesta que dio el usuario
     * @return el puntaje como entero
     */
    private int calcularPuntaje(int resp) {

        int points = 0;
        if(resp != pregunta.getAnswer())
            points = -6;
        else{
            if(count < 10)
                points = 10;
            else if(count < 20)
                points = 9;
            else if(count < 30)
                points = 8;
            else if(count < 40)
                points = 7;
            else if(count < 50)
                points = 6;
            else if(count < 60)
                points = 5;
            else if(count < 70)
                points = 4;
            else if(count < 80)
                points = 3;
            else if(count < 90)
                points = 2;
            else if(count < 90)
                points = 1;
        }
        return points;
    }

    /**
     * Metodo que redirige a perfil
     */
    private void irAPerfil() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("usuarioVerificado", usuario.getUsuario_string());
        if(timer != null)
            timer.cancel();
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

        radioGroupRespuestas.clearCheck();

        rbAnswer1.setText(pregunta.getAnswer1());
        rbAnswer2.setText(pregunta.getAnswer2());
        rbAnswer3.setText(pregunta.getAnswer3());
        rbAnswer4.setText(pregunta.getAnswer4());

        rbAnswer1.setTextColor(Color.BLACK);
        rbAnswer2.setTextColor(Color.BLACK);
        rbAnswer3.setTextColor(Color.BLACK);
        rbAnswer4.setTextColor(Color.BLACK);

        if(timer != null)
            timer.cancel();
        startTimer();

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

    public void cambiarTextoPuntos(String puntos){
        tvPoints.setText(" Total Points: " + puntos);
    }

}
