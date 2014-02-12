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

public class Vendors {
    //------------FIELDS-----------
    public static final String tablename="vendors";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            ,"addr1"
            ,"addr2"
            ,"addr3"
            ,"vtype"
            ,"cont1"
            ,"cont2"
            ,"phone1"
            ,"phone2"
            ,"faxnum"
            ,"email"
            ,"taxid"
            ,"created_at"
            ,"updated_at"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"datetime"
            ,"datetime"
            };
    //-----------------------

    public Integer id;
    public String name;
    public String addr1;
    public String addr2;
    public String addr3;
    public String vtype;
    public String cont1;
    public String cont2;
    public String phone1;
    public String phone2;
    public String faxnum;
    public String email;
    public String taxid;
    public Timestamp created_at;
    public Timestamp updated_at;

    public Vendors() {
    }
    public Vendors(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
            addr1=rs.getString("addr1");
            addr2=rs.getString("addr2");
            addr3=rs.getString("addr3");
            vtype=rs.getString("vtype");
            cont1=rs.getString("cont1");
            cont2=rs.getString("cont2");
            phone1=rs.getString("phone1");
            phone2=rs.getString("phone2");
            faxnum=rs.getString("faxnum");
            email=rs.getString("email");
            taxid=rs.getString("taxid");
            created_at=rs.getTimestamp("created_at");
            updated_at=rs.getTimestamp("updated_at");
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getAddr1() {
            return addr1;
    }

    public void setAddr1(String addr1) {
            this.addr1 = addr1;
    }

    public String getAddr2() {
            return addr2;
    }

    public void setAddr2(String addr2) {
            this.addr2 = addr2;
    }

    public String getAddr3() {
            return addr3;
    }

    public void setAddr3(String addr3) {
            this.addr3 = addr3;
    }

    public String getVtype() {
            return vtype;
    }

    public void setVtype(String vtype) {
            this.vtype = vtype;
    }

    public String getCont1() {
            return cont1;
    }

    public void setCont1(String cont1) {
            this.cont1 = cont1;
    }

    public String getCont2() {
            return cont2;
    }

    public void setCont2(String cont2) {
            this.cont2 = cont2;
    }

    public String getPhone1() {
            return phone1;
    }

    public void setPhone1(String phone1) {
            this.phone1 = phone1;
    }

    public String getPhone2() {
            return phone2;
    }

    public void setPhone2(String phone2) {
            this.phone2 = phone2;
    }

    public String getFaxnum() {
            return faxnum;
    }

    public void setFaxnum(String faxnum) {
            this.faxnum = faxnum;
    }

    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getTaxid() {
            return taxid;
    }

    public void setTaxid(String taxid) {
            this.taxid = taxid;
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
            if(withId)values.add(id.toString());

            //add values for each field here
            values.add(id.toString());
            values.add(name);
            values.add(addr1);
            values.add(addr2);
            values.add(addr3);
            values.add(vtype);
            values.add(cont1);
            values.add(cont2);
            values.add(phone1);
            values.add(phone2);
            values.add(faxnum);
            values.add(email);
            values.add(taxid);
            values.add(created_at.toString());
            values.add(updated_at.toString());

            return values;
    }
    public void delete()
    {
            Vendors.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    Vendors.insert(this);
            else
                    Vendors.update(this);
    }
    public String toString()
    {
            return id.toString();
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static Vendors getByName(String name)
    {
            HashMap<Integer,Vendors> map=select(" name = '"+name+"'");
            for(Vendors item:map.values())return item;
            return null;
    }	
    */
    public static Vendors getById(Integer id) {
            HashMap<Integer,Vendors> map=select(" id = '"+id.toString()+"'");
            for(Vendors item:map.values())return item;
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
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(Vendors item)
    {
        delete(item.getId());
    }
    public static void insert(Vendors item)
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
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(Vendors item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId().toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static HashMap<Integer, Vendors> select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
            Connection conn=MySqlDBHelper.getInstance().getConnection();
            Statement st = null;
            ResultSet rs = null;
            try { 
                st = conn.createStatement();
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

                HashMap<Integer, Vendors> items=new HashMap<Integer, Vendors>();
                while (rs.next()) {
                    items.put(rs.getInt("id"), new Vendors(rs));
                }
                return items;
            } catch (SQLException ex) {
                Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

    }
    //-----------database helper functions--------------
    public static String implodeValues(Vendors item,boolean withId)
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
    public static String implodeFieldsWithValues(Vendors item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("Vendors:implodeFieldsWithValues(): ERROR: values length does not match fields length");
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

        HashMap<Integer,Vendors> items=Vendors.select("");
        for(Integer key:items.keySet())
        {
            Vendors item=items.get(key);
            System.out.println(key);
            System.out.println(item);
        }
    } 
}
