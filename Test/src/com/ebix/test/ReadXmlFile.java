package com.ebix.test;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
public class ReadXmlFile {
  public static void main(String[] args) {
    try {
      File file = new File("D:\\SVN_IC6_PDGIC\\PDGCollectionConfig\\Backend\\populatedata\\CPF_EXT_MST_AGENCY.xml");
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      doc.getDocumentElement().normalize();
      NodeList tableList = doc.getElementsByTagName("TABLE");
      for (int i = 0; i < tableList.getLength(); i++) {
        Node tableNode = tableList.item(i);
        if (tableNode.getNodeType() == Node.ELEMENT_NODE) {
          Element tableElement = (Element) tableNode;
          String tableName = tableElement.getAttribute("name");
          System.out.println("<TABLE name=\"" + tableName + "\">");
          NodeList rowList = tableElement.getElementsByTagName("ROW");
          for (int j = 0; j < rowList.getLength(); j++) {
            Node rowNode = rowList.item(j);
            if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
              Element rowElement = (Element) rowNode;
              NodeList childNodes = rowElement.getChildNodes();
              System.out.println("  <ROW>");
              for (int k = 0; k < childNodes.getLength(); k++) {
                Node childNode = childNodes.item(k);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element childElement = (Element) childNode;
                  String tagName = childElement.getTagName();
                  String textContent = childElement.getTextContent();
                  System.out.println("    <" + tagName + ">" + textContent + "</" + tagName + ">");
                }
              }
              System.out.println("  </ROW>");
            }
          }
          System.out.println("</TABLE>");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}