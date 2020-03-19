/**  
 * All rights Reserved, Designed By Heqinuc   
 * @author tlcc1
 * @date   2020/01/14 
 */
package com.heqinuc.cc;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**   
 * zookeeper ���߷���
 * @version V1.0         
 */
public class DistributeServer {
	
	private String connectString = "server1:2181,server2:2181,server3:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zooKeeper;
	private String path = "/servers";

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		DistributeServer distributeServer = new DistributeServer();
		
		//��ȡzk����
		distributeServer.getConnect();
		
		//����zk����ע���������Ϣ
		distributeServer.registServer(args[0]);
		
		//����ҵ����
		distributeServer.business(args[0]);
	}

	

	/**
	 * ҵ����
	 * @param string
	 * @throws InterruptedException 
	 */ 
	private void business(String hostName) throws InterruptedException {
		System.out.println(hostName+" is working ...");
		Thread.sleep(50000);
		
	}

	/**
	 * ע���������Ϣ
	 * @param string
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */ 
	private void registServer(String hostName) throws KeeperException, InterruptedException {
		byte[] data = hostName.getBytes();
		String create = zooKeeper.create(path+"/server" , data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostName+" is online "+create);
	}

	/**
	 * ��ȡZK����
	 * @throws IOException 
	 */ 
	private void getConnect() throws IOException {
		zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

}
