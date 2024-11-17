package com.example.appdocrss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class PhuongTrinhActivity extends AppCompatActivity {
    private EditText editTextA, editTextB, editTextC;
    private Button btketqua,btreset;
    private TextView txtketqua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuong_trinh);
        editTextA = findViewById(R.id.edSoA);
        editTextB = findViewById(R.id.edSoB);
        editTextC = findViewById(R.id.edSoC);
        btketqua = findViewById(R.id.btNghiem);
        btreset = findViewById(R.id.btBoQua);
        txtketqua = findViewById(R.id.tvKetQua);

        btketqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a = Double.parseDouble(editTextA.getText().toString());
                double b = Double.parseDouble(editTextB.getText().toString());
                double c = Double.parseDouble(editTextC.getText().toString());

                // Giải phương trình bậc hai
                double delta = b * b - 4 * a * c;
                double x1, x2;
                if (delta > 0) {
                    x1 = (-b + Math.sqrt(delta)) / (2 * a);
                    x2 = (-b - Math.sqrt(delta)) / (2 * a);
                    txtketqua.setText("PT có 2 nghiệm: x1 = " + x1 + ",  x2 = " + x2);
                } else if (delta == 0) {
                    x1 = x2 = -b / (2 * a);
                    txtketqua.setText("PT có có nghiệm kép: x1 = x2 = " + x1);
                } else {
                    txtketqua.setText("PT có không có nghiệm thực");
                }
            }

        });
        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextA.setText("");
                editTextB.setText("");
                editTextC.setText("");
                txtketqua.setText("");
            }
        });
    }
}