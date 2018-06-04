# [Spring Boot入门]模拟在线票务管理系统

本项目可作为Spring Boot初学者入门非常实用的练手项目。

本项目使用了Spring Boot + Mybatis实现了模拟在线票务管理系统，集成Spring Security实现用户的认证和授权。

希望可以有一起在学习Spring Boot的朋友可以一起来完善该项目。

目前已完成功能：

*用户注册、登录、查看用户资料、上传头像，查看头像

*根据始发站、目的地和出发日查找可购买的车次信息

*购票、退票、查看“我的订单”。（数据库事务）

Version 0.9主要实现了以上的主要功能。

Version 1.0集成了Spring Security，使用JWT实现用户的认证和授权。
认证原理为：向/login地址发送POST请求，提交包含用户名密码的表单，程序进入`JWTLoginFilter`的`attemptAuthentication`方法，接收并解析用户凭证。
然后进入`CustomerAuthenticationProvider`的`authenticate`方法将接受到的用户信息与数据库中信息做比较，
如果一致，生成令牌并进入`JWTLoginFilter`的`successfulAuthentication`方法，在该方法中生成JWT并将JWT作为response的header返回至前端。

在之后的请求中只要将JWT设置为request header的“Authorization”字段传给后端，后端就可以解析该header获取用户信息。

用户信息中包含了登录时间，

####数据库结构：

包含3张表:customer、orderform(order为sql关键字)、ticket

customer属性：
- id  
- username
- password 
- email
- cellphone
- avatar_url

orderform属性:

- orderid
- customerid
- username
- ticketid
- trainnumber
- checkin
- checkout
- starttime
- endtime
- seattype
- seatnumber
- price

ticket属性:

- id
- trainnumber
- checkin
- checkout
- starttime
- endtime
- seattype
- seatnumber
- price
- isordered

数据库操作在mapper包中

SQL还有许多优化的地方，由于作者SQL知识还不是很充分，希望大家可以多提一些SQL优化的建议。

