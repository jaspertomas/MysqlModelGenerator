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

public class Parameter {
    //------------FIELDS-----------
    public static final String tablename="parameter";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            ,"type"
            ,"options"
            ,"is_required"
            ,"is_optional"
            ,"is_deprecated"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(50)"
            ,"varchar(50)"
            ,"varchar(255)"
            ,"tinyint(4)"
            ,"tinyint(4)"
            ,"tinyint(4)"
            };
    //-----------------------

    public Integer id;
    public String name;
    public String type;
    public String options;
    public Integer is_required;
    public Integer is_optional;
    public Integer is_deprecated;

    public Parameter() {
    }
    public Parameter(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
            type=rs.getString("type");
            options=rs.getString("options");
            is_required=rs.getInt("is_required");
            is_optional=rs.getInt("is_optional");
            is_deprecated=rs.getInt("is_deprecated");
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

    public String getType() {
            return type;
    }

    public void setType(String type) {
            this.type = type;
    }

    public String getOptions() {
            return options;
    }

    public void setOptions(String options) {
            this.options = options;
    }

    public Integer getIsRequired() {
            return is_required;
    }

    public void setIsRequired(Integer is_required) {
            this.is_required = is_required;
    }

    public Integer getIsOptional() {
            return is_optional;
    }

    public void setIsOptional(Integer is_optional) {
            this.is_optional = is_optional;
    }

    public Integer getIsDeprecated() {
            return is_deprecated;
    }

    public void setIsDeprecated(Integer is_deprecated) {
            this.is_deprecated = is_deprecated;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(name);
            values.add(type);
            values.add(options);
            values.add(is_required!=null?is_required.toString():null);
            values.add(is_optional!=null?is_optional.toString():null);
            values.add(is_deprecated!=null?is_deprecated.toString():null);

            return values;
    }
    public void delete()
    {
            Parameters.delete(this);
    }
    public void save()
    {
            if(id==null || id==0 || Parameters.getById(id)==null)
                    id=Parameters.insert((Parameter)this);
            else
                    Parameters.update((Parameter)this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
