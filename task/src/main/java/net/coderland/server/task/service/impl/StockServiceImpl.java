package net.coderland.server.task.service.impl;

import net.coderland.server.core.dao.OptionsDao;
import net.coderland.server.core.dao.StockCodeDao;
import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.model.bvo.BaiduStockRespose;
import net.coderland.server.core.model.pojo.StockCode;
import net.coderland.server.task.service.StockService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Resource(name = "optionsDao")
    private OptionsDao optionsDao;

    private String baiduApiKey = null;

    private static final Map<String, String> TIME_ZONE_MAP = new HashMap<String, String>() {{
        put("sz", "CST");
        put("sh", "CST");
    }};

    @Override
    public void collect() {
        if(!validedDateTime())
            return;

        int count = stockCodeDao.count();
        int limit = 20;
        int iteratorCount = count % limit == 0 ? count / limit : count / limit + 1;
        List<StockCode> stockCodes = new ArrayList<>();
        for(int i = 0; i < iteratorCount; i++) {
            stockCodes.addAll(stockCodeDao.getStockCodes(i * limit, limit));
        }

        if(baiduApiKey == null)
            baiduApiKey = optionsDao.getOption("baiduapikey");

        stockCodes.parallelStream()
                .map(item->item.getHouse() == 1 ? "sh" + item.getCode() : "sz" + item.getCode())
                .map(code->invokeBaiduAPI(code))
                .forEach(obj->{
                    try {
                        save(obj);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private boolean validedDateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        if(day_of_week < Calendar.MONDAY || day_of_week > Calendar.FRIDAY)
            return false;

        long now = System.currentTimeMillis();
        calendar.set(year, month, day_of_month, 9, 30, 0);
        long amStart = calendar.getTimeInMillis();

        calendar.set(year, month, day_of_month, 11, 30, 0);
        long amEnd = calendar.getTimeInMillis();

        calendar.set(year, month, day_of_month, 13, 0, 0);
        long pmStart = calendar.getTimeInMillis();

        calendar.set(year, month, day_of_month, 15, 0, 0);
        long pmEnd = calendar.getTimeInMillis();

        if((now >= amStart && now <= amEnd) || (now >= pmStart && now <= pmEnd))
            return true;
        else
            return false;
    }

    private BaiduStockRespose invokeBaiduAPI(String code) {
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
        ResponseEntity<BaiduStockRespose> objResponse = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, BaiduStockRespose.class);
        return objResponse.getBody();
    }

    private void save(BaiduStockRespose respose) throws ParseException {
        List<BaiduStockRespose.StockInfo> stockInfoList = respose.getRetData().getStockinfo();
        for(BaiduStockRespose.StockInfo info: stockInfoList) {
            if(info.getDate() != null && info.getTime() != null && TIME_ZONE_MAP.get(info.getCode().substring(0, 2)) != null) {
                String dateTime = info.getDate() + " " + info.getTime() + " " + TIME_ZONE_MAP.get(info.getCode().substring(0, 2));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                Date date = dateFormat.parse(dateTime);
                stockDao.insert(info.getName(), info.getCode(), (int) (info.getCurrentPrice() * 100), date.getTime(), (byte) 1);
            }
        }
    }
}
