import com.mockuai.dts.core.util.ExcelUtil;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by luliang on 15/7/6.
 */
public class ExcelTest {

    @Test
    public void testWriteExcel() {
        String fileName = UUID.randomUUID().toString();
        File file = new File("/tmp/" + fileName + ".xls");
        String sheetName = "商品表";
        // 测试商品;
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemName("婴儿毛巾444");
        itemDTO.setSellerId(100L);
        itemDTO.setItemBrandId(99L);
        itemDTO.setMarketPrice(33L);
        itemDTO.setPromotionPrice(22L);
        itemDTO.setWirelessPrice(11L);
        itemDTO.setItemDesc("详情");
        itemDTO.setItemType(99);
        itemDTO.setIconUrl("iconUrl");
        itemDTO.setCategoryId(99L);
        itemDTO.setItemBrief("itemBrief");
        itemDTO.setSaleBegin(new Date());
        itemDTO.setSaleEnd(new Date());
        itemDTO.setSaleMinNum(99);
        itemDTO.setSaleMinNum(99);
        itemDTO.setItemStatus(99);
        itemDTO.setCornerIconId(99L);
        itemDTO.setCostPrice(99L);
        itemDTO.setDeliveryType(99);
        itemDTO.setBizCode("bizCode");
        List<ItemDTO> list = new ArrayList<ItemDTO>();
        list.add(itemDTO);
        try {
            ExcelUtil.wirteExcel(file, sheetName, list, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ItemDTO itemDTO2 = new ItemDTO();
        itemDTO2.setItemName("婴儿毛巾666");
        itemDTO2.setSellerId(100L);
        itemDTO2.setItemBrandId(99L);
        itemDTO2.setMarketPrice(33L);
        itemDTO2.setPromotionPrice(22L);
        itemDTO2.setWirelessPrice(11L);
        itemDTO2.setItemDesc("详情");
        itemDTO2.setItemType(99);
        itemDTO2.setIconUrl("iconUrl");
        itemDTO2.setCategoryId(99L);
        itemDTO2.setItemBrief("itemBrief");
        itemDTO2.setSaleBegin(new Date());
        itemDTO2.setSaleEnd(new Date());
        itemDTO2.setSaleMinNum(99);
        itemDTO2.setSaleMinNum(99);
        itemDTO2.setItemStatus(99);
        itemDTO2.setCornerIconId(99L);
        itemDTO2.setCostPrice(99L);
        itemDTO2.setDeliveryType(99);
        itemDTO2.setBizCode("bizCode");
        List<ItemDTO> list2 = new ArrayList<ItemDTO>();
        list2.add(itemDTO2);
        try {
            ExcelUtil.wirteExcel(file, sheetName, list2, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // load data;
        try   {
            Workbook book  =  Workbook.getWorkbook(file);
            //  获得第一个工作表对象
            Sheet sheet  =  book.getSheet(0);
            int lines = sheet.getRows();
            int cols = sheet.getColumns();
            System.out.println("=================================");
            for(int i = 0; i < lines; i++) {
//                System.out.println("第" + i + "行纪录");
                for(int j = 0; j < cols; j++) {
                    Cell cell1  =  sheet.getCell(j, i);
                    System.out.print(cell1.getContents() + "  ");
                }
                System.out.println();
            }
            book.close();
        }   catch  (Exception e)  {
            System.out.println(e);
        }
    }
}
