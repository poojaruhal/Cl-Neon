package org;

/*
Set up the pipeline
 */
public class Worker{

    public static void main(String[] args) throws Exception {

        //connect to database
        // DbConnector connector = new DbConnector();
        //connector.connect();

        //run the Neon tool
       // JarRunner jarRunner = new JarRunner();
       // jarRunner.run();

        //parse the result file
       XmlParser parser =new XmlParser();
       parser.parse( );

    }


}
