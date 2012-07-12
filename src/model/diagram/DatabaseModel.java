package model.diagram;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseModel {
    private String name;
    private HashMap<String, Table> tables;

    /**
     * Creates a DatabaseModel with the specified name.
     * @param databaseName the name of the new Database
     */
    public DatabaseModel(String databaseName)
    {
        this.name = databaseName;
        this.tables = new HashMap<String, Table>();
    }

    /**
     * Return the Table list of the model
     * @return the Table list of the model
     */
    public HashMap<String, Table> getTables()
    {
        return this.tables;
    }


    /**
     * Sets the specified Table list to the model.
     * @param tables the Table list to be set to the model
     */
    public void setTables(HashMap<String, Table> tables)
    {
        this.tables = tables;
    }

    /**
     * Adds a Table object to the model.
     * @param table the table object to be added
     */
    public void addTable(Table table)
    {
        tables.put(table.getName(), table);
    }

    /**
     * Removes a Table from the model
     * @param tableName the name of the Table to be removed
     * @return true if the table existed and was removed, false otherwise
     */
    public boolean removeTable(String tableName)
    {
        if(tableExists(tableName)) {
            this.tables.remove(tableName);
            return true;
        }
        else return false;
    }

    /**
     * Indicates if the specified exists in the current model
     * @param tableName the name of the Table to search
     * @return true if the Table is present, false otherwise
     */
    public boolean tableExists(String tableName)
    {
        return this.tables.get(tableName) != null;
    }

    /**
     * Creates a new Table using the specified name and adds it to the model.
     * @param tableName the name of the Table to be created
     * @return the new Table object on success, null otherwise
     */
    public Table createTable(String tableName) {
        if(!tableExists(tableName)) {
            Table newTable = new Table(tableName);
            this.addTable(newTable);
            return newTable;
        }
        else return null;
    }
}
