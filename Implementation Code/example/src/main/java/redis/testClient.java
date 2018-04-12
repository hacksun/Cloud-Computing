package redis;
        import redis.clients.jedis.Jedis;
        import redis.clients.jedis.JedisPubSub;


public class testClient {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.31.71",6379);
        JedisPubSub jedisPubSub=new JedisPubSub() {

            /**
             * 处理普通订阅的频道的消息。
             */

            public void onMessage(String channel, String message) {
                System.out.println("TestClient:channel："+channel+",message:"+message);

            }
        };

        //执行完subscribe，本线程会阻塞。
        //订阅两个频道：china.beijing和china.shanghai
        //当订阅的频道有消息时，会执行jedisPubSub的onMessage方法。
        jedis.subscribe(jedisPubSub,"rdb");

        System.out.println("该行代码是执行不到的");
    }

}
