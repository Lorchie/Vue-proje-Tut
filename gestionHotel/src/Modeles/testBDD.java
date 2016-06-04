package Modeles;

import java.sql.*;

/**
 * Created by evanzyker on 04/06/16.
 */
public class testBDD {

    public class MySQLAccess{

        private Connection connect;

        private PreparedStatement prepStatement;

        private ResultSet result;

        public void readDatabase(){
            try{
                //Chargement driver mySQL
                Class.forName("com.mysql.jdbc.Driver");
                //Mise en place de la connection
                connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
                + "user=sqluser&password=sqluserpw");
                // Les PreparedStatement servent à faire les requêtes
                prepStatement = connect.prepareStatement("insert into feedback.comments values (default, ?, ?, ?, ?, ?, ?);");
                prepStatement.setString();


            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }








    public static void main(String[] args) {

    }
}
