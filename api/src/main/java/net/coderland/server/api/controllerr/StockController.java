package net.coderland.server.api.controllerr;

import net.coderland.server.api.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by zhangxin on 15/12/23.
 */
@Controller
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
}
