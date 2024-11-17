package com.example.appdocrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuanLyTaiKhoanActivity extends AppCompatActivity {
    EditText edmataikhoan,edtentaikhoan,edmatkhau;
    Button btthem,btxoa,btcapnhat,bttruyvan,btreset;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);

                edmataikhoan=findViewById(R.id.edMaTaiKhoan);
                edtentaikhoan=findViewById(R.id.edTenTaiKhoan);
                edmatkhau=findViewById(R.id.edMatkhau);
                btcapnhat=findViewById(R.id.btCapNhat);
                btthem=findViewById(R.id.btThem);
                btxoa=findViewById(R.id.btXoa);
                bttruyvan=findViewById(R.id.btTruyVan);
                btreset=findViewById(R.id.btReSet);
                //tao listview
                lv=findViewById(R.id.lvDanhSach);
                mylist=new ArrayList<>();
                myadapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylist);
                lv.setAdapter(myadapter);
                //tao va mo co so du lieu sqlite
                mydatabase=openOrCreateDatabase("qltaikhoan.db",MODE_PRIVATE,null);
                // tao table de chua du lieu
                try {
                    String sql="CREATE TABLE tbTaiKhoan(maTaiKhoan TEXT primary key,tenTaiKhoan TEXT,matKhau TEXT)";
                    mydatabase.execSQL(sql);
                }catch (Exception e){
                    Log.e("loi","bang da ton tai");
                }
                btthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mataikhoan=edmataikhoan.getText().toString();
                        String tentaikhoan=edtentaikhoan.getText().toString();
                        String matkhau=edmatkhau.getText().toString();

                        ContentValues myvalue=new ContentValues();
                        myvalue.put("maTaiKhoan",mataikhoan);
                        myvalue.put("tenTaiKhoan",tentaikhoan);
                        myvalue.put("matKhau",matkhau);
                        String msg="";
                        if (mydatabase.insert("tbTaiKhoan",null,myvalue)==-1){
                            msg="loi them ko thanh cong";
                        }else {
                            msg="them thanh cong";
                        }
                        Toast.makeText(QuanLyTaiKhoanActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                bttruyvan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mylist.clear();
                        Cursor c=mydatabase.query("tbTaiKhoan",null,null,null,null,null,null);
                        c.moveToNext();
                        String dulieu="";
                        while (c.isAfterLast()==false){
                            dulieu=c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2);
                            c.moveToNext();
                            mylist.add(dulieu);
                        }
                        c.close();
                        myadapter.notifyDataSetChanged();
                    }
                });
                btxoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mataikhoan = edmataikhoan.getText().toString();
                        int n = mydatabase.delete("tbTaiKhoan","maTaiKhoan = ?",new
                                String[]{mataikhoan});
                        String msg ="";
                        if (n == 0)
                        {
                            msg = "ko xoa duoc";
                        }
                        else {
                            msg = n +" Xoa thanh cong";
                        }
                        Toast.makeText(QuanLyTaiKhoanActivity.this, msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                btcapnhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tentaikhoan=edtentaikhoan.getText().toString();
                        String matkhau=edmatkhau.getText().toString();
                        String mataikhoan = edmataikhoan.getText().toString();
                        ContentValues myvalue = new ContentValues();
                        myvalue.put("tenTaiKhoan",tentaikhoan);
                        myvalue.put("matKhau",matkhau);
                        int n = mydatabase.update("tbTaiKhoan",myvalue,"maTaiKhoan = ?",new
                                String[]{mataikhoan});
                        String msg = "";
                        if (n == 0)
                        {
                            msg = "ko cap nhat duoc";
                        }
                        else {
                            msg = n+ " Cap nhat thanh cong";
                        }
                        Toast.makeText(QuanLyTaiKhoanActivity.this, msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                btreset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edmataikhoan.setText("");
                        edtentaikhoan.setText("");
                        edmatkhau.setText("");
                    }
                });

    }
}