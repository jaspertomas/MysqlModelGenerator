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

public class Account {
    //------------FIELDS-----------
    public static final String tablename="account";
    //field names
    public static String[] fields={
            "id"
            ,"code"
            ,"name"
            ,"account_type_id"
            ,"account_category_id"
            ,"is_special"
            ,"currentqty"
            ,"date"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(20)"
            ,"varchar(150)"
            ,"int(11)"
            ,"int(11)"
            ,"tinyint(1)"
            ,"decimal(10,2)"
            ,"date"
            };
    //-----------------------

    public Integer id;
    public String code;
    public String name;
    public Integer account_type_id;
    public Integer account_category_id;
    public Integer is_special;
    public BigDecimal currentqty;
    public Date date;

    public Account() {
    }
    public Account(ResultSet rs) {
        try {
            id=rs.getInt("id");
            code=rs.getString("code");
            name=rs.getString("name");
            account_type_id=rs.getInt("account_type_id");
            account_category_id=rs.getInt("account_category_id");
            is_special=rs.getInt("is_special");
            currentqty=rs.getBigDecimal("currentqty");
            date=rs.getDate("date");
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getCode() {
            return code;
    }

    public void setCode(String code) {
            this.code = code;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public Integer getAccountTypeId() {
            return account_type_id;
    }

    public void setAccountTypeId(Integer account_type_id) {
            this.account_type_id = account_type_id;
    }

    public Integer getAccountCategoryId() {
            return account_category_id;
    }

    public void setAccountCategoryId(Integer account_category_id) {
            this.account_category_id = account_category_id;
    }

    public Integer getIsSpecial() {
            return is_special;
    }

    public void setIsSpecial(Integer is_special) {
            this.is_special = is_special;
    }

    public BigDecimal getCurrentqty() {
            return currentqty;
    }

    public void setCurrentqty(BigDecimal currentqty) {
            this.currentqty = currentqty;
    }

    public Date getDate() {
            return date;
    }

    public void setDate(Date date) {
            this.date = date;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(code);
            values.add(name);
            values.add(account_type_id!=null?account_type_id.toString():null);
            values.add(account_category_id!=null?account_category_id.toString():null);
            values.add(is_special!=null?is_special.toString():null);
            values.add(currentqty!=null?currentqty.toString():null);
            values.add(date!=null?date.toString():null);

            return values;
    }
    public void delete()
    {
            Account.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    Account.insert(this);
            else
                    Account.update(this);
    }
    public String toString()
    {
            return id.toString();
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static Account getByName(String name)
    {
            HashMap<Integer,Account> map=select(" name = '"+name+"'");
            for(Account item:map)return item;
            return null;
    }	
    */
    public static Account getById(Integer id) {
            RecordList map=select(" id = '"+id.toString()+"'");
            for(Account item:map)return item;
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
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(Account item)
    {
        delete(item.getId());
    }
    public static void insert(Account item)
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
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(Account item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId().toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
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
                items.add(new Account(rs));
                    //items.put(rs.getInt("id"), new Account(rs));
            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }
    }

    //-----------database helper functions--------------
    public static String implodeValues(Account item,boolean withId)
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
                    if(!withId && field.contentEquals("id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=field;
            }
            return output;
    }
    public static String implodeFieldsWithValues(Account item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("Account:implodeFieldsWithValues(): ERROR: values length does not match fields length");
            }

            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(!withId && fields[i].contentEquals("id"))continue;
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
    public static class RecordList extends ArrayList<Account>{
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

        RecordList items=Account.select("");
        for(Account item:items)
        {
            System.out.println(item);
        }
        System.out.println(Account.count(""));
    } 
}
