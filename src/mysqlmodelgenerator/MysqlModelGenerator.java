/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlmodelgenerator;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.MySqlDBHelper;
import utils.fileaccess.FileWriter;

/**
 *
 * @author jaspertomas
 */
public class MysqlModelGenerator {

    public static void main(String args[])
    {
        String databasename="jeegen";
        String password = "password";
        String username = "root";
        String url = "jdbc:mysql://localhost:3306/"+databasename;

        MySqlDBHelper.init(url, username, password);            
        Connection conn=MySqlDBHelper.getInstance().getConnection();
        
        Statement st = null;
        ResultSet rs = null;
        ArrayList<String> tables=new ArrayList<String>();
        ArrayList<String> fields=new ArrayList<String>();
        ArrayList<String> fieldtypes=new ArrayList<String>();
        try { 
            st = conn.createStatement();
            rs = st.executeQuery("Show Tables");

            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            for(String table:tables) {
                rs = st.executeQuery("SHOW COLUMNS FROM "+table);
                fields.clear();
                fieldtypes.clear();
                while (rs.next()) {
                    fields.add(rs.getString(1));
                    fieldtypes.add(rs.getString(2));
                }
                createModelFile(table,fields,fieldtypes);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void createModelFile(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
        //write file to current directory
        File outputdir=new File("./output");
        outputdir.mkdir();
        FileWriter.write(outputdir.getPath()+"/"+table+".txt", createModelFileContents(table,fields,fieldtypes));
    }
    public static String createModelFileContents(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
        String tablecaps=toCamelCase(table);
        int counter=0;
        
        String fieldsstring="";
        for(int i=0;i<fields.size();i++)
        {
            fieldsstring+="\n            "+(i==0?"":",")+"\""+fields.get(i) +"\"";
        }
        
        String fieldtypesstring="";
        for(int i=0;i<fieldtypes.size();i++)
        {
            fieldtypesstring+="\n            "+(i==0?"":",")+"\""+fieldtypes.get(i) +"\"";
        }
        
        String gettersandsetters="";
        String datatype;
        String field="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            datatype=datatypeFor(fieldtypes.get(i));
            gettersandsetters+=
"\n    public "+datatype+" get"+toCamelCase(field)+"() {"
+"\n            return "+field+";"
+"\n    }"
+"\n"
+"\n    public void set"+toCamelCase(field)+"("+datatype+" id) {"
+"\n            this."+field+" = "+field+";"
+"\n    }"
+"\n";
        }
        
        String vardefinitions="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            datatype=datatypeFor(fieldtypes.get(i));
            vardefinitions+=
"\n    public "+datatype+" "+field+";";
        }
                        
        String constructorfields="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            constructorfields+=
"\n            "+field+"=rs."+rsGetterFor(fieldtypes.get(i))+"(\"id\");";
        }
/*
+"\n            id=rs.getString(\"id\");"
+"\n            username = rs.getString(\"username\");"
+"\n            password_hash = rs.getString(\"password_hash\");"
 
 */        
 
        String implodevaluesstring="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            implodevaluesstring+=
"\n            values.add("+field+");";
        }
        
