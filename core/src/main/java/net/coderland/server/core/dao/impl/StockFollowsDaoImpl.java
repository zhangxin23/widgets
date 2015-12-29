package net.coderland.server.core.dao.impl;

import net.coderland.server.common.exception.WidgetsBadRequestException;
import net.coderland.server.core.dao.StockFollowsDao;
import net.coderland.server.core.dao.mapper.StockFollowsMapper;
import net.coderland.server.core.model.pojo.StockFollows;
import net.coderland.server.core.model.pojo.StockFollowsExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-29
 */
public class StockFollowsDaoImpl implements StockFollowsDao {

    @Autowired
    private StockFollowsMapper stockFollowsMapper;

    @Override
    public int insert(String user, String code) {
        if(user == null || code == null)
            throw new WidgetsBadRequestException("invalid parameter");

        StockFollows follows = new StockFollows();
        follows.setUser(user);
        follows.setStockCode(code);
        follows.setCtime(new Date());
        follows.setStatus((byte)1);

        return stockFollowsMapper.insert(follows);
    }

    @Override
    public List<StockFollows> getFollows(String user) {
        if(user == null)
            throw new WidgetsBadRequestException("invalid parameter");

        StockFollowsExample example = new StockFollowsExample();
        StockFollowsExample.Criteria criteria = example.createCriteria();
        criteria.andUserEqualTo(user);
        return stockFollowsMapper.selectByExample(example);
    }
}
