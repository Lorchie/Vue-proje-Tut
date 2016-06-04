package Modeles;

import java.sql.*;

/**
 * Created by evanzyker on 04/06/16.
 */
public class testBDD {

    public static class MySQLAccess{

        private Connection connect;

        private PreparedStatement prepStatement;

        private ResultSet result;

        public void readDatabase(){
            try{
                //Chargement driver mySQL
                Class.forName("com.mysql.jdbc.Driver");
                //Mise en place de la connection
                connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
                + "user=sqluser&password=passwd");
                // Les PreparedStatement servent à faire les requêtes
                prepStatement = connect.prepareStatement("insert into feedback.comments values (default, ?, ?, ?, ?, ?, ?);");
                prepStatement.setString(1, "Test");
                prepStatement.setString(2, "TestEmail");
                prepStatement.setString(3, "TestWebPage");
                prepStatement.setDate(4, new java.sql.Date(20160505));
                prepStatement.setString(5, "TestSummary");
                prepStatement.setString(6, "TestComment");
                // executeUpdate pour un Insert
                prepStatement.executeUpdate();

                prepStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, comments from feedback.comments");
                // executeQuery pour executer un Select
                result = prepStatement.executeQuery();
                writeResults(result);

                //Maintenant on enlève le insert
                prepStatement = connect.prepareStatement("Select * from feedback.comments where myuser=? ;");
                prepStatement.setString(1, "Test");
                prepStatement.executeQuery()    ;

                result = prepStatement.executeQuery("SELECT * FROM  feedback.comments;");
                writeMetaData(result);




            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }finally {
                close();
            }
        }
        private void close() {
            try {
                if (result != null) {
                    result.close();
                }

                if (prepStatement != null) {
                    prepStatement.close();
                }

                if (connect != null) {
                    connect.close();
                }
            } catch (Exception ignored) {

            }
        }

        private void writeMetaData(ResultSet resultSet) {
            System.out.println("The columns in the table are: ");

            try {
                System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
                for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
                    System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void writeResults(ResultSet resultSet) {
            // ResultSet is initially before the first data set
            try {
                while (resultSet.next()) {
                    // It is possible to get the columns via name
                    // also possible to get the columns via the column number
                    // which starts at 1
                    // e.g. resultSet.getSTring(2);
                    String user = resultSet.getString("myuser");
                    String website = resultSet.getString("webpage");
                    String summery = resultSet.getString("summary");
                    Date date = resultSet.getDate("datum");
                    String comment = resultSet.getString("comments");
                    System.out.println("User: " + user);
                    System.out.println("Website: " + website);
                    System.out.println("Summery: " + summery);
                    System.out.println("Date: " + date);
                    System.out.println("Comment: " + comment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }




    public static void main(String[] args) {
        MySQLAccess dao = new MySQLAccess();
        dao.readDatabase();
    }
}
