import java.sql.*;

public class App {

    //Queries to make tables
    private static final String MAKE_DRONE_TABLE =
        "CREATE TABLE IF NOT EXISTS DRONE ("
        + "DRONE_ID SERIAL PRIMARY KEY, "
        + "ROUTE_ID INTEGER, "
        + "LOCATION_ID INTEGER NOT NULL, "
        + "BATTERY_PERCENTAGE DECIMAL NOT NULL,"
        + "DEPARTURE_TIME TIME,"
        + "ETA TIME,"
        + "ARRIVAL_TIME TIME"
        + ");";

    private static final String MAKE_ROUTE_TABLE =
            "CREATE TABLE IF NOT EXISTS ROUTE ("
            + "ROUTE_ID SERIAL PRIMARY KEY, "
            + "STARTING_POINT INTEGER NOT NULL, "
            + "ENDING_POINT INTEGER NOT NULL, "
            + "UNIQUE (STARTING_POINT, ENDING_POINT)"
            + ");";

    private static final String MAKE_LOCATION_TABLE =
            "CREATE TABLE IF NOT EXISTS LOCATION ("
            + "LOCATION_ID SERIAL PRIMARY KEY, "
            + "STREET TEXT, "
            + "CITY TEXT, "
            + "STATE TEXT, "
            + "ZIP TEXT, "
            + "COUNTRY TEXT,"
            + "UNIQUE (STREET, CITY, STATE, ZIP, COUNTRY)"
            + ");";

    private static final String MAKE_CONTAINER_TABLE =
            "CREATE TABLE IF NOT EXISTS CONTAINER ("
            + "CONTAINER_ID SERIAL PRIMARY KEY, "
            + "DRONE_ID INTEGER, "
            + "PRODUCT_ID INTEGER,"
            + "BATTERY_PERCENTAGE DECIMAL NOT NULL, "
            + "MAX_CAPACITY DECIMAL NOT NULL,"
            + "TEMPERATURE DECIMAL,"
            + "UNITS INTEGER"
            + ");";

    private static final String MAKE_PRODUCT_TABLE =
            "CREATE TABLE IF NOT EXISTS PRODUCT ("
            + "PRODUCT_ID SERIAL PRIMARY KEY, "
            + "PRODUCT_NAME TEXT NOT NULL, "
            + "PRODUCT_WEIGHT DECIMAL NOT NULL,"
            + "MINIMUM_TEMPERATURE DECIMAL,"
            + "MAXIMUM_TEMPERATURE DECIMAL,"
            + "UNIQUE (PRODUCT_NAME, PRODUCT_WEIGHT)"
            + ");";

    //Queries to define constraints

    private static final String ADD_DRONE_CONSTRAINT1 = "ALTER TABLE DRONE ADD CONSTRAINT FK_LOCATION FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION(LOCATION_ID)";

    private static final String ADD_DRONE_CONSTRAINT2 = "ALTER TABLE DRONE ADD CONSTRAINT FK_ROUTE FOREIGN KEY (ROUTE_ID) REFERENCES ROUTE(ROUTE_ID)";

    private static final String ADD_ROUTE_CONSTRAINT1 = "ALTER TABLE ROUTE ADD CONSTRAINT FK_START FOREIGN KEY (STARTING_POINT) REFERENCES LOCATION(LOCATION_ID)";

    private static final String ADD_ROUTE_CONSTRAINT2 = "ALTER TABLE ROUTE ADD CONSTRAINT FK_END FOREIGN KEY (ENDING_POINT) REFERENCES LOCATION(LOCATION_ID)";

    private static final String ADD_CONTAINER_CONSTRAINT1 = "ALTER TABLE CONTAINER ADD CONSTRAINT FK_DRONE FOREIGN KEY (DRONE_ID) REFERENCES DRONE(DRONE_ID)";

    private static final String ADD_CONTAINER_CONSTRAINT2 = "ALTER TABLE CONTAINER ADD CONSTRAINT FK_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)";

    public static void main(String[] args){
        System.out.println("Hello world!");
        makeTable(MAKE_DRONE_TABLE);
        makeTable(MAKE_ROUTE_TABLE);
        makeTable(MAKE_CONTAINER_TABLE);
        makeTable(MAKE_LOCATION_TABLE);
        makeTable(MAKE_PRODUCT_TABLE);
        addConstraint(ADD_DRONE_CONSTRAINT1);
        addConstraint(ADD_DRONE_CONSTRAINT2);
        addConstraint(ADD_ROUTE_CONSTRAINT1);
        addConstraint(ADD_ROUTE_CONSTRAINT2);
        addConstraint(ADD_CONTAINER_CONSTRAINT1);
        addConstraint(ADD_CONTAINER_CONSTRAINT2);

    }
    /***
     * Method Creation Date (DD/MM/YYYY, Developer Name): 28/5/2025, Zachary Treichler
     * Most Recent Change (DD/MM/YYYY, Developer Name): 3/6/2025, Zachary Treichler
     * Method Description: This method is used to generate tables in a database. It establishes a connection using a database URI, and then executes a prepared statement based
     * on the query variable that was passed into the method. This method uses try with resources, so the connection closes if any error occurs to prevent zombie instances.
     * Functions Using This Method: main method
     * Description of Variables:
     * @param query - Used to store the SQL query that will be executed by this method
     */
    public static void makeTable(String query){
        try(
            Connection c = DriverManager.getConnection(System.getenv("DATABASE_URI"));
        ){
            try(
                PreparedStatement stmt1 = c.prepareStatement(query);
            ){
                stmt1.executeUpdate();
            }
            c.close();
        }
        catch(SQLException e){
            System.out.print("SQL error - " + e.getMessage());
        }
    }

    public static void addConstraint(String query){
        try(
            Connection c = DriverManager.getConnection(System.getenv("DATABASE_URI"));
        ){
            try(
                PreparedStatement stmt1 = c.prepareStatement(query);
            ){
                stmt1.executeUpdate();
            }
            c.close();
        }
        catch(SQLException e){
            System.out.print("SQL error - " + e.getMessage());
        }
    }
}
