package com.portfolio.hamburgueriaz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CheckBox;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText txtClie;
    private CheckBox cxQuei, cxBac, cxOni;
    private EditText numQuan;
    private Button btnMen, btnMai;
    private EditText txtRes;
    private Button btnPed;
    private static String cliente = "";
    private static String resumoPedido = "";
    private static String quantStr = "";
    private static int quant = 0;
    private static final float PRECO_LANCHE = 20.00f;
    private static float bacon = 0.00f;
    private static float queijo = 0.00f;
    private static float onions = 0.00f;
    private static float precoFinal = 0.00f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtClie = (EditText)findViewById(R.id.txtClie);
        cxQuei = (CheckBox)findViewById(R.id.cxQuei);
        cxBac = (CheckBox)findViewById(R.id.cxBac);
        cxOni = (CheckBox)findViewById(R.id.cxOni);
        numQuan = (EditText)findViewById(R.id.numQuan);
        btnMen = (Button)findViewById(R.id.btnMen);
        btnMai = (Button)findViewById(R.id.btnMai);
        txtRes = (EditText)findViewById(R.id.txtRes);
        btnPed = (Button)findViewById(R.id.btnPed);

        numQuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSeq, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSeq, int i, int i1, int i2) {
                quantStr = numQuan.getText().toString();

                if(!quantStr.isEmpty()) {
                    quant = Integer.parseInt(quantStr);
                }
                else {
                    quant = 0;

                    numQuan.setText(Integer.toString(quant));
                }

                calculaLanche();
            }

            @Override
            public void afterTextChanged(Editable edita) {

            }
        });

        cxBac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    bacon = 2.00f;
                }
                else {
                    bacon = 0.00f;
                }

                calculaLanche();
            }
        });

        cxQuei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    queijo = 2.00f;
                }
                else {
                    queijo = 0.00f;
                }

                calculaLanche();
            }
        });

        cxOni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    onions = 3.00f;
                }
                else {
                    onions = 0.00f;
                }

                calculaLanche();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void subtrair(View v) {
        if(quant > 0) {
            quant--;

            numQuan.setText(Integer.toString(quant));

            this.calculaLanche();
        }
    }

    public void somar(View v) {
        if(quant < 100) {
            quant++;

            numQuan.setText(Integer.toString(quant));

            this.calculaLanche();
        }
    }

    public void calculaLanche() {
        precoFinal = (PRECO_LANCHE + bacon + queijo + onions) * quant;

        txtRes.setText(String.format("R$ %.2f", precoFinal).replace(".", ","));
    }

    public void enviarPedido(View v) {
        cliente = txtClie.getText().toString().trim();
        quantStr = numQuan.toString().trim();
        String comBac, comQuei, comOni = "";

        if(cliente.isEmpty()) {
            txtClie.setError("Esse Campo não Pode Ficar Vazio!");
        }
        else if(quantStr.isEmpty()) {
            quant = 0;
            numQuan.setText(Integer.toString(quant));
        }
        else if(quant < 1) {
            this.exibirMensagem("Escolha uma quantidade de lanches maior do que zero!");
        }
        else if(quant > 100) {
            this.exibirMensagem("O máximo de lanches que pode se fazer por pedido é de 100!");
        }
        else {
            comBac = cxBac.isChecked() ? "Sim" : "Não";
            comQuei = cxQuei.isChecked() ? "Sim" : "Não";
            comOni = cxOni.isChecked() ? "Sim" : "Não";

            resumoPedido = "======= RESUMO DO PEDIDO =======\n\nQuantidade: " + quant +
                    ".\nCom Bacon: " + comBac + ".\nCom Queijo: " + comQuei + ".\nCom Onion Rings: " + comOni  +
                    ".\n\nPreço Final: " + String.format("R$ %.2f", precoFinal).replace(".", ",") +
                    ".\n\nAgradecemos a preferência!";

            Intent inte = new Intent(Intent.ACTION_SEND);

            inte.setType("text/plain");
            inte.putExtra(Intent.EXTRA_EMAIL, new String[]{"pedido@hamburgueriaz.com.br"});
            inte.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + cliente);
            inte.putExtra(Intent.EXTRA_TEXT, resumoPedido);

            try {
                startActivity(Intent.createChooser(inte, "Enviar e-mail"));
            }
            catch(ActivityNotFoundException ex) {
                this.exibirMensagem("Cliente de e-mail não instalado!\nInstale um aplicativo de e-mail para enviar seu pedido!");
            }
        }
    }

    public void exibirMensagem(String mens) {
        AlertDialog.Builder diag = new AlertDialog.Builder(MainActivity.this);

        diag.setMessage(mens.trim());
        diag.setNeutralButton("Ok", null);
        diag.show();
    }
}