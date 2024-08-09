package com.lfq.tts.tools.netty.utils;

import com.lfq.tts.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.io.InputStream;
import java.util.Date;

/**
 * @作者 lfq
 * @DATE 2024-08-09
 * current year
 **/
@Slf4j
public class XmlUtils {

    public static void main(String[] args) throws Exception{
//        ResponseXmlData responseXmlData = new ResponseXmlData();
//        responseXmlData.setCode("0000");
//        responseXmlData.setMsg("成功");
//        String xmldata = createResponseXML(responseXmlData);
//        System.out.println(responseXmlData);
//        System.out.println(analysisResponseXML(xmldata));

        RequestXmlData requestXmlData = new RequestXmlData();
        requestXmlData.setRequestDate(DateUtil.formatDateToStr(new Date(),"yyyyMMddHHmmss"));
        requestXmlData.setRequestUserName("罗伯特");
        String xmldata = createRequestXML(requestXmlData);
        System.out.println(xmldata);
    }

    /**
     * 创建xml发送报文
     * @param requestXmlData
     * @return
     * @throws Exception
     */
    public static String createRequestXML(RequestXmlData requestXmlData) throws Exception {
        // 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        // 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
        Document document = builder.newDocument();
        //组织生产xml文件内容
        Element root = document.createElement("发送报文");
        document.appendChild(root);
        Element person = document.createElement("发送内容");

        Element requestDate = document.createElement("REQUEST_DATE");
        requestDate.appendChild(document.createTextNode(requestXmlData.getRequestDate()));
        person.appendChild(requestDate);


        Element requestUserName = document.createElement("REQUEST_USERNAME");
        requestUserName.appendChild(document.createTextNode(requestXmlData.getRequestUserName()));
        person.appendChild(requestUserName);

        root.appendChild(person);

        TransformerFactory tf = TransformerFactory.newInstance();
        // 此抽象类的实例能够将源树转换为结果树
        Transformer transformer;
        transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transformer.transform(source, new StreamResult(bos));
        String xmlStr = bos.toString();
        return xmlStr;
    }

    /**
     * 解析xml发送报文
     * @param xmlData
     * @return
     * @throws Exception
     */
    public static RequestXmlData analysisRequestXML(String xmlData) throws Exception {

        return null;
    }



    /**
     * 创建xml返回报文
     * @return
     * @throws Exception
     * <?xml version="1.0" encoding="UTF-8" standalone="no"?>
     * <返回报文>
     * <报文内容>
     * <CODE>0000</CODE>
     * <MSG>成功</MSG>
     * </报文内容>
     * </返回报文>
     */
    public static String createResponseXML(ResponseXmlData responseXmlData) throws Exception {
        // 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        // 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
        Document document = builder.newDocument();
        //组织生产xml文件内容
        Element root = document.createElement("返回报文");
        document.appendChild(root);
        Element person = document.createElement("报文内容");

        Element sex = document.createElement("CODE");
        sex.appendChild(document.createTextNode(responseXmlData.getCode()));
        person.appendChild(sex);

        Element age = document.createElement("MSG");
        age.appendChild(document.createTextNode(responseXmlData.getMsg()));
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
        return xmlStr;
    }

    /**
     * 解析xml返回报文
     * @param xmlData
     * @return
     * @throws Exception
     */
    public static ResponseXmlData analysisResponseXML(String xmlData) throws Exception {
        ResponseXmlData responseXmlData = new ResponseXmlData();
        byte[] b = xmlData.getBytes();
        InputStream inp = new ByteArrayInputStream(b);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inp);
        NodeList nl = doc.getElementsByTagName("返回报文");
        for (int i = 0; i < nl.getLength(); i++) {
            String code = doc.getElementsByTagName("CODE").item(i).getFirstChild().getNodeValue();
            String msg = doc.getElementsByTagName("MSG").item(i).getFirstChild().getNodeValue();
            log.info("CODE:" + code );
            log.info("MSG:" + msg );
            responseXmlData.setMsg(msg);
            responseXmlData.setCode(code);
        }
        return responseXmlData;
    }


}
