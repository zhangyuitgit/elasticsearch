package cn.com.skyvis.dao;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.com.skyvis.util.EsClient;

/**
 * 操作index类
 * @author 张宇 Description: 2018年2月7日下午1:59:27
 */
public class EsCreateIndex {

	private TransportClient client;
    /**
     * 创建 索引 和 映射
     * @param index_name
     *                  索引
     * @param type 
     *                  类型
     * @param jsonStr
     *                  映射 
     */
	public void createIndex(String index_name, String type, String jsonStr) {
		// 创建es连接
		client = new EsClient().getConnection();
		IndicesExistsRequest request = new IndicesExistsRequest(index_name);
		IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
		// 用于判断index是否存在，不存在返回false
		if (!response.isExists()) {
			// 创建mapping
			try {
				CreateIndexResponse index=client.admin().indices().prepareCreate(index_name).execute().get();
				JSONObject jsons = JSONObject.parseObject(jsonStr);
				PutMappingRequest requests = Requests.putMappingRequest(index_name).type(type).source(jsons);
				PutMappingResponse responses =client.admin().indices().putMapping(requests).get();
				System.out.println(responses.isAcknowledged());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除索引
	 * 
	 * @param index_name
	 *                  索引名称
	 */
	public void deleteIndex(String index_name) {
		// 创建es连接
		client = new EsClient().getConnection();
		DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index_name).execute().actionGet();
		if (dResponse.isAcknowledged()) {
			System.out.println("delete index " + index_name + "  successfully!");
		} else {
			System.out.println("Fail to delete index " + index_name);
		}

	}

}
