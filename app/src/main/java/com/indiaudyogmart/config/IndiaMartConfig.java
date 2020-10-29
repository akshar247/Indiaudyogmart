package com.indiaudyogmart.config;

public class IndiaMartConfig {
    public static String APP_VERSION;


    public static String BASEURL = "https://daddyscode.co.in/ium-api2/public/";
    public static String WEBURL = BASEURL+"api/v1/";
    //public static String WEBURL = "https://daddyscode.co.in/ium-api/public/api/v1/";
    public static String signup_user = "signup_user";
    public static String login = "login";
    public static String store_wyw="mart/store_wyw";
    public static String store_wyw1="store_wyw";
    public static String search_product_wyw="mart/product/search_product_wyw?name=";
    public static String forgot_password = "forgot_password";
    public static String resend_forgot_password = "resend_forgot_password";
    public static String forgot_password_validate_otp = "forgot_password_validate_otp";
    public static String forgot_password_change_password = "forgot_password_change_password";
    public static String get_home_page_data = "get_home_page_data";
    public static String get_home_page_by_seller = "get_home_page_by_seller";
    public static String my_package="package/get_packages";
    public static String left_lead_by_seller="mart/lead/left_lead_by_seller";
    public static String help_and_support = "mart/help_and_support";
    public static String get_orders_by_seller = "mart/order/get_orders_by_seller";
    public static String get_review_products = "mart/review/get_review_products";
    public static String get_transaction_data = "transaction/get_transaction_data";
    public static String get_leads_by_seller = "mart/lead/get_leads_by_seller";
    public static String get_wyw_data = "get_wyw_data";
    public static String about_us="mart/about_us";
    public static String get_important_suppliers_list = "mart/order/get_important_suppliers_list";
    public static String order_image_path= BASEURL+"uploads/mart_gallery_images/";
    public static String review_image_path= BASEURL+"uploads/mart_gallery_images/";
    public static String lead_image_path= BASEURL+"/uploads/mart_gallery_images/";
    public static String purchase_history = "purchase-history";
    public static String purchase_history_details = "purchase-history-details";
    public static String service_list = "service_list";
    public static String service_category = "service_category";
    public static String product_details = "product_details";
    public static String add_product_rating = "add_product_rating";
    public static String offers = "offers";
    public static String offer_product_details = "offer_product_details";
    public static String add_to_cart = "add_to_cart";
    public static String user_details = "user_details";
    public static String remove_product = "remove_product";
    public static String user_cart_list = "user_cart_list";
    public static String order_store = "order/store";
    public static String getCategory_Mart="mart/category/get_category";
    public static String category_path= BASEURL+"/uploads/mart_category/";
    public static String getCategoryChild_Mart= "mart/category/get_subcategory/";
    public static String package_purchase= "package/package-purchase/";
    public static String get_lead_detail_by_seller= "mart/lead/get_lead_detail_by_seller/";
    public static String get_products_details= "mart/product/get_products_details/";
    public static String get_quote_detail_by_customer= "mart/quote/get_quote_detail_by_customer/";
    public static String getProduct_details= "mart/product/get_products_by_category/";
    public static String search_product= "mart/product/search_product?name=";
    public static String product_path= BASEURL+"uploads/mart_gallery_images/";
    public static  String user_image=BASEURL+"mart/seller/images/";
    public static String chat_list="chat/get_chat_list";
    public static String chat_list_detail="chat/get_chat_details/";
    public static String send_chat_message="chat/send_chat_message";
    public static String add_quote_by_customer="mart/quote/add_quote_by_customer/";
    public static String send_quote_by_seller="mart/lead/send_quote_by_seller/";
    public static String send_quote_request_by_customer="mart/quote/send_quote_request_by_customer/";
    public static String send_lead_request_by_seller="mart/lead/send_lead_request_by_seller/";
    public static String home_category_image=BASEURL+"/uploads/home_category/";
    public static String home_category=BASEURL+"/uploads/mart_category/";//http://daddyscode.co.in/ium-api2/public/uploads/mart_category
    public static String profile_pic_path=BASEURL+"mart/seller/images/";
    public static String get_quotes_list_by_customer="mart/quote/get_quotes_list_by_customer";
    public static String get_cities="city/get_cities";
    public static String get_all_brand="mart/brand/get_all_brand";
    public static String get_all_seller_type="mart/get_all_seller_type";

    public static String sendkycrequest="bankdetail/request";

    public static String get_products_by_seller = "mart/product/get_products_by_seller/";


    public static String feedback="mart/feedback/add_feedback";
    public static String update_wyw="update_wyw/";
    public static String get_user_data="user/get_user_data";
    public static String update_user_details="user/update_user_details";
    public static String add_company_details="user/add_company_details";

    public static String signup_seller="signup_seller";
    public static String app_addproduct=IndiaMartConfig.BASEURL+"app_addproduct/";
    public static String app_mart_product_edit=IndiaMartConfig.BASEURL+"app_mart_product_edit/";
    public static String show_kyc_history="show-kyc-history";


    /*
    *Banner

http://daddyscode.co.in/ium-api/public/uploads/banner

Trending category,premium collection
http://daddyscode.co.in/ium-api/public/uploads/home_category

Deal of the day
http://daddyscode.co.in/ium-api/public/uploads/home_productdeal

Mart Category
http://daddyscode.co.in/ium-api/public/uploads/mart_category

Mart Product
http://daddyscode.co.in/ium-api/public/uploads/mart_gallery_images

Mall Category
http://daddyscode.co.in/ium-api/public/uploads/


Mall Product
http://daddyscode.co.in/ium-api/public/uploads/gallery_images
*
* src="http://daddyscode.co.in/ium-api/public/mart/seller/images/user-profile.png"
    * */
}