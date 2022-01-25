# YMY-Netty

## BIOServer case说明
### BIO问题分析：
1. 每个请求都需要独立的线程处理，系统资源占用大
2. 服务器再等待客户端连接时会阻塞主线程、建立连接后的socket线程在等待客户端端输入是也会阻塞，造成线程资源浪费

## NIO群聊系统（case1）
1. 服务器端：可以检测用户上线、离线并实现消息转发功能
2. 客户端：通过channel可以无阻塞发送消息给其他所有用户吗，同时可以接受其它用户发送的消息（服务器转发得到）

## Netty快速入门实例-TCP服务
1. Netty服务器在6668端口监听， 客户端发送消息给服务器 “hello， 服务器~”
2. 服务器可以回复消息给客户端 “hello， 客户端~”

## Netty快速入门实例-HTTP服务
1. Netty服务器在6668端口监听，浏览器发出请求“http://localhost：6668”
2. 服务器可以恢复消息给客户端 “hello，我是服务器”， 并对特定请求资源进行过滤
> HTTP服务case在实际应用中，端口号要大于8000，不然浏览器无法访问
> 通过限定请求路径来达到过滤资源的目的

## Netty应用实例-群聊系统
1. 实现服务器端和客户端之间的数据简单通讯（非阻塞）
2. 实现多人群聊
3. 服务器端：可以监测用户上线、离线，并实现消息转发功能
4. 客户端：通过channel可以无阻赛发送消息给其他所有用户， 同时可以接受其他用户发送的的消息（通过服务器转发）

## Netty心跳检测机制案例
1. 当服务器3秒没有读时，就提示空闲
2. 当服务器超过5秒没有写操作时，就提示写空闲
3. 当服务器超过7秒没有读或者写操作时，就提示读写空闲

## Netty通过WebSocket编程实现服务器和客户端长链接
1. 实现基于webSocket的长连接的全双工的交互
2. 客户端浏览器和服务器会相互感知，比如服务器关闭了，浏览器会感知，同样浏览器关闭了，服务器会感知

## Netty-protobuf入门使用
1. 客户端发送一个StudentPOJO对象到服务器（通过protobuf编码）
2. 服务端能够接收这个对象并显示属性信息（通过protobuf解码）

## Netty - protobuf入门实例2
1. 客户端发送一个StudentPOJO / WrokerPOJO对象到服务器（通过protobuf编码）
2. 服务端能够接收StudentPOJO / WrokerPOJO对象（需要判断类型）并显示属性信息（通过protobuf解码）
> 解决方案：就是将多个类放在一个proto文件中，同时在设置一个类来用枚举的方式管理类型，同时设置这两个类为依赖属性，这样就可以先通过类型判断再获取相应的数据

