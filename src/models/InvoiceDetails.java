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

public class InvoiceDetails {
    //------------FIELDS-----------
    public static final String tablename="invoice_details";
    //field names
    public static String[] fields={
            "id"
            ,"barcode"
            ,"invoice_id"
            ,"product_id"
            ,"description"
            ,"discrate"
            ,"discamt"
            ,"is_cancelled"
            ,"qty"
            ,"price"
            ,"unittotal"
            ,"total"
            ,"created_at"
            ,"updated_at"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(255)"
            ,"int(11)"
            ,"int(11)"
            ,"varchar(255)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"tinyint(1)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"datetime"
            ,"datetime"
            };
    //-----------------------

    public Integer id;
    public String barcode;
    public Integer invoice_id;
    public Integer product_id;
    public String description;
    public BigDecimal discrate;
    public BigDecimal discamt;
    public Integer is_cancelled;
    public BigDecimal qty;
    public BigDecimal price;
    public BigDecimal unittotal;
    public BigDecimal total;
    public Timestamp created_at;
    public Timestamp updated_at;

    public InvoiceDetails() {
    }
    public InvoiceDetails(ResultSet rs) {
        try {
            id=rs.getInt("id");
            barcode=rs.getString("barcode");
            invoice_id=rs.getInt("invoice_id");
            product_id=rs.getInt("product_id");
            description=rs.getString("description");
            discrate=rs.getBigDecimal("discrate");
            discamt=rs.getBigDecimal("discamt");
            is_cancelled=rs.getInt("is_cancelled");
            qty=rs.getBigDecimal("qty");
            price=rs.getBigDecimal("price");
            unittotal=rs.getBigDecimal("unittotal");
            total=rs.getBigDecimal("total");
            created_at=rs.getTimestamp("created_at");
            updated_at=rs.getTimestamp("updated_at");
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDetails.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getBarcode() {
            return barcode;
    }

    public void setBarcode(String barcode) {
            this.barcode = barcode;
    }

    public Integer getInvoiceId() {
            return invoice_id;
    }

    public void setInvoiceId(Integer invoice_id) {
            this.invoice_id = invoice_id;
    }

    public Integer getProductId() {
            return product_id;
    }

    public void setProductId(Integer product_id) {
            this.product_id = product_id;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public BigDecimal getDiscrate() {
            return discrate;
    }

    public void setDiscrate(BigDecimal discrate) {
            this.discrate = discrate;
    }

    public BigDecimal getDiscamt() {
            return discamt;
    }

    public void setDiscamt(BigDecimal discamt) {
            this.discamt = discamt;
    }

    public Integer getIsCancelled() {
            return is_cancelled;
    }

    public void setIsCancelled(Integer is_cancelled) {
            this.is_cancelled = is_cancelled;
    }

    public BigDecimal getQty() {
            return qty;
    }

    public void setQty(BigDecimal qty) {
            this.qty = qty;
    }

    public BigDecimal getPrice() {
            return price;
    }

    public void setPrice(BigDecimal price) {
            this.price = price;
    }

    public BigDecimal getUnittotal() {
            return unittotal;
    }

    public void setUnittotal(BigDecimal unittotal) {
            this.unittotal = unittotal;
    }

    public BigDecimal getTotal() {
            return total;
    }

    public void setTotal(BigDecimal total) {
            this.total = total;
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
            values.add(barcode);
            values.add(invoice_id.toString());
            values.add(product_id.toString());
            values.add(description);
            values.add(discrate.toString());
            values.add(discamt.toString());
            values.add(is_cancelled.toString());
            values.add(qty.toString());
            values.add(price.toString());
            values.add(unittotal.toString());
            values.add(total.toString());
            values.add(created_at.toString());
            values.add(updated_at.toString());

            return values;
    }
    public void delete()
    {
            InvoiceDetails.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    InvoiceDetails.insert(this);
            else
                    InvoiceDetails.update(this);
    }
    public String toString()
    {
            return id.toString();
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static InvoiceDetails getByName(String name)
    {
            HashMap<Integer,InvoiceDetails> map=select(" name = '"+name+"'");
            for(InvoiceDetails item:map.values())return item;
            return null;
    }	
    */
    public static InvoiceDetails getById(Integer id) {
            HashMap<Integer,InvoiceDetails> map=select(" id = '"+id.toString()+"'");
            for(InvoiceDetails item:map.values())return item;
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
            Logger.getLogger(InvoiceDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(InvoiceDetails item)
    {
        delete(item.getId());
    }
    public static void insert(InvoiceDetails item)
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
            Logger.getLogger(InvoiceDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(InvoiceDetails item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId().toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static HashMap<Integer, InvoiceDetails> select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
            Connection conn=MySqlDBHelper.getInstance().getConnection();
            Statement st = null;
            ResultSet rs = null;
            try { 
                st = conn.createStatement();
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

                HashMap<Integer, InvoiceDetails> items=new HashMap<Integer, InvoiceDetails>();
                while (rs.next()) {
                    items.put(rs.getInt("id"), new InvoiceDetails(rs));
                }
                return items;
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDetails.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

    }
    //-----------database helper functions--------------
    public static String implodeValues(InvoiceDetails item,boolean withId)
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
    public static String implodeFieldsWithValues(InvoiceDetails item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("InvoiceDetails:implodeFieldsWithValues(): ERROR: values length does not match fields length");
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

        HashMap<Integer,InvoiceDetails> items=InvoiceDetails.select("");
        for(Integer key:items.keySet())
        {
            InvoiceDetails item=items.get(key);
            System.out.println(key);
            System.out.println(item);
        }
    } 
}
