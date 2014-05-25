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

public class Vehicle {
    //------------FIELDS-----------
    public static final String tablename="vehicle";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            ,"plate"
            ,"registration"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(100)"
            ,"varchar(10)"
            ,"varchar(30)"
            };
    //-----------------------

    public Integer id;
    public String name;
    public String plate;
    public String registration;

    public Vehicle() {
    }
    public Vehicle(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
            plate=rs.getString("plate");
            registration=rs.getString("registration");
        } catch (SQLException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getPlate() {
            return plate;
    }

    public void setPlate(String plate) {
            this.plate = plate;
    }

    public String getRegistration() {
            return registration;
    }

    public void setRegistration(String registration) {
            this.registration = registration;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(name);
            values.add(plate);
            values.add(registration);

            return values;
    }
    public void delete()
    {
            Vehicles.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    Vehicles.insert(this);
            else
                    Vehicles.update(this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
