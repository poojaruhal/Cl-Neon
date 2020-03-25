package org;

import java.sql.*;

public class DbConnector{

    public static void connect() {
        Connection connection = null;
        try {
            // create a database connection
            String dbUrl = "jdbc:sqlite:comment-analysis.db";

            connection = DriverManager.getConnection(dbUrl);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //statement.executeUpdate("create table person (id integer, name string)");
            // statement.executeUpdate("insert into person values(1, 'leo')");
            // statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from Bucket_4");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("Intent"));
            }
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }

}
