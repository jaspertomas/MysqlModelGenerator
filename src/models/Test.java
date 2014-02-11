/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author jaspertomas
 */
public class Test {
    public static void main(String args[])
    {
        String type="abcd45782abcd";
        type=type.replaceAll("[0-9]", "");
        System.out.println(type);

    }
}
