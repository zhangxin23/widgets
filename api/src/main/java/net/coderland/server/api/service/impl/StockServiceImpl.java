package net.coderland.server.api.service.impl;

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
    public Object getStocksByName(String name, Long start, Long end) {
        List<Stock> stocks = stockDao.getStocksByName(name, start, end);

        return stocks;
    }

    @Override
    public Object getStocksByCode(String code, Long start, Long end) {
        List<Stock> stocks = stockDao.getStocksByCode(code, start, end);

        return stocks;
    }
}
