package net.coderland.server.api.aop;

import net.coderland.server.api.model.response.StockResponse;
import org.springframework.stereotype.Component;

/**
 * Created by zhangxin on 16/3/4.
 */

@Component(value = "logPointcut")
public class LogPointcut {
    public StockResponse stockPointcut(StockResponse response) {
        return response;
    }
}
