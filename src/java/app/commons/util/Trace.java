/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.commons.util;

/**
 *
 * @author Kai
 */
public class Trace {

    Class clazz = null;
    
    public Trace(){}

    public Trace(Class c) {
        clazz = c;
    }

    public static boolean TRACE = true;

    public void out(String s) {
        
        if(TRACE) {
            StringBuilder sb = new StringBuilder(512);
            sb.append("TRACE: " + s);
            String clazzName = "";
            if(clazz != null) {
                clazzName = clazz.getName();
            }
            sb.append(" FROM ").append(clazzName);
            System.out.println(sb.toString());
        }
    }

}
