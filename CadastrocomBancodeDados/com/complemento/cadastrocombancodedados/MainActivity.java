package com.complemento.cadastrocombancodedados;

import android.app.Service;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtCod, txtNome, txtTel, txtMail;
    private Button btnLimp, btnSalv, btnDest;
    private ListView lstClie;
    private BancoDados db = new BancoDados(this);
    private ArrayAdapter<String> adapter;
    private List<String> lista;
    private InputMethodManager inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtCod = (EditText)findViewById(R.id.txtCod);
        txtNome = (EditText)findViewById(R.id.txtNome);
        txtTel = (EditText)findViewById(R.id.txtTel);
        txtMail = (EditText)findViewById(R.id.txtMail);

        btnLimp = (Button)findViewById(R.id.btnLimp);
        btnSalv = (Button)findViewById(R.id.btnSalv);
        btnDest = (Button)findViewById(R.id.btnDest);

        lstClie = (ListView)findViewById(R.id.lstClie);

        inp = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);

        listarClientes();

        lstClie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String conteudo = (String)lstClie.getItemAtPosition(position);

                String codigo = conteudo.substring(0, conteudo.indexOf(" - "));

                Cliente cl = db.selecCliente(Integer.parseInt(codigo));

                txtCod.setText(String.valueOf(cl.getCodigo()));
                txtNome.setText(cl.getNome());
                txtTel.setText(cl.getTelefone());
                txtMail.setText(cl.getEmail());
            }
        });

        btnLimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
            }
        });

        btnSalv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = txtCod.getText().toString().trim();
                String nome = txtNome.getText().toString().trim();
                String tel = txtTel.getText().toString().trim();
                String mail = txtMail.getText().toString().trim();

                if(nome.isEmpty()) {
                    txtNome.setError("Este Campo é Obrigatório!");
                }
                else {
                    if(codigo.isEmpty()) {
                        db.addCliente(new Cliente(nome, tel, mail));

                        Toast.makeText(MainActivity.this, "Salvo com Sucesso!", Toast.LENGTH_LONG).show();

                        limpaCampos();

                        listarClientes();

                        escTeclado();
                    }
                    else {
                        db.atCliente(new Cliente(Integer.parseInt(codigo), nome, tel, mail));

                        Toast.makeText(MainActivity.this, "Atualizado com Sucesso!", Toast.LENGTH_LONG).show();

                        limpaCampos();

                        listarClientes();

                        escTeclado();
                    }
                }
            }
        });

        btnDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = txtCod.getText().toString().trim();

                if(codigo.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nenhum Cliente Selecionado!", Toast.LENGTH_LONG).show();
                }
                else {
                    Cliente cl = new Cliente();

                    cl.setCodigo(Integer.parseInt(codigo));

                    db.delCliente(cl);

                    Toast.makeText(MainActivity.this, "Deletado com Sucesso!", Toast.LENGTH_LONG).show();

                    limpaCampos();

                    listarClientes();

                    escTeclado();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void listarClientes() {
        List<Cliente> clientes = db.listarContatos();

        lista = new ArrayList<>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lista) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView)v).setTextColor(Color.BLACK);

                return v;
            }
        };

        lstClie.setAdapter(adapter);

        for(Cliente c: clientes) {
            lista.add(c.getCodigo() + " - " + c.getNome() + " - " + c.getTelefone() + " - " + c.getEmail());

            adapter.notifyDataSetChanged();
        }
    }

    public void limpaCampos() {
        txtCod.setText("");
        txtNome.setText("");
        txtTel.setText("");
        txtMail.setText("");

        txtNome.requestFocus();
    }

    public void escTeclado() {
        inp.hideSoftInputFromWindow(txtNome.getWindowToken(), 0);
    }
}