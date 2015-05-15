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

public class Purchase {
    //------------FIELDS-----------
    public static final String tablename="purchase";
    //field names
    public static String[] fields={
            "id"
            ,"date"
            ,"vehicle_id"
            ,"amount"
            ,"description"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"date"
            ,"int(11)"
            ,"decimal(10,0)"
            ,"text"
            };
    //-----------------------

    public Integer id;
    public Date date;
    public Integer vehicle_id;
    public Double amount;
    public String description;

    public Purchase() {
    }
    public Purchase(ResultSet rs) {
        try {
            id=rs.getInt("id");
            date=rs.getDate("date");
            vehicle_id=rs.getInt("vehicle_id");
            amount=rs.getDouble("amount");
            description=rs.getString("description");
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
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

    public Double getAmount() {
            return amount;
    }

    public void setAmount(Double amount) {
            this.amount = amount;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(date!=null?date.toString():null);
            values.add(vehicle_id!=null?vehicle_id.toString():null);
            values.add(amount!=null?amount.toString():null);
            values.add(description);

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
    @Override
    public String toString()
    {
            return id.toString();
    }
}
