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
import utils.GeneratorHelper;
import utils.MySqlDBHelper;
import utils.fileaccess.FileWriter;

/**
 *
 * @author jaspertomas
 */
public class MysqlModelGenerator {
    
    static String database="ApiDocuParser";
    static String hostname="localhost";
    static String username="root";
    static String password="password";

    public static void main(String args[])
    {
        String url = "jdbc:mysql://"+hostname+":3306/"+database;

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
                createModelsFile(table,fields,fieldtypes);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void createModelsFile(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
        //write file to current directory
        File outputdir=new File("./src/models");
        outputdir.mkdir();
        FileWriter.write(outputdir.getPath()+"/"+toCamelCase(table)+"s.java", createModelsFileContents(table,fields,fieldtypes));
    }
    public static String createModelsFileContents(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
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
        
        String idfield=fields.get(0);
        String idfieldtype=fieldtypes.get(0);
        String idfieldtypestringifier=stringifier(fieldtypes.get(0));
        String iddatatype=datatypeFor(fieldtypes.get(0));
        
        String gettersandsetters="";
        String datatype="";
        String field="";
        String fieldtype="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            datatype=datatypeFor(fieldtypes.get(i));
            gettersandsetters+=
"\n    public "+datatype+" get"+toCamelCase(field)+"() {"
+"\n            return "+field+";"
+"\n    }"
+"\n"
+"\n    public void set"+toCamelCase(field)+"("+datatype+" "+field+") {"
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
"\n            "+field+"=rs."+rsGetterFor(fieldtypes.get(i))+"(\""+field+"\");";
        }
 
        String implodevaluesstring="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            fieldtype=fieldtypes.get(i);
            implodevaluesstring+=
"\n            "+(field.contentEquals(idfield)?"if(withId)":"")+"values.add("+stringifiedWithNull(field,fieldtype)+");";
        }
        
