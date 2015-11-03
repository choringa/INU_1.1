package co.com.instrasoft.inu_11;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import asynktasks.DarPuntajes;
import mundo.UsuarioPuntaje;

public class StatisticsActivity extends AppCompatActivity {

    private ListView listView;

    private CustomListAdapterStatistics customAdapter;

    private ArrayList<UsuarioPuntaje> lista;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        listView = (ListView) findViewById(R.id.listView);
        lista = new ArrayList<>();
        customAdapter = new CustomListAdapterStatistics(StatisticsActivity.this, R.layout.list_item_statistics, lista);
        listView.setAdapter(customAdapter);

        fillListData();
    }

    private void fillListData() {
        //La ruedita
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Retrieving data, please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();


        new DarPuntajes(this,lista).execute();

    }

    public void quitarProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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

    public void cambiarLista(ArrayList<UsuarioPuntaje> listaNueva){
        lista = listaNueva;
        customAdapter.notifyDataSetChanged();
        quitarProgressDialog();
    }

}

