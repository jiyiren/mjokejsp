# mjokejsp

> mjoke app的后台，纯java servlet后台，未使用框架，主要对于数据库增、删、改、查操作。


## 1、开发环境

* java SDK: JDK1.7
* IDE: MyEclipse
* 服务器软件：Tomcat7.0
* 数据库：Mysql5.6+MysqlWorkBench

## 2、项目结构

![enter image description here](http://img.blog.csdn.net/20150918143202630)

## 3、使用

#### 1、导入数据库

* 修改mysql的配置文件**my.ini**(在mysql安装的根目录下)，如果没有**my.ini**,则将MySQL安装目录下的my-default.ini复制为my.ini,然后在my.ini添加如下编码设置：

```sql
[client]
default-character-set=utf8
[mysql]
default-character-set=utf8
[mysqld]
character-set-server=utf8
```

* 导入项目WebRoot里sql目录下有**.sql**文件,可以用命令行导入(注意路径要正确)：

```sql
source D:/db_joke.sql
```

或者可以用MySqlWorkBench里的导入数据库操作即可。

* 查看数据库里是否已经完全导入db_joke数据表,对照下图

![enter image description here](http://7xknpe.com1.z0.glb.clouddn.com/mjoke1.png)

#### 2、让后台使用本地数据库

* 项目的src目录下有个**dbconfig.properties**文件，打开文件修改**dburl**,**user**,**password**分别对应于你本地数据库的数据库地址名称，数据库用户名，数据库密码，配置好后如下(是我本地的例子)：

![enter image description here](http://7xknpe.com1.z0.glb.clouddn.com/mjoke2.png)


#### 3、部署后台代码到tomcat

* 部署成功后访问项目根目录，比如我的是：http://localhost:8088/joke 就显示

![enter image description here](http://7xknpe.com1.z0.glb.clouddn.com/mjoke3.png)

#### 3、mjoke app连接本地的服务后台

* 用AndrodiStudio打开mjok项目app,app源码在[https://github.com/jiyiren/mjoke](https://github.com/jiyiren/mjoke) ，在项目根目录有**MyConfig.java**文件，修改里面的**BASE_PROJECT**字段值为你当前本地的项目地址，如，我上面的测试地址为**http://localhost:8088/joke** 但是写在app里要将localhost该为本机的ip,修改后为：**http://192.168.18.140:8088/joke** ,如此运行在手机上的mjoke就显示本地数据库的里内容了。有以下注意点：
* 千万不能将localhost或者127.0.0.1直接写在mjoke里作为服务地址的，一定要写你本机的ip,因为android也是一个小的Linux系统，127.0.0.1或者localhost会识别成它自己的本机。
* 并且你的测试的实体手机要连接与电脑在同一局域网下的wifi，不然app无法找到你的服务器的，如果用虚拟机则不用担心，因为虚拟机用的是本机的网络可以查找到你本机服务器地址。

## 4、问题

* 乱码问题：一定要配置mysql的配置味觉my.ini,设置相应的编码，不然mjoke里发表的中文显示出来的是乱码。
* 服务器tomcat部署问题，如果将项目部署在tomcat的webapps下面而非ROOT目录下，则项目根目录在**ip:端口/mjoke**下面，如果直接部署在webapps/ROOT下，则直接访问**ip:端口**就是项目根目录。

