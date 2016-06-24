package me.hsky.androidshop.data;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class ResponseCart {
    public int result;
    public int category_total;
    public int total_price;
    public List<Cart> cart;

    public class Cart{
        public String category_name;
        public List<Shops> shops;

        public class Shops{
            public String shop_name;
            public String shop_desc;
            public String shop_price;
            public String shop_standard;
            public int shop_number;
        }
    }
}
