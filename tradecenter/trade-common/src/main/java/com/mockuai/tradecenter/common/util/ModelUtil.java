package com.mockuai.tradecenter.common.util;


import com.mockuai.tradecenter.common.domain.*;


public class ModelUtil {

    public static WealthAccountUidDTO parseWealthAccountUid(String wealthAccountUid){
        if(wealthAccountUid == null){
            return null;
        }

        String[] strs = wealthAccountUid.split("_");
        if (strs.length != 2) {
            return null;
        }

        WealthAccountUidDTO wealthAccountUidDTO = new WealthAccountUidDTO();

        long userId = Long.parseLong(strs[0]);
        long wealthAccountId = Long.parseLong(strs[1]);
        wealthAccountUidDTO.setUserId(userId);
        wealthAccountUidDTO.setWealthAccountId(wealthAccountId);
        return wealthAccountUidDTO;
    }

    public static CouponUidDTO parseCouponUid(String couponUid){
        if(couponUid == null){
            return null;
        }

        String[] strs = couponUid.split("_");
        if (strs.length != 2) {
            return null;
        }

        CouponUidDTO couponUidDTO = new CouponUidDTO();

        long userId = Long.parseLong(strs[0]);
        long couponId = Long.parseLong(strs[1]);
        couponUidDTO.setCouponId(couponId);
        couponUidDTO.setUserId(userId);

        return couponUidDTO;
    }

    public static CartItemUidDTO parseCartItemUid(String cartItemUid) {
        if(cartItemUid == null){
            return null;
        }

        String[] strs = cartItemUid.split("_");
        if (strs.length != 2) {
            return null;
        }

        CartItemUidDTO cartItemUidDTO = new CartItemUidDTO();

        long userId = Long.parseLong(strs[0]);
        long cartItemId = Long.parseLong(strs[1]);
        cartItemUidDTO.setUserId(userId);
        cartItemUidDTO.setCartItemId(cartItemId);

        return cartItemUidDTO;
    }

    public static SkuUidDTO parseSkuUid(String skuUid) {
        if(skuUid == null){
            return null;
        }

        String[] strs = skuUid.split("_");
        if (strs.length != 2) {
            return null;
        }

        SkuUidDTO skuUidDTO = new SkuUidDTO();

        long sellerId = Long.parseLong(strs[0]);
        long skuId = Long.parseLong(strs[1]);
        skuUidDTO.setSellerId(Long.valueOf(sellerId));
        skuUidDTO.setSkuId(Long.valueOf(skuId));

        return skuUidDTO;
    }

    public static ConsigneeUidDTO parseConsigneeUid(String consigneeUid) {
        if(consigneeUid == null){
            return null;
        }

        String[] strs = consigneeUid.split("_");
        if (strs.length != 2) {
            return null;
        }

        ConsigneeUidDTO consigneeUidDTO = new ConsigneeUidDTO();
        long userId = Long.parseLong(strs[0]);
        long consigneeId = Long.parseLong(strs[1]);
        consigneeUidDTO.setUserId(Long.valueOf(userId));
        consigneeUidDTO.setConsigneeId(Long.valueOf(consigneeId));

        return consigneeUidDTO;
    }

    public static String genOrderUid(long sellerId, long userId, long orderId) {
        return "" + sellerId + "_" + userId + "_" + orderId;
    }

    public static OrderUidDTO parseOrderUid(String orderUid) {
        if(orderUid == null){
            return null;
        }

        String[] strs = orderUid.split("_");
        if (strs.length != 3) {
            return null;
        }

        OrderUidDTO orderUidDTO = new OrderUidDTO();
        long sellerId = Long.parseLong(strs[0]);
        long buyerId = Long.parseLong(strs[1]);
        long orderId = Long.parseLong(strs[2]);

        orderUidDTO.setSellerId(Long.valueOf(sellerId));
        orderUidDTO.setUserId(Long.valueOf(buyerId));
        orderUidDTO.setOrderId(Long.valueOf(orderId));
        return orderUidDTO;
    }
    
    public static StoreUidDTO parseStoreUid(String storeUid){
    	 if(storeUid == null){
             return null;
         }

         String[] strs = storeUid.split("_");
         if (strs.length != 2) {
             return null;
         }

         StoreUidDTO storeUidDTO = new StoreUidDTO();
         long userId = Long.parseLong(strs[0]);
         long storeId = Long.parseLong(strs[1]);
         storeUidDTO.setUserId(Long.valueOf(userId));
         storeUidDTO.setStoreId(storeId);

         return storeUidDTO;
    }
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.util.ModelUtil
 * JD-Core Version:    0.6.2
 */