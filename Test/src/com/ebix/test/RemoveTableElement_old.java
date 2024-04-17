package com.ebix.test;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

public class RemoveTableElement_old {
	public static void main(String[] args) {
	    try { // Parse the XML file
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("D:/SVN_IC6_PDGIC/PDGCollectionConfig/Backend/populatedata/CPF_EXT_MST_AGENCY.xml"));
			// Get the root element
			Element root = doc.getDocumentElement();
			// Find the TABLE element with name attribute "EXT_MST_AGENCY"
			NodeList tables = root.getElementsByTagName("TABLE");
			for (int i = 0; i < tables.getLength(); i++) {
				Element table = (Element) tables.item(i);
				String name = table.getAttribute("name");
				if (name.equals("EXT_MST_AGENCY")) {
					// Remove the table element
					root.removeChild(table);
					break;
					// Exit loop after removing the first occurrence
				}
			}
			// Write the modified document back to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("D:/SVN_IC6_PDGIC/PDGCollectionConfig/Backend/populatedata/CPF_EXT_MST_AGENCY.xml"));
			transformer.transform(source, result);
			System.out.println("Table element removed successfully.");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
