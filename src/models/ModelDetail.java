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

public class ModelDetail {
    //------------FIELDS-----------
    public static final String tablename="model_detail";
    //field names
    public static String[] fields={
            "id"
            ,"model_id"
            ,"field"
            ,"fieldtype"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"int(11)"
            ,"varchar(30)"
            ,"varchar(30)"
            };
    //-----------------------

    public Integer id;
    public Integer model_id;
    public String field;
    public String fieldtype;

    public ModelDetail() {
    }
    public ModelDetail(ResultSet rs) {
        try {
            id=rs.getInt("id");
            model_id=rs.getInt("model_id");
            field=rs.getString("field");
            fieldtype=rs.getString("fieldtype");
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

    public Integer getModelId() {
            return model_id;
    }

    public void setModelId(Integer model_id) {
            this.model_id = model_id;
    }

    public String getField() {
            return field;
    }

    public void setField(String field) {
            this.field = field;
    }

    public String getFieldtype() {
            return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
            this.fieldtype = fieldtype;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(model_id!=null?model_id.toString():null);
            values.add(field);
            values.add(fieldtype);

            return values;
    }
    public void delete()
    {
            ModelDetails.delete(this);
    }
    public void save()
    {
            if(id==null || id==0 || ModelDetails.getById(id)==null)
                    id=ModelDetails.insert((ModelDetail)this);
            else
                    ModelDetails.update((ModelDetail)this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
