package cn.com.skyvis.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author 张宇 Description: 2018年2月6日上午11:28:07
 */
public class StringUtil {
    
	/**
	 * Oracle Clob转String
	 * @param clob
	 * @return
	 */
	public static String clobToString(Clob clob) {
		Reader reader = null;
		BufferedReader buffred = null;
		String text = null;
		try {
			reader = clob.getCharacterStream();
			buffred = new BufferedReader(reader);
			String buff = buffred.readLine();
			StringBuffer sb = new StringBuffer();
			while (buff != null) {
				sb.append(buff);
				buff = buffred.readLine();
			}
			if (sb != null) {
				text = sb.toString();
			}
			text = text.replaceAll("\\s*|\t|\r|\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;

	}
	
	/**
	 * 根据sql返回表名
	 * @param sql
	 * @return
	 */
	public static String getTableName(String sql){
		String name=null;
		sql=sql.toLowerCase().replaceAll("\\s*|\t|\r|\n", "");
		if(sql!=null && !sql.equals("")){
			int beg=sql.indexOf("from");
			int end=sql.indexOf("where");
			if(beg>-1 && end >-1){
				name=sql.substring(beg+4,end);
			}else if(beg>-1 && end==-1){
				name=sql.substring(beg+4);
			}
		}
		return name;
		
	}
	/**
	 * 根据sql返回字段名
	 * @param sql
	 * @return
	 */
	public static String [] getTableMappins(String sql){
		String [] mappings=null;
		sql=sql.toLowerCase().replaceAll("\\s*|\t|\r|\n", ""); 
		if(sql!=null && !sql.equals("")){
			int beg=sql.indexOf("select");
			int end=sql.indexOf("from");
			String name=sql.substring(beg+6,end);
		    mappings=name.split(",");
		}
		return mappings;
		
	}

}
