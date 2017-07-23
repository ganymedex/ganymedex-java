1 新系统安装 ssh 默认防火墙全部关闭
2 安装curl
sudo apt-get update
sudo apt-get install curl

3 安装docker
curl -k -sSl https://get.docker.com | sudo sh

service docker start
service docker restart
service docker stop

4 查看docker版本
 docker version


----------------------------------------------这段是个坑但是也是可以执行过-----------------------------------
安装安装Docker图形化管理界面-Shipyard中文版

@Shipyard 默认访问端口是8080，默认用户名和密码是admin 和 shipyard

依次执行以下命令： 安装基础组件
root@meng-virtual-machine:~# sudo docker pull swarm
root@meng-virtual-machine:~# sudo docker pull shipyard/shipyard
root@meng-virtual-machine:~# sudo docker pull rethinkdb
root@meng-virtual-machine:~# sudo docker pull microbox/etcd
root@meng-virtual-machine:~# sudo docker pull shipyard/docker-proxy
root@meng-virtual-machine:~# sudo docker pull alpine
root@meng-virtual-machine:~# sudo docker pull dockerclub/shipyard



1数据存储（rethinkdb）
#sudo docker run -ti -d --name shipyard-rethinkdb rethinkdb
2.服务发现（etcd）
为了启用Swarm leader选择，我们必须使用来自Swarm容器的外部键值存储。此处，我们使用Etcd作为服务发现工具。可以选用的服务发现工具还有Consul、Zookeeper等。
#sudo docker run -ti -d -p 4001:4001 -p 7001:7001 --name shipyard-discovery microbox/etcd -name discovery
3Docker代理服务（shipyard/docker-proxy）
默认情况下，Docker引擎只侦听套接字。 我们可以重新配置引擎以使用TLS，或者您可以使用代理容器。 这是一个非常轻量级的容器，它只是将请求从TCP转发到Docker监听的Unix套接字。
# sudo docker run  -ti -d -p 2375:2375 --hostname=$HOSTNAME --name shipyard-proxy -v /var/run/docker.sock:/var/run/docker.sock -e PORT=2375 shipyard/docker-proxy
4将IP-OF-HOST替换为对应的ip;
将SWARM-INNER-PORT换成对应的端口，默认2375.
将SWARM-MAP-PORT换成映射到host机的ip，例如2376
@格式：docker run -ti -d --name -p [SWARM-MAP-PORT]:[SWARM-INNER-PORT] shipyard-swarm-manager docker.io/swarm manage --host tcp://0.0.0.0:[SWARM-INNER-PORT] etcd://[IP-OF-HOST]:4001
@实例化：
#sudo docker run  -ti -d --name shipyard-swarm-manager swarm manage -p 2376:2375 --host tcp://0.0.0.0:3375 etcd://192.168.2.104:4001

5.Swarm Agent节点将当前docker节点加入到集群中

将IP-OF-HOST替换为对应的ip
将IP-OF-ETCD-HOST换成etcd所在的docker节点ip。
将LOCAL-DOCKER-PORT换成本机docker所监听的端口，例如2375
@格式：docker run -ti -d --name shipyard-swarm-agent docker.io/swarm join --addr [IP-OF-HOST]:[LOCAL-DOCKER-PORT] etcd://[IP-OF-ETCD-HOST]:4001
@实例： 
# sudo  docker run -ti -d --name shipyard-swarm-agent swarm join --addr 192.168.2.104:2375 etcd://192.168.2.104:4001

#sudo docker run -ti -d --name shipyard-controller --link shipyard-rethinkdb:rethinkdb --link shipyard-swarm-manager:swarm -p 8083:8080 shipyard/shipyard server -d tcp://swarm:3375


7.浏览器访问：http://192.168.2.104:8083/ 即可进入链接

默认用户名/密码 admin/shipyard

---------------------------------------------------------------------------------------------------------------------------------------------------------

自动安装中文方式
添加docker 权限
1如果还没有 docker group 就添加一个：
sudo groupadd docker
2将用户加入该 group 内。然后退出并重新登录就生效啦。
sudo gpasswd -a ${USER} docker
3重启 docker 服务
sudo service docker restart
4切换当前会话到新 group 或者重启 X 会话
newgrp - docker
5关闭正在启动的shipyard 相关容器
sudo docker stop $(sudo docker ps -a -q)
sudo docker rm $(sudo docker ps -a -q)
6
安装
sudo curl -sSL https://shipyard-project.com/deploy | bash -s
删除
sudo curl https://shipyard-project.com/deploy  | ACTION =remove bash -s
http://192.168.2.104:8080/

#  安装mq
docker pull rabbitmq 拉取最新的mq镜像
docker run -d --name myrabbitmq -p 5673:5672 -p 15673:15672 docker.io/rabbitmq:3-management 这条带管理界面
http://192.168.2.104:15673/ web管理地址 guest 本地访问。程序访问请添加 角色账号。

进入docker
方式一 docker exet -i
方式二 shipyard 命令

启动命令：
docker run -d --hostname my-rabbit --name my-rabbit --network host -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=xixing -p 5673:5672 -p 15673:15672 docker.io/rabbitmq:3-management

-d :表示后台进程运行
--hostname:指定主机名称
--name : 容器名称
--network: 网络模式:host，如果不想以默认端口使用rabbitmq ，则可以使用-p参数进行主机端口映射
-e:环境变量，指定默认用户名，密码
http://192.168.2.104:15672/






