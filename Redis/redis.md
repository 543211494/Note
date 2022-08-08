## 1.安装Redis

(1)解压文件

```sh
tar -zxvf redis-6.2.7.tar.gz
```

(2)编译

```sh
cd redis-6.2.7
make
```

(3)安装

```sh
make install
```

(4)验证

```
cd /usr/local/bin
ll
```

查看默认安装目录 /usr/local/bin

redis-benchmark：性能测试工具

redis-check-aof：修复有问题的AOF文件

redis-check-rdb：修复有问题的rdb文件

redis-sentinel：Redis集群使用

redis-server：Redis服务器启动命令

redis-cli：客户端，操作入口

(5)启动

前台启动,进入/usr/local/bin目录，ctrl+C退出

```shell
redis-server
```

后台启动，进入redis目录

```
vi redis.conf
```

将daemonize no改为daemonize yes

设置密码，requirepass password

```shell
cd /usr/local/bin
redis-server /usr/local/redis-6.2.7/redis.conf
#查看redis进程
ps -ef | grep redis
#通过客户端连接
redis-cli
#关闭
redis-cli shutdown
#设置密码后关闭
redis-cli -a password shutdown
```

## 2.使用

默认16个数据库，下标从0开始，默认使用0号库

Redis是单线程＋IO多路复用技术

```shell
#换库
select index
#查看当前数据库的key的数量
dbsize
#清空当前库
flushdb
```

### 2.1数据类型

#### 2.1.1常用key操作

```shell
#查看当前库的所有key
keys *
#设置一个key
set key value
#判断某个key是否存在
exists key
#查看某个键的类型
type key
#删除指定的key
del key
#删除指定的key，非阻塞删除，真正的删除会在后续异步操作
unlink key
#给指定的key设置10秒的过期时间
expire key 10
#查看指定的key还有多少秒过期，-1表示永不过期，-2表示已经过期
ttl key
```

#### 2.1.2常用数据类型

(1)string

二进制安全，value最大为512M

```shell
#获取值
get key
#设置值
set key value
#追加值
append key value
#获取string的长度
strlen key
#让存储的数字值增1，如果为空，新增值为1
incr key
#让存储的数字值减1
decr key
#增加value
incrby key value
#减少value
decrby key value
#设置多个key,value
mset key1 value1 key2 value2 ...
#获取多个value
mget key1 key2 ...
#设置key,value的同时设置过期时间
setex key time value
```

(2)List

```shell
#在左/右侧加入数据
lpush/rpush key value1 value2 ...
#在左/右侧弹出数据
lpop/rpop key
#从列表key1右边取一个值插入key2左边
rpoplpush key1 key2
#取出指定范围的值
lrange key start end
#按下标获取元素
lindex key index
#获取列表长度
llen key
#在value前/后面插入新值
linsert key BEFORE|AFTER value newvalue
#从左边删除n个value
lrem key n value
#设置指定下标元素的值
lset key index value
```

(3)set

```shell
#将一个或多个元素加入刀集合key中，已经存在的元素将被忽略
sadd key value1 value2 ...
#取出set中的所有值
smembers key
#判断set中是否含有value,1代表有，0代表没有
sismember key value
#删除某个元素
srem key value1 value2 ...
#随机从集合中吐出一个值
spop key
#取两个集合的交集
sinter key1 key2
#取两个集合的并集
sunion key1 key2
#取两个集合的差集
sdiff key1 key2
```

(4)Hash

```shell
#给key集合中的field键赋值value
hset key field value
#获取key集合中的值
hget key field
#批量设置field,value
hmset key field1 value1 field2 value2...
#查看哈希表中给定域field是否存在
hexists key field
#列出该hash集合的所有field
hkeys key
#列出该hash集合的所有value
hvals key
#在哈希表key中的域field的值上加上增量
hincrby key field increment
```

(5)Zset

```shell
#将一个或多个元素加入刀集合key中
zadd key score1 value1 score2 value2 ...
#返回指定下标间的元素
zrange key start stop
#取出分数在score1到score2只见的值,score1<score2
zrangebyscore key score1 scroe2 [withscores]
#取出分数在score1到score2只见的值，从大到小排序,score1>score2
zrevrangebyscore key score1 scroe2 [withscores]
#删除指定值的元素
zrem key value
#统计该集合指定分区内元素个数
zcount key min max
#返回该值在集合中的排名，从0开始
zrank key value
```

### 2.2发布订阅

```shell
#订阅频道
subscribe channel
#发送消息
publish channel meaasge
```

### 2.3Jedis的使用

```xml
<dependency>
	<groupId>redis.clients</groupId>
	<artifactId>jedis</artifactId>
	<version>3.2.0</version>
</dependency>
```

