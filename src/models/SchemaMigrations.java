package models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlDBHelper;

public class SchemaMigrations {
    //------------FIELDS-----------
    public static final String tablename="schema_migrations";
    //field names
    public static String[] fields={
            "version"
            };
    //field types
    public static String[] fieldtypes={
            "varchar(255)"
            };
    //-----------------------

    public String version;

    public SchemaMigrations() {
    }
    public SchemaMigrations(ResultSet rs) {
        try {
            version=rs.getString("version");
        } catch (SQLException ex) {
            Logger.getLogger(SchemaMigrations.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

//	public String getUuid()
//	{
//		return id.toString()+"-";
//	}

    public String getVersion() {
            return version;
    }

    public void setVersion(String version) {
            this.version = version;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 
            if(withId)values.add(version);

            //add values for each field here
            values.add(version);

            return values;
    }
    public void delete()
    {
            SchemaMigrations.delete(this);
    }
    public void save()
    {
            if(version==null || version.isEmpty() )
                    SchemaMigrations.insert(this);
            else
                    SchemaMigrations.update(this);
    }
    public String toString()
    {
            return version;
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static SchemaMigrations getByName(String name)
    {
            HashMap<String,SchemaMigrations> map=select(" name = '"+name+"'");
            for(SchemaMigrations item:map.values())return item;
            return null;
    }	
    */
    public static SchemaMigrations getByVersion(String version) {
            HashMap<String,SchemaMigrations> map=select(" version = '"+version+"'");
            for(SchemaMigrations item:map.values())return item;
            return null;
    }
    //-----------database functions--------------

    public static void delete(String version)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        try { 
            st = conn.createStatement();
            st.executeUpdate("delete from "+tablename+" where version = '"+version+"';");
        } catch (SQLException ex) {
            Logger.getLogger(SchemaMigrations.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(SchemaMigrations item)
    {
        delete(item.getVersion());
    }
    public static void insert(SchemaMigrations item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            //for tables with integer primary key
            if(fieldtypes[0].contentEquals("integer"))withid=false;                
            //for tables with varchar primary key
            else if(fieldtypes[0].contains("varchar"))withid=true;                
            st.executeUpdate("INSERT INTO "+tablename+" ("+implodeFields(withid)+")VALUES ("+implodeValues(item, withid)+");");
        } catch (SQLException ex) {
            Logger.getLogger(SchemaMigrations.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(SchemaMigrations item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where version = '"+item.getVersion()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(SchemaMigrations.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static HashMap<String, SchemaMigrations> select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
            Connection conn=MySqlDBHelper.getInstance().getConnection();
            Statement st = null;
            ResultSet rs = null;
            try { 
                st = conn.createStatement();
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

                HashMap<String, SchemaMigrations> items=new HashMap<String, SchemaMigrations>();
                while (rs.next()) {
                    items.put(rs.getString("version"), new SchemaMigrations(rs));
                }
                return items;
            } catch (SQLException ex) {
                Logger.getLogger(SchemaMigrations.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

    }
    //-----------database helper functions--------------
    public static String implodeValues(SchemaMigrations item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(withId);
            String output="";
            for(String value:values)
            {
                    if(!output.isEmpty())
                            output+=",";
                    output+="'"+value+"'";
            }
            return output;
    }
    public static String implodeFields(boolean withId)
    {
            String output="";
            for(String field:fields)
            {
                    if(!withId && field.contentEquals("version"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=field;
            }
            return output;
    }
    public static String implodeFieldsWithValues(SchemaMigrations item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("SchemaMigrations:implodeFieldsWithValues(): ERROR: values length does not match fields length");
            }

            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(!withId && fields[i].contentEquals("version"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=fields[i]+"='"+values.get(i)+"'";
            }
            return output;
    }	
    public static String implodeFieldsWithTypes()
    {
            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(fields[i].contentEquals(fields[0]))//fields[0] being the primary key
                            output+=fields[i]+" "+fieldtypes[i]+" PRIMARY KEY";
                    else
                            output+=","+fields[i]+" "+fieldtypes[i];
            }
            return output;
    }	
    public static String createTable()
    {
            return "CREATE TABLE IF NOT EXISTS "+tablename+" ("+implodeFieldsWithTypes()+" );";
    }
    public static String deleteTable()
    {
            return "DROP TABLE IF EXISTS "+tablename;
    }
    public static void main(String args[])
    {
        String database="erp_development";
        String url = "jdbc:mysql://localhost:3306/"+database;
        String username="root";
        String password = "password";

        boolean result=MySqlDBHelper.init(url, username, password);            

        HashMap<String,SchemaMigrations> items=SchemaMigrations.select("");
        for(String key:items.keySet())
        {
            SchemaMigrations item=items.get(key);
            System.out.println(key);
            System.out.println(item);
        }
    } 
}