        String savestring="";
        if(iddatatype.contentEquals("String"))
            savestring="\n            if("+idfield+"==null || "+idfield+".isEmpty() )";
        else
            savestring="\n            if("+idfield+"==null || "+idfield+"==0)";
        
        
        String output="package models;"
+"\n"
+"\nimport java.io.IOException;"
//+"\nimport java.math.BigDecimal;"
+"\nimport java.sql.Connection;"
+"\nimport java.sql.ResultSet;"
+"\nimport java.sql.SQLException;"
+"\nimport java.sql.Statement;"
+"\nimport java.sql.Date;"
+"\nimport java.sql.Timestamp;"
+"\nimport java.util.ArrayList;"
+"\nimport java.util.logging.Level;"
+"\nimport java.util.logging.Logger;"
+"\nimport utils.SqliteDbHelper;"
+"\nimport utils.JsonHelper;"
+"\n"
+"\npublic class [tableCapsPlural] {"
+"\n    //------------FIELDS-----------"
+"\n    public static final String tablename=[tableCapsSingular].tablename;"
+"\n    public static String[] fields=[tableCapsSingular].fields;"
+"\n    public static String[] fieldtypes=[tableCapsSingular].fieldtypes;"
+"\n    //-----------------------"
+"\n    //-------------------------TABLE FUNCTIONS---------------------"
+"\n"
+"\n    //-----------getter functions----------"
+"\n    /*"
+"\n    public static [tableCapsPlural] getByName(String name)"
+"\n    {"
+"\n            HashMap<"+iddatatype+",[tableCapsPlural]> map=select(\" name = '\"+name+\"'\");"
+"\n            for([tableCapsPlural] item:map)return item;"
+"\n            return null;"
+"\n    }	"
+"\n    */"
+"\n    public static [tableCapsSingular] getBy"+toCamelCase(idfield)+"("+iddatatype+" "+idfield+") {"
+"\n            RecordList map=select(\" "+idfield+" = '\"+"+idfield+idfieldtypestringifier+"+\"'\");"
+"\n            for([tableCapsSingular] item:map)return item;"
+"\n            return null;"
+"\n    }"
+"\n    //-----------database functions--------------"
+"\n"
+"\n    public static void delete("+iddatatype+" "+idfield+")"
+"\n    {"
+"\n        Connection conn=SqliteDbHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            st.executeUpdate(\"delete from \"+tablename+\" where "+idfield+" = '\"+"+idfield+idfieldtypestringifier+"+\"';\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCapsPlural].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static void delete([tableCapsSingular] item)"
+"\n    {"
+"\n        delete(item.get"+toCamelCase(idfield)+"());"
+"\n    }"
+"\n    public static Integer insert([tableCapsSingular] item)"
+"\n    {"
+"\n        Connection conn=SqliteDbHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        boolean withid=false;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            //for tables with integer primary key"
+"\n            if(fieldtypes[0].contentEquals(\"integer\"))withid=false;                "
+"\n            //for tables with varchar primary key"
+"\n            else if(fieldtypes[0].contains(\"varchar\"))withid=true;                "
+"\n            st.executeUpdate(\"INSERT INTO \"+tablename+\" (\"+implodeFields(withid)+\")VALUES (\"+implodeValues(item, withid)+\");\");"
+"\n            return getLastInsertId();"
+"\n        } catch (SQLException ex) {"
+"\n            ex.printStackTrace();"
+"\n            return null;"
+"\n        }"
+"\n    }"
+"\n    public static void update([tableCapsSingular] item)"
+"\n    {"
+"\n        Connection conn=SqliteDbHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        boolean withid=false;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            st.executeUpdate(\"update \"+tablename+\" set \"+implodeFieldsWithValues(item,false)+\" where "+idfield+" = '\"+item.get"+toCamelCase(idfield)+"()"+idfieldtypestringifier+"+\"';\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCapsPlural].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static Integer getLastInsertId()" 
+"\n    {" 
+"\n        Connection conn=SqliteDbHelper.getInstance().getConnection();\n" +
"        Statement st = null;\n" +
"        ResultSet rs = null;\n" +
"        try { \n" +
"        st = conn.createStatement();\n" +
"        rs = st.executeQuery(\"SELECT last_insert_rowid() from \"+tablename);\n" +
"            while (rs.next()) {\n" +
"                return rs.getInt(1);\n" +
"            }\n" +
"        } catch (SQLException ex) {\n" +
"            ex.printStackTrace();\n" +
"        }\n" +
"        return null;\n" +
"    }" +
"\n    public static Integer count(String conditions)"
+"\n    {"
+"\n        if(conditions.isEmpty())conditions = \"1\";"
+"\n"
+"\n        //if conditions contains a limit clause, remove it. "
+"\n        //It is not applicable to a count query"
+"\n        else if(conditions.contains(\"limit\"))"
+"\n        {"
+"\n            String[] segments=conditions.split(\"limit\");"
+"\n            conditions=segments[0];"
+"\n        }"
+"\n"
+"\n        Connection conn=SqliteDbHelper.getInstance().getConnection();"
+"\n        Statement st = null;"
+"\n        ResultSet rs = null;"
+"\n        try { "
+"\n        st = conn.createStatement();"
+"\n        rs = st.executeQuery(\"SELECT count(*) from \"+tablename+\" where \"+conditions);"
+"\n            while (rs.next()) {"
+"\n                return rs.getInt(1);"
+"\n            }"
+"\n        } catch (SQLException ex) {"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n        return null;"
+"\n    }"
+"\n    public static RecordList select(String conditions)"
+"\n    {"
+"\n        if(conditions.isEmpty())conditions = \"1\";"
+"\n        Connection conn=SqliteDbHelper.getInstance().getConnection();"
+"\n        Statement st = null;"
+"\n        ResultSet rs = null;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n                rs = st.executeQuery(\"SELECT * from \"+tablename+\" where \"+conditions);"
+"\n"
+"\n            RecordList items=new RecordList();"
+"\n            while (rs.next()) {"
+"\n                items.add(new [tableCapsSingular](rs));"
+"\n                    //items.put(rs."+rsGetterFor(idfieldtype)+"(\""+idfield+"\"), new [tableCapsPlural](rs));"
+"\n            }"
+"\n            return items;"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCapsPlural].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n            return null;"
+"\n        }"
+"\n    }"
+"\n"
+"\n    //-----------database helper functions--------------"
+"\n    public static String implodeValues([tableCapsSingular] item,boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=item.implodeFieldValuesHelper(withId);"
+"\n            String output=\"\";"
+"\n            for(String value:values)"
+"\n            {"
+"\n                    if(!output.isEmpty())"
+"\n                            output+=\",\";"
+"\n                    output+=(value!=null?\"'\"+value+\"'\":\"null\");"
+"\n            }"
+"\n            return output;"
+"\n    }"
+"\n    public static String implodeFields(boolean withId)"
+"\n    {"
+"\n            String output=\"\";"
+"\n            for(String field:fields)"
+"\n            {"
+"\n                    if(!withId && field.contentEquals(\""+idfield+"\"))continue;"
+"\n                    if(!output.isEmpty())"
+"\n                            output+=\",\";"
+"\n                    output+=field;"
+"\n            }"
+"\n            return output;"
+"\n    }"
+"\n    public static String implodeFieldsWithValues([tableCapsSingular] item,boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later."
+"\n"
+"\n            if(values.size()!=fields.length)"
+"\n            {"
+"\n                    System.err.println(\"[tableCapsPlural]:implodeFieldsWithValues(): ERROR: values length does not match fields length\");"
+"\n            }"
+"\n"
+"\n            String output=\"\";"
+"\n            for(int i=0;i<fields.length;i++)"
+"\n            {"
+"\n                    if(!withId && fields[i].contentEquals(\""+idfield+"\"))continue;"
+"\n                    if(!output.isEmpty())"
+"\n                            output+=\",\";"
+"\n                    output+=fields[i]+\"=\"+(values.get(i)!=null?\"'\"+values.get(i)+\"'\":\"null\");"
+"\n            }"
+"\n            return output;"
+"\n    }	"
+"\n    public static String implodeFieldsWithTypes()"
+"\n    {"
+"\n            String output=\"\";"
+"\n            for(int i=0;i<fields.length;i++)"
+"\n            {"
+"\n                    if(fields[i].contentEquals(fields[0]))//fields[0] being the primary key"
+"\n                    {"
+"\n                        if(fieldtypes[i].toLowerCase().contains(\"int\"))"
+"\n                            output+=fields[i]+\" INTEGER PRIMARY KEY\";"
+"\n                        else"
+"\n                            output+=fields[i]+\" \"+fieldtypes[i]+\" PRIMARY KEY\";"
+"\n                    }"
+"\n                    else"
+"\n                            output+=\",\"+fields[i]+\" \"+fieldtypes[i];"
+"\n            }"
+"\n            return output;"
+"\n    }	"
+"\n    public static void createTable()"
+"\n    {"
+"\n        String query = \"CREATE TABLE IF NOT EXISTS \"+tablename+\" (\"+implodeFieldsWithTypes()+\" );\";"
+"\n        try { "
+"\n            SqliteDbHelper.getInstance().getConnection().createStatement().executeUpdate(query);"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCapsSingular].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static void deleteTable()"
+"\n    {"
+"\n        String query = \"DROP TABLE IF EXISTS \"+tablename;"
+"\n        try { "
+"\n            SqliteDbHelper.getInstance().getConnection().createStatement().executeUpdate(query);"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([tableCapsSingular].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static class RecordList extends ArrayList<[tableCapsSingular]>{"
+"\n        public static RecordList fromJsonString(String resultstring) throws IOException"
+"\n        {"
+"\n            return JsonHelper.mapper.readValue(resultstring, RecordList.class);"
+"\n        }"
+"\n        public String toEscapedJsonString() throws IOException"
+"\n        {"
+"\n            return \"\\\"\"+JsonHelper.mapper.writeValueAsString(this).replace(\"\\\"\", \"\\\\\\\"\") +\"\\\"\";"
+"\n        }"
+"\n    }"
+"\n    public static void main(String args[])"
+"\n    {"
+"\n        try {"
+"\n            deleteTable();"
+"\n            createTable();"
//+"\n////            //System.out.println("Table created successfully");"
+"\n            //[tableCapsSingular] i=new [tableCapsSingular]();"
//+"\n            i.setName(\"baa\");"
+"\n            //i.save();"
+"\n            "
+"\n//            [tableCapsPlural].delete(1);"
+"\n            for([tableCapsSingular] j:[tableCapsPlural].select(\"\"))"
+"\n                System.out.println(j.getId());"//+j.getName()
+"\n            "
+"\n            System.out.println([tableCapsPlural].count(\"\"));"
+"\n            "
+"\n        } catch (Exception e) {"
+"\n            e.printStackTrace();"
+"\n        }"
+"\n    } "
+"\n}"
+"\n";
//        String tableplural=table;
//        String tablesingular=GeneratorHelper.singularize(table);
//        String tablecapssingular=toCamelCase(tablesingular);
//        String tablecapsplural=toCamelCase(tableplural);
//        output=output.replace("[table]", table);
//        output=output.replace("[tableCapsSingular]", tablecapssingular);
//        output=output.replace("[tableSingular]", tablesingular);
//        output=output.replace("[tableCapsPlural]", tablecapsplural);
//        output=output.replace("[tablePlural]", tableplural);

        String tablecaps=toCamelCase(table);
        String tablecapsplural=toCamelCase(table+"s");
        String tableplural=table+"s";
        output=output.replace("[tableCapsSingular]", tablecaps);
        output=output.replace("[table]", table);
        output=output.replace("[tableCapsPlural]", tablecapsplural);
        output=output.replace("[tablePlural]", tableplural);
        
        return output;
    }
    public static void createModelFile(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
        //write file to current directory
        File outputdir=new File("./src/models");
        outputdir.mkdir();
        FileWriter.write(outputdir.getPath()+"/"+toCamelCase(table)+".java", createModelFileContents(table,fields,fieldtypes));
    }
    public static String createModelFileContents(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
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
        
        String idfield=fields.get(0);
        String idfieldtype=fieldtypes.get(0);
        String idfieldtypestringifier=stringifier(fieldtypes.get(0));
        String iddatatype=datatypeFor(fieldtypes.get(0));
        
        String gettersandsetters="";
        String datatype="";
        String field="";
        String fieldtype="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            datatype=datatypeFor(fieldtypes.get(i));
            gettersandsetters+=
"\n    public "+datatype+" get"+toCamelCase(field)+"() {"
+"\n            return "+field+";"
+"\n    }"
+"\n"
+"\n    public void set"+toCamelCase(field)+"("+datatype+" "+field+") {"
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
"\n            "+field+"=rs."+rsGetterFor(fieldtypes.get(i))+"(\""+field+"\");";
        }
 
        String implodevaluesstring="";
        for(int i=0;i<fields.size();i++)
        {
            field=fields.get(i);
            fieldtype=fieldtypes.get(i);
            implodevaluesstring+=
"\n            "+(field.contentEquals(idfield)?"if(withId)":"")+"values.add("+stringifiedWithNull(field,fieldtype)+");";
        }
        
        String savestring="";
        if(iddatatype.contentEquals("String"))
        {
            savestring="\n            if("+idfield+"==null || "+idfield+".isEmpty() || [tableCapsPlural].getById(id)==null)";
            savestring+="\n                    [tableCapsPlural].insert(([tableCapsSingular])this);";
        }
        else
        {
            savestring="\n            if("+idfield+"==null || "+idfield+"==0 || [tableCapsPlural].getById(id)==null)";
            savestring+="\n                    "+idfield+"=[tableCapsPlural].insert(([tableCapsSingular])this);";
        }
        
        
        String output="package models;"
+"\n"
+"\nimport java.io.IOException;"
//+"\nimport java.math.BigDecimal;"
+"\nimport java.sql.Connection;"
+"\nimport java.sql.ResultSet;"
+"\nimport java.sql.SQLException;"
+"\nimport java.sql.Statement;"
+"\nimport java.sql.Date;"
+"\nimport java.sql.Timestamp;"
+"\nimport java.util.ArrayList;"
+"\nimport java.util.logging.Level;"
+"\nimport java.util.logging.Logger;"
+"\nimport utils.SqliteDbHelper;"
+"\nimport utils.JsonHelper;"
+"\n"
+"\npublic class [tableCapsSingular] {"
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
+"\n    public [tableCapsSingular]() {"
+"\n    }"
+"\n    public [tableCapsSingular](ResultSet rs) {"
+"\n        try {"
+constructorfields
+"\n        } catch (SQLException ex) {"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n"
+"\n//	public String getUuid()"
+"\n//	{"
+"\n//		return id.toString()+\"-\";"
+"\n//	}"
+"\n"
+gettersandsetters
+"\n"
+"\n    //database functions"
+"\n    public ArrayList<String> implodeFieldValuesHelper(boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=new ArrayList<String>(); "
+"\n"
+"\n            //add values for each field here"
+implodevaluesstring
+"\n"
+"\n            return values;"
+"\n    }"
+"\n    public void delete()"
+"\n    {"
+"\n            [tableCapsPlural].delete(this);"
+"\n    }"
+"\n    public void save()"
+"\n    {"
+savestring
+"\n            else"
+"\n                    [tableCapsPlural].update(([tableCapsSingular])this);"
+"\n    }"
+"\n    @Override"
+"\n    public String toString()"
+"\n    {"
+"\n            return "+idfield+idfieldtypestringifier+";"
+"\n    }"
+"\n}"
+"\n";
        String tablecaps=toCamelCase(table);
        String tablecapsplural=toCamelCase(table+"s");
        String tableplural=table+"s";
        output=output.replace("[tableCapsSingular]", tablecaps);
        output=output.replace("[table]", table);
        output=output.replace("[tableCapsPlural]", tablecapsplural);
        output=output.replace("[tablePlural]", tableplural);
        
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
        type=type.replaceAll("[,0-9]", "");
        //System.out.println(type);
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return "Integer";
        else if(type.contentEquals("varchar()"))
            return "String";
        else if(type.contentEquals("char()"))
            return "String";
        else if(type.contentEquals("text"))
            return "String";
        else if(type.contentEquals("tinytext"))
            return "String";
        else if(type.contentEquals("date"))
            return "Date";
        else if(type.contains("bigint"))
            return "Long";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return "Integer";
        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
            return "Double";
        else if(type.contentEquals("float")||type.contentEquals("float()"))
            return "Float";
        else if(type.contentEquals("double")||type.contentEquals("double()"))
            return "Double";
        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
            return "Boolean";
        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
            return "Timestamp";
        else if(type.contains("enum"))
            return "String";
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
        type=type.replaceAll("[,0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return "getInt";
        else if(type.contains("varchar()"))
            return "getString";
        else if(type.contentEquals("char()"))
            return "getString";
        else if(type.contentEquals("text"))
            return "getString";
        else if(type.contentEquals("tinytext"))
            return "getString";
        else if(type.contentEquals("date"))
            return "getDate";
        else if(type.contains("bigint"))
            return "getLong";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return "getInt";
        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
            return "getDouble";
        else if(type.contentEquals("float")||type.contentEquals("float()"))
            return "getFloat";
        else if(type.contentEquals("double")||type.contentEquals("double()"))
            return "getDouble";
        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
            return "getBoolean";
        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
            return "getTimestamp";
        else if(type.contains("enum"))
            return "getString";
        else 
            return "";  
    }    
    public static String stringifier(String type)
    {
        type=type.replaceAll("[,0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return ".toString()";
        else if(type.contains("varchar()"))
            return "";
        else if(type.contentEquals("char()"))
            return "";
        else if(type.contentEquals("text"))
            return "";
        else if(type.contentEquals("tinytext"))
            return "";
        else if(type.contentEquals("date"))
            return ".toString()";
        else if(type.contains("bigint"))
            return ".toString()";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return ".toString()";
        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
            return ".toString()";
        else if(type.contentEquals("float")||type.contentEquals("float()"))
            return ".toString()";
        else if(type.contentEquals("double")||type.contentEquals("double()"))
            return ".toString()";
        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
            return ".toString()";
        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
            return ".toString()";
        else if(type.contains("enum"))
            return "";
        else 
            return "";  
    }       
    public static String stringifiedWithNull(String field,String type)
    {
        type=type.replaceAll("[,0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contains("varchar()"))
            return field;
        else if(type.contentEquals("char()"))
            return field;
        else if(type.contentEquals("text"))
            return field;
        else if(type.contentEquals("tinytext"))
            return field;
        else if(type.contentEquals("date"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contains("bigint"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contentEquals("float")||type.contentEquals("float()"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contentEquals("double")||type.contentEquals("double()"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
            return field+"!=null?"+field+".toString():null";
        else if(type.contains("enum"))
            return field;
        else 
            return field;  
    }    
     
}
