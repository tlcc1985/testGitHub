/**  
 * All rights Reserved, Designed By Heqinuc   
 * @author tlcc1
 * @date   2020/01/13 
 */
package com.heqinuc.cc;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**   
 * TODO 请在这里添加类说明
 * @version V1.0         
 */
public class TestZookeeper {
	
	private String connectString = "server1:2181,server2:2181,server3:2181";
	private int sessionTimeout = 2000;
	ZooKeeper zkClient;

	//@Test
	public void initClient() throws IOException{
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher(){
			
			@Override
			public void process(WatchedEvent watchedEvent){
				System.out.println(watchedEvent.getType()+"\t"+watchedEvent.getPath());
			}
		});
	}
	
	/**
	 * 创建子节点
	 * TODO 请在这里添加方法说明,以及下面的参数和返回说明
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public void create() throws KeeperException, InterruptedException {
		String path = zkClient.create("/heqinuc", "java_test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("path:"+path);
	}
	
	public void getChild() throws KeeperException, InterruptedException {
		List<String> childList=zkClient.getChildren("/", false);
		for(String child : childList){
			System.out.println(child);
		}
	}
	
	public static void main(String[] args){
		TestZookeeper testZookeeper = new TestZookeeper();
		try {
			testZookeeper.initClient();
			try {
				//testZookeeper.create();
				testZookeeper.getChild();
			} catch (KeeperException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		
//	}
}
