package net.coderland.server.core.dao;

import net.coderland.server.core.model.pojo.StockFollows;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-29
 */
public interface StockFollowsDao {

    int insert(String user, String code);

    List<StockFollows> getFollows(String user);
}
