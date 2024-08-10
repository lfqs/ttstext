功能1：在tools.text包--文字转语言并播放
jacob-1.18-x64.dll 放置在java安装路径bin目录下  如：D:\Java\jdk1.8.0_144\bin
pom文件引入jacob的jar包


功能2：在tools.wx包--微信推送
微信推送教程：
https://www.cnblogs.com/qiangzaiBlog/p/16979016.html

功能3：在tools.netty包--netty发送接收报文（报文自己定义）

功能4：整个springboot整合为类型开放平台接口授权 参考教程：https://blog.csdn.net/xm393392625/article/details/129815418
整合流程：
1，先配置AuthorizationServerConfig类配置授权中心信息
2，配置SecurityConfig 添加Security权限配置

