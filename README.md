# springcloud的脚手架开发项目，基于restfulAPI
如果觉得项目可以帮助到你，请帮忙`Star`支持一下

该项目目前只整合了 用户 权限认证、以及 日志管理等功能，因为只做为是个项目开发脚手架。

### 项目整合
* JDK1.8
* springboot2.1.6
* springcloud(Greenwich.SR2)
* spring-cloud-eureka
* spring-cloud-config
* spring-cloud-gateway
* spring-boot-admin(monitor)
* swagger&swagger-bootstrap-ui
* redis
* mysql5.7
* lombok

### 项目目录结构
```
project-cloud
|
├──project-common --通用包
|  |
|  ├──project-common-core --核心工具包
|  |
|  ├──project-common-redis --redis工具包
|  |
|  ├──project-common-log --日志工具包
|  |
|  ├──project-common-auth --权限工具包
|
├──project-config --cloud统一配置中心
|
├──project-eureka --注册中心
|
├──project-gateway --网关
|
├──project-service-api --服务api模块
|  |
|  ├──project-system-api --系统业务api
|
├──project-service --微服务
|  |
|  ├──project-system --系统业务
|  |
|  ├──project-auth --授权中心
|  |
|  ├──project-gen --代码生成
|  |
|  ├──project-dfs --文件
|
├──project-tool --工具
|  |
|  ├──project-monitor --监控中心

```
### 启动顺序
* EurekaApplication
* ConfigApplication
* GatewayApplication
* SystemApplication
* AuthApplication(可选)
* MonitorApplication(可选)

### 项目是参考 `ruoyi-cloud` 编写  


