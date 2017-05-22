package com.hospital.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static JSONArray formatRsToJsonArray(ResultSet rs)throws Exception{
		ResultSetMetaData md=rs.getMetaData();
		int num=md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				Object o=rs.getObject(i);
				if(o instanceof Timestamp){
					Calendar ca=Calendar.getInstance();
					ca.setTime((Timestamp)o);
					ca.add(Calendar.HOUR_OF_DAY, 2);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateStr = sdf.format(ca.getTime());
					Timestamp timestamp = Timestamp.valueOf(dateStr);
					mapOfColValues.put(md.getColumnName(i),DataUtil.formatDate((Timestamp)o, "yyyy-MM-dd HH:mm") + "至" + DataUtil.formatDate(timestamp, "HH:mm"));
				}else{
					mapOfColValues.put(md.getColumnName(i),rs.getObject(i));
				}
			}
			array.add(mapOfColValues);
		}
		return array;
	}
}
