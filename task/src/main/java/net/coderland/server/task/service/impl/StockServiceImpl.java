package net.coderland.server.task.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.coderland.server.common.exception.WidgetsInternalServerErrorException;
import net.coderland.server.core.dao.OptionsDao;
import net.coderland.server.core.dao.StockCodeDao;
import net.coderland.server.core.dao.StockDao;
import net.coderland.server.core.model.bvo.BaiduStockResponse;
import net.coderland.server.core.model.pojo.StockCode;
import net.coderland.server.task.cache.StockCache;
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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
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

    @Resource(name = "optionsDao")
    private OptionsDao optionsDao;

    @Resource(name = "stockCache")
    private StockCache stockCache;

    private String baiduApiKey = null;

    private List<String> codesList = null;

    private static final Map<String, String> TIME_ZONE_MAP = new HashMap<String, String>() {{
        put("sz", "CST");
        put("sh", "CST");
    }};

    @Override
    public void collect() {
//        if(!validedDateTime())
//            return;

        if(codesList == null) {
            int count = stockCodeDao.count();
            int limit = 20;
            int iteratorCount = count % limit == 0 ? count / limit : count / limit + 1;
            List<StockCode> stockCodes = new ArrayList<>();
            for (int i = 0; i < iteratorCount; i++) {
                stockCodes.addAll(stockCodeDao.getStockCodes(i * limit, limit));
            }

            codesList = new ArrayList<>();
            StringBuilder codeBuilder = new StringBuilder(10);
            for(int i = 0; i < stockCodes.size(); i++) {
                if((i == stockCodes.size() - 1) || (i != 0 && i % 10 == 0)) {
                    String codes = codeBuilder.toString();
                    codes = codes.substring(0, codes.length() - 2);
                    codesList.add(codes);
                    codeBuilder.delete(0, codeBuilder.length());
                }

                String codeName = stockCodes.get(i).getHouse() == 1 ? "sh" + stockCodes.get(i).getCode() :
                                                                      "sz" + stockCodes.get(i).getCode();
                codeBuilder.append(codeName).append(",");
            }
        }

        if(baiduApiKey == null)
            baiduApiKey = optionsDao.getOption("baiduapikey");

        codesList.parallelStream().map(item->invokeRawBaiduAPI(item))
                                  .forEach(item -> saveCache(item));
    }

    @Override
    public void saveCacheToDB() {
        ExecutorService executor = Executors.newFixedThreadPool(12);
        executor.execute(() -> {

            System.out.println(Thread.currentThread().getName());

//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            while (true/*!stockCache.empty()*/) {
//                try {
////                    String reponse = stockCache.getKey();
//                    String reponse = "{\"errNum\":0,\"errMsg\":\"success\",\"retData\":{\"stockinfo\":[{\"name\":\"\\u66b4\\u98ce\\u79d1\\u6280\",\"code\":\"sz300431\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"openningPrice\":0,\"closingPrice\":95.83,\"currentPrice\":0,\"hPrice\":0,\"lPrice\":0,\"competitivePrice\":0,\"auctionPrice\":0,\"totalNumber\":0,\"turnover\":0,\"increase\":-100,\"buyOne\":0,\"buyOnePrice\":0,\"buyTwo\":0,\"buyTwoPrice\":0,\"buyThree\":0,\"buyThreePrice\":0,\"buyFour\":0,\"buyFourPrice\":0,\"buyFive\":0,\"buyFivePrice\":0,\"sellOne\":0,\"sellOnePrice\":0,\"sellTwo\":0,\"sellTwoPrice\":0,\"sellThree\":0,\"sellThreePrice\":0,\"sellFour\":0,\"sellFourPrice\":0,\"sellFive\":0,\"sellFivePrice\":0,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300431.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300431.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300431.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300431.gif\"},{\"name\":\"\\u5bcc\\u4e34\\u7cbe\\u5de5\",\"code\":\"sz300432\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":25.77,\"closingPrice\":25.77,\"currentPrice\":26.06,\"hPrice\":26.15,\"lPrice\":25.65,\"competitivePrice\":26.05,\"auctionPrice\":26.06,\"totalNumber\":2691931,\"turnover\":69882783,\"increase\":1.1253395421032,\"buyOne\":3200,\"buyOnePrice\":26.05,\"buyTwo\":500,\"buyTwoPrice\":26.04,\"buyThree\":3300,\"buyThreePrice\":26.03,\"buyFour\":3000,\"buyFourPrice\":26.02,\"buyFive\":3000,\"buyFivePrice\":26.01,\"sellOne\":9700,\"sellOnePrice\":26.06,\"sellTwo\":3300,\"sellTwoPrice\":26.08,\"sellThree\":9100,\"sellThreePrice\":26.09,\"sellFour\":28400,\"sellFourPrice\":26.1,\"sellFive\":3400,\"sellFivePrice\":26.12,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300432.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300432.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300432.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300432.gif\"},{\"name\":\"\\u84dd\\u601d\\u79d1\\u6280\",\"code\":\"sz300433\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":85.22,\"closingPrice\":85.01,\"currentPrice\":85.38,\"hPrice\":85.7,\"lPrice\":84.6,\"competitivePrice\":85.37,\"auctionPrice\":85.38,\"totalNumber\":1526975,\"turnover\":130032945,\"increase\":0.43524291259851,\"buyOne\":700,\"buyOnePrice\":85.37,\"buyTwo\":26000,\"buyTwoPrice\":85.35,\"buyThree\":7200,\"buyThreePrice\":85.34,\"buyFour\":6000,\"buyFourPrice\":85.33,\"buyFive\":100,\"buyFivePrice\":85.32,\"sellOne\":2693,\"sellOnePrice\":85.38,\"sellTwo\":1000,\"sellTwoPrice\":85.39,\"sellThree\":1400,\"sellThreePrice\":85.4,\"sellFour\":300,\"sellFourPrice\":85.43,\"sellFive\":400,\"sellFivePrice\":85.45,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300433.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300433.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300433.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300433.gif\"},{\"name\":\"\\u91d1\\u77f3\\u4e1c\\u65b9\",\"code\":\"sz300434\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":67.51,\"closingPrice\":67.3,\"currentPrice\":67.66,\"hPrice\":68.5,\"lPrice\":67,\"competitivePrice\":67.66,\"auctionPrice\":67.7,\"totalNumber\":809746,\"turnover\":54973695,\"increase\":0.53491827637444,\"buyOne\":2700,\"buyOnePrice\":67.66,\"buyTwo\":12900,\"buyTwoPrice\":67.65,\"buyThree\":100,\"buyThreePrice\":67.64,\"buyFour\":100,\"buyFourPrice\":67.63,\"buyFive\":4100,\"buyFivePrice\":67.62,\"sellOne\":200,\"sellOnePrice\":67.7,\"sellTwo\":1100,\"sellTwoPrice\":67.8,\"sellThree\":300,\"sellThreePrice\":67.88,\"sellFour\":200,\"sellFourPrice\":67.9,\"sellFive\":100,\"sellFivePrice\":67.96,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300434.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300434.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300434.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300434.gif\"},{\"name\":\"\\u4e2d\\u6cf0\\u80a1\\u4efd\",\"code\":\"sz300435\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":83.95,\"closingPrice\":83.9,\"currentPrice\":79.6,\"hPrice\":83.98,\"lPrice\":78.6,\"competitivePrice\":79.58,\"auctionPrice\":79.6,\"totalNumber\":4066373,\"turnover\":326627622,\"increase\":-5.1251489868892,\"buyOne\":100,\"buyOnePrice\":79.58,\"buyTwo\":900,\"buyTwoPrice\":79.53,\"buyThree\":500,\"buyThreePrice\":79.5,\"buyFour\":1900,\"buyFourPrice\":79.48,\"buyFive\":4900,\"buyFivePrice\":79.47,\"sellOne\":8700,\"sellOnePrice\":79.6,\"sellTwo\":100,\"sellTwoPrice\":79.61,\"sellThree\":100,\"sellThreePrice\":79.68,\"sellFour\":900,\"sellFourPrice\":79.69,\"sellFive\":700,\"sellFivePrice\":79.7,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300435.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300435.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300435.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300435.gif\"},{\"name\":\"\\u5e7f\\u751f\\u5802\",\"code\":\"sz300436\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":85.2,\"closingPrice\":85.2,\"currentPrice\":85,\"hPrice\":85.96,\"lPrice\":84.77,\"competitivePrice\":85,\"auctionPrice\":85.1,\"totalNumber\":696227,\"turnover\":59335508,\"increase\":-0.23474178403756,\"buyOne\":669,\"buyOnePrice\":85,\"buyTwo\":800,\"buyTwoPrice\":84.99,\"buyThree\":2592,\"buyThreePrice\":84.98,\"buyFour\":600,\"buyFourPrice\":84.97,\"buyFive\":300,\"buyFivePrice\":84.96,\"sellOne\":1200,\"sellOnePrice\":85.1,\"sellTwo\":300,\"sellTwoPrice\":85.13,\"sellThree\":9400,\"sellThreePrice\":85.14,\"sellFour\":6200,\"sellFourPrice\":85.15,\"sellFive\":19800,\"sellFivePrice\":85.16,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300436.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300436.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300436.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300436.gif\"},{\"name\":\"\\u6e05\\u6c34\\u6e90\",\"code\":\"sz300437\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":0,\"closingPrice\":105.77,\"currentPrice\":0,\"hPrice\":0,\"lPrice\":0,\"competitivePrice\":0,\"auctionPrice\":0,\"totalNumber\":0,\"turnover\":0,\"increase\":-100,\"buyOne\":0,\"buyOnePrice\":0,\"buyTwo\":0,\"buyTwoPrice\":0,\"buyThree\":0,\"buyThreePrice\":0,\"buyFour\":0,\"buyFourPrice\":0,\"buyFive\":0,\"buyFivePrice\":0,\"sellOne\":0,\"sellOnePrice\":0,\"sellTwo\":0,\"sellTwoPrice\":0,\"sellThree\":0,\"sellThreePrice\":0,\"sellFour\":0,\"sellFourPrice\":0,\"sellFive\":0,\"sellFivePrice\":0,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300437.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300437.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300437.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300437.gif\"},{\"name\":\"\\u9e4f\\u8f89\\u80fd\\u6e90\",\"code\":\"sz300438\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":133.58,\"closingPrice\":133.88,\"currentPrice\":129.87,\"hPrice\":133.58,\"lPrice\":126.96,\"competitivePrice\":129.87,\"auctionPrice\":129.92,\"totalNumber\":2219318,\"turnover\":286587175,\"increase\":-2.9952195996415,\"buyOne\":6400,\"buyOnePrice\":129.87,\"buyTwo\":15500,\"buyTwoPrice\":129.86,\"buyThree\":10600,\"buyThreePrice\":129.85,\"buyFour\":200,\"buyFourPrice\":129.8,\"buyFive\":100,\"buyFivePrice\":129.6,\"sellOne\":100,\"sellOnePrice\":129.92,\"sellTwo\":600,\"sellTwoPrice\":129.95,\"sellThree\":100,\"sellThreePrice\":129.97,\"sellFour\":200,\"sellFourPrice\":129.98,\"sellFive\":2000,\"sellFivePrice\":129.99,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300438.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300438.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300438.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300438.gif\"},{\"name\":\"\\u7f8e\\u5eb7\\u751f\\u7269\",\"code\":\"sz300439\",\"date\":\"2015-12-30\",\"time\":\"15:00:40\",\"OpenningPrice\":40.7,\"closingPrice\":40.41,\"currentPrice\":40.64,\"hPrice\":40.9,\"lPrice\":40.14,\"competitivePrice\":40.64,\"auctionPrice\":40.65,\"totalNumber\":3827996,\"turnover\":155019713,\"increase\":0.56916604800793,\"buyOne\":24600,\"buyOnePrice\":40.64,\"buyTwo\":19700,\"buyTwoPrice\":40.63,\"buyThree\":6000,\"buyThreePrice\":40.62,\"buyFour\":3700,\"buyFourPrice\":40.61,\"buyFive\":17500,\"buyFivePrice\":40.6,\"sellOne\":1000,\"sellOnePrice\":40.65,\"sellTwo\":4400,\"sellTwoPrice\":40.66,\"sellThree\":700,\"sellThreePrice\":40.67,\"sellFour\":4400,\"sellFourPrice\":40.68,\"sellFive\":2000,\"sellFivePrice\":40.69,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz300439.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz300439.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz300439.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz300439.gif\"},{\"name\":\"\",\"code\":\"sz30044\",\"date\":null,\"time\":null,\"OpenningPrice\":0,\"closingPrice\":0,\"currentPrice\":0,\"hPrice\":0,\"lPrice\":0,\"competitivePrice\":0,\"auctionPrice\":0,\"totalNumber\":0,\"turnover\":0,\"increase\":\"\",\"buyOne\":0,\"buyOnePrice\":0,\"buyTwo\":0,\"buyTwoPrice\":0,\"buyThree\":0,\"buyThreePrice\":0,\"buyFour\":0,\"buyFourPrice\":0,\"buyFive\":0,\"buyFivePrice\":0,\"sellOne\":0,\"sellOnePrice\":0,\"sellTwo\":0,\"sellTwoPrice\":0,\"sellThree\":0,\"sellThreePrice\":0,\"sellFour\":0,\"sellFourPrice\":0,\"sellFive\":0,\"sellFivePrice\":0,\"minurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/min\\/n\\/sz30044.gif\",\"dayurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/daily\\/n\\/sz30044.gif\",\"weekurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/weekly\\/n\\/sz30044.gif\",\"monthurl\":\"http:\\/\\/image.sinajs.cn\\/newchart\\/monthly\\/n\\/sz30044.gif\"}],\"market\":{\"shanghai\":{\"name\":\"\\u4e0a\\u8bc1\\u6307\\u6570\",\"curdot\":3572.6938,\"curprice\":8.9582,\"rate\":0.25,\"dealnumber\":1878896,\"turnover\":26778767},\"shenzhen\":{\"name\":\"\\u6df1\\u8bc1\\u6210\\u6307\",\"curdot\":12889.834,\"curprice\":83.678,\"rate\":0.65,\"dealnumber\":231731893,\"turnover\":45554777},\"DJI\":{\"name\":\"\\u9053\\u743c\\u65af\",\"date\":\"2015-12-30 07:30:06\",\"curdot\":17720.98,\"rate\":1.1,\"growth\":192.71,\"startdot\":17547.37,\"closedot\":17528.27,\"hdot\":17750.02,\"ldot\":17547.37,\"turnover\":19970000},\"IXIC\":{\"name\":\"\\u7eb3\\u65af\\u8fbe\\u514b\",\"date\":\"2015-12-30 06:40:00\",\"curdot\":5107.94,\"rate\":1.33,\"growth\":66.95,\"startdot\":5066.52,\"closedot\":5040.99,\"hdot\":5116.99,\"ldot\":5065.89,\"turnover\":1376274374},\"INX\":{\"name\":\"\\u7eb3\\u65af\\u8fbe\\u514b\",\"date\":\"2015-12-30 06:40:00\",\"curdot\":2078.36,\"rate\":1.06,\"growth\":21.86,\"startdot\":2060.54,\"closedot\":2056.5,\"hdot\":2081.56,\"ldot\":2060.54,\"turnover\":93380000},\"HSI\":{\"name\":\"\\u6052\\u751f\\u6307\\u6570\",\"date\":\"2015\\/12\\/30 14:58\",\"curdot\":21922.66,\"rate\":-0.35,\"growth\":-76.96,\"startdot\":22094.59,\"closedot\":21999.62,\"hdot\":22114.56,\"ldot\":21870.75,\"turnover\":34430663,\"52hdot\":28588.52,\"52ldot\":20368.12}}}}";
//                    BaiduStockResponse responseObj = mapper.readValue(reponse, BaiduStockResponse.class);
//                    System.out.println(Thread.currentThread().getName() + ": " + responseObj.getErrMsg());
//                    //save(responseObj);
//                } catch (Exception e) {
//                    throw new WidgetsInternalServerErrorException("invalid format json");
//                }
//            }
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

    private String invokeRawBaiduAPI(String codes) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", baiduApiKey);
        HttpEntity entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://apis.baidu.com/apistore/stockservice/stock")
                                                        .queryParam("stockid", codes)
                                                        .queryParam("list", "1");
        ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private BaiduStockResponse invokeBaiduAPI(String code) {
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

    private void save(BaiduStockResponse respose) throws ParseException {
        List<BaiduStockResponse.StockInfo> stockInfoList = respose.getRetData().getStockinfo();
        for(BaiduStockResponse.StockInfo info: stockInfoList) {
            if(info.getDate() != null && info.getTime() != null && TIME_ZONE_MAP.get(info.getCode().substring(0, 2)) != null) {
                String dateTime = info.getDate() + " " + info.getTime() + " " + TIME_ZONE_MAP.get(info.getCode().substring(0, 2));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                Date date = dateFormat.parse(dateTime);
                stockDao.insert(info.getName(), info.getCode(), (int) (info.getCurrentPrice() * 100), date.getTime(), (byte) 1);
            }
        }
    }

    private void saveCache(String response) {
        stockCache.setKey(response);
    }
}
