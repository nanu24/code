package org.apache.bikerenting1;

import org.apache.commons.compress.archivers.ArchiveException;

import static org.apache.bikerenting1.support.ExtractorSupport.*;
import java.io.*;
//import java.net.MalformedURLException;


public class test {
    public static void main(String args[]) throws IOException, ArchiveException {

        downloadFile("https://s3.amazonaws.com/capitalbikeshare-data", "C:\\Users\\prem wilfred\\Documents\\extraction\\download.xml");

        for (String fileName: getFiles("C:\\Users\\prem wilfred\\Documents\\extraction\\download.xml")){

            downloadFile("https://s3.amazonaws.com/capitalbikeshare-data/"+fileName, "C:\\Users\\prem wilfred\\Documents\\extraction\\test.zip");

            unzip("C:\\Users\\prem wilfred\\Documents\\extraction\\test.zip", "C:\\Users\\prem wilfred\\Documents\\extraction\\");

            deleteFile("C:\\Users\\prem wilfred\\Documents\\extraction\\test.zip");

            System.out.println(fileName);
        }



    }

    }




