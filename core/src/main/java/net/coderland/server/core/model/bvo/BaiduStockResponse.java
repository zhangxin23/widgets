package net.coderland.server.core.model.bvo;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
public class BaiduStockResponse {
    private Integer errNum; //返回错误码
    private String errMsg; //返回错误信息
    private StockData retData;

    public Integer getErrNum() {
        return errNum;
    }

    public void setErrNum(Integer errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public StockData getRetData() {
        return retData;
    }

    public void setRetData(StockData retData) {
        this.retData = retData;
    }

    public static class StockData {
        private List<StockInfo> stockinfo;
        private Market market;
        private KLineGraph klinegraph;

        public List<StockInfo> getStockinfo() {
            return stockinfo;
        }

        public void setStockinfo(List<StockInfo> stockinfo) {
            this.stockinfo = stockinfo;
        }

        public Market getMarket() {
            return market;
        }

        public void setMarket(Market market) {
            this.market = market;
        }

        public KLineGraph getKlinegraph() {
            return klinegraph;
        }

        public void setKlinegraph(KLineGraph klinegraph) {
            this.klinegraph = klinegraph;
        }
    }

    public static class StockInfo{
        private String name; //股票名称
        private String code; //股票代码，SZ指在深圳交易的股票
        private String date; //当前显示股票信息的日期
        private String time; //具体时间
        private Double OpenningPrice; //今日开盘价
        private Double closingPrice; //昨日收盘价
        private Double currentPrice; //当前价格
        private Double hPrice; //今日最高价
        private Double lPrice; //今日最低价
        private Double competitivePrice; //买一报价
        private Double auctionPrice; //卖一报价
        private Double totalNumber; //成交的股票数
        private Double turnover; //成交额，以元为单位
        private Integer buyOne; //买一
        private Double buyOnePrice; //买一价格
        private Integer buyTwo; //买二
        private Double buyTwoPrice; //买二价格
        private Integer buyThree; //买三
        private Double buyThreePrice; //买三价格
        private Integer buyFour; //买四
        private Double buyFourPrice; //买四价格
        private Integer buyFive; //买五
        private Double buyFivePrice; //买五价格
        private Integer sellOne; //卖一
        private Double sellOnePrice; //卖一价格
        private Integer sellTwo; //卖二
        private Double sellTwoPrice; //卖二价格
        private Integer sellThree; //卖三
        private Double sellThreePrice; //卖三价格
        private Integer sellFour; //卖四
        private Double sellFourPrice; //卖四价格
        private Integer sellFive; //卖五
        private Double sellFivePrice; //卖五价格

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Double getOpenningPrice() {
            return OpenningPrice;
        }

        public void setOpenningPrice(Double openningPrice) {
            OpenningPrice = openningPrice;
        }

        public Double getClosingPrice() {
            return closingPrice;
        }

        public void setClosingPrice(Double closingPrice) {
            this.closingPrice = closingPrice;
        }

        public Double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(Double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public Double gethPrice() {
            return hPrice;
        }

        public void sethPrice(Double hPrice) {
            this.hPrice = hPrice;
        }

        public Double getlPrice() {
            return lPrice;
        }

        public void setlPrice(Double lPrice) {
            this.lPrice = lPrice;
        }

        public Double getCompetitivePrice() {
            return competitivePrice;
        }

        public void setCompetitivePrice(Double competitivePrice) {
            this.competitivePrice = competitivePrice;
        }

        public Double getAuctionPrice() {
            return auctionPrice;
        }

        public void setAuctionPrice(Double auctionPrice) {
            this.auctionPrice = auctionPrice;
        }

        public Double getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(Double totalNumber) {
            this.totalNumber = totalNumber;
        }

        public Double getTurnover() {
            return turnover;
        }

        public void setTurnover(Double turnover) {
            this.turnover = turnover;
        }

        public Integer getBuyOne() {
            return buyOne;
        }

        public void setBuyOne(Integer buyOne) {
            this.buyOne = buyOne;
        }

        public Double getBuyOnePrice() {
            return buyOnePrice;
        }

        public void setBuyOnePrice(Double buyOnePrice) {
            this.buyOnePrice = buyOnePrice;
        }

        public Integer getBuyTwo() {
            return buyTwo;
        }

        public void setBuyTwo(Integer buyTwo) {
            this.buyTwo = buyTwo;
        }

        public Double getBuyTwoPrice() {
            return buyTwoPrice;
        }

        public void setBuyTwoPrice(Double buyTwoPrice) {
            this.buyTwoPrice = buyTwoPrice;
        }

        public Integer getBuyThree() {
            return buyThree;
        }

        public void setBuyThree(Integer buyThree) {
            this.buyThree = buyThree;
        }

        public Double getBuyThreePrice() {
            return buyThreePrice;
        }

        public void setBuyThreePrice(Double buyThreePrice) {
            this.buyThreePrice = buyThreePrice;
        }

        public Integer getBuyFour() {
            return buyFour;
        }

        public void setBuyFour(Integer buyFour) {
            this.buyFour = buyFour;
        }

        public Double getBuyFourPrice() {
            return buyFourPrice;
        }

        public void setBuyFourPrice(Double buyFourPrice) {
            this.buyFourPrice = buyFourPrice;
        }

        public Integer getBuyFive() {
            return buyFive;
        }

        public void setBuyFive(Integer buyFive) {
            this.buyFive = buyFive;
        }

        public Double getBuyFivePrice() {
            return buyFivePrice;
        }

        public void setBuyFivePrice(Double buyFivePrice) {
            this.buyFivePrice = buyFivePrice;
        }

        public Integer getSellOne() {
            return sellOne;
        }

        public void setSellOne(Integer sellOne) {
            this.sellOne = sellOne;
        }

        public Double getSellOnePrice() {
            return sellOnePrice;
        }

        public void setSellOnePrice(Double sellOnePrice) {
            this.sellOnePrice = sellOnePrice;
        }

        public Integer getSellTwo() {
            return sellTwo;
        }

        public void setSellTwo(Integer sellTwo) {
            this.sellTwo = sellTwo;
        }

        public Double getSellTwoPrice() {
            return sellTwoPrice;
        }

        public void setSellTwoPrice(Double sellTwoPrice) {
            this.sellTwoPrice = sellTwoPrice;
        }

        public Integer getSellThree() {
            return sellThree;
        }

        public void setSellThree(Integer sellThree) {
            this.sellThree = sellThree;
        }

        public Double getSellThreePrice() {
            return sellThreePrice;
        }

        public void setSellThreePrice(Double sellThreePrice) {
            this.sellThreePrice = sellThreePrice;
        }

        public Integer getSellFour() {
            return sellFour;
        }

        public void setSellFour(Integer sellFour) {
            this.sellFour = sellFour;
        }

        public Double getSellFourPrice() {
            return sellFourPrice;
        }

        public void setSellFourPrice(Double sellFourPrice) {
            this.sellFourPrice = sellFourPrice;
        }

        public Integer getSellFive() {
            return sellFive;
        }

        public void setSellFive(Integer sellFive) {
            this.sellFive = sellFive;
        }

        public Double getSellFivePrice() {
            return sellFivePrice;
        }

        public void setSellFivePrice(Double sellFivePrice) {
            this.sellFivePrice = sellFivePrice;
        }
    }

    public static class Market {
        private MarketInfo shanghai;
        private MarketInfo shenzhen;
        private MarketInfo DJI;
        private MarketInfo IXIC;
        private MarketInfo INX;
        private MarketInfo HSI;

        public MarketInfo getShanghai() {
            return shanghai;
        }

        public void setShanghai(MarketInfo shanghai) {
            this.shanghai = shanghai;
        }

        public MarketInfo getShenzhen() {
            return shenzhen;
        }

        public void setShenzhen(MarketInfo shenzhen) {
            this.shenzhen = shenzhen;
        }

        public MarketInfo getDJI() {
            return DJI;
        }

        public void setDJI(MarketInfo DJI) {
            this.DJI = DJI;
        }

        public MarketInfo getIXIC() {
            return IXIC;
        }

        public void setIXIC(MarketInfo IXIC) {
            this.IXIC = IXIC;
        }

        public MarketInfo getINX() {
            return INX;
        }

        public void setINX(MarketInfo INX) {
            this.INX = INX;
        }

        public MarketInfo getHSI() {
            return HSI;
        }

        public void setHSI(MarketInfo HSI) {
            this.HSI = HSI;
        }
    }

    public static class MarketInfo {
        private String name;
        private Double curdot; // 当前价格
        private Double curprice; //当前价格涨幅
        private Double rate; // 涨幅率
        private Integer dealnumber; //交易量，单位为手（一百股）
        private Integer turnover; //成交额，万元为单位

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getCurdot() {
            return curdot;
        }

        public void setCurdot(Double curdot) {
            this.curdot = curdot;
        }

        public Double getCurprice() {
            return curprice;
        }

        public void setCurprice(Double curprice) {
            this.curprice = curprice;
        }

        public Double getRate() {
            return rate;
        }

        public void setRate(Double rate) {
            this.rate = rate;
        }

        public Integer getDealnumber() {
            return dealnumber;
        }

        public void setDealnumber(Integer dealnumber) {
            this.dealnumber = dealnumber;
        }

        public Integer getTurnover() {
            return turnover;
        }

        public void setTurnover(Integer turnover) {
            this.turnover = turnover;
        }
    }

    public static class KLineGraph {
        private String minurl; //分时K线图
        private String dayurl; //日K线图
        private String weekurl; //周K线图
        private String monthurl; //月K线图

        public String getMinurl() {
            return minurl;
        }

        public void setMinurl(String minurl) {
            this.minurl = minurl;
        }

        public String getDayurl() {
            return dayurl;
        }

        public void setDayurl(String dayurl) {
            this.dayurl = dayurl;
        }

        public String getWeekurl() {
            return weekurl;
        }

        public void setWeekurl(String weekurl) {
            this.weekurl = weekurl;
        }

        public String getMonthurl() {
            return monthurl;
        }

        public void setMonthurl(String monthurl) {
            this.monthurl = monthurl;
        }
    }
}
