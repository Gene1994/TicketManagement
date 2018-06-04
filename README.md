# TicketManagement
模拟在线票务管理系统

最近在学习Spring Boot,看网上有说最好的学习方法就是自己动手编程，有人推荐尝试自己开发一个模拟在线票务管理系统。

本项目使用了Spring Boot + Mybatis实现了模拟在线票务管理系统，集成Spring Security实现用户的认证和授权。

希望可以有一起在学习Spring Boot的朋友可以一起来完善该项目。

目前已完成功能：

_用户注册、登录、查看用户资料、上传头像，查看头像

根据始发站、目的地和出发日查找可购买的车次信息

购票、退票、查看“我的订单”。_

Version 0.9主要实现了以上的主要功能。

Version 1.0集成了Spring Security，使用JWT实现用户的认证和授权。
认证原理为：向/login地址发送POST请求，提交包含用户名密码的表单，进入JWTLoginFilter的attemptAuthentication方法，接收并解析用户凭证。
然后进入CustomerAuthenticationProvider的authenticate方法将接受到的用户信息与数据库中信息做比较，
如果一致，生成令牌并进入JWTLoginFilter的successfulAuthentication方法，在该方法中生成JWT并将JWT作为response的header返回至前端。

在之后的请求中只要将JWT设置为request header的“Authorization”字段传给后端，后端就可以解析该header获取用户信息。

数据库结构：

包含3张表:customer、orderform(order为sql关键字)、ticket

customer:

|名        | 类型    |  长度  |
| :-------- :| :-----:  | :----: |
| id     | varchar     |   255    |
| username     | varchar      |   255    |
| password     | varchar     |   255    |
| email     | varchar     |   255    |
| cellphone     | varchar     |   255    |
| avatar_url     | varchar     |   255    |

orderform:

|名        | 类型    |  长度  |
| :-------- :| :-----:  | :----: |
| orderid    | varchar     |   255    |
| customerid    | varchar      |   255    |
| username     | varchar     |   255    |
| ticketid     | varchar     |   255    |
| trainnumber     | varchar     |   255    |
| checkin     | varchar     |   255    |
| checkout     | varchar     |   255    |
| starttime     | datetime     |   0    |
| endtime     | datetime     |   0    |
| seattype     | varchar     |   255    |
| seatnumber     | varchar     |   255    |
| price     | double     |   11    |

ticket:

|名        | 类型    |  长度  |
| :-------- :| :-----:  | :----: |
| id    | varchar     |   255    |
| trainnumber    | varchar      |   255    |
| checkin     | varchar     |   255    |
| checkout     | varchar     |   255    |
| starttime     | datetime     |   0    |
| endtime     | datetime     |   0    |
| seattype     | varchar     |   255    |
| seatnumber     | varchar     |   255    |
| price     | double     |   11    |
| isordered     | char     |   1    |

