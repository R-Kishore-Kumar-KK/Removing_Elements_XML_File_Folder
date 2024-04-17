package com.ebix.test;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RemoveTableElement {

	 public static void main(String[] args) {
	        try {
	            File inputFile = new File("C:/IndusJDE/jboss-eap-7.1/server/8880/deployments/pdgic.ear/pdgic.war/WEB-INF/auditxml/Audit_Config_Data.xml");
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(inputFile);

	            doc.getDocumentElement().normalize();

	            NodeList tableList = doc.getElementsByTagName("table");
	             
	            for (int i = 0; i < tableList.getLength(); i++) {
	                Element table = (Element) tableList.item(i);
	                String tableName = table.getAttribute("name");

	                if (tableName.equals("EXT_MST_AGENCY")) {
	                    table.getParentNode().removeChild(table);
	                }
	            }

	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(inputFile);
	            transformer.transform(source, result);

	            System.out.println("Table elements removed successfully.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
