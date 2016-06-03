package com.jandar.frame.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jandar.frame.support.PaginationSupport;

/**
 * @author ZXP
 */
public class JQueryDataTablesUtil {
	static public <T> String prepareResponseJson(
			String echo, ArrayList<String> props,PaginationSupport<T> result) {
		List<T> ts = result.getItems();
		StringBuffer aaData = new StringBuffer("[");
		if (ts != null && ts.size() > 0) {
			int ti=0;
			for (T s : ts) {
				if (ti > 0) {
					aaData.append(",");
				}
				aaData.append("{");
				int pi = 0;
				for (String prop : props) {
					if (pi > 0) {
						aaData.append(",");
					}
					aaData.append("\"" + prop + "\":");
					aaData.append("\"" + getObjectProp(s, prop) + "\"");
					pi++;
				}// for prop
				aaData.append("}");
				ti++;
			}// for Ts
		}
		aaData.append("]");
		// responseText("{\"iTotalDisplayRecords\":44,\"iTotalRecords\":100,\"aaData\":[{\"ID\":1},{\"ID\":2}],\"sEcho\":1}");
		String t=("{\"sEcho\":"+echo+",\"iTotalDisplayRecords\":"+result.getTotalCount()+",\"iTotalRecords\":"+result.getTotalCount()+",\"aaData\":"+aaData
				+"}");
		System.out.println(t);
		return t;
	}

	static public String getObjectProp(Object model, String prop) {
		try {
			Method m = model.getClass().getMethod("get" + prop);
			return  m.invoke(model).toString(); // 调用getter方法获取属性值
		} catch (Exception e) {
			System.out.println("getObjectProp "+prop+ " error:"+e.getMessage());
		}
		return "";
	}
	
	private static ObjectMapper mapper;
	public static synchronized ObjectMapper getMapperInstance() {   
        if (mapper == null) {   
            mapper = new ObjectMapper();   
            //@JsonInclude(Include.NON_NULL) //for pojo annotation
            mapper.setSerializationInclusion(Include.NON_NULL);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            mapper.setDateFormat(fmt);  
        } 
        return mapper;
	}
	
	/**
	 * use jackson
	 * @param echo
	 * @param props
	 * @param result
	 * @return
	 */
	static public <T> String prepareResponseJsonData(String echo, ArrayList<String> props,PaginationSupport<T> result) {
		String t=("{\"sEcho\":"+echo+",\"iTotalDisplayRecords\":"+result.getTotalCount()+",\"iTotalRecords\":"+result.getTotalCount()+",\"aaData\":"+beanToJson(result.getItems())+"}");
		return t;
	}
	
	public static String beanToJson(Object obj){
		try {
			ObjectMapper objectMapper = getMapperInstance();
			String json =objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";		
	}

}
