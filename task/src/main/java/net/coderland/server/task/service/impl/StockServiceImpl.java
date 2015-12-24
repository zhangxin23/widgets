package net.coderland.server.task.service.impl;

import net.coderland.server.core.dao.StockCodeDao;
import net.coderland.server.core.model.pojo.StockCode;
import net.coderland.server.task.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
@Service(value = "stockService")
public class StockServiceImpl implements StockService {

    @Resource(name = "stockCodeDao")
    private StockCodeDao stockCodeDao;

    @Override
    public void collect() {
        int count = stockCodeDao.count();
        int limit = 20;
        int iteratorCount = count % limit == 0 ? count / limit : count / limit + 1;
        List<StockCode> stockCodes = new ArrayList<>();
        for(int i = 0; i < iteratorCount; i++) {
            stockCodes.addAll(stockCodeDao.getStockCodes(i * iteratorCount, limit));
        }


    }
}
