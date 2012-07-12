package controller.query;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 08/07/12
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public class QueryBuilder {
    /**
     *
     * @param fields
     * @param table
     * @return
     */
    public static String build(ArrayList<String> fields, String table)
    {
        StringBuilder query = new StringBuilder();
        String tableAlias = table.substring(0,1);
        String delimiter = "";
        query.append("SELECT ");
        for(String field : fields) {
            query.append(delimiter);
            query.append(tableAlias);
            query.append(".");
            query.append("`"+field+"`");
            delimiter = ", ";
        }
        query.append(" FROM ");
        query.append(table);
        query.append(" ");
        query.append(tableAlias);
        return query.toString();
    }
}
