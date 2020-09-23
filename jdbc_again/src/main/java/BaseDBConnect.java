import java.sql.*;
import java.util.Scanner;

public class BaseDBConnect {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public static void main(String[] args) throws SQLException {
        // Starting connection
        getDBConnection();
        // Command line menu to select DB commands like Select Query, Update Query, etc ...
        MainMenu();
        // Close all DB related objects
        resultSet.close();
        statement.close();
        connection.close();
    }

    private static Statement getDBConnection() throws SQLException {
        String dbUrl = "jdbc:sqlserver://EN411161;databaseName=BikeStores;";
        String user = "sa";
        String pass = "w@nt_To_l#@rn";

        try {
            // Initialize DB connection
            connection = DriverManager.getConnection(dbUrl, user, pass);
            //  Get the connection to DB
            System.out.println(" Successfully connected to the DB => " + dbUrl.substring(17));
            System.out.println(" - - - - - - - - - - - -  - - - - -  - - - - - - - - - - - - - - - - - ");
            // Initialize DB statement
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Connection error ...");
            e.printStackTrace();
        }

        return statement;
    }

    private static void executeSelectQuery() throws SQLException {
        String selectQuery = "select * from ";
        String tableName = "[sales].[customers]";

        try {
            // Initialize DB result Set
            resultSet = statement.executeQuery(selectQuery + tableName);
            // Display resultSet values
            while (resultSet.next()) {
                System.out.println(resultSet.getString("customer_id")
                        + "  | " + resultSet.getString("first_name")
                        + "  | " + resultSet.getString("last_name")
                        + "  | " + resultSet.getString("email"));
            }
            // Close the result set to be used by another SQL commands
            resultSet.close();

        } catch (Exception e) {
            System.out.println("Select query execution error ...");
            e.printStackTrace();
        }
    }

    private static void executeInsertQuery () throws SQLException {
            String insertQuery = "INSERT INTO";
            String tableName = "[sales].[customers]";
            String insertedValues = "VALUES ('Ono', 'Bono', '(373)693 52 52', 'lambda.bambda@gmail.com', " +
                                            "'25 Iazului Avenue', 'San Angelo', 'TX', '76901')";

            try {
                statement.executeUpdate(insertQuery + tableName + insertedValues);
            }catch (Exception e){
                System.out.println("Insert query execution error ...");
                e.printStackTrace();
            }
    }

    private static void executeUpdateQuery () throws SQLException {
        String updateQuery = "UPDATE";
        String tableName = "[sales].[customers]";
        String updatedValues = "SET email = 'new.person@gmail.com' " +
                "WHERE first_name = 'Ana' and last_name = 'Vasile' ";

        try {
            statement.executeUpdate(updateQuery + tableName + updatedValues);
        }catch (Exception e){
            System.out.println("Update query execution error ...");
            e.printStackTrace();
        }
    }

    private static void executeDeleteQuery () throws SQLException {
        String deleteQuery = "DELETE FROM";
        String tableName = "[sales].[customers]";
        String deleteCriteria = "WHERE first_name = 'Ion' and last_name = 'Cirdei' ";

        try {
            statement.executeUpdate(deleteQuery + tableName + deleteCriteria);
        }catch (Exception e){
            System.out.println("Delete query execution error ...");
            e.printStackTrace();
        }
    }

    private static void executeBulkInsertQuery () throws SQLException {
        String insertQuery = "BULK INSERT";
        String tableName = "[sales].[customers]";
        String bulkInserCondition = "FROM 'C:\\hWORKs\\Java Projects\\bulk_insert_ex.csv'"
                                  + "WITH (FIELDTERMINATOR = ',' , ROWTERMINATOR = '\n' )" ;

        try {
            statement.executeUpdate(insertQuery + tableName + bulkInserCondition);
        }catch (Exception e){
            System.out.println("Bulk Insert query execution error ...");
            e.printStackTrace();
        }
    }

        public static void MainMenu () throws SQLException {
            // Display menu
            MenuOptions();
            // User choice
            int userSelection;
            // Setup scanner
            Scanner scan = new Scanner(System.in);
            //Get choice from user
            userSelection = scan.nextInt();
            // Check user input
            while (userSelection != 6) {
                if (userSelection == 1) {
                    System.out.println("Display all table rows  (Select Query)\n");
                    executeSelectQuery();
                } else if (userSelection == 2) {
                    System.out.println(" Insert a row into DB table \n");
                    executeInsertQuery();
                } else if (userSelection == 3) {
                    System.out.println(" Update a row from DB table \n");
                    executeUpdateQuery();
                } else if (userSelection == 4) {
                    System.out.println(" Delete a row from DB table ");
                    executeDeleteQuery ();
                }else if (userSelection == 5) {
                    System.out.println(" Bulk Insert into DB table ");
                    executeBulkInsertQuery ();
                }
                System.out.println();
                // Display menu
                MenuOptions();
                //Get choice from user
                userSelection = scan.nextInt();
            }
            System.out.println("----------------");
            System.out.println("Goodbye. Have a nice day!");

        }

        public static void MenuOptions () {
            System.out.println("Select your option:");
            System.out.println("--------------------");
            System.out.println("1 - Display all records from the DB table(Select Query)");
            System.out.println("2 - Insert a row into DB table  (Insert Query)");
            System.out.println("3 - Edit a row in DB table (Update Query)");
            System.out.println("4 - Delete a row(s) from DB table (Delete Query)");
            System.out.println("5 - Bulk Insert (from CSV file) into DB table (Bulk Insert Query");
            System.out.println("6 - EXIT");
            System.out.println("--------------------");
            System.out.print("Your selected option is: ");
        }

    }
