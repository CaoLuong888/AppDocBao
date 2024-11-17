package com.example.appdocrss;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLDOMParser {
    public Document getDocument(String xml) {
        //nhận một tham số là một chuỗi xml đại diện cho nội dung XML cần phân tích và trả về một đối tượng Document.
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            // Thiết lập luồng đầu vào của InputSource để đọc từ chuỗi XML được truyền vào.
            is.setEncoding("UTF-8");
            document = db.parse(is);
            //Sử dụng DocumentBuilder để phân tích (parse) dữ liệu từ InputSource và gán kết quả vào biến document.
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        return document;
    }

    public String getValue(Element item, String name) {
        NodeList nodes = item.getElementsByTagName(name);
        //Tạo một danh sách các nút (nodes) bằng cách lấy tất cả các phần tử con của phần tử item mà có tên là name.
        return this.getTextNodeValue(nodes.item(0));
        // lấy giá trị của phần tử đầu tiên trong danh sách nodes và trả về kết quả.
    }

    private final String getTextNodeValue(Node elem) {
        Node child;
        //Khai báo một biến child kiểu Node để lưu trữ con của phần tử elem.
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild();
                     //Duyệt qua tất cả các nút con của elem, bắt đầu từ nút con đầu tiên, lấy nút con tiếp theo sau mỗi lần lặp
                     child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        //Kiểm tra xem nút con hiện tại có phải là nút văn bản hay không.
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}

