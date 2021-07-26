package com.yang.zookeeper;

import java.io.IOException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>@ProjectName:zookeeper-stady</p>
 * <p>@Package:com.yang.zookeeper</p>
 * <p>@ClassName:TestZookeeper</p>
 * <p>@Description:${description}</p>
 * <p>@Author:yang</p>
 * <p>@Date:2021/4/7 10:44</p>
 * <p>@Version:1.0</p>
 */
public class TestZookeeper {

    private String connectString = "192.168.220.142:2181,192.168.220.143:2181,192.168.220.144:2181";

    private int sessionTime = 2000;

    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException, KeeperException, InterruptedException {

        zkClient = new ZooKeeper(connectString, sessionTime, new Watcher() {

            public void process(WatchedEvent watchedEvent) {
                List<String> children = null;
                try {
                    children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println(child);
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String nodeCreate = zkClient
                .create("/yang", "jinlian".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println(nodeCreate);
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {

        Stat ex = zkClient.exists("/cheng", false);

        System.out.println(ex == null ? "not exist" : "exist");
    }

}
