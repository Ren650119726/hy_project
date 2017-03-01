package hotName;

import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HotNameManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/23.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class HotNameCount {
    @Resource
    private HotNameManager hotNameManager;

    @Test
    public void testCount() throws ItemException {
//        Long count = hotNameManager.hotNameCount();
//        System.out.println(count);
        HotNameDTO hotNameDTO = hotNameManager.getHotNameByName("云");
        System.out.println("123"+hotNameDTO.getHotName());

    }
}
