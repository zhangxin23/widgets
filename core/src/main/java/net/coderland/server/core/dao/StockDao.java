package net.coderland.server.core.dao;

import net.coderland.server.core.model.pojo.Stock;

import java.util.List;

/**
 * Created by zhangxin on 15/12/23.
 */
public interface StockDao {

    int insert(String name, String code, int price, Long ctime, byte unit);

    List<Stock> getStocksByName(String name, Long start, Long end);
    List<Stock> getStocksByCode(String code, Long start, Long end);
}
