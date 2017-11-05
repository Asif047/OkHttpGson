package com.deliman_poc.rokomari.deliman_rokomari_poc;

/**
 * Created by DEVPC on 05/11/2017.
 */

public class ResponseApi {


    private Integer order_id;

    private String recipient;

    private String order_type;

    private String area;

    private String district;

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer orderId) {
        this.order_id = order_id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getOrderType() {
        return order_type;
    }

    public void setOrderType(String orderType) {
        this.order_type = order_type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
