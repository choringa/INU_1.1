package co.com.instrasoft.inu_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import mundo.UsuarioPuntaje;

/**
 * Created by Choringa on 24/09/15.
 */
public class CustomListAdapterStatistics extends ArrayAdapter<UsuarioPuntaje> {

    private List<UsuarioPuntaje> lista;
    private Context context;

    private static final String TAG = "CustomListAdapterStatistics";

    public CustomListAdapterStatistics(Context context, int resource, List<UsuarioPuntaje> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        DataHolder holder = new DataHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            v = layoutInflater.inflate(R.layout.list_item_statistics, parent, false);
            TextView tvNombre = (TextView) v.findViewById(R.id.tvNombreUsuario);
            TextView tvPuntaje = (TextView) v.findViewById(R.id.tvPuntajeUsuario);
            RelativeLayout rl_container= (RelativeLayout) v.findViewById(R.id.relativeLayoutContact);

            holder.tvNombreUsuario = tvNombre;
            holder.tvPuntajeUsuario = tvPuntaje;
            holder.relativeLayout = rl_container;
            v.setTag(holder);

        }
        else
        {
            holder = (DataHolder)v.getTag();
        }
        final UsuarioPuntaje userTemp =  lista.get(position);
        holder.tvNombreUsuario.setText(userTemp.getNombre());
        holder.tvPuntajeUsuario.setText(userTemp.getPoints() + "");
        return v;
    }

    private  class DataHolder
    {
        private TextView tvNombreUsuario;
        private TextView  tvPuntajeUsuario;
        private RelativeLayout relativeLayout;
    }
}
