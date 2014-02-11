package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlDBHelper;

public class User {
    //------------FIELDS-----------
    public static final String tablename="users";
    //field names
    public static String[] fields={
            "id"
            ,"username"
            ,"password_hash"
            };
    //field types
    public static String[] fieldtypes={
            "integer"
            ,"varchar(50)"
            ,"varchar(40)"
            };
    //-----------------------

    String username="",password_hash="",id="";
    public User() {
    }
    public User(ResultSet rs) {
        try {
            id=rs.getString("id");
            username = rs.getString("username");
            password_hash = rs.getString("password_hash");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }

//	public String getUuid()
//	{
//		return "app-"+id.toString()+"-";
//	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 
            if(withId)values.add(id.toString());

            //add values for each field here
            values.add(username);
            values.add(password_hash);

            return values;
    }
    public void delete()
    {
            User.delete(this);
    }
    public void save()
    {
            //if(User.getById(id)==null)
            if(id==null || id.toString().isEmpty() || id.toString().contentEquals("0"))
                    User.insert(this);
            else
                    User.update(this);
    }
    public String toString()
    {
            return username;
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static User getByName(String name)
    {
            HashMap<String,User> map=select(" name = '"+name+"'");
            for(User app:map.values())return app;
            return null;
    }	
    */
    public static User getById(String id) {
            HashMap<String,User> map=select(" id = '"+id+"'");
            for(User app:map.values())return app;
            return null;
    }
    //-----------database functions--------------

    public static void delete(String id)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        try { 
            st = conn.createStatement();
            st.executeUpdate("delete from "+tablename+" where id = '"+id+"';");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(User item)
    {
        delete(item.getId());
    }
    public static void insert(User item)
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(User item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static HashMap<String, User> select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
            Connection conn=MySqlDBHelper.getInstance().getConnection();
            Statement st = null;
            ResultSet rs = null;
            try { 
                st = conn.createStatement();
//                rs = st.executeQuery("SELECT VERSION()");
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

                HashMap<String, User> items=new HashMap<String, User>();
                while (rs.next()) {
                    items.put(rs.getString("id"), new User(rs));
                }
                return items;
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

    }
    //-----------database helper functions--------------
    public static String implodeValues(User item,boolean withId)
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
                    if(!withId && field.contentEquals("id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=field;
            }
            return output;
    }
    public static String implodeFieldsWithValues(User item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("User:implodeFieldsWithValues(): ERROR: values length does not match fields length");
            }

            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(!withId && fields[i].contentEquals("id"))continue;
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
        String url = "jdbc:mysql://localhost:3306/tmcuser";
        String password = "password";

        boolean result=MySqlDBHelper.init(url, "root", password);            

        HashMap<String,User> users=User.select("");
        for(String key:users.keySet())
        {
            User user=users.get(key);
            System.out.println(key);
            System.out.println(user);
        }
    }  
/*
    public static void main(String args[])
    {
        String url = "jdbc:mysql://localhost:3306/jeegen";
        String password = "password";

        boolean result=MySqlDBHelper.init(url, "root", password);            

        HashMap<String,Modules> users=Modules.select("");
        for(String key:users.keySet())
        {
            Modules user=users.get(key);
            System.out.println(key);
            System.out.println(user);
        }
    }  
 
 */    
}
