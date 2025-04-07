package online.marcosvirgilio.mobile.seg.ui.credencial;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import online.marcosvirgilio.mobile.seg.R;
import online.marcosvirgilio.mobile.seg.model.Credencial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadCredencialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadCredencialFragment extends Fragment
        implements View.OnClickListener, Response.ErrorListener, Response.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    //objetos de tela
    private Spinner spGrantType;
    private EditText etTokenEndpoint;
    private EditText etClientID;
    private EditText etClientSecret;
    private Button btGetToken;
    //volley
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectReq;


    public CadCredencialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadCredencialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadCredencialFragment newInstance(String param1, String param2) {
        CadCredencialFragment fragment = new CadCredencialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_cad_credencial, container, false);
        //Binding
        this.etClientID = view.findViewById(R.id.etClientID);
        this.etClientSecret = view.findViewById(R.id.etClientSecret);
        this.etTokenEndpoint = view.findViewById(R.id.etTokenEndpoint);
        this.spGrantType = view.findViewById(R.id.spGrantType);
        this.btGetToken = view.findViewById(R.id.btGetToken);
        //definindo o listener do botão
        this.btGetToken.setOnClickListener(this);
        //instanciando a fila de requests - caso o objeto seja o view
        this.requestQueue = Volley.newRequestQueue(view.getContext());
        //inicializando a fila de requests do SO
        this.requestQueue.start();
        //retorno da função
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btGetToken){
            try {
                //instanciar meu objeto de negócio
                Credencial credencial = new Credencial();
                //pegar dados da tela e por no objeto
                credencial.setClientScret(this.etClientSecret.getText().toString());
                credencial.setClientID(this.etClientID.getText().toString());
                credencial.setEndpoint(this.etTokenEndpoint.getText().toString());
                credencial.setGrantType(this.spGrantType.getSelectedItem().toString());
                //REQUEST VOLLEY AQUI !!!!!!!
                jsonObjectReq = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://10.0.2.2:8080/seg/cadusuario.php",
                        credencial.toJsonObject(), this, this);
                requestQueue.add(jsonObjectReq);
                //mensagem de sucesso
                Toast.makeText(view.getContext(), "Sucesso!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Snackbar mensagem = Snackbar.make(view,
                "Ops! Houve um problema ao realizar o cadastro: " +
                        error.toString(),Snackbar.LENGTH_LONG);
        mensagem.show();
    }

    @Override
    public void onResponse(Object response) {
        try {
            //instanciando objeto para manejar o JSON que recebemos
            JSONObject json = new JSONObject(response.toString());
            //contexto e text são para a mensagem na tela o Toast
            Context contexto = view.getContext();
            //pegando mensagem que veio do json
            CharSequence mensagem = json.getString("message");
            //duração da mensagem na tela
            int duracao = Toast.LENGTH_SHORT;
            //verificando se salvou sem erro para limpar campos da tela
            if (json.getBoolean("success")){
                //limpar campos da tela
                this.etClientID.setText("");
                this.etClientSecret.setText("");
                this.etTokenEndpoint .setText("");
                //selecionando primeiro item dos spinners
                this.spGrantType.setSelection(0);
            }
            //mostrando a mensagem que veio do JSON
            Toast toast = Toast.makeText (contexto, mensagem, duracao);
            toast.show();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}