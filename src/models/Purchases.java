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

public class Purchases {
    //------------FIELDS-----------
    public static final String tablename="purchases";
    //field names
    public static String[] fields={
            "id"
            ,"pono"
            ,"invno"
            ,"total"
            ,"tax"
            ,"vendor_id"
            ,"vendor_name"
            ,"terms_id"
            ,"employee_id"
            ,"template_id"
            ,"date"
            ,"datereceived"
            ,"duedate"
            ,"vendor_invoice"
            ,"discrate"
            ,"discamt"
            ,"status"
            ,"type"
            ,"cash"
            ,"cheque"
            ,"credit"
            ,"chequeno"
            ,"chequedate"
            ,"balance"
            ,"chequedata"
            ,"created_at"
            ,"updated_at"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"int(11)"
            ,"varchar(255)"
            ,"int(11)"
            ,"int(11)"
            ,"int(11)"
            ,"date"
            ,"date"
            ,"date"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"decimal(10,0)"
            ,"varchar(255)"
            ,"varchar(255)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"varchar(255)"
            ,"date"
            ,"decimal(10,0)"
            ,"varchar(255)"
            ,"datetime"
            ,"datetime"
            };
    //-----------------------

    public Integer id;
    public String pono;
    public String invno;
    public BigDecimal total;
    public BigDecimal tax;
    public Integer vendor_id;
    public String vendor_name;
    public Integer terms_id;
    public Integer employee_id;
    public Integer template_id;
    public Date date;
    public Date datereceived;
    public Date duedate;
    public String vendor_invoice;
    public String discrate;
    public BigDecimal discamt;
    public String status;
    public String type;
    public BigDecimal cash;
    public BigDecimal cheque;
    public BigDecimal credit;
    public String chequeno;
    public Date chequedate;
    public BigDecimal balance;
    public String chequedata;
    public Timestamp created_at;
    public Timestamp updated_at;

