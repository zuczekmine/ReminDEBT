package com.example.anna.testapplicationremindebt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Anna on 2017-05-28.
 */

public class XMLParser {

    public Document getDOMElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public String getElementValue(Node elem) {
        Node child;
        if(elem != null) {
            if(elem.hasChildNodes()) {
                for(child=elem.getFirstChild(); child!=null; child=child.getNextSibling()) {
                    if(child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        NodeList nl = item.getElementsByTagName(str);
        return this.getElementValue(nl.item(0));
    }
}
