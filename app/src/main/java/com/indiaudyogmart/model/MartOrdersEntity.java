package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MartOrdersEntity {
    @Expose
    @SerializedName("cust_contactno")
    public String custContactno;
    @Expose
    @SerializedName("cust_email")
    public String custEmail;
    @Expose
    @SerializedName("cust_name")
    public String custName;
    @Expose
    @SerializedName("rfqp_unit")
    public int rfqpUnit;
    @Expose
    @SerializedName("rfqp_status")
    public int rfqpStatus;
    @Expose
    @SerializedName("rfq_id")
    public int rfqId;
    @Expose
    @SerializedName("rfqp_issample")
    public String rfqpIssample;
    @Expose
    @SerializedName("rfqp_product_image")
    public String rfqpProductImage;
    @Expose
    @SerializedName("rfqp_priceperqty")
    public int rfqpPriceperqty;
    @Expose
    @SerializedName("rfqp_qty")
    public int rfqpQty;
    @Expose
    @SerializedName("rfqp_description")
    public String rfqpDescription;
    @Expose
    @SerializedName("rfqp_name")
    public String rfqpName;
    @Expose
    @SerializedName("product_id")
    public int productId;
    @Expose
    @SerializedName("product_is_avaliable")
    public String productIsAvaliable;
    @Expose
    @SerializedName("category_id")
    public int categoryId;
    @Expose
    @SerializedName("order_delivery_date")
    public String orderDeliveryDate;
    @Expose
    @SerializedName("order_date")
    public String orderDate;
    @Expose
    @SerializedName("remark")
    public String remark;
    @Expose
    @SerializedName("payment_status")
    public String paymentStatus;
    @Expose
    @SerializedName("order_status")
    public String orderStatus;
    @Expose
    @SerializedName("payment_total")
    public int paymentTotal;
    @Expose
    @SerializedName("grand_total")
    public int grandTotal;
    @Expose
    @SerializedName("payment_type")
    public String paymentType;
    @Expose
    @SerializedName("mo_seller_id")
    public int moSellerId;
    @Expose
    @SerializedName("customer_id")
    public int customerId;
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("updated_at")
    public String updatedAt;
    @Expose
    @SerializedName("created_at")
    public String createdAt;
    @Expose
    @SerializedName("od_delivery_date")
    public String odDeliveryDate;
    @Expose
    @SerializedName("od_status")
    public String odStatus;
    @Expose
    @SerializedName("total_amount")
    public int totalAmount;
    @Expose
    @SerializedName("price")
    public int price;
    @Expose
    @SerializedName("qty")
    public int qty;
    @Expose
    @SerializedName("history_send_quote_request_id")
    public int historySendQuoteRequestId;
    @Expose
    @SerializedName("rfq_product_id")
    public int rfqProductId;
    @Expose
    @SerializedName("order_id")
    public int orderId;
    @Expose
    @SerializedName("order_details_id")
    public int orderDetailsId;
}
