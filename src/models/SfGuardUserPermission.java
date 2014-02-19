package models;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlDBHelper;
import utils.JsonHelper;

public class SfGuardUserPermission {
    //------------FIELDS-----------
    public static final String tablename="sf_guard_user_permission";
    //field names
    public static String[] fields={
            "user_id"
            ,"permission_id"
            ,"created_at"
            ,"updated_at"
            };
    //field types
    public static String[] fieldtypes={
            "bigint(20)"
            ,"bigint(20)"
            ,"datetime"
            ,"datetime"
            };
    //-----------------------

    public Long user_id;
    public Long permission_id;
    public Timestamp created_at;
    public Timestamp updated_at;

    public SfGuardUserPermission() {
    }
    public SfGuardUserPermission(ResultSet rs) {
        try {
            user_id=rs.getLong("user_id");
            permission_id=rs.getLong("permission_id");
            created_at=rs.getTimestamp("created_at");
            updated_at=rs.getTimestamp("updated_at");
        } catch (SQLException ex) {
            Logger.getLogger(SfGuardUserPermission.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

//	public String getUuid()
//	{
//		return id.toString()+"-";
//	}

    public Long getUserId() {
            return user_id;
    }

    public void setUserId(Long user_id) {
            this.user_id = user_id;
    }

    public Long getPermissionId() {
            return permission_id;
    }

    public void setPermissionId(Long permission_id) {
            this.permission_id = permission_id;
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


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(user_id!=null?user_id.toString():null);
            values.add(permission_id!=null?permission_id.toString():null);
            values.add(created_at!=null?created_at.toString():null);
            values.add(updated_at!=null?updated_at.toString():null);

            return values;
    }
    public void delete()
    {
            SfGuardUserPermission.delete(this);
    }
    public void save()
    {
            if(user_id==null || user_id==0)
                    SfGuardUserPermission.insert(this);
            else
                    SfGuardUserPermission.update(this);
    }
    public String toString()
    {
            return user_id.toString();
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static SfGuardUserPermission getByName(String name)
    {
            HashMap<Long,SfGuardUserPermission> map=select(" name = '"+name+"'");
            for(SfGuardUserPermission item:map)return item;
            return null;
    }	
    */
    public static SfGuardUserPermission getByUserId(Long user_id) {
            RecordList map=select(" user_id = '"+user_id.toString()+"'");
            for(SfGuardUserPermission item:map)return item;
            return null;
    }
    //-----------database functions--------------

    public static void delete(Long user_id)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        try { 
            st = conn.createStatement();
            st.executeUpdate("delete from "+tablename+" where user_id = '"+user_id.toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(SfGuardUserPermission.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(SfGuardUserPermission item)
    {
        delete(item.getUserId());
    }
    public static void insert(SfGuardUserPermission item)
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
            Logger.getLogger(SfGuardUserPermission.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(SfGuardUserPermission item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where user_id = '"+item.getUserId().toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(SfGuardUserPermission.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static Integer count(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";

        //if conditions contains a limit clause, remove it. 
        //It is not applicable to a count query
        else if(conditions.contains("limit"))
        {
            String[] segments=conditions.split("limit");
            conditions=segments[0];
        }

        Connection conn=MySqlDBHelper.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;
        try { 
        st = conn.createStatement();
        rs = st.executeQuery("SELECT count(*) from "+tablename+" where "+conditions);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SfGuardUserPermission.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }
    public static RecordList select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
        Connection conn=MySqlDBHelper.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;
        try { 
            st = conn.createStatement();
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

            RecordList items=new RecordList();
            while (rs.next()) {
                items.add(new SfGuardUserPermission(rs));
                    //items.put(rs.getLong("user_id"), new SfGuardUserPermission(rs));
            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(SfGuardUserPermission.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }
    }

    //-----------database helper functions--------------
    public static String implodeValues(SfGuardUserPermission item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(withId);
            String output="";
            for(String value:values)
            {
                    if(!output.isEmpty())
                            output+=",";
                    output+=(value!=null?"'"+value+"'":"null");
            }
            return output;
    }
    public static String implodeFields(boolean withId)
    {
            String output="";
            for(String field:fields)
            {
                    if(!withId && field.contentEquals("user_id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=field;
            }
            return output;
    }
    public static String implodeFieldsWithValues(SfGuardUserPermission item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("SfGuardUserPermission:implodeFieldsWithValues(): ERROR: values length does not match fields length");
            }

            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(!withId && fields[i].contentEquals("user_id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=fields[i]+"="+(values.get(i)!=null?"'"+values.get(i)+"'":"null");
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
    public static class RecordList extends ArrayList<SfGuardUserPermission>{
        public static RecordList fromJsonString(String resultstring) throws IOException
        {
            return JsonHelper.mapper.readValue(resultstring, RecordList.class);
        }
        public String toEscapedJsonString() throws IOException
        {
            return "\""+JsonHelper.mapper.writeValueAsString(this).replace("\"", "\\\"") +"\"";
        }
    }
    public static void main(String args[])
    {
        String database="tmcprogram3";
        String url = "jdbc:mysql://localhost:3306/"+database+"?zeroDateTimeBehavior=convertToNull";
        String username="root";
        String password = "password";

        boolean result=MySqlDBHelper.init(url, username, password);            

        RecordList items=SfGuardUserPermission.select("");
        for(SfGuardUserPermission item:items)
        {
            System.out.println(item);
        }
        System.out.println(SfGuardUserPermission.count(""));
    } 
}
