package com.mockuai.rainbowcenter.map.ready;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by yeliming on 16/4/14.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class MappingReady {

    private static final String BIZ_CODE = "haihuan";
    private static final String TYPE = "edb";

    private static final Integer BRAND_START_ID = 100;
    private static final Integer SUPPLIER_START_ID = 11;

    private static final Integer SELLER_ID = 86541;

    private static final String BRANDS = "A2\n" +
            "AHC\n" +
            "AlpineSilk\n" +
            "altapharma\n" +
            "always\n" +
            "Ambrosoli\n" +
            "Aquafresh\n" +
            "Bambix\n" +
            "beautyfriends\n" +
            "Bickiepegs\n" +
            "BioIsland\n" +
            "Blackmores\n" +
            "bonjela\n" +
            "Boots\n" +
            "BornFree\n" +
            "BRIGHTHOPE\n" +
            "Britax\n" +
            "CANMAKE\n" +
            "caprilac\n" +
            "CRINABLE\n" +
            "CuCuTe\n" +
            "DALLACOSTA\n" +
            "DDrops\n" +
            "Deonatulle\n" +
            "DHC\n" +
            "D'MUG\n" +
            "DokkanAburaDas\n" +
            "EarthMama\n" +
            "Easiyo\n" +
            "Ego\n" +
            "ELMEX\n" +
            "EMSA\n" +
            "Eskimo-3\n" +
            "Fafa\n" +
            "Farex\n" +
            "Femfresh\n" +
            "Feroglobin\n" +
            "Forencos\n" +
            "Galatine\n" +
            "gallia\n" +
            "GIFTHair\n" +
            "GMP\n" +
            "GNC\n" +
            "Goat\n" +
            "HappyBaby\n" +
            "Healtheries\n" +
            "HealthyCare\n" +
            "HollandBarrett\n" +
            "Holle\n" +
            "HONEYSTICKS\n" +
            "HUMWELL\n" +
            "Ivory\n" +
            "keepmycare\n" +
            "Kissme\n" +
            "KOSE\n" +
            "Lactrase\n" +
            "LADYSpeedStick\n" +
            "Lansinoh\n" +
            "LKIMEX\n" +
            "LOSHI\n" +
            "Lucas\n" +
            "lululun\n" +
            "Maker\n" +
            "mamachi\n" +
            "ManettiRoberts\n" +
            "Marvis\n" +
            "Merino\n" +
            "Monster\n" +
            "MorningFresh\n" +
            "MRKEAT\n" +
            "MYCOOK\n" +
            "Nannycare\n" +
            "NatureMade\n" +
            "Nature'sCare\n" +
            "Naturie\n" +
            "Nellie\n" +
            "NELSONS\n" +
            "NENE\n" +
            "Neocell\n" +
            "Neutral\n" +
            "NISHIKI\n" +
            "Nordic\n" +
            "NUK\n" +
            "NUROFEN\n" +
            "OMI\n" +
            "Onec\n" +
            "OptibacProbiotics\n" +
            "Organicstory\n" +
            "Osteocare\n" +
            "Pampers\n" +
            "Petitfee\n" +
            "PhytoTree\n" +
            "Pororo\n" +
            "Pregnacare\n" +
            "Purepawpaw\n" +
            "Rapunzel\n" +
            "RECARO\n" +
            "ReinPlatz\n" +
            "REPEL\n" +
            "Reveur\n" +
            "RolandEsprincess\n" +
            "Rosette\n" +
            "Sambucol\n" +
            "SANA\n" +
            "Sanhelios\n" +
            "SCALABO\n" +
            "SNP\n" +
            "Sudocrem\n" +
            "SunRype\n" +
            "Swisse\n" +
            "TangleTeezer\n" +
            "Tonymoly\n" +
            "Toocoolforschool\n" +
            "Trunki\n" +
            "VAPE\n" +
            "vegefru\n" +
            "Weleda\n" +
            "Wellkid\n" +
            "WMF\n" +
            "阿柔玛\n" +
            "艾尔邦尼\n" +
            "艾拉厨房\n" +
            "艾维诺\n" +
            "爱茉莉\n" +
            "爱他美\n" +
            "安耐晒\n" +
            "奥蓓尔\n" +
            "奥伯伦\n" +
            "澳莉娅\n" +
            "芭妮兰\n" +
            "白元\n" +
            "宝比珊\n" +
            "宝莹\n" +
            "保宁\n" +
            "贝德玛\n" +
            "贝拉米\n" +
            "贝力\n" +
            "贝娜婷\n" +
            "贝亲\n" +
            "贝印\n" +
            "比那氏\n" +
            "碧唇\n" +
            "碧然德\n" +
            "碧柔\n" +
            "博朗\n" +
            "布朗博士\n" +
            "大岛椿\n" +
            "大王\n" +
            "大象\n" +
            "袋鼠\n" +
            "德国凯斯乐\n" +
            "德运\n" +
            "地球最好\n" +
            "东方宝石\n" +
            "法国巴隆之花\n" +
            "法国波赫马奴芳达庄园\n" +
            "法国福莱士\n" +
            "法国皮特\n" +
            "法国特纳城堡\n" +
            "菲拉思德\n" +
            "冈本\n" +
            "格纳通\n" +
            "贵爱娘\n" +
            "哈罗闪\n" +
            "汉高施华蔻\n" +
            "汉诺金\n" +
            "好感觉\n" +
            "好奇\n" +
            "皓乐齿\n" +
            "和光堂\n" +
            "贺本清\n" +
            "红印\n" +
            "瑚玛娜\n" +
            "虎牌\n" +
            "花王\n" +
            "惠润\n" +
            "惠氏\n" +
            "肌美精\n" +
            "吉娜\n" +
            "加州宝宝\n" +
            "佳思敏\n" +
            "嘉宝\n" +
            "嘉娜宝SIMPRO\n" +
            "箭牌\n" +
            "洁诺\n" +
            "锦缎水\n" +
            "近江兄弟\n" +
            "精致\n" +
            "凯芮纳\n" +
            "康萃乐\n" +
            "康维他\n" +
            "可莱丝\n" +
            "可瑞康\n" +
            "克罗爱\n" +
            "拉杜蓝乔\n" +
            "乐敦\n" +
            "乐高\n" +
            "乐康膏\n" +
            "丽贝乐\n" +
            "丽得姿\n" +
            "柳屋\n" +
            "露诗\n" +
            "马油\n" +
            "曼丹\n" +
            "玫瑰锅\n" +
            "美乐鹊伏特\n" +
            "美林\n" +
            "美容棒\n" +
            "美时\n" +
            "美思满\n" +
            "美素\n" +
            "美体小铺\n" +
            "谜尚\n" +
            "妙思乐\n" +
            "明色\n" +
            "摩瑞\n" +
            "妮飘\n" +
            "牛栏\n" +
            "牛乳石碱\n" +
            "纽崔莱\n" +
            "挪威小鱼\n" +
            "欧树\n" +
            "帕玛氏\n" +
            "普丽普莱\n" +
            "巧虎\n" +
            "日本\n" +
            "睿嫣\n" +
            "三次元\n" +
            "三洋/Dacco\n" +
            "山本汉方\n" +
            "膳魔师\n" +
            "生生草\n" +
            "盛田屋\n" +
            "狮王\n" +
            "施巴\n" +
            "树之惠本铺\n" +
            "双心\n" +
            "丝华芙\n" +
            "斯塔迪尼\n" +
            "苏菲\n" +
            "索菲雅\n" +
            "特福芬\n" +
            "铁元\n" +
            "童年时光\n" +
            "伍吉\n" +
            "西班牙里奥哈柏格威\n" +
            "喜宝\n" +
            "相模\n" +
            "象印\n" +
            "小林制药\n" +
            "小田\n" +
            "新溪岛\n" +
            "玄米青汁\n" +
            "雅漾\n" +
            "伊奈\n" +
            "伊莎贝尔\n" +
            "伊思\n" +
            "印度拉茶\n" +
            "尤妮佳\n" +
            "尤妮佳卫生巾\n" +
            "尤妮佳纸尿裤\n" +
            "佑天兰\n" +
            "悦诗风吟\n" +
            "智利宝佳丽\n" +
            "智利宝佳丽梅洛\n" +
            "资生堂\n" +
            "自然晨露\n" +
            "自然之宝";

    private static final String SUPPLIER = "自营仓\n" +
            "粮油\n" +
            "海豚\n" +
            "中柏\n" +
            "嘻呗";

    private static final Integer USER_ID = 86541;

    @Test
    public void brandCreateTest() throws Exception {
        String[] brandArray = BRANDS.split("\\n");
        System.out.println("supplier count = " + brandArray.length);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `seller_brand` (`biz_code`, `delete_mark`, `gmt_created`, `gmt_modified`, `status`, `brand_name`, `brand_en_name`, `category_class`, `logo`, `brand_desc`)\n");
        sql.append("VALUES ");
        StringBuilder valueBody = new StringBuilder();
        for (String brand : brandArray) {
            valueBody.append(",\n\t('" + BIZ_CODE + "', 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', NULL, '" + brand + "', NULL, '其他', '', '')");
        }
        sql.append(valueBody.toString().substring(1)).append(";");
        FileOutputStream fos = new FileOutputStream("./target/brands.sql");
        fos.write(sql.toString().getBytes("utf-8"));
        fos.close();
    }

    @Test
    public void brandMappingTest() throws Exception {
        Integer brandId = BRAND_START_ID;
        String[] brandArray = BRANDS.split("\\n");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `sys_field_mapping` (`field_name`, `out_value`, `out_value_type`, `value`, `value_type`,`unique_sys`, `biz_code`, `type`, `gmt_created`, `gmt_modified`, `delete_mark`)\n");
        sql.append("VALUES ");
        StringBuilder valueBody = new StringBuilder();
        for (String brand : brandArray) {
            valueBody.append(",\n\t( 'itemBrand', '" + brand + "', 1, " + brandId + ", 3, '" + DigestUtils.md5Hex("itemBrand" + brand + brandId + BIZ_CODE + TYPE) + "', '" + BIZ_CODE + "', '" + TYPE + "', '1970-01-01 00:00:00', '1970-01-01 00:00:00', 0)");
            brandId++;
        }
        sql.append(valueBody.toString().substring(1)).append(";");
        System.out.println(sql.toString());

        FileOutputStream fos = new FileOutputStream("./target/itemBrand.sql");
        fos.write(sql.toString().getBytes("utf-8"));
        fos.close();
    }

    @Test
    public void supplierTest() throws Exception {
        String[] supplierArray = SUPPLIER.split("\n");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `supplier` (`name`, `contact_name`, `contact_phone`, `level`, `type`, `seller_id`, `gmt_created`, `gmt_modified`, `biz_code`, `delete_mark`)\n");
        sql.append("VALUES ");
        StringBuilder valueBody = new StringBuilder();
        for (String supplier : supplierArray) {
            valueBody.append(",\n\t('" + supplier + "', '" + supplier + "', '1234567890', '1', 1, " + USER_ID + ", '1970-01-01 00:00:00', '1970-01-01 00:00:00', '" + BIZ_CODE + "', 0)");
        }
        sql.append(valueBody.substring(1)).append(";");
        System.out.println(sql.toString());

        FileOutputStream fos = new FileOutputStream("./target/supplier.sql");
        fos.write(sql.toString().getBytes());
        fos.close();

    }

    @Test
    public void supplierMappingTest() throws Exception {
        Integer supplierId = SUPPLIER_START_ID;
        String[] supplierArray = SUPPLIER.split("\\n");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `sys_field_mapping` (`field_name`, `out_value`, `out_value_type`, `value`, `value_type`,`unique_sys`, `biz_code`, `type`, `gmt_created`, `gmt_modified`, `delete_mark`)\n");
        sql.append("VALUES ");
        StringBuilder valueBody = new StringBuilder();
        int i = 1;
        for (String supplier : supplierArray) {
            valueBody.append(",\n\t( 'supplier', '" + i++ + "', 1, " + supplierId + ", 3, '" + DigestUtils.md5Hex("supplier" + supplier + supplierId + BIZ_CODE + TYPE) + "', '" + BIZ_CODE + "', '" + TYPE + "', now(), now(), 0)");
            supplierId++;
        }
        sql.append(valueBody.toString().substring(1)).append(";");
        System.out.println(sql.toString());

        FileOutputStream fos = new FileOutputStream("./target/supplierMapping.sql");
        fos.write(sql.toString().getBytes("utf-8"));
        fos.close();
    }

    @Test
    public void storageMappingTest() throws Exception {
        Integer supplierId = SUPPLIER_START_ID;
        String[] supplierArray = SUPPLIER.split("\\n");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `sys_field_mapping` (`field_name`, `out_value`, `out_value_type`, `value`, `value_type`,`unique_sys`, `biz_code`, `type`, `gmt_created`, `gmt_modified`, `delete_mark`)\n");
        sql.append("VALUES ");
        StringBuilder valueBody = new StringBuilder();
        int i = 1;
        for (String supplier : supplierArray) {
            valueBody.append(",\n\t( 'storage', '" + i++ + "', 1, " + supplierId + ", 3, '" + DigestUtils.md5Hex("storage" + supplier + supplierId + BIZ_CODE + TYPE) + "', '" + BIZ_CODE + "', '" + TYPE + "', now(), now(), 0)");
            supplierId++;
        }
        sql.append(valueBody.toString().substring(1)).append(";");
        System.out.println(sql.toString());

        FileOutputStream fos = new FileOutputStream("./target/storageMapping.sql");
        fos.write(sql.toString().getBytes("utf-8"));
        fos.close();
    }

    @Test
    public void test1() throws Exception {
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream("/Users/Leo/Downloads/brand.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(poifsFileSystem);
        HSSFSheet sheet = wb.getSheetAt(0);
        int contentRowNum = sheet.getLastRowNum();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `sys_field_mapping` (`field_name`, `out_value`, `out_value_type`, `value`, `value_type`,`unique_sys`, `biz_code`, `type`, `gmt_created`, `gmt_modified`, `delete_mark`)\n");
        sql.append("VALUES ");
        StringBuilder valueBody = new StringBuilder();
        for (int i = 1; i <= contentRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            Integer id = new Double(row.getCell(0).getNumericCellValue()).intValue();
            String brandName = row.getCell(2).getStringCellValue();
            valueBody.append(",\n\t( 'itemBrand', '" + brandName + "', 1, " + id + ", 3, '" + DigestUtils.md5Hex("itemBrand" + brandName + brandName + BIZ_CODE + TYPE) + "', '" + BIZ_CODE + "', '" + TYPE + "', '1970-01-01 00:00:00', '1970-01-01 00:00:00', 0)");
        }
        sql.append(valueBody.substring(1)).append(";");
        System.out.println(sql.toString());
    }


}
