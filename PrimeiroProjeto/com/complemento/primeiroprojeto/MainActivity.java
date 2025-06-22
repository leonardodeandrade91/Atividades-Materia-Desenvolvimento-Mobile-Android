package com.complemento.primeiroprojeto;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import classes.FiltroNumeros;

public class MainActivity extends AppCompatActivity {
    private EditText txtNome;
    private Button btnClick;
    private RadioButton radMasc, radFem;
    private EditText numId;
    private int idade;
    private String sexo;
    private CheckBox chProg, chRed, chHard;
    private static String conhe = "";
    private Spinner cxCurs;
    private ArrayAdapter adap;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNome = (EditText)findViewById(R.id.txtNome);
        btnClick = (Button)findViewById(R.id.btnClick);
        radMasc = (RadioButton)findViewById(R.id.radMasc);
        radFem = (RadioButton)findViewById(R.id.radFem);
        numId = (EditText)findViewById(R.id.numId);
        chProg = (CheckBox)findViewById(R.id.chProg);
        chRed = (CheckBox)findViewById(R.id.chRed);
        chHard = (CheckBox)findViewById(R.id.chHard);
        cxCurs = (Spinner)findViewById(R.id.cxCurs);

        adap = ArrayAdapter.createFromResource(this, R.array.superior, R.layout.custom_spinner_item);

        cxCurs.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        cxCurs.setAdapter(adap);

        numId.setFilters(new InputFilter[]{new FiltroNumeros(0, 100)});

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder diag = new AlertDialog.Builder(MainActivity.this);

                if(radMasc.isChecked()) {
                    sexo = "Masculino";
                }
                else if(radFem.isChecked()) {
                    sexo = "Feminino";
                }

                idade = !numId.getText().toString().trim().equals("") ? Integer.parseInt(numId.getText().toString().trim()) : 0;

                if(chProg.isChecked()) {
                    conhe += "\n";
                    conhe += chProg.getText().toString();
                }

                if(chRed.isChecked()) {
                    conhe += "\n";
                    conhe += chRed.getText().toString();
                }

                if(chHard.isChecked()) {
                    conhe += "\n";
                    conhe += chHard.getText().toString();
                }

                item = cxCurs.getSelectedItem().toString();

                diag.setMessage("Nome: " + txtNome.getText().toString().trim() + "\nSexo: " + sexo + "\nIdade: " + idade + "\nConhecimentos:\n" + conhe + "\nEnsino Superior: " + item);
                diag.setNeutralButton("Ok", null);
                diag.show();

                conhe = "";
            }
        });
    }
}