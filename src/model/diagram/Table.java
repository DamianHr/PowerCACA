package model.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table {
    private String name;
    private HashMap<String, Field> fields;

    /**
     *
     * @param tableName
     */
    protected Table(String tableName)
    {
        this.name = tableName;
        this.fields = new HashMap<String, Field>();
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<String> getFieldNames()
    {
        ArrayList<String> fieldNames = new ArrayList<String>();
        for(Map.Entry<String, Field> mapEntry: this.fields.entrySet()) fieldNames.add(mapEntry.getKey());
        return fieldNames;
    }

    public Field getField(String fieldName)
    {
        return this.fields.get(fieldName);
    }

    public Field createField(String name, FieldType fieldType, boolean isNullable, Object defaultValue) {
        Field newField = new Field(name, fieldType, isNullable, defaultValue);
        this.fields.put(name, newField);
        return newField;
    }

    public boolean removeField(Field field)
    {
        return fields.remove(field) != null;
    }

    public void clearFields()
    {
        this.fields = new HashMap<String, Field>();
    }

    private void addField(Field field)
    {
        this.fields.put(field.getName(), field);
    }

    public Table clone()
    {
        Table clonedTable = new Table(this.name);
        for(Map.Entry<String, Field> fieldEntry : fields.entrySet()) clonedTable.addField(fieldEntry.getValue());
        return clonedTable;
    }

    public boolean equals(Table table)
    {
        boolean result = true;
        if(!table.name.equals(this.name)) result = false;
        else if (table.fields.size() != fields.size()) result = false;
        else {
            for(Map.Entry<String, Field> fieldEntry : fields.entrySet())
            {
                if(!fieldEntry.getValue().equals(table.fields.get(fieldEntry.getKey())))
                {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
