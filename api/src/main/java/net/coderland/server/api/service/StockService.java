package net.coderland.server.api.service;

/**
 * Created by zhangxin on 15/12/23.
 */
public interface StockService {

    Object getStocksByName(String name, Long start, Long end);
    Object getStocksByCode(String code, Long start, Long end);
}
