package com.complemento.calculadorasimples;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText numVal1;
    private EditText numVal2;
    private Button btnCalc;
    private EditText txtRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        numVal1 = (EditText)findViewById(R.id.numVal1);
        numVal2 = (EditText)findViewById(R.id.numVal2);
        btnCalc = (Button)findViewById(R.id.btnCalc);
        txtRes = (EditText)findViewById(R.id.txtRes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void soma(View v) {
        int n1, n2, res;

        n1 = Integer.parseInt(numVal1.getText().toString().trim());
        n2 = Integer.parseInt(numVal2.getText().toString().trim());

        res = n1 + n2;

        txtRes.setText(String.format("O resultado é %d.", res));
    }
}