package com.ich.proman;

import com.ich.proman.socket.SystemWebSocketHandler;
import com.ich.proman.socket.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 用来注册websocket server实现类,访问websocket的地址
     * @param
     * */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler(),"/websocket").addInterceptors(new WebSocketHandshakeInterceptor());
        registry.addHandler(systemWebSocketHandler(), "/sockjs/websocket").addInterceptors(new WebSocketHandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }

}
