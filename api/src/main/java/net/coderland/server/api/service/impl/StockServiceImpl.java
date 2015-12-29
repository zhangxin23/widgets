package net.coderland.server.api.service.impl;

import net.coderland.server.api.model.response.FollowsResponse;
import net.coderland.server.api.model.response.StockResponse;
import net.coderland.server.api.service.StockService;
import net.coderland.server.common.exception.WidgetsBadRequestException;
import net.coderland.server.core.dao.OptionsDao;
import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.dao.StockFollowsDao;
import net.coderland.server.core.model.bvo.BaiduStockResponse;
import net.coderland.server.core.model.pojo.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangxin on 15/12/23.
 */
@Service("stockService")
public class StockServiceImpl implements StockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "stockDao")
    private StockDao stockDao;

    @Resource(name = "stockFollowsDao")
    private StockFollowsDao stockFollowsDao;

    @Resource(name = "optionsDao")
    private OptionsDao optionsDao;

    private String baiduApiKey = null;

    @Override
    public Object getStocksByName(String name, Long since, Long util, Integer limit) {
        List<Stock> stocks = stockDao.getStocksByName(name, since, util, limit);
        Long start = 0L;
        Long end = 0L;
        if(stocks.size() > 0) {
            start = stocks.get(stocks.size() - 1).getCtime();
            end = stocks.get(0).getCtime();
        }

        return new StockResponse(stocks.size(), start, end, stocks);
    }

    @Override
    public Object getStocksByCode(String code, Long since, Long util, Integer limit) {
        List<Stock> stocks = stockDao.getStocksByCode(code, since, util, limit);
        Long start = 0L;
        Long end = 0L;
        if(stocks.size() > 0) {
            start = stocks.get(stocks.size() - 1).getCtime();
            end = stocks.get(0).getCtime();
        }

        return new StockResponse(stocks.size(), start, end, stocks);
    }

    @Override
    public Object getFollows(String user) {
        List<BaiduStockResponse> responseList = stockFollowsDao.getFollows(user)
                                    .parallelStream()
                                    .map(item->item.getStockCode())
                                    .map(item -> invokeBaiduAPI(item))
                                    .collect(Collectors.toList());
        List<FollowsResponse.FollowsItem> rtnData = new ArrayList<>();
        responseList.stream().forEach(item->{
            for(BaiduStockResponse.StockInfo info: item.getRetData().getStockinfo()) {
                rtnData.add(new FollowsResponse.FollowsItem(info.getName(), info.getCode(), info.getCurrentPrice()));
            }
        });

        return new FollowsResponse(rtnData.size(), rtnData);
    }

    @Override
    public void setFollows(String user, String code) {
        try {
            stockFollowsDao.insert(user, code);
        } catch (Exception e) {
            logger.info("{} had followed {}", user, code);
            throw new RuntimeException(user + " had followed " + code);
        }
    }

    @Override
    public void delFollows(String user, String code) {
        try {
            stockFollowsDao.delete(user, code);
        } catch (Exception e) {
            logger.info("delete {} {} failed", user, code);
            throw new RuntimeException("delete " + user + " " + code + " failed");
        }
    }

    private BaiduStockResponse invokeBaiduAPI(String code) {
        if(baiduApiKey == null)
            baiduApiKey = optionsDao.getOption("baiduapikey");

        if(baiduApiKey == null)
            throw new WidgetsBadRequestException("api key null");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{
                new StringHttpMessageConverter(),
                new MappingJackson2HttpMessageConverter()}));

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", baiduApiKey);
        HttpEntity entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://apis.baidu.com/apistore/stockservice/stock")
                .queryParam("stockid", code)
                .queryParam("list", "1");
        ResponseEntity<BaiduStockResponse> objResponse = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, BaiduStockResponse.class);
        return objResponse.getBody();
    }
}
