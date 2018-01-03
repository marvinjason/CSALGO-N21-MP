
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection
{
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    
    public DatabaseConnection()
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Murv", "Murv");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch (Exception ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
    }
    
    public void insert(String sender, String senderAddress, String recipient, String recipientAddress, String kilo, String amount)
    {
        try
        {
            statement.execute("INSERT INTO SHIPMENTS(SENDER_NAME, SENDER_ADDRESS, RECIPIENT_NAME, RECIPIENT_ADDRESS, KILO, AMOUNT) VALUES ('" +
                    sender + "', '" + senderAddress + "', '" + recipient + "', '" + recipientAddress + "', " + kilo + ", " + amount + ")");
        }
        catch (SQLException ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
    }
    
    public void update(int id, int batch)
    {
        try
        {
            statement.execute("UPDATE SHIPMENTS SET BATCH = " + batch + " WHERE ID = " + id);
        }
        catch (SQLException ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
    }
    
    public int currentVal()
    {
        int n = 0;
        
        try
        {
            result = statement.executeQuery("SELECT MAX(ID) FROM SHIPMENTS");
            result.next();
            n = result.getInt(1);
        }
        catch (SQLException ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
        
        return n;
    }
    
    public int count(String str)
    {
        int n = 0;
        
        try
        {
            result = statement.executeQuery("SELECT COUNT(*) FROM SHIPMENTS WHERE SENDER_ADDRESS = '" + str + "'");
            result.next();
            n = result.getInt(1);
        }
        catch (SQLException ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
        
        return n;
    }
    
    public ResultSet executeQuery(String query)
    {
        result = null;
        
        try
        {
            result = statement.executeQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
        
        return result;
    }
    
    public void close()
    {
        try
        {
            result.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
    }
}
