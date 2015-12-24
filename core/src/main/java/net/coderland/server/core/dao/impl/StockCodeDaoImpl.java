package net.coderland.server.core.dao.impl;

import net.coderland.server.common.exception.WidgetsBadRequestException;
import net.coderland.server.core.dao.StockCodeDao;
import net.coderland.server.core.dao.mapper.StockCodeMapper;
import net.coderland.server.core.model.pojo.StockCode;
import net.coderland.server.core.model.pojo.StockCodeExample;
import net.coderland.server.core.model.pojo.StockExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
public class StockCodeDaoImpl implements StockCodeDao {

    @Autowired
    private StockCodeMapper stockCodeMapper;

    @Override
    public int count() {
        StockCodeExample example = new StockCodeExample();
        return stockCodeMapper.countByExample(example);
    }

    @Override
    public int count(Byte house) {
        if(house == null)
            return 0;

        StockCodeExample example = new StockCodeExample();
        StockCodeExample.Criteria criteria = example.createCriteria();
        criteria.andHouseEqualTo(house);
        return stockCodeMapper.countByExample(example);
    }

    @Override
    public List<StockCode> getStockCodes(Integer offset, Integer limit) {
        if(offset == null || limit == null)
            throw new WidgetsBadRequestException("invalid parameter.");

        StockCodeExample example = new StockCodeExample();
        example.setOrderByClause(" id limit " + offset + ", " + limit);
        return stockCodeMapper.selectByExample(example);
    }

    @Override
    public List<StockCode> getStockCodes(Byte house, Integer offset, Integer limit) {
        if(house == null || offset == null || limit == null)
            throw new WidgetsBadRequestException("invalid parameter.");

        StockCodeExample example = new StockCodeExample();
        StockCodeExample.Criteria criteria = example.createCriteria();
        criteria.andHouseEqualTo(house);
        example.setOrderByClause(" id limit " + offset + ", " + limit);
        return stockCodeMapper.selectByExample(example);
    }
}
