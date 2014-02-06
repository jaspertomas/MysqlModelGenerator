/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlmodelgenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import utils.MySqlDBHelper;

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
        try { 
            st = conn.createStatement();
//                rs = st.executeQuery("SELECT VERSION()");
            rs = st.executeQuery("Show Tables");

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
