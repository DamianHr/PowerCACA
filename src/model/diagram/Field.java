package model.diagram;

public class Field {
    private String name;
    private FieldType fieldType;
    private Integer size;
    private boolean nullable;
    private boolean hasDefaultValue;
    private Object defaultValue;

    protected Field(String fieldName, FieldType fieldType, boolean isNullable, Object defaultValue)
    {
        this.name = fieldName;
        this.fieldType = fieldType;
        this.nullable = isNullable;
        this.hasDefaultValue = true;
        this.defaultValue = defaultValue;
    }
    protected Field(String fieldName, FieldType fieldType, boolean isNullable)
    {
        this.name = fieldName;
        this.fieldType = fieldType;
        this.nullable = isNullable;
        this.hasDefaultValue = false;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean equals(Field field)
    {
        return (field.name.equals(this.name))
                && (field.fieldType.equals(this.fieldType))
                && (field.size == this.size)
                && (field.nullable == this.nullable)
                && (field.hasDefaultValue == this.hasDefaultValue)
                && (this.defaultValue.equals(defaultValue));
    }

    public Field clone()
    {
        Field clonedField = new Field(this.name, this.fieldType, this.nullable, this.defaultValue);
        clonedField.size = this.size;
        return clonedField;
    }

    public FieldType getFieldType()
    {
        return this.fieldType;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setFieldType(FieldType fieldType)
    {
        this.fieldType = fieldType;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    public boolean getNullable()
    {
        return this.nullable;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue()
    {
        return this.defaultValue;
    }
}