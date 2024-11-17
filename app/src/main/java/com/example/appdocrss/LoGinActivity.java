package com.example.appdocrss;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoGinActivity extends AppCompatActivity {
    EditText edhocten,edmatkhau;
    Button btdangnhap,btdangky,btthoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lo_gin);

        edhocten=findViewById(R.id.et_username);
        edmatkhau=findViewById(R.id.et_password);
        btdangnhap=findViewById(R.id.btn_login);
        btdangky=findViewById(R.id.btDangKy);
        btthoat=findViewById(R.id.btn_exit);

        btdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten=edhocten.getText().toString();
                String matkhau=edmatkhau.getText().toString();
                if (hoten.equals("admin") && matkhau.equals("Admin123")){
                    Toast.makeText(LoGinActivity.this,"Dang nhap thanh cong",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoGinActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoGinActivity.this,"sai ten hoac mat khau",Toast.LENGTH_LONG).show();

                }
            }
        });
        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoGinActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });
        btthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}