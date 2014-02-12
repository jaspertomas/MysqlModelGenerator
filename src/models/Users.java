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

public class Users {
    //------------FIELDS-----------
    public static final String tablename="users";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            ,"email"
            ,"password_digest"
            ,"is_admin"
            ,"is_encoder"
            ,"is_batcher"
            ,"created_at"
            ,"updated_at"
            ,"remember_token"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"tinyint(1)"
            ,"tinyint(1)"
            ,"tinyint(1)"
            ,"datetime"
            ,"datetime"
            ,"varchar(255)"
            };
    //-----------------------

    public Integer id;
    public String name;
    public String email;
    public String password_digest;
    public Integer is_admin;
    public Integer is_encoder;
    public Integer is_batcher;
    public Timestamp created_at;
    public Timestamp updated_at;
    public String remember_token;

    public Users() {
    }
    public Users(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
            email=rs.getString("email");
            password_digest=rs.getString("password_digest");
            is_admin=rs.getInt("is_admin");
            is_encoder=rs.getInt("is_encoder");
            is_batcher=rs.getInt("is_batcher");
            created_at=rs.getTimestamp("created_at");
            updated_at=rs.getTimestamp("updated_at");
            remember_token=rs.getString("remember_token");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

//	public String getUuid()
//	{
//		return id.toString()+"-";
//	}

    public Integer getId() {
            return id;
    }

    public void setId(Integer id) {
            this.id = id;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getPasswordDigest() {
            return password_digest;
    }

    public void setPasswordDigest(String password_digest) {
            this.password_digest = password_digest;
    }

    public Integer getIsAdmin() {
            return is_admin;
    }

    public void setIsAdmin(Integer is_admin) {
            this.is_admin = is_admin;
    }

    public Integer getIsEncoder() {
            return is_encoder;
    }

    public void setIsEncoder(Integer is_encoder) {
            this.is_encoder = is_encoder;
    }

    public Integer getIsBatcher() {
            return is_batcher;
    }

    public void setIsBatcher(Integer is_batcher) {
            this.is_batcher = is_batcher;
    }

    public Timestamp getCreatedAt() {
            return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
            this.created_at = created_at;
    }

    public Timestamp getUpdatedAt() {
            return updated_at;
    }

    public void setUpdatedAt(Timestamp updated_at) {
            this.updated_at = updated_at;
    }

    public String getRememberToken() {
            return remember_token;
    }

    public void setRememberToken(String remember_token) {
            this.remember_token = remember_token;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 
            if(withId)values.add(id.toString());

            //add values for each field here
            values.add(id.toString());
            values.add(name);
            values.add(email);
            values.add(password_digest);
            values.add(is_admin.toString());
            values.add(is_encoder.toString());
            values.add(is_batcher.toString());
            values.add(created_at.toString());
            values.add(updated_at.toString());
            values.add(remember_token);

            return values;
    }
    public void delete()
    {
            Users.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    Users.insert(this);
            else
                    Users.update(this);
    }
    public String toString()
    {
            return id.toString();
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static Users getByName(String name)
    {
            HashMap<Integer,Users> map=select(" name = '"+name+"'");
            for(Users item:map.values())return item;
            return null;
    }	
    */
    public static Users getById(Integer id) {
            HashMap<Integer,Users> map=select(" id = '"+id.toString()+"'");
            for(Users item:map.values())return item;
            return null;
    }
    //-----------database functions--------------

    public static void delete(Integer id)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        try { 
            st = conn.createStatement();
            st.executeUpdate("delete from "+tablename+" where id = '"+id.toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(Users item)
    {
        delete(item.getId());
    }
    public static void insert(Users item)
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
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(Users item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId().toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static HashMap<Integer, Users> select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
            Connection conn=MySqlDBHelper.getInstance().getConnection();
            Statement st = null;
            ResultSet rs = null;
            try { 
                st = conn.createStatement();
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

                HashMap<Integer, Users> items=new HashMap<Integer, Users>();
                while (rs.next()) {
                    items.put(rs.getInt("id"), new Users(rs));
                }
                return items;
            } catch (SQLException ex) {
                Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

    }
    //-----------database helper functions--------------
    public static String implodeValues(Users item,boolean withId)
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
    public static String implodeFieldsWithValues(Users item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("Users:implodeFieldsWithValues(): ERROR: values length does not match fields length");
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
        String database="erp_development";
        String url = "jdbc:mysql://localhost:3306/"+database;
        String username="root";
        String password = "password";

        boolean result=MySqlDBHelper.init(url, username, password);            

        HashMap<Integer,Users> items=Users.select("");
        for(Integer key:items.keySet())
        {
            Users item=items.get(key);
            System.out.println(key);
            System.out.println(item);
        }
    } 
}
