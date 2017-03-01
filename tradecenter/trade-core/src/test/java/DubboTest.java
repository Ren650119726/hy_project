


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * Dubbo服务启动
 * 
 * @author Kerwin
 * @date 2013-10-24 下午08:52:35
 * 
 */
public class DubboTest {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        
        System.out.println("dubbo service");

        System.out.println
        ("stared...");
        System.in.read();
    }
    
    
    
    /**
     * 处理订单优惠信息，并生成订单对象
     *
     * @param orderDTO
     * @param itemMap
     * @param itemSkuMap
     * @return
     */
    
}
