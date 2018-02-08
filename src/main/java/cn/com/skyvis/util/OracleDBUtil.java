package cn.com.skyvis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


/**
 * oracle数据库处理类
 * @author 张宇
 * Description:
 * 2018年2月6日下午1:28:19
 */
public class OracleDBUtil {
	
	private static final String URL = "jdbc:oracle:thin:@192.168.1.5:1521:MyOracleDB";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "skyvis123456";
	private static final String DRVIER = "oracle.jdbc.OracleDriver";
	//数据库conn对象
	private static Connection conn=null;
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		if(conn==null){
			try {
				//加载数据库驱动
				Class.forName(DRVIER);
				//创建conn连接
				conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	/**
	 * 关闭数据库
	 * @param conn
	 * @param stmet
	 * @param res
	 */
	public static void closeOracleConn(Connection conn,PreparedStatement stmet,ResultSet res){
		try {
			if (res != null) {
				res.close();
			}
			if (stmet != null) {
				stmet.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据table返回表结构
	 * @param table
	 * @return
	 */
	public static Map<String,String> queryTableMappings(String table){
		Map<String,String> map=null;
		Connection conn=getConnection();
		PreparedStatement stment=null;
		ResultSet res=null;
		try{
			stment=conn.prepareStatement("select column_name,data_type from user_tab_columns where table_name='"+table.toUpperCase()+"'");
			res=stment.executeQuery();
			map=new HashMap<String, String>();
			while(res.next()){
				 map.put(res.getString("column_name").toLowerCase(), res.getString("data_type").toLowerCase());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return map;
	}
}
