/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author jaspertomas
 */
public class GeneratorHelper {
    public static String toUnderscoreCase(String string)
    {
        String letter="";
        String output="";
        //for each letter
        for(int i=0;i<string.length();i++)
        {
            letter=String.valueOf(string.charAt(i));
            //if uppercase
            if(letter.matches("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]") )
            {
                //if first letter
                if(i==0)
                {
                    output+=letter.toLowerCase();
                }
                else
                {
                    output+="_"+letter.toLowerCase();
                }
            }
            else
            {
                output+=letter;
            }
        }
        return output;
    }    
    public static String singularize(String table)
    {
        String singular=table;
        //if length > 3 and last 3 letters = "ies"
        if(table.length()>3 && table.substring(table.length()-3, table.length()).contentEquals("ies"))
        {
            singular=table.substring(0,table.length()-3)+"y";
        }
        else if(table.length()>1 && table.substring(table.length()-1, table.length()).contentEquals("s"))
        {
            singular=table.substring(0,table.length()-1);
        }
        else 
            singular= "";
        
        return singular;
    }
    public static String toLabelCase(String string)
    {
        boolean first=true;
        string=string.replace("[", "_");
        string=string.replace("]", "");
        String[] segments=string.split("_");
        String output="";
        for(String s:segments)
        {
            if(first)
            {
                output+=capitalize(s);
                first=false;
            }
            else
            {
                output+=" "+s;
            }
        }
        if(output.contains(" id"))
            output=output.replace(" id", " ID");
        return output;
    }
    public static String toCamelCase(String string)
    {
        string=string.replace("[", "_");
        string=string.replace("]", "");
        String[] segments=string.split("_");
        String output="";
        for(String s:segments)
            output+=capitalize(s);
        return output;
    }
    public static String toCamelCaseLowerStart(String string)
    {
        string=string.replace("[", "_");
        string=string.replace("]", "");
        String[] segments=string.split("_");
        String output=segments[0];//first segment not capitalized
        for(int i=1;i<segments.length;i++)
            output+=capitalize(segments[i]);
        return output;
    }
    public static String capitalize(String s)
    {
        if(s.length()==0)
        return "";
        else if(s.length()==1)
        return String.valueOf(s.charAt(0));
        else
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }
    public static String datatypeFor(String type)
    {
        type=type.replaceAll("[,0-9]", "");
        //System.out.println(type);
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return "Integer";
        else if(type.contentEquals("varchar()"))
            return "String";
        else if(type.contentEquals("char()"))
            return "String";
        else if(type.contentEquals("text"))
            return "String";
        else if(type.contentEquals("tinytext"))
            return "String";
        else if(type.contentEquals("date"))
            return "Date";
        else if(type.contains("bigint"))
            return "Long";
        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
            return "Integer";
        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
            return "BigDecimal";
        else if(type.contentEquals("float")||type.contentEquals("float()"))
            return "Float";
        else if(type.contentEquals("double")||type.contentEquals("double()"))
            return "Double";
        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
            return "Boolean";
        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
            return "Timestamp";
        else if(type.contains("enum"))
            return "String";
        else 
            return "";
/*
<option value="INT" selected="selected">INT</option>
<option value="VARCHAR">VARCHAR</option>
<option value="TEXT">TEXT</option>
<option value="DATE">DATE</option>
<optgroup label="NUMERIC"><option value="TINYINT">TINYINT</option>
<option value="SMALLINT">SMALLINT</option>
<option value="MEDIUMINT">MEDIUMINT</option>
<option value="INT" selected="selected">INT</option>
<option value="BIGINT">BIGINT</option>
<option value="-">-</option>
<option value="DECIMAL">DECIMAL</option>
<option value="FLOAT">FLOAT</option>
<option value="DOUBLE">DOUBLE</option>
<option value="REAL">REAL</option>
<option value="-">-</option>
<option value="BIT">BIT</option>
<option value="BOOLEAN">BOOLEAN</option>
<option value="SERIAL">SERIAL</option>
</optgroup><optgroup label="DATE and TIME"><option value="DATE">DATE</option>
<option value="DATETIME">DATETIME</option>
<option value="TIMESTAMP">TIMESTAMP</option>
<option value="TIME">TIME</option>
<option value="YEAR">YEAR</option>
</optgroup><optgroup label="STRING"><option value="CHAR">CHAR</option>
<option value="VARCHAR">VARCHAR</option>
<option value="-">-</option>
<option value="TINYTEXT">TINYTEXT</option>
<option value="TEXT">TEXT</option>
<option value="MEDIUMTEXT">MEDIUMTEXT</option>
<option value="LONGTEXT">LONGTEXT</option>
<option value="-">-</option>
<option value="BINARY">BINARY</option>
<option value="VARBINARY">VARBINARY</option>
<option value="-">-</option>
<option value="TINYBLOB">TINYBLOB</option>
<option value="MEDIUMBLOB">MEDIUMBLOB</option>
<option value="BLOB">BLOB</option>
<option value="LONGBLOB">LONGBLOB</option>
<option value="-">-</option>
<option value="ENUM">ENUM</option>
<option value="SET">SET</option>
</optgroup><optgroup label="SPATIAL"><option value="GEOMETRY">GEOMETRY</option>
<option value="POINT">POINT</option>
<option value="LINESTRING">LINESTRING</option>
<option value="POLYGON">POLYGON</option>
<option value="MULTIPOINT">MULTIPOINT</option>
<option value="MULTILINESTRING">MULTILINESTRING</option>
<option value="MULTIPOLYGON">MULTIPOLYGON</option>
<option value="GEOMETRYCOLLECTION">GEOMETRYCOLLECTION</option>
</optgroup>    
 */        
    }
//    public static String rsGetterFor(String type, String field)
//    {
//        type=type.replaceAll("[,0-9]", "");
//        if(type.contentEquals("int")||type.contentEquals("int()"))
//            return "c.getInt"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contains("varchar()"))
//            return "c.getString"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("char()"))
//            return "c.getString"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("text"))
//            return "c.getString"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("tinytext"))
//            return "c.getString"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("date"))
//            return "StandardDateHelper.toDate(c.getString"+"(c.getColumnIndex(\""+field+"\")));";
//        else if(type.contains("bigint"))
//            return "c.getLong"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
//            return "c.getInt"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
//            return "c.getBigDecimal"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("float")||type.contentEquals("float()"))
//            return "c.getFloat"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("double")||type.contentEquals("double()"))
//            return "c.getDouble"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
//            return "c.getBoolean"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
//            return "c.getTimestamp"+"(c.getColumnIndex(\""+field+"\"));";
//        else if(type.contains("enum"))
//            return "c.getString"+"(c.getColumnIndex(\""+field+"\"));";
//        else 
//            return "";  
//    }    
//    public static String stringifier(String type)
//    {
//        type=type.replaceAll("[,0-9]", "");
//        if(type.contentEquals("int")||type.contentEquals("int()"))
//            return ".toString()";
//        else if(type.contains("varchar()"))
//            return "";
//        else if(type.contentEquals("char()"))
//            return "";
//        else if(type.contentEquals("text"))
//            return "";
//        else if(type.contentEquals("tinytext"))
//            return "";
//        else if(type.contentEquals("date"))
//            return ".toString()";
//        else if(type.contains("bigint"))
//            return ".toString()";
//        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
//            return ".toString()";
//        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
//            return ".toString()";
//        else if(type.contentEquals("float")||type.contentEquals("float()"))
//            return ".toString()";
//        else if(type.contentEquals("double")||type.contentEquals("double()"))
//            return ".toString()";
//        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
//            return ".toString()";
//        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
//            return ".toString()";
//        else if(type.contains("enum"))
//            return "";
//        else 
//            return "";  
//    }       
//    public static String stringifiedWithNull(String field,String type)
//    {
//        type=type.replaceAll("[,0-9]", "");
//        if(type.contentEquals("int")||type.contentEquals("int()"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contains("varchar()"))
//            return field;
//        else if(type.contentEquals("char()"))
//            return field;
//        else if(type.contentEquals("text"))
//            return field;
//        else if(type.contentEquals("tinytext"))
//            return field;
//        else if(type.contentEquals("date"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contains("bigint"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contentEquals("float")||type.contentEquals("float()"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contentEquals("double")||type.contentEquals("double()"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
//            return field+"!=null?"+field+".toString():null";
//        else if(type.contains("enum"))
//            return field;
//        else 
//            return field;  
//    }    
    public static String jsonGetterFor(String type, String field)
    {
        type=type.replaceAll("[,0-9]", "");
        if(type.contentEquals("int")||type.contentEquals("int()"))
            return "getInt";
        else if(type.contains("varchar()"))
            return "getString";
        else if(type.contentEquals("char()"))
            return "getString";
        else if(type.contentEquals("text"))
            return "getString";
        else if(type.contentEquals("tinytext"))
            return "getString";
        else if(type.contentEquals("date"))
            return "StandardDateHelper.toDate(getString"+"(getColumnIndex(\""+field+"\")));";
//        else if(type.contains("bigint"))
//            return "getLong";
//        else if(type.contains("tinyint") || type.contains("smallint") || type.contains("mediumint"))
//            return "getInt";
//        else if(type.contentEquals("decimal")||type.contentEquals("decimal()"))
//            return "getBigDecimal";
//        else if(type.contentEquals("float")||type.contentEquals("float()"))
//            return "getFloat";
//        else if(type.contentEquals("double")||type.contentEquals("double()"))
//            return "getDouble";
        else if(type.contentEquals("boolean")||type.contentEquals("boolean()"))
            return "getBoolean";
//        else if(type.contentEquals("datetime")||type.contentEquals("timestamp"))
//            return "getTimestamp";
//        else if(type.contains("enum"))
//            return "getString";
        else 
            return "";  
    }    
     
    
}
