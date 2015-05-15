package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SqliteDbHelper;
import utils.JsonHelper;

public class PurchaseItem {
    //------------FIELDS-----------
    public static final String tablename="purchase_item";
    //field names
    public static String[] fields={
            "id"
            ,"purchase_id"
            ,"date"
            ,"vehicle_id"
            ,"item_id"
            ,"unit_price"
            ,"qty"
            ,"amount"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"int(11)"
            ,"date"
            ,"int(11)"
            ,"int(11)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            ,"decimal(10,0)"
            };
    //-----------------------

    public Integer id;
    public Integer purchase_id;
    public Date date;
    public Integer vehicle_id;
    public Integer item_id;
    public Double unit_price;
    public Double qty;
    public Double amount;

    public PurchaseItem() {
    }
    public PurchaseItem(ResultSet rs) {
        try {
            id=rs.getInt("id");
            purchase_id=rs.getInt("purchase_id");
            date=rs.getDate("date");
            vehicle_id=rs.getInt("vehicle_id");
            item_id=rs.getInt("item_id");
            unit_price=rs.getDouble("unit_price");
            qty=rs.getDouble("qty");
            amount=rs.getDouble("amount");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseItem.class.getName()).log(Level.SEVERE, null, ex);
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

    public Integer getPurchaseId() {
            return purchase_id;
    }

    public void setPurchaseId(Integer purchase_id) {
            this.purchase_id = purchase_id;
    }

    public Date getDate() {
            return date;
    }

    public void setDate(Date date) {
            this.date = date;
    }

    public Integer getVehicleId() {
            return vehicle_id;
    }

    public void setVehicleId(Integer vehicle_id) {
            this.vehicle_id = vehicle_id;
    }

    public Integer getItemId() {
            return item_id;
    }

    public void setItemId(Integer item_id) {
            this.item_id = item_id;
    }

    public Double getUnitPrice() {
            return unit_price;
    }

    public void setUnitPrice(Double unit_price) {
            this.unit_price = unit_price;
    }

    public Double getQty() {
            return qty;
    }

    public void setQty(Double qty) {
            this.qty = qty;
    }

    public Double getAmount() {
            return amount;
    }

    public void setAmount(Double amount) {
            this.amount = amount;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(purchase_id!=null?purchase_id.toString():null);
            values.add(date!=null?date.toString():null);
            values.add(vehicle_id!=null?vehicle_id.toString():null);
            values.add(item_id!=null?item_id.toString():null);
            values.add(unit_price!=null?unit_price.toString():null);
            values.add(qty!=null?qty.toString():null);
            values.add(amount!=null?amount.toString():null);

            return values;
    }
    public void delete()
    {
            PurchaseItems.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    PurchaseItems.insert(this);
            else
                    PurchaseItems.update(this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
