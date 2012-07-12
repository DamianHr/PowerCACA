package library.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 08/07/12
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class DBConnector {
    Connection link = null;
    private String username;
    private String password;
    private String host;
    private int port = 3306;
    private boolean connected = false;

    public DBConnector(String username, String password, String host)
    {
        this.username = username;
        this.password = password;
        this.host = host;
    }

    public DBConnector(String username, String password, String host, int port)
    {
        this(username, password, host);
        this.port = port;
    }

    private String getConnectionString()
    {
        StringBuilder connectionString = new StringBuilder();
        connectionString.append("jdbc:mysql://");
        connectionString.append(host);
        if(port != 3306) connectionString.append(":"+port);
        connectionString.append("/?");
        connectionString.append("user=");
        connectionString.append(this.username);
        connectionString.append("&password=");
        connectionString.append(this.password);
        return connectionString.toString();
    }

    public void connect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            link = DriverManager.getConnection(this.getConnectionString());
            this.connected = true;
        } catch (SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch (ClassNotFoundException ex)
        {

        }
    }

    public ArrayList<String> getTableFields(String tableName)
    {
        try
        {
            Statement statement = link.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " LIMIT 1;");
            return this.getFieldNames(resultSet);
        }catch (SQLException ex)
        {
            return new ArrayList<String>();
        }
    }

    public SQLResult read(String query)
    {
        ResultSet resultSet = this.executeQuery(query);
        if(resultSet != null)
        {
            ArrayList<String> fields = this.getFieldNames(resultSet);
            SQLResult sqlResult = new SQLResult(fields);
            ArrayList<String> record;
            while((record = this.getNextRecord(resultSet, fields)) != null){
                sqlResult.addData((ArrayList<String>) record);
            }
            return sqlResult;
        }
        else return null;
    }

    public boolean write(String query)
    {
        return this.executeQuery(query) != null;
    }

    private ResultSet executeQuery(String query)
    {
        try
        {
            Statement statement = link.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }catch (SQLException ex){
            return null;
        }
    }

    private ArrayList<String> getFieldNames(ResultSet rs)
    {
        List<String> fieldNames = new ArrayList<String>();
        try {
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                fieldNames.add(rs.getMetaData().getColumnName(i));
            return (ArrayList<String>)fieldNames;
        }catch (SQLException ex)
        {
            return null;
        }
    }

    private ArrayList<String> getNextRecord(ResultSet rs, ArrayList<String> fields)
    {
        try{
            if(rs.next()) {
                List<String> record = new ArrayList<String>();
                for(String field : fields) record.add(rs.getString(field));
                return (ArrayList<String>) record;
            }
            else return null;
        }catch(SQLException ex) {
            return null;
        }
    }

    public ArrayList<String> getDatabaseList()
    {
        try
        {
            Statement statement = link.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            ArrayList<String> fields = this.getFieldNames(resultSet);
            ArrayList<String> data = new ArrayList<String>(), record;
            while((record = this.getNextRecord(resultSet, fields)) != null){
                data.add(record.get(0));
            }
            return data;
        }catch (SQLException ex){
            return new ArrayList<String>();
        }
    }

    public ArrayList<String> getTableList(String database)
    {
        try
        {
            link.setCatalog(database);
            DatabaseMetaData metaData = link.getMetaData();
            ResultSet resultSet = metaData.getTables(null,null,"%", null);
            ArrayList<String> tableList = new ArrayList<String>();
            while(resultSet.next())
                tableList.add(resultSet.getString(3));
            return tableList;
        }
        catch (SQLException ex){
            return new ArrayList<String>();
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
