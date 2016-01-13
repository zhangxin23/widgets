package net.coderland.server.api.controller;

import net.coderland.server.api.model.response.FollowsResponse;
import net.coderland.server.api.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by zhangxin on 15/12/23.
 */
@Controller(value = "stockController")
@RequestMapping("stock")
public class StockController {

    @Resource(name = "stockService")
    private StockService stockService;

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
}
