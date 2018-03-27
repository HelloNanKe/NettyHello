package com.zt.Server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.ServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String [] args){
        ServerBootstrap bootstrap=new ServerBootstrap();

        ExecutorService boss= Executors.newCachedThreadPool();
        ExecutorService worker=Executors.newCachedThreadPool();

        bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline= Channels.pipeline();
                pipeline.addLast("encoder",new StringEncoder());
                pipeline.addLast("decoder",new StringDecoder());
                pipeline.addLast("helloHandler",new HelloHandler());
                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(8080));

        System.out.println("start!!!");
    }
}
