# Mjoke 后台

[Mjoke App](https://github.com/jiyiren/mjoke) 的后台，纯 **Servlet** 后台，边学边做！

## 开发环境

* Java SDK: JDK-1.7
* IDE: MyEclipse
* 服务器：Tomcat7.0
* 数据库：Mysql5.6 + MysqlWorkBench

## 项目结构

![](http://img.godjiyi.cn/jy_mjoke_bg.jpg)

## 使用说明

### 导入数据库


```sql
source D:/db_joke.sql
```

数据库结构

![db desc](http://img.godjiyi.cn/mjoke1.png)

### 配置数据库

修改 `dbconfig.properties`：

![](http://img.godjiyi.cn/mjoke2.png)


### 部署代码

部署成功后访问项目根目录，比如我的是：http://localhost:8088/joke 就显示

![enter image description here](http://img.godjiyi.cn/mjoke3.png)




