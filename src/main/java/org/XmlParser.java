package org;

import com.google.common.collect.*;
import com.opencsv.CSVWriter.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
Parse the xml files and extract patterns, their categories from heuristics file.
 */
public class XmlParser{
    File xmlFile;

    private String heursticPath = "src/main/resources/heuristics.xml";
    private String resultPath = "src/main/resources/output.xml";

    public void parse() throws ParserConfigurationException {

        try {
            File xmlFile = new File( heursticPath );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( xmlFile );
            parseHeuristicsFile( doc );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    Parse the heuristics xml and extract patterns, their categories.
    @param doc
    */
    public void parseHeuristicsFile(Document doc) throws ParserConfigurationException{
        //Gather a pattern and all the categories it is present in
        Multimap<String, String> pattern_categories = ArrayListMultimap.create();
        //Gather a category and all the patterns present in it
        Multimap<String, String> category_patterns = ArrayListMultimap.create();

        String category = null;
        String pattern = null;

        //read in case of doubt - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();
        System.out.println( "Root element :" + doc.getDocumentElement().getNodeName() );

        NodeList nList = doc.getElementsByTagName( "NLP_heuristic" );

        System.out.println( "----------------------------" );

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item( temp );

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                pattern = eElement.getElementsByTagName( "text" ).item( 0 ).getTextContent();
                category = eElement.getElementsByTagName( "sentence_class" ).item( 0 ).getTextContent();


                //System.out.println( "pattern : " + pattern );
                //System.out.println( "category : " + category);

/*                if (pattern.length()== 0)
                {
                    pattern = "NO_PATTERN";
                }*/

                pattern_categories.put(pattern,category);
                category_patterns.put(category,pattern);
            }
        }

        System.out.println("pattern_categories: "+pattern_categories);
        System.out.println( "----------------------------" );
        System.out.println("category_patterns: "+category_patterns);

    }

    public void parseOutput(Document doc) throws ParserConfigurationException {
        Multimap<String, String> myMultimap = ArrayListMultimap.create();
        String category = null;
        String sentence = null;

        NodeList nList = doc.getElementsByTagName( "categorized_sentence" );

        System.out.println( "----------------------------" );

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item( temp );

            System.out.println( "\nCurrent Element :" + nNode.getNodeName() );

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                sentence = eElement.getElementsByTagName( "sentence" ).item( 0 ).getTextContent();
                category =  eElement.getElementsByTagName( "category" ).item( 0 ).getTextContent();

                System.out.println( "sentence : " + sentence );
                System.out.println( "category : " + category );

                myMultimap.put(category,sentence);

            }
        }
    }

    /*
    Write the output in a csv files.
     */
    public static void writeAllData(String filePath, List<String>[] data)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
           // CSVWriter writer = new CSVWriter(outputfile);

            //write.writeAll(data);

            // closing writer connection
            //writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
