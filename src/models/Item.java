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

public class Item {
    //------------FIELDS-----------
    public static final String tablename="item";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(100)"
            };
    //-----------------------

    public Integer id;
    public String name;

    public Item() {
    }
    public Item(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
        } catch (SQLException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
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


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(name);

            return values;
    }
    public void delete()
    {
            Items.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    Items.insert(this);
            else
                    Items.update(this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
