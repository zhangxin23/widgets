package net.coderland.server.core.dao.impl;

import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.model.pojo.Stock;

import java.util.List;

/**
 * Created by zhangxin on 15/12/23.
 */
public class StockDaoImpl implements StockDao {

    @Override
    public int insert(String name, String code, int price, Long ctime, byte unit) {
        return 0;
    }

    @Override
    public List<Stock> getStocksByName(String name, Long start, Long end) {
        return null;
    }

    @Override
    public List<Stock> getStocksByCode(String code, Long start, Long end) {
        return null;
    }

}
