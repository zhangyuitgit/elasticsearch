package cn.com.skyvis.util;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Es单实例连接
 * 
 * @author 张宇 Description: 2018年2月6日上午11:15:32
 */
public class EsClient {
	// 声明client对象
	private TransportClient client = null;

	// 初始化
	public EsClient() {
		try {
			// 开启集群嗅探功能
			Settings settings = Settings.builder().put("cluster.name", "skyvis").put("client.transport.sniff", true)
					.build();
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (Exception e) {
			client.close();
		}
	}
    /**
     * 创建client连接
     * @return
     */
	public TransportClient getConnection() {
		if (client == null) {
			synchronized (EsClient.class) {
				if (client == null) {
					new EsClient();
                    System.out.println("创建连接");
				}
			}
		}
		return client;
	}
	/**
	 * 关闭连接
	 * 
	 * @param client
	 */
	public  void closeClient(TransportClient client) {
		if (client != null) {
			client.close();
			System.out.println("关闭连接");
		}
	}
}
