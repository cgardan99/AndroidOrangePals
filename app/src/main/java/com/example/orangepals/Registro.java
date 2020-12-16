package com.example.orangepals;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orangepals.adapters.MainAdapter;
import com.example.orangepals.interfaces.VolleyCallback;
import com.example.orangepals.models.Publicacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button regresar_interfaz, terminar_registro;
    Spinner pais;
    Integer seleccionado = null;
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
    EditText usUsername, usEmail, usPwd1, usPwd2;
    Boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        regresar_interfaz = (Button) findViewById(R.id.regresar);
        regresar_interfaz.setOnClickListener(this);

        terminar_registro = (Button) findViewById(R.id.btn_terminar_registro);
        terminar_registro.setOnClickListener(this);

        pais = (Spinner) findViewById(R.id.selectPais);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Registro.this,
                android.R.layout.simple_spinner_item, paises);
        usUsername = findViewById(R.id.usUsername);
        usEmail = findViewById(R.id.usEmail);
        usPwd1 = findViewById(R.id.usPwd1);
        usPwd2 = findViewById(R.id.usPwd2);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pais.setAdapter(adapter);
        pais.setOnItemSelectedListener(this);

        getSupportActionBar().hide();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regresar:
                Intent RegresarInterfaz = new Intent(Registro.this, Iniciar_Sesion.class);
                startActivity(RegresarInterfaz);
                break;
            case R.id.btn_terminar_registro:
                error = false;
                if(usUsername.getText().toString().isEmpty()) {
                    usUsername.setError("Completa este campo, por favor.");
                    error = true;
                }
                if(usEmail.getText().toString().isEmpty()) {
                    usEmail.setError("Completa este campo, por favor.");
                    error = true;
                }
                if(usPwd1.getText().toString().isEmpty()) {
                    usPwd1.setError("Completa este campo, por favor.");
                    error = true;
                }
                if(usPwd2.getText().toString().isEmpty()) {
                    usPwd2.setError("Completa este campo, por favor.");
                    error = true;
                }
                if(!usPwd1.getText().toString().equals(usPwd2.getText().toString())) {
                    usPwd1.setError("Las contaseñas no coinciden.");
                    usPwd2.setError("Las contaseñas no coinciden.");
                    Toast.makeText(Registro.this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
                    error = true;
                }
                if(seleccionado == null) {
                    error = true;
                    Toast.makeText(Registro.this, "Selecciona un pais.", Toast.LENGTH_LONG).show();
                }
                if(!error) {
                    enviarUsuario(new VolleyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            JSONObject r;
                            try {
                                r = new JSONObject(response.trim());
                                Toast.makeText(Registro.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                                Intent TerminarRegistro = new Intent(Registro.this, Iniciar_Sesion.class);
                                startActivity(TerminarRegistro);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }});
                }
                break;
        }
    }

    public void enviarUsuario(final VolleyCallback callback) {
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/create/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registro.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("USERNAME", usUsername.getText().toString());
                params.put("EMAIL", usEmail.getText().toString());
                params.put("PAIS", seleccionado.toString());
                params.put("PWD", usPwd1.getText().toString());
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(Registro.this);
        rq.add(sr);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        seleccionado = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
