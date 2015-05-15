package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Api {
    //------------FIELDS-----------
    public static final String tablename="api";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            ,"description"
            ,"controller"
            ,"method"
            ,"path"
            ,"returnType"
            ,"returnIsArray"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(100)"
            ,"varchar(255)"
            ,"varchar(50)"
            ,"varchar(50)"
            ,"varchar(50)"
            ,"varchar(50)"
            ,"tinyint(4)"
            };
    //-----------------------

    public Integer id;
    public String name;
    public String description;
    public String controller;
    public String method;
    public String path;
    public String returnType;
    public Integer returnIsArray;

    public Api() {
    }
    public Api(ResultSet rs) {
        try {
            id=rs.getInt("id");
            name=rs.getString("name");
            description=rs.getString("description");
            controller=rs.getString("controller");
            method=rs.getString("method");
            path=rs.getString("path");
            returnType=rs.getString("returnType");
            returnIsArray=rs.getInt("returnIsArray");
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

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public String getController() {
            return controller;
    }

    public void setController(String controller) {
            this.controller = controller;
    }

    public String getMethod() {
            return method;
    }

    public void setMethod(String method) {
            this.method = method;
    }

    public String getPath() {
            return path;
    }

    public void setPath(String path) {
            this.path = path;
    }

    public String getReturnType() {
            return returnType;
    }

    public void setReturnType(String returnType) {
            this.returnType = returnType;
    }

    public Integer getReturnIsArray() {
            return returnIsArray;
    }

    public void setReturnIsArray(Integer returnIsArray) {
            this.returnIsArray = returnIsArray;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(name);
            values.add(description);
            values.add(controller);
            values.add(method);
            values.add(path);
            values.add(returnType);
            values.add(returnIsArray!=null?returnIsArray.toString():null);

            return values;
    }
    public void delete()
    {
            Apis.delete(this);
    }
    public void save()
    {
            if(id==null || id==0 || Apis.getById(id)==null)
                    id=Apis.insert((Api)this);
            else
                    Apis.update((Api)this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
