package com.example.appdocrss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PhepTinhActivity extends AppCompatActivity {
    EditText edsoa,edsob;
    Button btCong,btTru,btNhan,btChia,btThoat;
    TextView txtketqua;
    EditText ednhietdo;
    RadioGroup rdgrnhietdo;
    Button btchuyendoi,btreset,btthoat;
    TextView txtkq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_tinh);

        edsoa = findViewById(R.id.editSoA);
        edsob = findViewById(R.id.editSoB);
        txtketqua = findViewById(R.id.editKQ);
        btCong = findViewById(R.id.btcong);
        btTru = findViewById(R.id.bttru);
        btNhan = findViewById(R.id.btnhan);
        btChia = findViewById(R.id.btchia);
        btThoat = findViewById(R.id.btthoat);

        btCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hamtinh('+');
            }
        });
        btTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hamtinh('-');
            }
        });
        btNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hamtinh('*');
            }
        });
        btChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hamtinh('/');
            }
        });
        btThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ednhietdo=findViewById(R.id.editND);
        rdgrnhietdo=findViewById(R.id.radioChon);
        txtkq=findViewById(R.id.txtvKQ);
        btchuyendoi=findViewById(R.id.btchuyen);
        btreset=findViewById(R.id.buttonClear);
        btthoat=findViewById(R.id.buttonExit);

        btchuyendoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiNhienDo();
            }
        });
        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        btthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void hamtinh(char tinh){
        double soa=Double.parseDouble(edsoa.getText().toString());
        double sob=Double.parseDouble(edsob.getText().toString());
        double kq=0;
        switch (tinh){
            case '+':
                kq=soa+sob;
                break;
            case '-': kq=soa-sob;break;
            case '*': kq=soa *sob;break;
            case '/':
                if (sob!=0){
                    kq=soa/sob;}
                else {
                    txtketqua.setText("khong chia cho 0");
                    return;
                }
                break;
        }
        txtketqua.setText(""+kq);
    }


//CHUYỂN ĐỔI NHIỆT ĐỘ
    private void DoiNhienDo(){
        double nhapnhietdo=Double.parseDouble(ednhietdo.getText().toString());
        int chonrdgr=rdgrnhietdo.getCheckedRadioButtonId();
        RadioButton thaydoirdgr=findViewById(chonrdgr);
        if(chonrdgr==R.id.radioButtonCtoF){
            double ketqua=(nhapnhietdo * 9/5)+32;
            txtkq.setText("ket qua: "+ketqua+"F");
        }else if (chonrdgr==R.id.radioButtonFtoC) {
            double ketqua=(nhapnhietdo-32)*5/9;
            txtkq.setText("ket qua :"+ketqua+" C");
        }
    }
    private void reset(){
        ednhietdo.getText().clear();
        rdgrnhietdo.clearCheck();
        txtkq.setText(" ");
    }
}