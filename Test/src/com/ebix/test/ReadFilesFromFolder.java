package com.ebix.test;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadFilesFromFolder {

    public static void main(String[] args) {
        File folder = new File("D:/Kishore Kumar R/PDGIC_XML_Files");
        List<String> tableNames = readTableNamesFromFile("D:/Kishore Kumar R/table_names.txt");
        processXmlFiles(folder, tableNames);
    }
    
    //Read the Table names from external File
    private static List<String> readTableNamesFromFile(String filename) {
        List<String> tableNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                tableNames.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    //Processing files recursively from folder
    private static void processXmlFiles(File folder, List<String> tableNames) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processXmlFiles(file, tableNames);
                } else if (file.getName().endsWith(".xml")) {
                    if (shouldDeleteFile(file, tableNames)) {
                        file.delete();
                        System.out.println("File Deleted: " + file.getAbsolutePath());
                    } else {
                        removeTableElements(file, tableNames);
                    }
                }
            }
        }
    }

    //Deleting a file if it has one table element with the name attribute from external file
    private static boolean shouldDeleteFile(File file, List<String> tableNames) {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        NodeList tables = doc.getElementsByTagName("TABLE");
        tables = tables.getLength() == 0 ? doc.getElementsByTagName("table") : tables;

        for (int i = 0; i < tables.getLength(); i++) {
			if (tables.getLength() == 1) {
				Element table = (Element) tables.item(i);
				String name = table.getAttribute("name");
				if (tableNames.contains(name)) {
					return true;
				}
			}
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
		return false;
	}

    //Removing table elements for the name attribute from external file and updating the file
    private static void removeTableElements(File file, List<String> tableNames) {
    	try {
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = factory.newDocumentBuilder();
    		Document doc = builder.parse(file);
    		doc.getDocumentElement();
        
    		for (String tableName : tableNames) {
    			removeElementsByTagName(doc, "TABLE", tableName);
    			removeElementsByTagName(doc, "table", tableName);
    		}
    		doc.setXmlStandalone(true);
    		StringWriter stringWriter = new StringWriter();
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    		transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
    		String xmlString = stringWriter.toString();
    		xmlString = xmlString.replaceFirst("<\\?xml.*?\\?>", "$0\n");
    		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
    			writer.write(xmlString);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    //Removing the elements
	private static void removeElementsByTagName(Document doc, String tagName, String tableName) {
		NodeList nodeList = doc.getElementsByTagName(tagName);
		for (int i = nodeList.getLength() - 1; i >= 0; i--) {
			Element element = (Element) nodeList.item(i);
			String name = element.getAttribute("name");
			if (tableName.equals(name)) {
				element.getParentNode().removeChild(element);
			}
		}
	}
}