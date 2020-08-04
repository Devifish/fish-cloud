# Fish Cloud
[![Build status](https://ci.appveyor.com/api/projects/status/apa6tcw8v7dxc1l2?svg=true)](https://ci.appveyor.com/project/Devifish/fish-cloud)
[![star](https://img.shields.io/github/stars/Devifish/fish-cloud.svg?logo=github)](https://github.com/Devifish/fish-cloud)
[![license](https://img.shields.io/github/license/Devifish/fish-cloud)](https://github.com/Devifish/fish-cloud)

> 基于 Spring Cloud Hoxton & Alibaba 微服务框架开发<br/>
> 紧跟 Java 长期支持版 代码库使用 Java 11 源码级别<br/>
> 使用 Lambda、Java Stream Api 现代化的函数式编码<br/>
> 提供对 Docker 容器环境运行的支持,可使用 Docker Compose、Kubernetes 编排

## 依赖环境
依赖 | 版本
---|---
Java Language | 11
Spring Boot | 2.3.2.RELEASE
Spring Cloud | Hoxton.SR6
Spring Cloud Alibaba | 2.2.1.RELEASE
Mybatis Plus | 3.3.2

- 要求 JDK 11+, 代码库基于 Java 11开发无法使用其以下版本运行
- 其余依赖包版本基本源自 ```Spring Boot``` ```Spring Cloud``` ```Spring Cloud Alibaba``` 提供的DependencyManagement
- 中间件依赖环境建议使用提供的 ```docker-compose``` 构建
  
## 模块说明
```
└── fish-common --------------------------------- 公共组件工具包
     ├── fish-common-core ----------------------- 公共核心组件
     ├── fish-common-mybatis -------------------- 公共ORM组件(Mybatis)
     ├── fish-common-rabbitmq ------------------- 公共消息队列组件(RabbitMQ) [TODO]
     ├── fish-common-redis ---------------------- 公共缓存组件(Redis)
     ├── fish-common-security ------------------- 公共安全组件(Spring OAuth2)
     ├── fish-common-sentinel ------------------- 公共流量控制组件(Sentinel) [TODO]
     └── fish-common-swagger -------------------- 公共接口文档组件(Swagger) [TODO]
└── fish-file ----------------------------------- 文件服务聚合包
     ├── fish-file-common ----------------------- 文件服务公共组件
     └── fish-file-server ----------------------- 文件服务
├── fish-gateway -------------------------------- Gateway 路由网关
└── fish-message -------------------------------- 消息服务聚合包 [TODO]
     ├── fish-message-common -------------------- 消息服务公共组件 [TODO]
     └── fish-message-server -------------------- 消息服务 [TODO]
└── fish-search --------------------------------- 搜索服务聚合包 [TODO]
     ├── fish-search-common --------------------- 搜索服务公共组件 [TODO]
     └── fish-search-server --------------------- 搜索服务 [TODO]
└── fish-upms ----------------------------------- 用户权限服务聚合包
     ├── fish-upms-common ----------------------- 用户权限服务公共组件
     └── fish-upms-server ----------------------- 用户权限服务
```