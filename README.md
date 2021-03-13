# ShiroAdmin—基于 RBAC 权限模型和 Shiro 框架的后台权限管理系统

> 本项目GitHub地址：https://github.com/ZengZhiK/shiro-admin

## 系统功能

1. 系统登录： 基于 JWT 的 Token 登录认证， Payload 中记录用户角色、 权限等数据； 当管理员修改普通用户的角色、权限等数据时， 会在 Redis 中产生标记， 普通用户会使用 RefreshToken 刷新机制刷新 Token

2. 部门/用户/角色/菜单管理：使用 Mybatis 对数据进行 CRUD， 并采用 Shiro 注解方式和 Shiro 的 Thymeleaf模板标签控制对应用户的权限， 并整合 Redis 实现二级缓存， 提高查询效率

3. 文件上传下载功能： 通过添加静态资源映射（addResourceHandlers）， 配置文件存放路径， 实现文件上传下载

4. 日志监控： 对用户的操作进行记录， 使用 AOP 环绕通知， 当用户调用接口时会在数据库中插入日志数据

5. 接口管理： 根据业务代码自动生成相关的 API 接口文档（Swagger2）

6. SQL 监控： 对系统使用的 SQL 进行监控（Druid 连接池）

## 本地运行手册

1、` git clone https://github.com/ZengZhiK/fastanswer.git `把本项目的代码拉取下来，之后右键IntelliJ IDEA 启动，等待maven自动加载包

2、使用MySQL数据库客户端软件（例如SQLyog、Navicat）执行脚本sql/ShiroAdmin.sql创建2、使用MySQL数据库客户端软件（例如SQLyog、Navicat）执行脚本sql/ShiroAdmin.shiro_admin数据库

3、安装并启动Redis

4、重命名`src/main/resources/`目录下的`application-backup.yml`为`application.yml`
   
- 需要按实际情况修改如：数据库url、用户名、密码；redis主机、密码；file.path文件上传路径等信息

5、至此配置完毕，点击`com.zzk.shiroadmin.ShiroAdminApplication`启动，在浏览器地址栏输入` http://localhost:8080/ `访问

## 技术栈

|      | 技术                | 链接                                                         |
| ---- | ------------------- | ------------------------------------------------------------ |
| 后端 | Spring Boot         | https://spring.io/                                           |
|      | JWT                 | https://jwt.io/                                              |
|      | MyBatis             | https://mybatis.org/mybatis-3/                               |
|      | Shiro               | http://shiro.apache.org/                                     |
| 前端 | Layui               | https://www.layui.com/                                       |

## 页面展示

- 登录页
    - 管理员用户名：admin，普通用户用户名查看数据库
    - 密码：666666，密码都是这个

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/login.png"/>
</div>

采用的是JWT的登录方式，Token保存在浏览器的Session Storage中

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/jwt_token.png"/>
</div>

解析出来包括Header.Payload.Signature

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/jwt_parse.png"/>
</div>

- 首页

1. 管理员能看到所有内容，拥有最高权限

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/admin_index.png"/>
</div>

2. 普通用户的权限可以被控制，包括是否可以登录、菜单、按钮等

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/user_index.png"/>
</div>

- 角色管理

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/role.png"/>
</div>

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/add&update_role.png"/>
</div>

- 菜单权限管理

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/permission.png"/>
</div>

- 部门管理

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/department.png"/>
</div>

- 用户管理

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/user.png"/>
</div>

- 日志管理

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/log.png"/>
</div>

- 接口管理

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/interface.png"/>
</div>

- SQL监控
    - 用户名：admin
    - 密码：666666，可在application.yml文件修改

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/sql_login.png"/>
</div>

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/sql_index.png"/>
</div>

- 我的文件——文件上传下载

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/file.png"/>
</div>

## Redis缓存

- 查看缓存，可以看到缓存在redis如何储存

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/redis.png"/>
</div>

- 查看日志（文件或命令行），可查看缓存的命中率

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/ShiroAdmin/log_commandline.png"/>
</div>