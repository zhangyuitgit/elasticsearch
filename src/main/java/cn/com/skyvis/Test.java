package cn.com.skyvis;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;  
import com.fasterxml.jackson.databind.ObjectMapper; 
import cn.com.skyvis.dao.EsCreateIndex;
import cn.com.skyvis.dao.EsDao;
import cn.com.skyvis.dao.QueryDao;
import cn.com.skyvis.util.EsClient;
import cn.com.skyvis.util.OracleDBUtil;

public class Test {

	private static final int Map = 0;

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//      创建index  mapping
//		String jsonStr="{\"novel\":{\"properties\":{\"ajbs\":{\"type\":\"text\"},\"wb\":{\"type\":\"text\"}}}}";
//		System.out.println(jsonStr);
//		new EsCreateIndex().createIndex("book", "novel", jsonStr);
		//导入数据
//		new QueryDao().queryList("book","novel","ajbs","select * from test3", "D:\\ss.doc");
	
	}

}
