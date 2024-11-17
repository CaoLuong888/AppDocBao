package com.example.appdocrss;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class Customadapter extends ArrayAdapter<Docbao> {

    public Customadapter (Context context, int resource, List<Docbao> items) {
        //ba tham số: context (bối cảnh của ứng dụng), resource (ID của layout cho mỗi hàng trong ListView), và items (danh sách các mục dữ liệu của ListView). Hàm khởi tạo này gọi phương thức khởi tạo của lớp cơ sở (ArrayAdapter) để cài đặt các giá trị này.
        super(context, resource, items);
    }
        public View getView(int position, View convertView, ViewGroup parent){
        //Phương thức này được ghi đè từ lớp cơ sở ArrayAdapter và được sử dụng để tạo giao diện cho mỗi mục trong ListView.
            View view = convertView;
            if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    //đối tượng LayoutInflater từ context của ứng dụng.
                    view = inflater.inflate(R.layout.dong_layout_listview, null);
                    //Sử dụng LayoutInflater để "inflate" (tạo) một View từ layout được chỉ định bởi
        }
        Docbao p = getItem(position);
    //Lấy ra mục dữ liệu tại vị trí position trong danh sách items.
        if (p != null) {
// Anh xa + Gan gia tri
            TextView txttitle = (TextView) view.findViewById(R.id.textviewtitle);
            txttitle.setText (p.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(p.image).into(imageView);
        }
        return view ;
    }
}
