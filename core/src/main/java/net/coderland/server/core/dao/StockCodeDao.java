package net.coderland.server.core.dao;

import net.coderland.server.core.model.pojo.StockCode;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
public interface StockCodeDao {

    int count();
    int count(Byte house);

    List<StockCode> getStockCodes(Integer offset, Integer limit);
    List<StockCode> getStockCodes(Byte house, Integer offset, Integer limit);
}
