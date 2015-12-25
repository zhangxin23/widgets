package net.coderland.server.core.dao.impl;

import net.coderland.server.core.dao.OptionsDao;
import net.coderland.server.core.dao.mapper.OptionsMapper;
import net.coderland.server.core.model.pojo.Options;
import net.coderland.server.core.model.pojo.OptionsExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-25
 */
public class OptionsDaoImpl implements OptionsDao {

    @Autowired
    private OptionsMapper optionsMapper;

    @Override
    public String getOption(String name) {
        if(name == null)
            return null;

        OptionsExample example = new OptionsExample();
        OptionsExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Options> optionses = optionsMapper.selectByExample(example);
        if(optionses.size() == 1)
            return optionses.get(0).getValue();
        return null;
    }
}
