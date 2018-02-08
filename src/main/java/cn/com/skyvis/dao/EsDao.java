package cn.com.skyvis.dao;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import cn.com.skyvis.util.EsClient;

public class EsDao {
	private static BulkProcessor bulkProcessor = null;
	public EsDao() {
		bulkProcessor = BulkProcessor.builder(new EsClient().getConnection(), new BulkProcessor.Listener() {

			public void beforeBulk(long executionId, BulkRequest request) {
				System.out.println("发送请求前，可以做一些事情");

			}

			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("发送请求成功后，可以做一些事情");

			}

			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("发送请求失败，可以做一些事情");

			}

		})
	      .setBulkActions(1)
		  .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
		  .setFlushInterval(TimeValue.timeValueSeconds(5))
		  .setConcurrentRequests(2).build();
	}
	
	
    public void EsImport(String index,String type,String id,List<Map<String,Object>> list){
    	for (Map<String, Object> map : list) {
    		bulkProcessor.add(new IndexRequest(index,type,map.get(id).toString()).source(map));
		}
    }
	
	

}
