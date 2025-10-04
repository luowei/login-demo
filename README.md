# Spring Security 登录示例项目

## 项目简介

这是一个基于 Spring Framework 3.1 和 Spring Security 3.1 的用户登录注册系统示例项目。该项目展示了如何使用 Spring Security 实现完整的用户认证和授权功能，包括用户注册、登录、密码加密以及基于角色的访问控制。项目采用 MongoDB 作为数据持久化存储方案。

## 技术栈

### 核心框架
- **Spring Framework**: 3.1.0.RELEASE
  - Spring MVC (Web层)
  - Spring Context (IoC容器)
  - Spring AOP (面向切面编程)
  - Spring ORM (对象关系映射)

- **Spring Security**: 3.1.0.RELEASE
  - spring-security-core (核心安全功能)
  - spring-security-web (Web安全)
  - spring-security-config (安全配置)
  - spring-security-crypto (密码加密)
  - spring-security-taglibs (JSP标签库)

### 数据存储
- **MongoDB**: 2.7.2
- **Spring Data MongoDB**: 1.0.0.RELEASE

### 视图层
- **JSP**: 2.1.0
- **JSTL**: 1.2.0

### 其他依赖
- **BCrypt**: 密码加密算法
- **Jackson**: 1.8.1 (JSON处理)
- **Hibernate Validator**: 4.2.0.Final (数据验证)
- **SLF4J + Log4j**: 日志框架
- **JUnit**: 4.8.1 (单元测试)

### 构建工具
- **Maven**: 项目管理和构建
- **Java**: 1.6

## 项目结构

```
login-demo/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── rootls/
│       │           ├── config/           # 配置类
│       │           │   └── MongoTemplateConfig.java
│       │           ├── controller/       # 控制器层
│       │           │   ├── LoginController.java
│       │           │   └── MainController.java
│       │           ├── domain/           # 领域模型
│       │           │   └── User.java
│       │           ├── repository/       # 数据访问层
│       │           │   ├── UserRepository.java
│       │           │   └── UserRepositoryImpl.java
│       │           ├── service/          # 业务逻辑层
│       │           │   ├── UserService.java
│       │           │   └── UserServiceImpl.java
│       │           └── tools/            # 工具类
│       │               ├── AuthenticationManagerImpl.java
│       │               └── HashedPasswordWithSalt.java
│       ├── resources/                    # 资源文件
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── jsp/                  # JSP视图
│           │   │   ├── index.jsp
│           │   │   ├── login.jsp
│           │   │   └── signup.jsp
│           │   ├── applicationContext.xml
│           │   ├── security-context.xml
│           │   ├── springsecuritylogin-servlet.xml
│           │   └── web.xml
│           └── css/                      # 样式文件
└── pom.xml                               # Maven配置文件
```

## 主要功能

### 1. 用户注册 (Signup)
- **URL**: `/signup`
- **功能描述**: 新用户注册功能
- **实现细节**:
  - 用户名、密码和邮箱验证（使用 Hibernate Validator）
  - 密码使用 BCrypt 算法进行加密（强度等级：5）
  - 新注册用户默认角色为 `ROLE_USER`
  - 用户信息存储到 MongoDB 的 `usercollection` 集合中

### 2. 用户登录 (Login)
- **URL**: `/login`
- **功能描述**: 用户认证登录
- **实现细节**:
  - Spring Security 表单登录
  - 密码自动验证（BCrypt）
  - 登录失败跳转到 `/login?loginerror=true`
  - 登录成功后重定向到首页

### 3. 用户主页
- **URL**: `/`
- **功能描述**: 登录后的用户主页
- **实现细节**:
  - 从 Spring Security Context 获取当前登录用户信息
  - 显示用户个人信息

### 4. 安全控制
- **角色层级**:
  ```
  ROLE_ADMIN > ROLE_STAFF > ROLE_USER > ROLE_GUEST
  ```
- **访问控制**:
  - `/admin/**`: 需要 `ROLE_ADMIN` 角色
  - 其他路径: 允许所有用户访问（permitAll）
  - 登录、注册、静态资源无需认证

### 5. 密码加密
- 使用 BCrypt 密码加密算法
- 加密强度：5（可在 security-context.xml 中配置）
- 支持盐值（Salt）机制，使用用户名作为盐值

## 使用方法

