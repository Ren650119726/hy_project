package store;

import com.mockuai.shopcenter.core.domain.StoreDO;
import com.mockuai.shopcenter.core.manager.impl.StoreManagerImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/11/11.
 */

public class StoreSelectTest {

    /**
     * 配合断点测试精度，这里每个点距离增加0.000001km
     */
    @Test
    public void StoreSelectTest(){
        StoreManagerImpl storeManager = new StoreManagerImpl();

        List<StoreDO> storeDOList = new ArrayList<StoreDO>();

        double longitude = 116.413554d;
        double latitude = 39.911013d;

        double longitude1 = 120.046738d;
        double latitude1 = 30.290932d;

        for(int i=0;i<100;i++){
            StoreDO storeDO = new StoreDO();
            storeDO.setLongitude(Double.toString(longitude1+0.000001d*i));
            storeDO.setLatitude(Double.toString(latitude1 + 0.000001d * i));
            storeDO.setDeliveryRange(10);
            storeDOList.add(storeDO);
        }

        StoreDO storeDO = storeManager.getNearestStore(storeDOList, longitude, latitude);

        assertThat(storeDO,is(storeDOList.get(0)));


    }
}
