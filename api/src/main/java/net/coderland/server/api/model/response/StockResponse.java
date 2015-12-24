package net.coderland.server.api.model.response;

import net.coderland.server.core.model.pojo.Stock;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
public class StockResponse {
    private Integer total;
    private Long since;
    private Long util;
    private List<Stock> data;

    public StockResponse() {
    }

    public StockResponse(Integer total, Long since, Long util, List<Stock> data) {
        this.total = total;
        this.since = since;
        this.util = util;
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getSince() {
        return since;
    }

    public void setSince(Long since) {
        this.since = since;
    }

    public Long getUtil() {
        return util;
    }

    public void setUtil(Long util) {
        this.util = util;
    }

    public List<Stock> getData() {
        return data;
    }

    public void setData(List<Stock> data) {
        this.data = data;
    }
}
