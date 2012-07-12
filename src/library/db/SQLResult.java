package library.db;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 08/07/12
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class SQLResult {

    public ArrayList<String> fields;
    public ArrayList<ArrayList<String>> data;

    public SQLResult(ArrayList<String> _fields)
    {
        data = new ArrayList<ArrayList<String>>();
        this.fields = _fields;
    }

    public void addData(ArrayList<String> record)
    {
        data.add(record);
    }
}