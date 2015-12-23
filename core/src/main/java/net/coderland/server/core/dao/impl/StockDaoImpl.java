package net.coderland.server.core.dao.impl;

import net.coderland.server.common.exception.WidgetsBadRequestException;
import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.dao.mapper.StockMapper;
import net.coderland.server.core.model.pojo.Stock;
import net.coderland.server.core.model.pojo.StockExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxin on 15/12/23.
 */
public class StockDaoImpl implements StockDao {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public int insert(String name, String code, Integer price, Long ctime, Byte unit) {
        if(name == null || code == null || price == null || ctime == null || unit == null)
            throw new WidgetsBadRequestException("invalid parameter.");

        Stock stock = new Stock();
        stock.setName(name);
        stock.setCode(code);
        stock.setPrice(price);
        stock.setCtime(ctime);
        stock.setUnit(unit);

        return stockMapper.insert(stock);
    }

    @Override
    public List<Stock> getStocksByName(String name, Long start, Long end) {
        if(name == null || start == null || end == null)
            return new ArrayList<>();

        StockExample example = new StockExample();
        StockExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCtimeGreaterThanOrEqualTo(start);
        criteria.andCtimeLessThanOrEqualTo(end);

        return stockMapper.selectByExample(example);
    }

    @Override
    public List<Stock> getStocksByCode(String code, Long start, Long end) {
        if(code == null || start == null || end == null)
            return new ArrayList<>();

        StockExample example = new StockExample();
        StockExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        criteria.andCtimeGreaterThanOrEqualTo(start);
        criteria.andCtimeLessThanOrEqualTo(end);

        return stockMapper.selectByExample(example);
    }

}
