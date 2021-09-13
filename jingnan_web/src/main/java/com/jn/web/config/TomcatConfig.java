package com.jn.web.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Author yaxiongliu
 **/
@Configuration
public class TomcatConfig {
    //自定义SpringBoot嵌入式Tomcat
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {};
        tomcat.addAdditionalTomcatConnectors(http11NioConnector());
        tomcat.addAdditionalTomcatConnectors(http11Nio2Connector());
        /**
         * 当前的SpringBoot中的Tomcat拥有3个入口连接器
         * 9001 nio 【默认】
         * 9002 nio 【配置keepalive】
         * 9003 nio2【超高性能】
         */
        return tomcat;
    }
    //配置连接器nio2
    public Connector http11Nio2Connector() {
        Connector connector=new Connector("org.apache.coyote.http11.Http11Nio2Protocol");
        Http11Nio2Protocol nio2Protocol = (Http11Nio2Protocol) connector.getProtocolHandler();
        //等待队列最多允许1000个线程在队列中等待
        nio2Protocol.setAcceptCount(1000);
        // 设置最大线程数
        nio2Protocol.setMaxThreads(1000);
        // 设置最大连接数
        nio2Protocol.setMaxConnections(20000);
        //定制化keepalivetimeout,设置30秒内没有请求则服务端自动断开keepalive链接
        nio2Protocol.setKeepAliveTimeout(30000);
        //当客户端发送超过10000个请求则自动断开keepalive链接
        nio2Protocol.setMaxKeepAliveRequests(10000);
        // 请求方式
        connector.setScheme("http");
        connector.setPort(9003);                    //自定义的
        connector.setRedirectPort(8443);
        return connector;
    }
    //配置keepAlive
    //重新定义一个Connector
    public Connector http11NioConnector() {//Tomcat内部实现
        //默认SpringBoot不支持配置文件方式修改连接器的keepalive参数时间
        //网络通信的三要素：ip、协议、端口
        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol nioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
        //等待队列最多允许1000个线程在队列中等待
        nioProtocol.setAcceptCount(1000);
        // 设置最大线程数
        nioProtocol.setMaxThreads(1000);
        // 设置最大连接数
        nioProtocol.setMaxConnections(20000);
        //定制化keepalivetimeout,设置30秒内没有请求则服务端自动断开keepalive链接
        //默认是20s
        nioProtocol.setKeepAliveTimeout(3);
        //当客户端发送超过10000个请求则自动断开keepalive链接
        //默认100个
        nioProtocol.setMaxKeepAliveRequests(10000);
        // 请求方式
        connector.setScheme("http");
        connector.setPort(9002);                    //自定义的
        connector.setRedirectPort(8443);
        return connector;
    }

}
