package com.cgi.common;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by stepanm on 18.1.2016.
 */
public class XmlConverter {
    public static String objectToXml(Object source, Class... type){
        String result = null;
        StringWriter sw = new StringWriter();
        try{
            JAXBContext receiptContext = JAXBContext.newInstance(type);
            Marshaller receiptMarshaller = receiptContext.createMarshaller();
            receiptMarshaller.marshal(source, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Object xmlToObject(String source, Class... type){
        Object result = null;
        StringReader sr = new StringReader(source);
        try {
            JAXBContext receiptContext = JAXBContext.newInstance(type);
            Unmarshaller receiptUnmarshaller = receiptContext.createUnmarshaller();
            result =  receiptUnmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}
