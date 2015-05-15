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

public class Model {
    //------------FIELDS-----------
    public static final String tablename="model";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(30)"
            };
    //-----------------------

    public Integer id;
    public String name;

    public Model() {
    }
    public Model(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
        } catch (SQLException ex) {
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
            Models.delete(this);
    }
    public void save()
    {
            if(id==null || id==0 || Models.getById(id)==null)
                    id=Models.insert((Model)this);
            else
                    Models.update((Model)this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
