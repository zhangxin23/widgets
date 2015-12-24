package net.coderland.server.core.dao;

import net.coderland.server.core.model.pojo.Stock;

import java.util.List;

/**
 * Created by zhangxin on 15/12/23.
 */
public interface StockDao {

    int insert(String name, String code, Integer price, Long ctime, Byte unit);

    List<Stock> getStocksByName(String name, Long since, Long util, Integer limit);
    List<Stock> getStocksByCode(String code, Long since, Long util, Integer limit);
}
