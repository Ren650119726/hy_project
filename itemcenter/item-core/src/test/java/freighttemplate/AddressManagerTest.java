package freighttemplate;

import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.core.manager.AddressManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/10/27.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddressManagerTest {

    @Resource
    private AddressManager addressManager;



    @Test
    public void test01(){

        AddressDTO addressDTO = addressManager.getAddress(1000L, 100L, "dsdddddddddddd");

        assertThat(addressDTO,is(new AddressDTO()));

    }
}
