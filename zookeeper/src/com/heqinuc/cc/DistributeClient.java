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
 * TODO 请在这里添加类说明
 * @version V1.0         
 */
public class DistributeClient {
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		DistributeClient distributeClient = new DistributeClient();
		//获取zk连接
		distributeClient.getConnect();
		//获取servers的子节点信息
		distributeClient.getServerList();
		//业务进程启动
		distributeClient.business();
		
	}

	private String connectString = "server1:2181,server2:2181,server3:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zooKeeper;
	private String path = "/servers";
	private List<String> serverList;

	/**
	 * TODO 请在这里添加方法说明,以及下面的参数和返回说明
	 * @throws InterruptedException 
	 */ 
	private void business() throws InterruptedException {
		System.out.println("client is working ...");
		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * TODO 请在这里添加方法说明,以及下面的参数和返回说明
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */ 
	private void getServerList() throws KeeperException, InterruptedException {
		//获取服务器子节点信息，并对父节点进行监听
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
	 * TODO 请在这里添加方法说明,以及下面的参数和返回说明
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
