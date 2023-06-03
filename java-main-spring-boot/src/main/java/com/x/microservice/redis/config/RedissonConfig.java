package com.x.microservice.redis.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;

//@Configuration
//@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {
    // 读取配置文件里面的Redis信息
    private String host;
    private String port;
    private String password;
//    // 集群配置
//    private RedisProperties.Cluster cluster;


//    /**
//     * 配置redisson --集群方式
//     * Redisson是RedissonClient的实现类
//     * @return
//     */
//    @Bean(destroyMethod = "shutdown")
//    public Redisson redisson() {
//        List<String> clusterNodes = new ArrayList<>();
//        for (int i = 0; i < this.getCluster().getNodes().size(); i++) {
//            clusterNodes.add("redis://" + this.getCluster().getNodes().get(i));
//        }
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
//        clusterServersConfig.setPassword(getPassword());
//        return (Redisson) Redisson.create(config);
//    }


    /**
     * 配置redisson --单节点
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://" + host + ":" + port;
        System.out.println("redissonClient: " + address);
//        //使用json序列化方式
//        config.setCodec(new JsonJacksonCodec());
        config.useSingleServer().setAddress(address).setPassword(password).setDatabase(15);

        return Redisson.create(config);
    }
}
