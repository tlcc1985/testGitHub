/**  
 * All rights Reserved, Designed By Heqinuc   
 * @author tlcc1
 * @date   2020/01/14 
 */
package com.heqinuc.cc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**   
 * TODO �������������˵��
 * @version V1.0         
 */
public class DistributeClient {
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		DistributeClient distributeClient = new DistributeClient();
		//��ȡzk����
		distributeClient.getConnect();
		//��ȡservers���ӽڵ���Ϣ
		distributeClient.getServerList();
		//ҵ���������
		distributeClient.business();
		
	}

	private String connectString = "server1:2181,server2:2181,server3:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zooKeeper;
	private String path = "/servers";
	private List<String> serverList;

	/**
	 * TODO ����������ӷ���˵��,�Լ�����Ĳ����ͷ���˵��
	 * @throws InterruptedException 
	 */ 
	private void business() throws InterruptedException {
		System.out.println("client is working ...");
		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * TODO ����������ӷ���˵��,�Լ�����Ĳ����ͷ���˵��
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */ 
	private void getServerList() throws KeeperException, InterruptedException {
		//��ȡ�������ӽڵ���Ϣ�����Ը��ڵ���м���
		List<String> children = zooKeeper.getChildren(path, true);
		List<String> servers = new ArrayList<String>();
		for (String child : children) {
			byte[] data = zooKeeper.getData(path+"/"+child, false, null);
			servers.add(new String(data));
		}
		serverList = servers;
		System.out.println(serverList);
	}

	/**
	 * TODO ����������ӷ���˵��,�Լ�����Ĳ����ͷ���˵��
	 * @throws IOException 
	 */ 
	private void getConnect() throws IOException {
		zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				try {
					getServerList();
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

}
