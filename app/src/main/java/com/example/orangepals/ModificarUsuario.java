package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orangepals.interfaces.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarUsuario extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String [] paises = {
            "Afganistán",
            "Albania",
            "Alemania",
            "Andorra",
            "Angola",
            "Antigua y Barbuda",
            "Arabia Saudita",
            "Argelia",
            "Argentina",
            "Armenia",
            "Australia",
            "Austria",
            "Azerbaiyán",
            "Bahamas",
            "Bahrein",
            "Bangladesh",
            "Barbados",
            "Belarús",
            "Belice",
            "Benin",
            "Bhután",
            "Bolivia (Estado Plurinacional de)",
            "Bosnia y Herzegovina",
            "Botswana",
            "Brasil",
            "Brunei Darussalam",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Bélgica",
            "Cabo Verde",
            "Camboya",
            "Camerún",
            "Canadá",
            "Chad",
            "Chequia",
            "Chile",
            "China",
            "Chipre",
            "Colombia",
            "Comoras",
            "Congo",
            "Costa Rica",
            "Croacia",
            "Cuba",
            "Côte d'Ivoire",
            "Dinamarca",
            "Djibouti",
            "Dominica",
            "Ecuador",
            "Egipto",
            "El Salvador",
            "Emiratos Árabes Unidos",
            "Eritrea",
            "Eslovaquia",
            "Eslovenia",
            "España",
            "Estados Unidos de América",
            "Estonia",
            "Eswatini",
            "Etiopía",
            "Federación de Rusia",
            "Fiji",
            "Filipinas",
            "Finlandia",
            "Francia",
            "Gabón",
            "Gambia",
            "Georgia",
            "Ghana",
            "Granada",
            "Grecia",
            "Guatemala",
            "Guinea",
            "Guinea Ecuatorial",
            "Guinea-Bissau",
            "Guyana",
            "Haití",
            "Honduras",
            "Hungría",
            "India",
            "Indonesia",
            "Iraq",
            "Irlanda",
            "Irán (República Islámica del)",
            "Islandia",
            "Islas Cook",
            "Islas Feroe",
            "Islas Marshall",
            "Islas Salomón",
            "Israel",
            "Italia",
            "Jamaica",
            "Japón",
            "Jordania",
            "Kazajstán",
            "Kenya",
            "Kirguistán",
            "Kiribati",
            "Kuwait",
            "Lesotho",
            "Letonia",
            "Liberia",
            "Libia",
            "Lituania",
            "Luxemburgo",
            "Líbano",
            "Macedonia del Norte",
            "Madagascar",
            "Malasia",
            "Malawi",
            "Maldivas",
            "Malta",
            "Malí",
            "Marruecos",
            "Mauricio",
            "Mauritania",
            "Micronesia (Estados Federados de)",
            "Mongolia",
            "Montenegro",
            "Mozambique",
            "Myanmar",
            "México",
            "Mónaco",
            "Namibia",
            "Nauru",
            "Nepal",
            "Nicaragua",
            "Nigeria",
            "Niue",
            "Noruega",
            "Nueva Zelandia",
            "Níger",
            "Omán",
            "Pakistán",
            "Palau",
            "Panamá",
            "Papua Nueva Guinea",
            "Paraguay",
            "Países Bajos",
            "Perú",
            "Polonia",
            "Portugal",
            "Qatar",
            "Reino Unido de Gran Bretaña e Irlanda del Norte",
            "República Centroafricana",
            "República Democrática Popular Lao",
            "República Democrática del Congo",
            "República Dominicana",
            "República Popular Democrática de Corea",
            "República Unida de Tanzanía",
            "República de Corea",
            "República de Moldova",
            "República Árabe Siria",
            "Rumania",
            "Rwanda",
            "Saint Kitts y Nevis",
            "Samoa",
            "San Marino",
            "San Vicente y las Granadinas",
            "Santa Lucía",
            "Santo Tomé y Príncipe",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leona",
            "Singapur",
            "Somalia",
            "Sri Lanka",
            "Sudáfrica",
            "Sudán",
            "Sudán del Sur",
            "Suecia",
            "Suiza",
            "Suriname",
            "Tailandia",
            "Tayikistán",
            "Timor-Leste",
            "Togo",
            "Tokelau",
            "Tonga",
            "Trinidad y Tabago",
            "Turkmenistán",
            "Turquía",
            "Tuvalu",
            "Túnez",
            "Ucrania",
            "Uganda",
            "Uruguay",
            "Uzbekistán",
            "Vanuatu",
            "Venezuela (República Bolivariana de)",
            "Viet Nam",
            "Yemen",
            "Zambia",
            "Zimbabwe"
    };
    private RequestsHelp rh = new RequestsHelp();
    EditText edUsername, edEmail;
    Button edTerminar;
    Spinner pais;
    Integer seleccionado = null;
    private JSONObject udata;
    private String usrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_usuario);

        pais = (Spinner) findViewById(R.id.selectPais);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModificarUsuario.this,
                android.R.layout.simple_spinner_item, paises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pais.setAdapter(adapter);
        pais.setOnItemSelectedListener(this);

        edUsername = (EditText) findViewById(R.id.edUsername);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edTerminar = (Button) findViewById(R.id.edTerminar);
        edTerminar.setOnClickListener(this);

        getUsuario(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject r;
                try {
                    r = new JSONObject(response.trim());
                    edUsername.setText(r.getString("USERNAME"));
                    edEmail.setText(r.getString("EMAIL"));
                    seleccionado = Integer.parseInt(r.getString("PAIS_ID"));
                    pais.setSelection(seleccionado - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }

    public void getUsuario(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/get_user/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarUsuario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(ModificarUsuario.this);
        rq.add(sr);
    }

    @Override
    public void onClick(View v) {
        // edTerminar
        switch (v.getId()){
            case R.id.edTerminar:
                enviarUsuario(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject r;
                        try {
                            r = new JSONObject(response.trim());
                            Toast.makeText(ModificarUsuario.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                            Intent endModUser = new Intent(ModificarUsuario.this, DetalleUsuario.class);
                            startActivity(endModUser);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        seleccionado = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void enviarUsuario(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/update_user/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarUsuario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("USERNAME", edUsername.getText().toString());
                params.put("EMAIL", edEmail.getText().toString());
                params.put("PAIS", seleccionado.toString());
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(ModificarUsuario.this);
        rq.add(sr);
    }
}