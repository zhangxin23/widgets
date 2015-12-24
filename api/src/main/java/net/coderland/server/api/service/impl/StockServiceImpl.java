package net.coderland.server.api.service.impl;

import net.coderland.server.api.model.response.StockResponse;
import net.coderland.server.api.service.StockService;
import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.model.pojo.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangxin on 15/12/23.
 */
@Service("stockService")
public class StockServiceImpl implements StockService {

    @Resource(name = "stockDao")
    private StockDao stockDao;

    @Override
    public Object getStocksByName(String name, Long since, Long util, Integer limit) {
        List<Stock> stocks = stockDao.getStocksByName(name, since, util, limit);
        Long start = 0L;
        Long end = 0L;
        if(stocks.size() > 0) {
            start = stocks.get(stocks.size() - 1).getCtime();
            end = stocks.get(0).getCtime();
        }

        return new StockResponse(stocks.size(), start, end, stocks);
    }

    @Override
    public Object getStocksByCode(String code, Long since, Long util, Integer limit) {
        List<Stock> stocks = stockDao.getStocksByCode(code, since, util, limit);
        Long start = 0L;
        Long end = 0L;
        if(stocks.size() > 0) {
            start = stocks.get(stocks.size() - 1).getCtime();
            end = stocks.get(0).getCtime();
        }

        return new StockResponse(stocks.size(), start, end, stocks);
    }
}