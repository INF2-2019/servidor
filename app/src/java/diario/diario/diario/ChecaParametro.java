package diario.diario.diario;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author juan
 */
public class ChecaParametro {
    public static boolean parametroExiste(HttpServletRequest req, String parametro){
        return req.getParameter(parametro)!=null;
    }
    
    public static boolean parametroEInteiro(HttpServletRequest req, String parametro){
        try{
            int numero = Integer.valueOf(req.getParameter(parametro));
            return true;
        }catch(NumberFormatException e){}
        return false;
    }
    
    public static boolean parametroEDecimal(HttpServletRequest req, String parametro){
        try{
            Double numero = Double.valueOf(req.getParameter(parametro));
            return true;
        }catch(NumberFormatException e){}
        return false;
    }
    
    public static boolean parametroEData(HttpServletRequest req, String parametro){
        try{
            Date data = Date.valueOf(req.getParameter(parametro));
            return true;
        }catch(IllegalArgumentException e){}
        return false;
    }
    
    public static boolean parametroNaoVazio(HttpServletRequest req, String parametro){
        return true;
    }
    
    public static String parametroFaltante(HttpServletRequest req, String ...parametros){
        for(String parametro:parametros){
            if(!parametroExiste(req,parametro)) 
                return parametro;
        }
        return null;
    }
}
