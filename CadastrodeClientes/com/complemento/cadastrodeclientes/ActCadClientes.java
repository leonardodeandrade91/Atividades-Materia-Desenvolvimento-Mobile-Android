package com.complemento.cadastrodeclientes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActCadClientes extends AppCompatActivity {
    private EditText txtNome;
    private EditText txtTel;
    private EditText txtMail;
    private EditText txtEnd;
    private EditText txtObs;
    private EditText txtData;
    private Spinner cxTel;
    private Spinner cxMail;
    private Spinner cxEnd;
    private Spinner cxData;
    private ArrayAdapter<String> adpTipoTel;
    private ArrayAdapter<String> adpTipoMail;
    private ArrayAdapter<String> adpTipoEnd;
    private ArrayAdapter<String> adpTipoData;
    private SeekBar sliUrg;
    private TextView lblUrg;
    private Toolbar tbAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_cad_clientes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtNome = (EditText)findViewById(R.id.txtNome);
        txtTel = (EditText)findViewById(R.id.txtTel);
        txtMail = (EditText)findViewById(R.id.txtMail);
        txtEnd = (EditText)findViewById(R.id.txtEnd);
        txtObs = (EditText)findViewById(R.id.txtObs);
        txtData = (EditText)findViewById(R.id.txtData);
        cxTel = (Spinner)findViewById(R.id.cxTel);
        cxMail = (Spinner)findViewById(R.id.cxMail);
        cxEnd = (Spinner)findViewById(R.id.cxEnd);
        cxData = (Spinner)findViewById(R.id.cxData);
        sliUrg = (SeekBar)findViewById(R.id.sliUrg);
        lblUrg = (TextView)findViewById(R.id.lblUrg);
        tbAct = (Toolbar)findViewById(R.id.tbAct);

        setSupportActionBar(tbAct);

        adpTipoTel = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adpTipoMail = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adpTipoEnd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adpTipoData = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        adpTipoTel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpTipoMail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpTipoEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpTipoData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cxTel.setAdapter(adpTipoTel);
        cxMail.setAdapter(adpTipoMail);
        cxEnd.setAdapter(adpTipoEnd);
        cxData.setAdapter(adpTipoData);

        adpTipoTel.add("Celular");
        adpTipoTel.add("Fixo");
        adpTipoTel.add("Trabalho");

        adpTipoMail.add("Pessoal");
        adpTipoMail.add("Trabalho");

        adpTipoEnd.add("Casa");
        adpTipoEnd.add("Trabalho");

        adpTipoData.add("Cadastro");
        adpTipoData.add("Compra");

        sliUrg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int i, boolean b) {
                lblUrg.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seek) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seek) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_acao1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu men) {
        MenuInflater infl = getMenuInflater();
        infl.inflate(R.menu.menu_act_cad_clientes, men);

        return true;
    }
}