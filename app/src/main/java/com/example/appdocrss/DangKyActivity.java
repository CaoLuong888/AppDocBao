package com.example.appdocrss;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DangKyActivity extends AppCompatActivity {
    EditText edemail,edtendangnhap,edmatkhau;
    Button btdangky,btquaylai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edemail=findViewById(R.id.et_Email);
        edtendangnhap=findViewById(R.id.et_username);
        edmatkhau=findViewById(R.id.et_password);
        btdangky=findViewById(R.id.btDangKy);
        btquaylai=findViewById(R.id.btQuayLaiDangKy);

        btquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edemail.getText().toString();
                String tendangnhap=edtendangnhap.getText().toString();
                String matkhau=edmatkhau.getText().toString();
                AlertDialog.Builder builder=new AlertDialog.Builder(DangKyActivity.this);
                builder.setTitle("Đăng ký thành công");
                builder.setMessage("Email: "+email+"\n Tên đăng nhập:"+tendangnhap+"\n Mật khẩu:"+matkhau);
                builder.setPositiveButton("Đóng",null);
                builder.show();
            }
        });
    }
}