        String output="package models;"
+"\n"
+"\nimport java.sql.Connection;"
+"\nimport java.sql.ResultSet;"
+"\nimport java.sql.SQLException;"
+"\nimport java.sql.Statement;"
+"\nimport java.util.ArrayList;"
+"\nimport java.util.HashMap;"
+"\nimport java.util.logging.Level;"
+"\nimport java.util.logging.Logger;"
+"\nimport utils.MySqlDBHelper;"
+"\n"
+"\npublic class [tableCaps] {"
+"\n    //------------FIELDS-----------"
+"\n    public static final String tablename=\"[table]\";"
+"\n    //field names"
+"\n    public static String[] fields={"
+fieldsstring
+"\n            };"
+"\n    //field types"
+"\n    public static String[] fieldtypes={"
+fieldtypesstring
+"\n            };"
+"\n    //-----------------------"
+"\n"
+vardefinitions
+"\n"
+"\n    public [tableCaps]() {"
+"\n    }"
+"\n    public [tableCaps](ResultSet rs) {"
+"\n        try {"
+constructorfields
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCaps].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n"
+"\n//	public String getUuid()"
+"\n//	{"
+"\n//		return \"app-\"+id.toString()+\"-\";"
+"\n//	}"
+"\n"
+gettersandsetters
+"\n"
+"\n    //database functions"
+"\n    public ArrayList<String> implodeFieldValuesHelper(boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=new ArrayList<String>(); "
+"\n            if(withId)values.add(id.toString());"
+"\n"
+"\n            //add values for each field here"
+implodevaluesstring
+"\n"
+"\n            return values;"
+"\n    }"
+"\n    public void delete()"
+"\n    {"
+"\n            [tableCaps].delete(this);"
+"\n    }"
+"\n    public void save()"
+"\n    {"
+"\n            //if([tableCaps].getById(id)==null)"
+"\n            if(id==null || id.toString().isEmpty() || id.toString().contentEquals(\"0\"))"
+"\n                    [tableCaps].insert(this);"
+"\n            else"
+"\n                    [tableCaps].update(this);"
+"\n    }"
+"\n    public String toString()"
+"\n    {"
+"\n            return "+fields.get(0)+";"
+"\n    }"
+"\n"
+"\n    //-------------------------TABLE FUNCTIONS---------------------"
+"\n"
+"\n    //-----------getter functions----------"
+"\n    /*"
+"\n    public static [tableCaps] getByName(String name)"
+"\n    {"
+"\n            HashMap<String,[tableCaps]> map=select(\" name = '\"+name+\"'\");"
+"\n            for([tableCaps] app:map.values())return app;"
+"\n            return null;"
+"\n    }	"
+"\n    */"
+"\n    public static [tableCaps] getById(String id) {"
+"\n            HashMap<String,[tableCaps]> map=select(\" id = '\"+id+\"'\");"
+"\n            for([tableCaps] app:map.values())return app;"
+"\n            return null;"
+"\n    }"
+"\n    //-----------database functions--------------"
+"\n"
+"\n    public static void delete(String id)"
+"\n    {"
+"\n        Connection conn=MySqlDBHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            st.executeUpdate(\"delete from \"+tablename+\" where id = '\"+id+\"';\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCaps].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static void delete([tableCaps] item)"
+"\n    {"
+"\n        delete(item.getId());"
+"\n    }"
+"\n    public static void insert([tableCaps] item)"
+"\n    {"
+"\n        Connection conn=MySqlDBHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        boolean withid=false;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            //for tables with integer primary key"
+"\n            if(fieldtypes[0].contentEquals(\"integer\"))withid=false;                "
+"\n            //for tables with varchar primary key"
+"\n            else if(fieldtypes[0].contains(\"varchar\"))withid=true;                "
+"\n            st.executeUpdate(\"INSERT INTO \"+tablename+\" (\"+implodeFields(withid)+\")VALUES (\"+implodeValues(item, withid)+\");\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCaps].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static void update([tableCaps] item)"
+"\n    {"
+"\n        Connection conn=MySqlDBHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        boolean withid=false;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            st.executeUpdate(\"update \"+tablename+\" set \"+implodeFieldsWithValues(item,false)+\" where id = '\"+item.getId()+\"';\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCaps].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static HashMap<String, [tableCaps]> select(String conditions)"
+"\n    {"
+"\n        if(conditions.isEmpty())conditions = \"1\";"
+"\n            Connection conn=MySqlDBHelper.getInstance().getConnection();"
+"\n            Statement st = null;"
+"\n            ResultSet rs = null;"
+"\n            try { "
+"\n                st = conn.createStatement();"
+"\n//                rs = st.executeQuery(\"SELECT VERSION()\");"
+"\n                rs = st.executeQuery(\"SELECT * from \"+tablename+\" where \"+conditions);"
+"\n"
+"\n                HashMap<String, [tableCaps]> items=new HashMap<String, [tableCaps]>();"
+"\n                while (rs.next()) {"
+"\n                    items.put(rs.getString(\"id\"), new [tableCaps](rs));"
+"\n                }"
+"\n                return items;"
+"\n            } catch (SQLException ex) {"
+"\n                Logger.getLogger([tableCaps].class.getName()).log(Level.SEVERE, null, ex);"
+"\n                ex.printStackTrace();"
+"\n                return null;"
+"\n            }"
+"\n"
+"\n    }"
+"\n    //-----------database helper functions--------------"
+"\n    public static String implodeValues([tableCaps] item,boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=item.implodeFieldValuesHelper(withId);"
+"\n            String output=\"\";"
+"\n            for(String value:values)"
+"\n            {"
+"\n                    if(!output.isEmpty())"
+"\n                            output+=\",\";"
+"\n                    output+=\"'\"+value+\"'\";"
+"\n            }"
+"\n            return output;"
+"\n    }"
+"\n    public static String implodeFields(boolean withId)"
+"\n    {"
+"\n            String output=\"\";"
+"\n            for(String field:fields)"
+"\n            {"
+"\n                    if(!withId && field.contentEquals(\"id\"))continue;"
+"\n                    if(!output.isEmpty())"
+"\n                            output+=\",\";"
+"\n                    output+=field;"
+"\n            }"
+"\n            return output;"
+"\n    }"
+"\n    public static String implodeFieldsWithValues([tableCaps] item,boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later."
+"\n"
+"\n            if(values.size()!=fields.length)"
+"\n            {"
+"\n                    System.err.println(\"[tableCaps]:implodeFieldsWithValues(): ERROR: values length does not match fields length\");"
+"\n            }"
+"\n"
+"\n            String output=\"\";"
+"\n            for(int i=0;i<fields.length;i++)"
+"\n            {"
+"\n                    if(!withId && fields[i].contentEquals(\"id\"))continue;"
+"\n                    if(!output.isEmpty())"
+"\n                            output+=\",\";"
+"\n                    output+=fields[i]+\"='\"+values.get(i)+\"'\";"
+"\n            }"
+"\n            return output;"
+"\n    }	"
+"\n    public static String implodeFieldsWithTypes()"
+"\n    {"
+"\n            String output=\"\";"
+"\n            for(int i=0;i<fields.length;i++)"
+"\n            {"
+"\n                    if(fields[i].contentEquals(fields[0]))//fields[0] being the primary key"
+"\n                            output+=fields[i]+\" \"+fieldtypes[i]+\" PRIMARY KEY\";"
+"\n                    else"
+"\n                            output+=\",\"+fields[i]+\" \"+fieldtypes[i];"
+"\n            }"
+"\n            return output;"
+"\n    }	"
+"\n    public static String createTable()"
+"\n    {"
+"\n            return \"CREATE TABLE IF NOT EXISTS \"+tablename+\" (\"+implodeFieldsWithTypes()+\" );\";"
+"\n    }"
+"\n    public static String deleteTable()"
+"\n    {"
+"\n            return \"DROP TABLE IF EXISTS \"+tablename;"
+"\n    }"
+"\n}"
+"\n";
        output=output.replace("[tableCaps]", tablecaps);
        output=output.replace("[table]", table);
        
        return output;
    }
    public static String toCamelCase(String string)
    {
        String[] segments=string.split("_");
        String output="";
        for(String s:segments)
            output+=capitalize(s);
        return output;
    }
    public static String capitalize(String s)
    {
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }
    public static String datatypeFor(String type)
    {
        type=type.replaceAll("[0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return "Integer";
        else if(type.contains("varchar()"))
            return "String";
        else if(type.contentEquals("text"))
            return "String";
        else if(type.contentEquals("date"))
            return "Date";
        else if(type.contains("bigint"))
            return "Long";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return "Integer";
        else if(type.contentEquals("decimal"))
            return "Decimal";
        else if(type.contentEquals("float"))
            return "Float";
        else if(type.contentEquals("double"))
            return "Double";
        else if(type.contentEquals("real"))
            return "Real";
        else if(type.contentEquals("boolean"))
            return "Boolean";
        else 
            return "";
/*
<option value="INT" selected="selected">INT</option>
<option value="VARCHAR">VARCHAR</option>
<option value="TEXT">TEXT</option>
<option value="DATE">DATE</option>
<optgroup label="NUMERIC"><option value="TINYINT">TINYINT</option>
<option value="SMALLINT">SMALLINT</option>
<option value="MEDIUMINT">MEDIUMINT</option>
<option value="INT" selected="selected">INT</option>
<option value="BIGINT">BIGINT</option>
<option value="-">-</option>
<option value="DECIMAL">DECIMAL</option>
<option value="FLOAT">FLOAT</option>
<option value="DOUBLE">DOUBLE</option>
<option value="REAL">REAL</option>
<option value="-">-</option>
<option value="BIT">BIT</option>
<option value="BOOLEAN">BOOLEAN</option>
<option value="SERIAL">SERIAL</option>
</optgroup><optgroup label="DATE and TIME"><option value="DATE">DATE</option>
<option value="DATETIME">DATETIME</option>
<option value="TIMESTAMP">TIMESTAMP</option>
<option value="TIME">TIME</option>
<option value="YEAR">YEAR</option>
</optgroup><optgroup label="STRING"><option value="CHAR">CHAR</option>
<option value="VARCHAR">VARCHAR</option>
<option value="-">-</option>
<option value="TINYTEXT">TINYTEXT</option>
<option value="TEXT">TEXT</option>
<option value="MEDIUMTEXT">MEDIUMTEXT</option>
<option value="LONGTEXT">LONGTEXT</option>
<option value="-">-</option>
<option value="BINARY">BINARY</option>
<option value="VARBINARY">VARBINARY</option>
<option value="-">-</option>
<option value="TINYBLOB">TINYBLOB</option>
<option value="MEDIUMBLOB">MEDIUMBLOB</option>
<option value="BLOB">BLOB</option>
<option value="LONGBLOB">LONGBLOB</option>
<option value="-">-</option>
<option value="ENUM">ENUM</option>
<option value="SET">SET</option>
</optgroup><optgroup label="SPATIAL"><option value="GEOMETRY">GEOMETRY</option>
<option value="POINT">POINT</option>
<option value="LINESTRING">LINESTRING</option>
<option value="POLYGON">POLYGON</option>
<option value="MULTIPOINT">MULTIPOINT</option>
<option value="MULTILINESTRING">MULTILINESTRING</option>
<option value="MULTIPOLYGON">MULTIPOLYGON</option>
<option value="GEOMETRYCOLLECTION">GEOMETRYCOLLECTION</option>
</optgroup>    
 */        
    }
    public static String rsGetterFor(String type)
    {
        type=type.replaceAll("[0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return "getInt";
        else if(type.contains("varchar()"))
            return "getString";
        else if(type.contentEquals("text"))
            return "getString";
        else if(type.contentEquals("date"))
            return "getDate";
        else if(type.contains("bigint"))
            return "getLong";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return "getInt";
        else if(type.contentEquals("decimal"))
            return "getDecimal";
        else if(type.contentEquals("float"))
            return "getFloat";
        else if(type.contentEquals("double"))
            return "getDouble";
        else if(type.contentEquals("real"))
            return "getReal";
        else if(type.contentEquals("boolean"))
            return "getBoolean";
        else 
            return "";  
    }    
    public static String stringifier(String type)
    {
        type=type.replaceAll("[0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return ".toString";
        else if(type.contains("varchar()"))
            return "";
        else if(type.contentEquals("text"))
            return "";
        else if(type.contentEquals("date"))
            return ".toString";
        else if(type.contains("bigint"))
            return ".toString";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return ".toString";
        else if(type.contentEquals("decimal"))
            return ".toString";
        else if(type.contentEquals("float"))
            return ".toString";
        else if(type.contentEquals("double"))
            return ".toString";
        else if(type.contentEquals("real"))
            return "getReal";
        else if(type.contentEquals("boolean"))
            return ".toString";
        else 
            return "";  
    }    

}
