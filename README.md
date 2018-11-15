## 配置项目
```
heroku addons:destroy heroku-postgresql
heroku addons:create cleardb:ignite
```

## 本地启动

<img src="http://www.majiang.life/repository/asserts/spring-profile.png" width="350">  

如果上面不工作，使用
```
-Dspring.profiles.active=dev
```  
如果使用 Maven

```
mvn spring-boot:run -Dspring.profiles.active=dev
```
