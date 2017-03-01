import com.mockuai.common.uils.staticpage.StaticPage;
import org.junit.Test;

/**
 * Created by luliang on 15/7/23.
 */
public class DateTest {

    @Test
    public void testDateTransfer() {
        Double d = 100d;
        d = d* 0.1;
        Integer oneGain = 5;
        long  result =  ( Math.round(d*5/100));
        System.out.print(result);
    }
}
