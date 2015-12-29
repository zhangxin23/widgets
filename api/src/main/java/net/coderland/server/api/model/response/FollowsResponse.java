package net.coderland.server.api.model.response;

import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-29
 */
public class FollowsResponse {
    private Integer total;
    private List<FollowsItem> data;

    public FollowsResponse() {

    }

    public FollowsResponse(Integer total, List<FollowsItem> data) {
        this.total = total;
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<FollowsItem> getData() {
        return data;
    }

    public void setData(List<FollowsItem> data) {
        this.data = data;
    }

    public static class FollowsItem {
        private String name;
        private String code;
        private Double price;

        public FollowsItem() {

        }

        public FollowsItem(String name, String code, Double price) {
            this.name = name;
            this.code = code;
            this.price = price;
        }

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

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}
