package com.complemento.jogodavelha;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private View vi;
    private static final String QUAD = "quad";
    private static final String BOLA = "O";
    private static final String XIS = "X";
    private String lastPlay = "X";
    private int estadoFinal[][] = {{1, 2, 3},
                                    {4, 5, 6},
                                    {7, 8, 9},
                                    {1, 4, 7},
                                    {2, 5, 8},
                                    {3, 6, 9},
                                    {1, 5, 9},
                                    {3, 5, 7}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.setVi(getLayoutInflater().inflate(R.layout.activity_main, null));

        setContentView(this.getVi());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public Button getQuad(int tag) {
        return (Button)this.getVi().findViewWithTag(QUAD + tag);
    }

    public void clickQuad(View v) {
        if(!this.finalizado()) {
            if(((Button)v).getText().equals("")) {
                if(this.getLastPlay().equals(XIS)) {
                    ((Button)v).setText(BOLA);

                    this.setLastPlay(BOLA);
                }
                else {
                    ((Button)v).setText(XIS);

                    this.setLastPlay(XIS);
                }
            }
            else {
                Toast.makeText(this.getVi().getContext(), "Escolha Outro Botão!", Toast.LENGTH_LONG).show();
            }
        }

        this.finalizado();
    }

    public void novoJogo(View v) {
        this.ativarQuad(true);

        this.resetaCor();

        for(int i = 1; i <= 9; i++) {
            if(this.getQuad(i) != null) {
                this.getQuad(i).setText("");
            }
        }

        RadioButton rbX = this.getVi().findViewById(R.id.rbX);
        RadioButton rbO = this.getVi().findViewById(R.id.rbO);

        if(rbX.isChecked()) {
            this.setLastPlay(BOLA);
        }
        else if(rbO.isChecked()) {
            this.setLastPlay(XIS);
        }
    }

    public void ativarQuad(boolean b) {
        for(int i = 1; i <= 9; i++) {
            if(this.getQuad(i) != null) {
                this.getQuad(i).setEnabled(b);
            }
        }
    }

    public boolean finalizado() {
        for(int x = 0; x <= 7; x++) {
            String s1 = this.getQuad(estadoFinal[x][0]).getText().toString();
            String s2 = this.getQuad(estadoFinal[x][1]).getText().toString();
            String s3 = this.getQuad(estadoFinal[x][2]).getText().toString();

            if(!s1.equals("") && !s2.equals("") && !s3.equals("")) {
                if(s1.equals(s2) && s2.equals(s3)) {
                    this.mudaQuad(estadoFinal[x][0], R.color.red);
                    this.mudaQuad(estadoFinal[x][1], R.color.red);
                    this.mudaQuad(estadoFinal[x][2], R.color.red);

                    Toast.makeText(this.getVi().getContext(), "O Jogo Acabou!", Toast.LENGTH_LONG).show();

                    return true;
                }
            }
        }

        return false;
    }

    public void mudaQuad(int btn, int cor) {
        this.getQuad(btn).setTextColor(this.getResources().getColor(cor));
    }

    public void resetaCor() {
        for(int i = 1; i <= 9; i++) {
            if(this.getQuad(i) != null) {
                this.mudaQuad(i, R.color.blue);
            }
        }
    }

    public View getVi() {
        return this.vi;
    }

    public void setVi(View vi) {
        this.vi = vi;
    }

    public String getLastPlay() {
        return this.lastPlay;
    }

    public void setLastPlay(String lastPlay) {
        this.lastPlay = lastPlay;
    }
}