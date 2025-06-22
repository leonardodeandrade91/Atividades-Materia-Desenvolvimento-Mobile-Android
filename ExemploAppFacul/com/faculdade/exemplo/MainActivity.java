package com.faculdade.exemplo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void gravarDados() {
        SharedPreferences sharedPref = getSharedPreferences("MeusDados", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();

        edit.putString("perfil", "administrador");
        edit.putFloat("valor", 75.4f);
        edit.putInt("quantidade", 28);
        edit.putBoolean("privilegio", true);

        edit.apply();
    }

    private void localizarDados() {
        SharedPreferences sharedPref = getSharedPreferences("MeusDados", MODE_PRIVATE);

        String perfil = sharedPref.getString("perfil", "usuário comum");
        float valor = sharedPref.getFloat("valor", 0);
        int quantidade = sharedPref.getInt("quantidade", 0);
        boolean privilegio = sharedPref.getBoolean("privilegio", false);

        // Essa chave não existe, retornará o segundo parâmetro fornecido ao chamar o método:
        float multa = sharedPref.getFloat("multa", 100);
    }
}