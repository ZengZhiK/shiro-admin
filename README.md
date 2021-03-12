# ShiroAdmin—基于 RBAC 权限模型和 Shiro 框架的后台权限管理系统

> 本项目地址：https://github.com/ZengZhiK/shiro-admin

## 系统功能

1. 系统登录： 基于 JWT 的 Token 登录认证， Payload 中记录用户角色、 权限等数据； 当管理员修改普通用户的角色、权限等数据时， 会在 Redis 中产生标记， 普通用户会使用 RefreshToken 刷新机制刷新 Token

2. 部门/用户/角色/菜单管理：使用 Mybatis 对数据进行 CRUD， 并采用 Shiro 注解方式和 Shiro 的 Thymeleaf模板标签控制对应用户的权限， 并整合 Redis 实现二级缓存， 提高查询效率

3. 文件上传下载功能： 通过添加静态资源映射（addResourceHandlers）， 配置文件存放路径， 实现文件上传下载

4. 日志监控： 对用户的操作进行记录， 使用 AOP 环绕通知， 当用户调用接口时会在数据库中插入日志数据

5. 接口管理： 根据业务代码自动生成相关的 API 接口文档（Swagger2）

6. SQL 监控： 对系统使用的 SQL 进行监控（Druid 连接池）

## 本地运行手册

1、` git clone https://github.com/ZengZhiK/fastanswer.git `把本项目的代码拉取下来，之后右键IntelliJ IDEA 启动，等待maven自动加载包




## 技术栈

|      | 技术                | 链接                                                         |
| ---- | ------------------- | ------------------------------------------------------------ |
| 后端 | Spring Boot         | https://spring.io/                                           |
|      | JWT                 | https://jwt.io/                                              |
|      | MyBatis             | https://mybatis.org/mybatis-3/                               |
|      | Shiro               | http://shiro.apache.org/                                     |
| 前端 | Layui               | https://www.layui.com/                                       |

## 页面展示

- 首页

- 

