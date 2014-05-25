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
import utils.SqliteDbHelper;
import utils.JsonHelper;

public class PurchaseItem {
    //------------FIELDS-----------
    public static final String tablename="purchase_item";
    //field names
    public static String[] fields={
            "id"
            ,"vehicle_id"
            ,"item_id"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"int(11)"
            ,"int(11)"
            };
    //-----------------------

    public Integer id;
    public Integer vehicle_id;
    public Integer item_id;

    public PurchaseItem() {
    }
    public PurchaseItem(ResultSet rs) {
        try {
            id=rs.getInt("id");
            vehicle_id=rs.getInt("vehicle_id");
            item_id=rs.getInt("item_id");
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


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(vehicle_id!=null?vehicle_id.toString():null);
            values.add(item_id!=null?item_id.toString():null);

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
