/**
 * /**
 * 一个典型的Java NIO服务端开发需要做几件事:
 * <p>
 * 1. 　创建ServerSocketChannel,设置为非阻塞，并绑定端口
 * <p>
 * 2.　创建Selector对象
 * <p>
 * 3.　给ServerSocketChannel注册SelectionKey.OP_ACCEPT事件
 * <p>
 * 4.　启动一个线程循环，调用Selector的select方法来检查IO就绪事件，一旦有IO就绪事件，就通知用户线程去处理IO事件
 * <p>
 * 5.　如果有Accept事件，就创建一个SocketChannel，并注册SelectionKey_OP_READ
 * <p>
 * 6.    如果有读事件，判断一下是否全包，如果全包，就交给后端线程处理
 * <p>
 * 7.    写事件比较特殊。isWriteable表示的是本机的写缓冲区是否可写。这个在绝大多少情况下都是为真的。
 *  在Netty中只有写半包的时候才需要注册写事件，如果一次写就完全把数据写入了缓冲区就不需要注册写事件.
 */
package com.frank.concert.foundation.connect.nio;