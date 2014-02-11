/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlmodelgenerator;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.MySqlDBHelper;
import utils.fileaccess.FileWriter;

/**
 *
 * @author jaspertomas
 */
public class MysqlModelGenerator {

    public static void main(String args[])
    {
        String databasename="tmcuser";
        String password = "password";
        String username = "root";
        String url = "jdbc:mysql://localhost:3306/"+databasename;

        MySqlDBHelper.init(url, username, password);            
        Connection conn=MySqlDBHelper.getInstance().getConnection();
        
        Statement st = null;
        ResultSet rs = null;
        ArrayList<String> tables=new ArrayList<String>();
        ArrayList<String> fields=new ArrayList<String>();
        ArrayList<String> fieldtypes=new ArrayList<String>();
        try { 
            st = conn.createStatement();
            rs = st.executeQuery("Show Tables");

            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            for(String table:tables) {
                rs = st.executeQuery("SHOW COLUMNS FROM "+table);
                fields.clear();
                fieldtypes.clear();
                while (rs.next()) {
                    fields.add(rs.getString(1));
                    fieldtypes.add(rs.getString(2));
                }
                createModelFile(table,fields,fieldtypes);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void createModelFile(String table, ArrayList<String> fields, ArrayList<String> fieldtypes)
    {
        //write file to current directory
        File outputdir=new File("./output");
        outputdir.mkdir();
        FileWriter.write(outputdir.getPath()+"/a.txt", "hello world");

        
    }
}