### 环境要求
1. **JDK**: 1.6 或更高版本
2. **Maven**: 3.x
3. **MongoDB**: 已安装并运行在 localhost:27017
4. **Servlet容器**: Tomcat 6+ 或 Jetty

### 安装步骤

#### 1. 启动 MongoDB
```bash
# 确保 MongoDB 运行在默认端口 27017
mongod --dbpath /path/to/your/data
```

#### 2. 克隆或下载项目
```bash
git clone <repository-url>
cd login-demo
```

#### 3. 编译项目
```bash
mvn clean compile
```

#### 4. 运行项目

**使用 Jetty:**
```bash
mvn jetty:run
```

**使用 Tomcat:**
```bash
mvn tomcat6:run
```

**打包为 WAR 文件:**
```bash
mvn clean package
# 生成的 WAR 文件位于 target/springsecuritylogin.war
```

#### 5. 访问应用
```
http://localhost:8080/springsecuritylogin
```

### 主要页面
- **首页**: http://localhost:8080/springsecuritylogin/
- **登录**: http://localhost:8080/springsecuritylogin/login
- **注册**: http://localhost:8080/springsecuritylogin/signup

## 依赖说明

### Spring Security 依赖
项目使用 Spring Security 3.1.0.RELEASE 实现完整的安全功能：
- 认证（Authentication）
- 授权（Authorization）
- 密码加密
- 会话管理
- CSRF 保护

### MongoDB 配置
- **数据库名称**: schema
- **集合名称**: usercollection
- **连接地址**: localhost:27017
- **连接池配置**:
  - 每个主机的连接数: 50
  - 连接超时: 6000ms
  - Socket 超时: 120000ms
  - 自动重连: 启用

### 密码加密策略
- **算法**: BCrypt
- **强度**: 5（工作因子）
- **盐值来源**: 用户名（username）

## 核心类说明

### 1. User (com.rootls.domain.User)
- 实现 `UserDetails` 接口（Spring Security）
- MongoDB 文档映射（@Document）
- 包含用户基本信息和安全相关属性
- 内置验证规则（@NotBlank, @Email, @Size）

### 2. LoginController (com.rootls.controller.LoginController)
- 处理注册和登录请求
- 用户注册时密码自动加密
- 数据验证失败返回错误信息

### 3. UserServiceImpl (com.rootls.service.UserServiceImpl)
- 实现 `UserDetailsService` 接口
- Spring Security 用于加载用户信息
- 事务管理（@Transactional）

### 4. UserRepositoryImpl (com.rootls.repository.UserRepositoryImpl)
- MongoDB 数据访问层
- 使用 MongoTemplate 进行 CRUD 操作
- 写入策略: REPLICAS_SAFE

## 配置文件说明

### security-context.xml
Spring Security 核心配置文件：
- HTTP 安全规则
- 认证管理器配置
- 密码编码器配置
- 角色层级定义

### applicationContext.xml
Spring 应用上下文配置：
- Bean 定义
- 组件扫描
- 数据源配置

### web.xml
Web 应用部署描述符：
- Spring Security 过滤器链
- Spring MVC DispatcherServlet
- 字符编码过滤器

## 注意事项

1. **MongoDB 必须启动**: 应用启动前确保 MongoDB 服务正在运行
2. **端口冲突**: 默认使用 8080 端口，如有冲突请修改配置
3. **Java 版本**: 编译目标为 Java 1.6，建议使用 Java 1.6+
4. **密码安全**: BCrypt 强度设置为 5，生产环境建议提高到 10-12
5. **数据库名称**: 默认使用 "schema" 数据库，可在 MongoTemplateConfig.java 中修改

## 扩展建议

1. **安全增强**:
   - 添加验证码功能
   - 实现记住我（Remember Me）功能
   - 添加会话并发控制
   - 升级到更新版本的 Spring Security

2. **功能完善**:
   - 添加忘记密码/重置密码功能
   - 实现用户个人信息管理
   - 添加用户权限管理界面
   - 实现邮箱验证功能

3. **技术升级**:
   - 升级到 Spring Boot
   - 升级到最新的 Spring Security 版本
   - 使用 Java 8+ 特性
   - 前后端分离（使用 RESTful API + JWT）

## 参考资源

**视频讲解**

[基于Spring-Security与MongoDB的用户注册登录](http://www.tudou.com/programs/view/-j80J8HM5h4/)

## 许可证

本项目仅供学习和参考使用。

## 联系方式

如有问题或建议，请通过 GitHub Issues 反馈。