    public Purchases() {
    }
    public Purchases(ResultSet rs) {
        try {
            id=rs.getInt("id");
            pono=rs.getString("pono");
            invno=rs.getString("invno");
            total=rs.getBigDecimal("total");
            tax=rs.getBigDecimal("tax");
            vendor_id=rs.getInt("vendor_id");
            vendor_name=rs.getString("vendor_name");
            terms_id=rs.getInt("terms_id");
            employee_id=rs.getInt("employee_id");
            template_id=rs.getInt("template_id");
            date=rs.getDate("date");
            datereceived=rs.getDate("datereceived");
            duedate=rs.getDate("duedate");
            vendor_invoice=rs.getString("vendor_invoice");
            discrate=rs.getString("discrate");
            discamt=rs.getBigDecimal("discamt");
            status=rs.getString("status");
            type=rs.getString("type");
            cash=rs.getBigDecimal("cash");
            cheque=rs.getBigDecimal("cheque");
            credit=rs.getBigDecimal("credit");
            chequeno=rs.getString("chequeno");
            chequedate=rs.getDate("chequedate");
            balance=rs.getBigDecimal("balance");
            chequedata=rs.getString("chequedata");
            created_at=rs.getTimestamp("created_at");
            updated_at=rs.getTimestamp("updated_at");
        } catch (SQLException ex) {
            Logger.getLogger(Purchases.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getPono() {
            return pono;
    }

    public void setPono(String pono) {
            this.pono = pono;
    }

    public String getInvno() {
            return invno;
    }

    public void setInvno(String invno) {
            this.invno = invno;
    }

    public BigDecimal getTotal() {
            return total;
    }

    public void setTotal(BigDecimal total) {
            this.total = total;
    }

    public BigDecimal getTax() {
            return tax;
    }

    public void setTax(BigDecimal tax) {
            this.tax = tax;
    }

    public Integer getVendorId() {
            return vendor_id;
    }

    public void setVendorId(Integer vendor_id) {
            this.vendor_id = vendor_id;
    }

    public String getVendorName() {
            return vendor_name;
    }

    public void setVendorName(String vendor_name) {
            this.vendor_name = vendor_name;
    }

    public Integer getTermsId() {
            return terms_id;
    }

    public void setTermsId(Integer terms_id) {
            this.terms_id = terms_id;
    }

    public Integer getEmployeeId() {
            return employee_id;
    }

    public void setEmployeeId(Integer employee_id) {
            this.employee_id = employee_id;
    }

    public Integer getTemplateId() {
            return template_id;
    }

    public void setTemplateId(Integer template_id) {
            this.template_id = template_id;
    }

    public Date getDate() {
            return date;
    }

    public void setDate(Date date) {
            this.date = date;
    }

    public Date getDatereceived() {
            return datereceived;
    }

    public void setDatereceived(Date datereceived) {
            this.datereceived = datereceived;
    }

    public Date getDuedate() {
            return duedate;
    }

    public void setDuedate(Date duedate) {
            this.duedate = duedate;
    }

    public String getVendorInvoice() {
            return vendor_invoice;
    }

    public void setVendorInvoice(String vendor_invoice) {
            this.vendor_invoice = vendor_invoice;
    }

    public String getDiscrate() {
            return discrate;
    }

    public void setDiscrate(String discrate) {
            this.discrate = discrate;
    }

    public BigDecimal getDiscamt() {
            return discamt;
    }

    public void setDiscamt(BigDecimal discamt) {
            this.discamt = discamt;
    }

    public String getStatus() {
            return status;
    }

    public void setStatus(String status) {
            this.status = status;
    }

    public String getType() {
            return type;
    }

    public void setType(String type) {
            this.type = type;
    }

    public BigDecimal getCash() {
            return cash;
    }

    public void setCash(BigDecimal cash) {
            this.cash = cash;
    }

    public BigDecimal getCheque() {
            return cheque;
    }

    public void setCheque(BigDecimal cheque) {
            this.cheque = cheque;
    }

    public BigDecimal getCredit() {
            return credit;
    }

    public void setCredit(BigDecimal credit) {
            this.credit = credit;
    }

    public String getChequeno() {
            return chequeno;
    }

    public void setChequeno(String chequeno) {
            this.chequeno = chequeno;
    }

    public Date getChequedate() {
            return chequedate;
    }

    public void setChequedate(Date chequedate) {
            this.chequedate = chequedate;
    }

    public BigDecimal getBalance() {
            return balance;
    }

    public void setBalance(BigDecimal balance) {
            this.balance = balance;
    }

    public String getChequedata() {
            return chequedata;
    }

    public void setChequedata(String chequedata) {
            this.chequedata = chequedata;
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
            values.add(pono);
            values.add(invno);
            values.add(total.toString());
            values.add(tax.toString());
            values.add(vendor_id.toString());
            values.add(vendor_name);
            values.add(terms_id.toString());
            values.add(employee_id.toString());
            values.add(template_id.toString());
            values.add(date.toString());
            values.add(datereceived.toString());
            values.add(duedate.toString());
            values.add(vendor_invoice);
            values.add(discrate);
            values.add(discamt.toString());
            values.add(status);
            values.add(type);
            values.add(cash.toString());
            values.add(cheque.toString());
            values.add(credit.toString());
            values.add(chequeno);
            values.add(chequedate.toString());
            values.add(balance.toString());
            values.add(chequedata);
            values.add(created_at.toString());
            values.add(updated_at.toString());

            return values;
    }
    public void delete()
    {
            Purchases.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    Purchases.insert(this);
            else
                    Purchases.update(this);
    }
    public String toString()
    {
            return id.toString();
    }

    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    /*
    public static Purchases getByName(String name)
    {
            HashMap<Integer,Purchases> map=select(" name = '"+name+"'");
            for(Purchases item:map.values())return item;
            return null;
    }	
    */
    public static Purchases getById(Integer id) {
            HashMap<Integer,Purchases> map=select(" id = '"+id.toString()+"'");
            for(Purchases item:map.values())return item;
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
            Logger.getLogger(Purchases.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void delete(Purchases item)
    {
        delete(item.getId());
    }
    public static void insert(Purchases item)
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
            Logger.getLogger(Purchases.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void update(Purchases item)
    {
        Connection conn=MySqlDBHelper.getInstance().getConnection();            
        Statement st = null;
        boolean withid=false;
        try { 
            st = conn.createStatement();
            st.executeUpdate("update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId().toString()+"';");
        } catch (SQLException ex) {
            Logger.getLogger(Purchases.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static HashMap<Integer, Purchases> select(String conditions)
    {
        if(conditions.isEmpty())conditions = "1";
            Connection conn=MySqlDBHelper.getInstance().getConnection();
            Statement st = null;
            ResultSet rs = null;
            try { 
                st = conn.createStatement();
                rs = st.executeQuery("SELECT * from "+tablename+" where "+conditions);

                HashMap<Integer, Purchases> items=new HashMap<Integer, Purchases>();
                while (rs.next()) {
                    items.put(rs.getInt("id"), new Purchases(rs));
                }
                return items;
            } catch (SQLException ex) {
                Logger.getLogger(Purchases.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

    }
    //-----------database helper functions--------------
    public static String implodeValues(Purchases item,boolean withId)
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
    public static String implodeFieldsWithValues(Purchases item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("Purchases:implodeFieldsWithValues(): ERROR: values length does not match fields length");
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

        HashMap<Integer,Purchases> items=Purchases.select("");
        for(Integer key:items.keySet())
        {
            Purchases item=items.get(key);
            System.out.println(key);
            System.out.println(item);
        }
    } 
}
