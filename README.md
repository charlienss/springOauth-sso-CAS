# spring-Oauth-sso
#### 1.简介



#### 2.接口测试

```java
首先启动server项目,在先后启动client1和client2不然会报找不到认证服务器异常:
访问URL:
http://127.0.0.1:8060/client2/index.html
此时会进行认证服务器的登录校验,用户名随便填写,密码我这里固定设置为123456
跳转后点击访问client2即可访问client2的相关内容,至此完成Sso不同应用间单点登录的功能
```

#### 3.代码相关

