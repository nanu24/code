package org.apache.bikerenting1.support;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;




public class ExtractorSupport {
    public static void downloadFile(String url,String dst) throws IOException{
        FileUtils.copyURLToFile(new URL(url), new File(dst));

    }
    public static List<String> getFiles(String xmlPath){
        List<String> parseList = new ArrayList<String>();
        try {
            File inputFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Contents");//Contents=students
            System.out.println("----------------------------");


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //System.out.println("Student roll no : "
                    // + eElement.getAttribute("rollno"));
                    parseList.add(eElement.getElementsByTagName("Key")//Key=firstname
                            .item(0)
                            .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseList;
    }
    public static void unzip(String src, String dst) throws IOException, ArchiveException {
        File inputFile = new File(src);


        InputStream is = new FileInputStream(inputFile);
        ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("zip", is);
        ZipEntry entry = null;

        while ((entry = (ZipArchiveEntry) ais.getNextEntry()) != null) {

            if (entry.getName().endsWith("/")) {
                File dir = new File(dst + File.separator + entry.getName());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                continue;
            }

            File outFile = new File(dst + File.separator + entry.getName());

            if (outFile.isDirectory()) {
                continue;
            }

            if (outFile.exists()) {
                continue;
            }

            FileOutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = ais.read(buffer)) > 0) {
                out.write(buffer, 0, length);
                out.flush();
            }
            out.close();
        }
        ais.close();
        is.close();
    }
    public static void deleteFile(String FilePath) throws IOException {
        FileUtils.forceDelete(new File(FilePath));

    }

}

