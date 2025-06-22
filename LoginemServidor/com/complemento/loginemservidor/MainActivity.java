package com.complemento.loginemservidor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private EditText txtUser, txtSenha;
    private static final ExecutorService EXEC_SERVICE = Executors.newSingleThreadExecutor();
    private static final Handler HAND = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtUser = (EditText)findViewById(R.id.txtUser);
        txtSenha = (EditText)findViewById(R.id.txtSenha);

        findViewById(R.id.btnEntr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();
                
                if(user.isEmpty()) {
                    txtUser.setError("Este Campo é Obrigatório!");
                }
                else if(senha.isEmpty()) {
                    txtSenha.setError("Este Campo é Obrigatório!");
                }
                else {
                    realizarLogin(user, senha);
                }
            }
        });

        findViewById(R.id.btnLimp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUser.setText("");
                txtSenha.setText("");
                txtUser.requestFocus();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void realizarLogin(String user, String senha) {
        EXEC_SERVICE.execute(() -> {
            String response = realizarRequisicaoHttp(user, senha);

            HAND.post(() -> {
                if(response != null && response.startsWith("{")) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");

                        if(status.equals("sucesso")) {
                            String nome = jsonResponse.getString("nome");
                            exibirMensagem("Bem-vindo, " + nome + ".");
                        }
                        else {
                            String mensagem = jsonResponse.getString("mensagem");
                            exibirMensagem(mensagem);
                        }
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                        exibirMensagem("Erro ao Processar a Resposta!");
                    }
                }
                else {
                    exibirMensagem("Erro de Comunicação com o Servidor!");
                }
            });
        });
    }

    private String realizarRequisicaoHttp(String user, String senha) {
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;

        try {
            URL host = new URL("http://192.168.15.89/LoginEmServidor/login.php");

            urlConnection = (HttpURLConnection)host.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = "user=" + user + "&senha=" + senha;
            OutputStream os = urlConnection.getOutputStream();

            os.write(data.getBytes());
            os.flush();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuilder resp = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                resp.append(inputLine);
            }

            return resp.toString();
        }
        catch(Exception ex) {
            ex.printStackTrace();

            return null;
        }
        finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }

            try {
                if(in != null) {
                    in.close();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void exibirMensagem(String mens) {
        AlertDialog.Builder diag = new AlertDialog.Builder(MainActivity.this);

        diag.setMessage(mens.trim());
        diag.setNeutralButton("Ok", null);
        diag.show();
    }
}