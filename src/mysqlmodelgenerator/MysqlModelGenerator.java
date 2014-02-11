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
        table=capitalize(table);
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
+"\npublic class [table] {"
+"\n    //------------FIELDS-----------"
+"\n    public static final String tablename=\"[tablesmall]\";"
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
+"\n    String username=\"\",password_hash=\"\",id=\"\";"
+"\n    public [table]() {"
+"\n    }"
+"\n    public [table](ResultSet rs) {"
+"\n        try {"
+"\n            id=rs.getString(\"id\");"
+"\n            username = rs.getString(\"username\");"
+"\n            password_hash = rs.getString(\"password_hash\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([table].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n"
+"\n    public String getId() {"
+"\n            return id;"
+"\n    }"
+"\n"
+"\n    public void setId(String id) {"
+"\n            this.id = id;"
+"\n    }"
+"\n"
+"\n//	public String getUuid()"
+"\n//	{"
+"\n//		return \"app-\"+id.toString()+\"-\";"
+"\n//	}"
+"\n"
+"\n    public String getUsername() {"
+"\n        return username;"
+"\n    }"
+"\n"
+"\n    public void setUsername(String username) {"
+"\n        this.username = username;"
+"\n    }"
+"\n"
+"\n    public String getPassword_hash() {"
+"\n        return password_hash;"
+"\n    }"
+"\n"
+"\n    public void setPassword_hash(String password_hash) {"
+"\n        this.password_hash = password_hash;"
+"\n    }"
+"\n"
+"\n    //database functions"
+"\n    public ArrayList<String> implodeFieldValuesHelper(boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=new ArrayList<String>(); "
+"\n            if(withId)values.add(id.toString());"
+"\n"
+"\n            //add values for each field here"
+"\n            values.add(username);"
+"\n            values.add(password_hash);"
+"\n"
+"\n            return values;"
+"\n    }"
+"\n    public void delete()"
+"\n    {"
+"\n            [table].delete(this);"
+"\n    }"
+"\n    public void save()"
+"\n    {"
+"\n            //if([table].getById(id)==null)"
+"\n            if(id==null || id.toString().isEmpty() || id.toString().contentEquals(\"0\"))"
+"\n                    [table].insert(this);"
+"\n            else"
+"\n                    [table].update(this);"
+"\n    }"
+"\n    public String toString()"
+"\n    {"
+"\n            return username;"
+"\n    }"
+"\n"
+"\n    //-------------------------TABLE FUNCTIONS---------------------"
+"\n"
+"\n    //-----------getter functions----------"
+"\n    /*"
+"\n    public static [table] getByName(String name)"
+"\n    {"
+"\n            HashMap<String,[table]> map=select(\" name = '\"+name+\"'\");"
+"\n            for([table] app:map.values())return app;"
+"\n            return null;"
+"\n    }	"
+"\n    */"
+"\n    public static [table] getById(String id) {"
+"\n            HashMap<String,[table]> map=select(\" id = '\"+id+\"'\");"
+"\n            for([table] app:map.values())return app;"
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
+"\n            Logger.getLogger([table].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static void delete([table] item)"
+"\n    {"
+"\n        delete(item.getId());"
+"\n    }"
+"\n    public static void insert([table] item)"
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
+"\n            Logger.getLogger([table].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static void update([table] item)"
+"\n    {"
+"\n        Connection conn=MySqlDBHelper.getInstance().getConnection();            "
+"\n        Statement st = null;"
+"\n        boolean withid=false;"
+"\n        try { "
+"\n            st = conn.createStatement();"
+"\n            st.executeUpdate(\"update \"+tablename+\" set \"+implodeFieldsWithValues(item,false)+\" where id = '\"+item.getId()+\"';\");"
+"\n        } catch (SQLException ex) {"
+"\n            Logger.getLogger([table].class.getName()).log(Level.SEVERE, null, ex);"
+"\n            ex.printStackTrace();"
+"\n        }"
+"\n    }"
+"\n    public static HashMap<String, [table]> select(String conditions)"
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
+"\n                HashMap<String, [table]> items=new HashMap<String, [table]>();"
+"\n                while (rs.next()) {"
+"\n                    items.put(rs.getString(\"id\"), new [table](rs));"
+"\n                }"
+"\n                return items;"
+"\n            } catch (SQLException ex) {"
+"\n                Logger.getLogger([table].class.getName()).log(Level.SEVERE, null, ex);"
+"\n                ex.printStackTrace();"
+"\n                return null;"
+"\n            }"
+"\n"
+"\n    }"
+"\n    //-----------database helper functions--------------"
+"\n    public static String implodeValues([table] item,boolean withId)"
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
+"\n    public static String implodeFieldsWithValues([table] item,boolean withId)"
+"\n    {"
+"\n            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later."
+"\n"
+"\n            if(values.size()!=fields.length)"
+"\n            {"
+"\n                    System.err.println(\"[table]:implodeFieldsWithValues(): ERROR: values length does not match fields length\");"
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
        output=output.replace("[table]", table);
        
        return output;
    }
    public static String capitalize(String s)
    {
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }
}
