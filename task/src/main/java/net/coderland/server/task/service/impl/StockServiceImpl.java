package net.coderland.server.task.service.impl;

import net.coderland.server.core.dao.StockCodeDao;
import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.model.bvo.BaiduStockRespose;
import net.coderland.server.core.model.pojo.StockCode;
import net.coderland.server.task.service.StockService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
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
 * Author: zhangxin
 * Date:   15-12-24
 */
@Service(value = "stockService")
public class StockServiceImpl implements StockService {

    @Resource(name = "stockCodeDao")
    private StockCodeDao stockCodeDao;

    @Resource(name = "stockDao")
    private StockDao stockDao;

    @Override
    public void collect() {
        int count = stockCodeDao.count();
        int limit = 20;
        int iteratorCount = count % limit == 0 ? count / limit : count / limit + 1;
        List<StockCode> stockCodes = new ArrayList<>();
        for(int i = 0; i < iteratorCount; i++) {
            stockCodes.addAll(stockCodeDao.getStockCodes(i * limit, limit));
        }

        stockCodes.parallelStream()
                .map(item->item.getHouse() == 1 ? "sh" + item.getCode() : "sz" + item.getCode())
                .map(code->invokeBaiduAPI(code))
                .forEach(obj->save(obj));
    }

    private BaiduStockRespose invokeBaiduAPI(String code) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{
                        new StringHttpMessageConverter(),
                        new MappingJackson2HttpMessageConverter()}));

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", "c81b320919308e26b745f5020b1eab33");
        HttpEntity entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://apis.baidu.com/apistore/stockservice/stock")
                                                            .queryParam("stockid", code)
                                                            .queryParam("list", "1");
        ResponseEntity<BaiduStockRespose> objResponse = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, BaiduStockRespose.class);
        return objResponse.getBody();
    }

    private void save(BaiduStockRespose respose) {
        List<BaiduStockRespose.StockInfo> stockInfoList = respose.getRetData().getStockinfo();
        for(BaiduStockRespose.StockInfo info: stockInfoList) {
            String dateTime = info.getTime() + " " + info.getDate();
            stockDao.insert(info.getName(), info.getCode(), (int)(info.getCurrentPrice() * 100), System.currentTimeMillis(), (byte)1);
        }
    }
}
