package com.lfq.tts.tools.netty;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 **/
public class TCPClient {

    public static void main(String[] args) throws Exception {
        // 2017年12月29日 下午1:43:11
        Socket soc = null;
        String data = "";
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        int serverPort = 19080;
        if (addr.isReachable(5000)) {
            try {
                soc = new Socket(addr, serverPort);

//************************向客户端写xml文件******************************************
                DataOutputStream out=new DataOutputStream(soc.getOutputStream());
                // 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                // 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
                DocumentBuilder builder = factory.newDocumentBuilder();
                // Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
                Document document = builder.newDocument();
                //组织生产xml文件内容
                Element root = document.createElement("persons");
                document.appendChild(root);
                Element person = document.createElement("person");
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode("java小强"));
                person.appendChild(name);
                Element sex = document.createElement("sex");
                sex.appendChild(document.createTextNode("man"));
                person.appendChild(sex);
                Element age = document.createElement("age");
                age.appendChild(document.createTextNode("99"));
                person.appendChild(age);
                root.appendChild(person);

                TransformerFactory tf = TransformerFactory.newInstance();
                // 此抽象类的实例能够将源树转换为结果树
                Transformer transformer;
                transformer = tf.newTransformer();

                DOMSource source = new DOMSource(document);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                transformer.transform(source, new StreamResult(bos));
                String xmlStr = bos.toString();
                out.writeUTF(xmlStr);

//********************************接收读取客户端传来的内容******************************************
                //接收来自服务器的数据，并解析打印
                DataInputStream in = new DataInputStream(soc.getInputStream());
                data = in.readUTF();
//				System.out.println("接收到的数据:" + data);
                DOM(data);
            } catch (UnknownHostException e) {
                System.out.println("Socket Error:" + e.getMessage());
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            } finally {
                if (soc != null)
                    try {
                        soc.close();
                    } catch (IOException e) {/* close failed */
                    }
            }
        } else {
            System.out.println("FAILURE - ping " + addr
                    + " with no interface specified");
        }
    }

    public static void DOM(String data) {
        try {
            byte[] b = data.getBytes();
            InputStream inp = new ByteArrayInputStream(b);
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inp);
            NodeList nl = doc.getElementsByTagName("persons");
//			System.out.println(nl.getLength());
            for (int i = 0; i < nl.getLength(); i++) {
                System.out.println("person:  "
                        + doc.getElementsByTagName("person").item(i)
                        .getFirstChild().getNodeValue());
                System.out.println("name:  "
                        + doc.getElementsByTagName("name").item(i)
                        .getFirstChild().getNodeValue());
                System.out.println("sex:  "
                        + doc.getElementsByTagName("sex").item(i)
                        .getFirstChild().getNodeValue());
                System.out.println("age:  "
                        + doc.getElementsByTagName("age").item(i)
                        .getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
