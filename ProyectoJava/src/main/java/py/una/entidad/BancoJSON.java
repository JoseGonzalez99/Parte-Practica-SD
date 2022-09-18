package py.una.entidad;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BancoJSON {


    
    @SuppressWarnings("unchecked")
	public static String objetoString(Banco p) {	
		JSONObject obj = new JSONObject();
        obj.put("id", p.getID());
        obj.put("nombre", p.getNombre());
        obj.put("cotizacion", p.getCotizacion().longValue());

        return obj.toJSONString();
    }
    



    
    public static Banco stringObjeto(String str) throws Exception {
    	Banco p = new Banco();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;
       
        Long identificador = (Long) jsonObject.get("id");
        
        Long cotizacion = (Long) jsonObject.get("cotizacion");
       
        p.setID(identificador.intValue());
        //p.setID(identificador);
        p.setNombre((String)jsonObject.get("nombre"));
        
        p.setCotizacion((Integer)cotizacion.intValue());
        return p;
	}
    
    public static String response(String str) throws Exception {
    	JSONObject obj = new JSONObject();
        obj.put("mensaje", str);
         return obj.toJSONString();
    	
    }
    
    public static String read_response(String str) throws Exception {
    	JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;
       
        String msj = (String) jsonObject.get("mensaje");
        return msj;
    	
    }
    
}
