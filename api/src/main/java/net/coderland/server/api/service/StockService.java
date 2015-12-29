package net.coderland.server.api.service;

/**
 * Created by zhangxin on 15/12/23.
 */
public interface StockService {

    Object getStocksByName(String name, Long since, Long util, Integer limit);
    Object getStocksByCode(String code, Long since, Long util, Integer limit);

    Object getFollows(String user);
    void setFollows(String user, String code);
    void delFollows(String user, String code);
}
