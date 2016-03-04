package net.coderland.server.api.controller;

import net.coderland.server.api.aop.LogPointcut;
import net.coderland.server.api.model.response.FollowsResponse;
import net.coderland.server.api.model.response.StockResponse;
import net.coderland.server.api.service.StockService;


import net.coderland.server.common.exception.WidgetsBadRequestException;
import net.coderland.server.core.model.pojo.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxin on 15/12/23.
 */
@Controller(value = "stockController")
@RequestMapping("stock")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Resource(name = "stockService")
    private StockService stockService;

    @Resource(name = "logPointcut")
    private LogPointcut logPointcut;

    @RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getStocksByName(@RequestParam("name") String name, @RequestParam("since") Long since,
                                 @RequestParam("util") Long util, @RequestParam("limit") Integer limit) {
        return stockService.getStocksByName(name, since, util, limit);
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getStocksByCode(@RequestParam("code") String code, @RequestParam("since") Long since,
                                  @RequestParam("util") Long util, @RequestParam("limit") Integer limit) {
        return stockService.getStocksByCode(code, since, util, limit);
    }

    @RequestMapping(value = "/follows/{user}", method = RequestMethod.GET/*, produces = "application/json;charset=UTF-8"*/)
    @ResponseBody
    public Object getFollows(@PathVariable("user") String user) {
        return stockService.getFollows(user);
    }

    @RequestMapping(value = "/follows/{user}/{code}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void setFollows(@PathVariable("user") String user, @PathVariable("code") String code) {
        stockService.setFollows(user, code);
    }

    @RequestMapping(value = "/follows/{user}/{code}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delFollows(@PathVariable("user") String user, @PathVariable("code") String code) {
        stockService.delFollows(user, code);
    }

    @RequestMapping(value = "/view/{user}/data.json", method = RequestMethod.GET)
    public ModelAndView getView(@PathVariable("user") String user) {
        ModelAndView mav = new ModelAndView("ooo");
        FollowsResponse followsResponse = (FollowsResponse)stockService.getFollows(user);
        mav.addObject("follows", followsResponse);
        return mav;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String testInterceptor() {
        logger.info("Welcome, this is test page.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Before returning view page");

        return String.valueOf(System.currentTimeMillis());
    }

    @RequestMapping(value = "/test_interceptor", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object testInterceptorStocks(@RequestParam("id") int id) throws WidgetsBadRequestException {
        logger.info("===BEGIN===");

        List<Stock> stockList = new ArrayList<>();
        stockList.add(newStock(1, "first", "100000", 100, System.currentTimeMillis(), (byte)1));
        stockList.add(newStock(2, "second", "200000", 200, System.currentTimeMillis(), (byte)1));
        stockList.add(newStock(3, "third", "300000", 300, System.currentTimeMillis(), (byte)1));
        StockResponse stockResponse = new StockResponse(stockList.size(), 0L, 100L, stockList);

        logger.info("===END===");

        //test exception
        if(id == 1)
            throw new WidgetsBadRequestException();

        logPointcut.stockPointcut(stockResponse);

        return stockResponse;
    }

    private Stock newStock(int id, String name, String code, int price, Long ctime, byte unit) {
        Stock stock = new Stock();
        stock.setId(id);
        stock.setName(name);
        stock.setCode(code);
        stock.setPrice(price);
        stock.setCtime(ctime);
        stock.setUnit(unit);
        return stock;
    }
}
