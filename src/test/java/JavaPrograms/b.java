package JavaPrograms;


import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class b {
	
	 //	{
//	  "Company": {

//	    "Apple": {
//	      "products": ["iPhone", "iPad", "MacBook"],
//	      "founded": 1976,
//	      "isPublic": true
//	    },
//	    "Samsung": {
//	      "products": ["Galaxy S23", "Galaxy Tab"],
//	      "founded": 1938,
//	      "isPublic": true
//	    }
//	  }
//	}
	
	public static void main(String[]args) throws JsonProcessingException {

	List<String> sp = new ArrayList<>();
    sp.add("Galaxt S23");
    sp.add("Galaxy Tab");
    
    Map<String, Object> sm = new HashMap<>();
    sm.put("products", sp);
    sm.put("founded", "1938");
    sm.put("isPublic", "true");
    
    List<String> ap = new ArrayList<>();
    ap.add("iPhone");
    ap.add("ipad");
    ap.add("MacBook");
    
    Map<String, Object> am = new LinkedHashMap<>();
    am.put("products", ap);
    am.put("founded", "1976");
    am.put("isPublic", "true");
    
     List<Object> c = new ArrayList<>();
     c.add(am);
     c.add(sm);
     
     Map<String, Object> t = new HashMap<>();
     t.put("Company", c);
     
     
     ObjectMapper mapper = new ObjectMapper();
     mapper.enable(SerializationFeature.INDENT_OUTPUT);

     // Pretty JSON
     String prettyJson = mapper.writeValueAsString(t);
     System.out.println(prettyJson);
}}
