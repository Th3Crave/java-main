# 【微服务开发框架】脚手架项目

# 1、概述
【微服务开发框架】项目是一个脚手架项目，来帮助开发同事快速搭建一个完整结构的项目，
开发者在生成的项目基础上进行开发即可，以提高生产效率和代码质量。

【微服务开发框架】项目功能主要体现在：
- 不必从零开始搭建初始项目，提高开发效率；
- 统一异常处理
- 规范化接口返回结果集
- 错误码规范
- 服务调用规范
- 通用的参数校验
- 通用物理分页 

> 项目目前处于初期阶段，后续会根据实际需要增加更多功能支持。
> 下文将基于现有版本 (2021-08版) 对其使用方式和已实现功能进行描述和总结。

# 2、开始使用
开发者可以通过 IDEA 插件进行微服务框架项目的快速搭建。<br>
[IDEA 插件安装](https://x-docs.haier.net/#/guide/develop?id=ide%e6%8f%92%e4%bb%b6) <br>
[通过插件创建微服务框架项目](https://x-docs.haier.net/#/design/microservice/plugin/)  <br>

> ！！！ 请先在application-dev.yml中配置自己的数据库连接信息。<br>
> 启动项目，对 *-api 模块 >> feignclient 包 >> *Client 中的接口进行请求测试，
  如果接口测试成功，则证明项目搭建成功。<br>
> demo 包目录中提供了示例代码，供初始开发参考，后续可以将所有 demo 包目录进行删除

# 3、目录结构介绍
xx项目<br>
&emsp;-- doc &emsp; <font color=gray> 存放数据库初始化建表语句等文件 </font><br>
&emsp;-- *-api &emsp; <font color=gray> 供调用方引用的 api jar 包，
提供接口声明及请求和响应DTO</font><br>
&emsp;-- *-service &emsp; <font color=gray> 项目主体逻辑 </font><br>

# 4、功能概述
## 4.1 统一异常处理
- `BizException` 自定义业务异常类，业务逻辑失败时，
  可以直接通过抛 BizException 异常的方式结束本次请求并返回失败信息。
- `ErrorCodeEnum` 错误码枚举类
- `GlobalExceptionHandler` 全局异常处理器，通过 @ExceptionHandler 注解
  来统一处理方法抛出的自定义异常以及运行时异常。

## 4.2 规范化接口返回结果集
`GlobalResponse` 统一接口返回结果集，包括响应码(code)、描述信息(msg)、返回数据(data)，
以及快捷操作方法。
### 使用方式
引入 jar 包（默认已经引入）。
```java
<pangu.version>1.0.0</pangu.version>

<dependency>
    <groupId>com.haier.gdp</groupId>
    <artifactId>pangu-common</artifactId>
    <version>${pangu.version}</version>
</dependency>
```

## 4.3 服务调用规范
微服务项目分为 *-api 和 *-service 两个模块。
*-api 模块用来定义 dto （接口的出参和入参）和 feignClient （暴露给调用方的接口定义）。
### 服务暴露
服务提供方将 *-api 模块的 jar 包 deploy 发布到私服，供服务调用方引用。
### 服务调用 - Jar包方式
1. 服务调用方在自己的 *-service 模块中引用所需服务的 api 依赖；
2. 在application-dev.yml文件中配置feign-client的配置项，示例如下：
    ```java
    feign:
      client:
        config:
          default:
            connectTimeout: 3000
            readTimeout: 10000
            loggerLevel: full
          #inventory feignClient
          inventory-api:
            url: http://kong.qd-aliyun.haier.net/
            # 通过网关请求时，需要配置此值进行鉴权
            Authorization: xxxxxx
            #请求拦截器，feign.interceptor 包中定义的拦截器
            requestInterceptors:
              - <此处替换自己的包路径>.feign.interceptor.<这里替换自己的类名>FeignInterceptor
    ```
3. 在 springboot 启动类中配置feignClient包的扫描路径，示例如下：<br>
`@EnableFeignClients(basePackages = {"com.haier.rdtp.inventory.api.feignclient", 
   "com.haier.rdtp.product.service.feign.client"})`
4. 在业务处理类中使用 @Autowired 注入 feign 实例即可。

### 服务调用 - 非Jar包方式
参考 *-service 模块 >> feign.client 包中的示例

## 4.4 通用参数校验方式
使用 SpringBoot 默认集成的 Hibernate Validator 进行参数校验，校验结果处理方式有如下两种：<br>
- `list(@Validated ProductQueryDTO query, BindingResult bindingResult)` 
  <br>这种校验方式不会抛异常，需在方法体内通过 bindingResult 来检查是否有校验失败
- `list(@Validated ProductQueryDTO query)` 
  <br>这种方式参数校验不通过时，会抛异常，在全局异常处理类 `GlobalExceptionHandler` 中进行统一处理

**可以对如下几种情况进行校验：**
- 对 get 请求的对象参数校验
- 对 post 请求的对象参数校验
- 对单个参数的校验
- 对必填参数的校验

上边这几种情况对应的异常，已经在 `GlobalExceptionHandler` 全局异常处理类中进行了处理
  
**分组校验方式：**<br>
> 比如，在创建对象时不需要传入 id 字段（ id 字段是主键，由系统生成，不由用户指定），
> 但是在修改对象时就必须要传入 id 字段。在这样的场景下就需要对注解进行分组。

组件有个默认分组 `Default.class`, 所以我们可以再创建一个分组 `UpdateAction.class`，
如下所示：
```java
public interface UpdateAction { }
```
在参数类中需要校验的属性上，在注解中添加 groups 属性，如：
```java
public class UserDTO {
    
    @NotNull(groups = UpdateAction.class, message = "id不能为空")
    private Long id;
    
    @NotBlank
    private String name;
}
```

如上表示在 `UpdateAction` 分组下校验 id 字段，在默认情况下校验 name 字段。
然后在 controller 的方法中，在 @Validated 注解里指定哪种场景即可，
没有指定就代表采用 Default.class，采用其他分组就需要显示指定。
示例如下：<br>
```java
public Result addUser(@Validated UserDTO userAo) {
  // do something
}
public Result updateUser(@Validated({Default.class, UpdateAction.class}) UserAO userAo) {
  // do something
}
```

## 4.5 通用物理分页
框架集成了 MyBatis-Plus 自带的分页插件， MybatisPlusConfig 是分页插件配置类。 *-api 模块的接口中使用自定义的 PageRequest（分页请求基类） 和 PageResponse（分页响应基类） 来屏蔽分页插件对调用方的侵入。
分页同样有两种使用方式
- 第一种：请求 DTO 直接继承分页请求基类 PageRequest
- 第二种：controller 方法中加入 PageRequest 参数用于接收分页参数

service 中使用示例如下：
```java
public PageResponse<ProductDTO> selectPage(ProductQueryDTO query) {
    Page<Product> page = new Page<>(query.getPageNumber(), query.getPageSize());
    // 关联查询 并分页
    IPage<ProductDTO> pageData = productMapper.selectPageByParam(page, query);
    return new PageResponse<>(pageData.getRecords(), pageData.getTotal(), pageData.getCurrent(), pageData.getSize());
}
```

## 4.6 Swagger
```java
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>${knife4j-spring-boot-starter.version}</version>
</dependency>
```
* 项目集成的 Swagger 框架为 `Knife4j`， 它的前身 `swagger-bootstrap-ui` 是一个纯 `swagger-ui` 的 `UI` 皮肤项目。`Knife4j` 底层依赖 `springfox` 框架。
* 项目启动后，访问 `http://localhost:8080/doc.html` 即可看到生成的 Swagger 页面。