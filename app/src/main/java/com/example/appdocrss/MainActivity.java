package com.example.appdocrss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Customadapter customadapter;
    ArrayList<Docbao> mangdocbao;
    ArrayList<String> arrayTitle, arrayLink;
    ArrayAdapter adapter;
    MenuItem quanLyTaiKhoanItem, themTaiKhoanItem, phepTinhItem, phuongTrinhItem, chuyenNhietDoItem, thoatItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        mangdocbao = new ArrayList<Docbao>();

        runOnUiThread(new Runnable() {
            public void run() {
                new Readdata().execute("https://vnexpress.net/rss/so-hoa.rss");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dữ liệu link của bài báo tương ứng với mục được chọn được đưa vào Intent bằng phương thức putExtra()
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("link", mangdocbao.get(position).link);
                startActivity(intent);

            }
        });
    }

//ột lớp nội tuyến (inner class) có tên là Readdata, mở rộng từ lớp AsyncTask. Lớp này được sử dụng để thực hiện các tác vụ nền (background) một cách bất đồng bộ.
    private class Readdata extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            //Phương thức này trả về kết quả của tác vụ nền, trong trường hợp này là nội dung được đọc từ URL.
            return docNoiDung_Tu_URL(params[0]);
        }
        //Phương thức này được ghi đè từ lớp AsyncTask và được gọi khi phương thức doInBackground đã hoàn thành công việc. Đối số s là kết quả trả về từ phương thức doInBackground.
        protected void onPostExecute(String s) {
            //Đối tượng này được sử dụng để phân tích (parse) dữ liệu XML từ một chuỗi
            XMLDOMParser parser = new XMLDOMParser();
            // Dòng này sử dụng đối tượng parser để phân tích chuỗi XML s và trả về một đối tượng Document. Đối tượng Document này chứa cấu trúc cây của toàn bộ tài liệu XML, cho phép truy xuất các phần tử và thuộc tính của nó.
            Document document = parser.getDocument(s);
            // sử dụng đối tượng document để lấy tất cả các phần tử có tên là "item" trong tài liệu XML. Kết quả được trả về là một NodeList, một danh sách các nút (node) chứa các phần tử "item".
            NodeList nodeListitem = document.getElementsByTagName("item");
            //ấy tất cả các phần tử có tên là "description" trong tài liệu XML.
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String hinhanh = "";
            for (int i = 0; i < nodeListitem.getLength(); i++) {
                //Lấy nội dung (content) của mỗi mục tin tức từ nodeListdescription và gán cho biến cdata
                String cdata = nodeListdescription.item(i +1 ).getTextContent();
                //đối tượng Pattern để tìm kiếm các thẻ <img> trong nội dung tin tức
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                //đối tượng Matcher để so khớp Pattern với nội dung của mỗi mục tin tức.
                Matcher m = p.matcher(cdata);
                if (m.find()) {
                    //Nếu một kết quả khớp được tìm thấy, gán giá trị của thuộc tính src của thẻ <img> cho biến hinhanh
                    hinhanh = m.group(1);
//                    Log.d("hinhanh", hinhanh + "..........." + i);
                }
                Element element = (Element) nodeListitem.item(i);
                //ấy một phần tử trong nodeListitem tương ứng với vị trí i.
                title = parser.getValue(element, "title");
                //Lấy giá trị của phần tử có tên là "title" từ element và gán cho biến title.
                link = parser.getValue(element, "link");
                mangdocbao.add(new Docbao(title, link, hinhanh));
                //đối tượng Docbao mới với thông tin về tiêu đề, đường dẫn và hình ảnh, và thêm vào danh sách mangdocbao.
            }
            customadapter = new Customadapter(MainActivity.this, android.R.layout.simple_list_item_1, mangdocbao);
            //đối tượng Customadapter mới với dữ liệu từ mangdocbao.
            listView.setAdapter(customadapter);
            //để hiển thị danh sách tin tức lên giao diện người dùng.
            super.onPostExecute(s);
            //phương thức onPostExecute của lớp cha, nơi có thể thực hiện các tác vụ sau khi tác vụ nền đã hoàn thành.
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        //để tạo và quản lý nội dung dữ liệu đọc được từ URL.
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            //đọc dữ liệu từ đối tượng
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            lưu trữ từng dòng dữ liệu đọc được từ URL
            String line;
            //đọc dữ liệu từ bufferedReader một dòng tại một thời điểm, cho đến khi đọc hết dữ liệu.
            while ((line = bufferedReader.readLine()) != null) {
                //Thêm dòng dữ liệu đọc được vào đối tượng content của lớp StringBuilder
                content.append(line + "\n");
            }
            Log.d("content", content.toString());
            bufferedReader.close();
//            Bắt và xử lý các ngoại lệ có thể xảy ra trong quá trình đọc dữ liệu từ URL bằng cách in stack trace của ngoại lệ ra Logcat.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu, menu);
//        getMenuInflater().inflate(R.menu.todo_menu, menu);
        // Truy vấn item có id là itQuanLyTaiKhoan
        quanLyTaiKhoanItem = menu.findItem(R.id.itQuanLyTaiKhoan);
        themTaiKhoanItem = menu.findItem(R.id.itThem);
        phepTinhItem = menu.findItem(R.id.itPhepTinh);
        phuongTrinhItem = menu.findItem(R.id.itPhuongTrinhBac2);
        chuyenNhietDoItem = menu.findItem(R.id.itDoCDoF);
        thoatItem = menu.findItem(R.id.itThoat);

        quanLyTaiKhoanItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, QuanLyTaiKhoanActivity.class);
                startActivity(intent);
                return true;
            }
        });
        themTaiKhoanItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, QuanLyTaiKhoanActivity.class);
                startActivity(intent);
                return true;
            }
        });
        phepTinhItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, PhepTinhActivity.class);
                startActivity(intent);
                return true;
            }
        });
        chuyenNhietDoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, PhepTinhActivity.class);
                startActivity(intent);
                return true;
            }
        });
        phuongTrinhItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, PhuongTrinhActivity.class);
                startActivity(intent);
                return true;
            }
        });
        thoatItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                finish();
                return true;
            }
        });

        return true;
    }

}
