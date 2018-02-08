package cn.com.skyvis.dao;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.research.ws.wadl.Doc;

import cn.com.skyvis.util.DocUtil;
import cn.com.skyvis.util.OracleDBUtil;
import cn.com.skyvis.util.StringUtil;

public class QueryDao {
	/**
	 *  查询表数据插入es
	 * @param index
	 *             索引
	 * @param type
	 *             type
	 * @param sql
	 *             查询sql 支持   select *  from table ?可以加条件
	 *                          select  ajbs,baah from table ?可以加条件
	 * @param path
	 *            Bolb类型文件存放地址
	 * @return
	 */
	public static List<Map<String,Object>> queryList(String index,String type,String id,String sql,String path){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Connection conn=OracleDBUtil.getConnection();
		PreparedStatement stmet=null;
		ResultSet res=null;
		try{
			stmet=conn.prepareStatement(sql);
			res=stmet.executeQuery();
			Map<String,String> map=OracleDBUtil.queryTableMappings(StringUtil.getTableName(sql));
			//查看sql是否包含 * 
			if(sql.contains("*")){
				//获取表结构
				while(res.next()){
					//判断表结构判断后插入map
				    list.add(detailInfo(map,res,path));
					if(list.size()==100){
						new EsDao().EsImport(index,type,id,list);
						list=new ArrayList<Map<String,Object>>();
					}
				}
				new EsDao().EsImport(index,type,id,list);
			}else{
				//获取sql字段
				String [] mappings=StringUtil.getTableMappins(sql);
				//需要查询的字段的Map key 字段名 value 字段类型
				Map<String,String> replaceMap=new HashMap<String,String>();
				for (int i = 0; i < mappings.length; i++) {
					replaceMap.put(mappings[i], map.get(mappings[i]));
				}
				while(res.next()){
					//判断表结构判断后插入map
					 list.add(detailInfo(map,res,path));
					 if(list.size()==100){
						 new EsDao().EsImport(index,type,id,list);
						 list=new ArrayList<Map<String,Object>>();
					 }
				}
				new EsDao().EsImport(index,type,id,list);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			OracleDBUtil.closeOracleConn(conn, stmet, res);
		}
		return list;
	} 
	
	/**
	 * 根据ResultSet行数据封装Map 返回
	 * @param map
	 *           index _mappings 
	 * @param res
	 *           Result set 
	 * @param path
	 *           用于Blob类型存储地址
	 * @return
	 */
	public static Map<String,Object> detailInfo(Map<String,String> map,ResultSet res,String path){
		Map<String,Object> listMap=new HashMap<String,Object>();
		for (Map.Entry<String,String> entry:map.entrySet()) {
			try{
			//如果是blob类型的话需要处理
			if(entry.getValue().equals("blob")){
				Blob blob=res.getBlob(entry.getKey());
				DocUtil.blobExportDoc(path,blob);
				String text=DocUtil.readDoc(path);
				listMap.put(entry.getKey(),text);
			}else if(entry.getValue().equals("clob")){
				Clob clob=res.getClob(entry.getKey());
				listMap.put(entry.getKey(),StringUtil.clobToString(clob));
			}else{
				listMap.put(entry.getKey(),res.getObject(entry.getKey()));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return listMap;
	}

}
