package itemcategory;

import javax.annotation.Resource;

import com.google.common.collect.ImmutableList;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;
import com.mockuai.itemcenter.common.util.PriceUtil;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryTmplManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddItemCategoryTest {
    @Resource
    private ItemService itemService;


    @Resource
    private ItemCategoryTmplManager itemCategoryTmplManager;


    /**
     * 正常插入
     */
    @Test
    public void test001() {
        Request request = new BaseRequest();
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setCateLevel(2);
        itemCategoryDTO.setCateName("书");
        itemCategoryDTO.setParentId(330L);
        itemCategoryDTO.setSort(1);
        itemCategoryDTO.setTopId(1L);
        request.setParam("itemCategoryDTO", itemCategoryDTO);
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * itemCategoryDTO为空
     */
    public void test002() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * 父ID不合法
     */
    public void test003() {
        Request request = new BaseRequest();
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setCateLevel(2);
        itemCategoryDTO.setCateName("书");
        itemCategoryDTO.setParentId(9999L);
        itemCategoryDTO.setSort(1);
        itemCategoryDTO.setTopId(1L);
        request.setParam("itemCategoryDTO", itemCategoryDTO);
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * 类目名称为空
     */
    public void test004() {
        Request request = new BaseRequest();
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setCateLevel(2);
        itemCategoryDTO.setParentId(0L);
        itemCategoryDTO.setSort(1);
        itemCategoryDTO.setTopId(1L);
        request.setParam("itemCategoryDTO", itemCategoryDTO);
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * 层级为空
     */
    public void test005() {
        Request request = new BaseRequest();
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setCateName("书");
        itemCategoryDTO.setParentId(0L);
        itemCategoryDTO.setSort(1);
        itemCategoryDTO.setTopId(1L);
        request.setParam("itemCategoryDTO", itemCategoryDTO);
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * 父ID为空
     */
    public void test006() {
        Request request = new BaseRequest();
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setCateLevel(2);
        itemCategoryDTO.setCateName("书");
        itemCategoryDTO.setSort(1);
        itemCategoryDTO.setTopId(1L);
        request.setParam("itemCategoryDTO", itemCategoryDTO);
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * 顶级ID为空
     */
    public void test007() {
        Request request = new BaseRequest();
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setCateLevel(2);
        itemCategoryDTO.setCateName("书");
        itemCategoryDTO.setParentId(0L);
        itemCategoryDTO.setSort(1);
        request.setParam("itemCategoryDTO", itemCategoryDTO);
        request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    @Test
    public void test008() {

        List<ItemCategoryTmplDTO> list = ImmutableList.of(
                CategoryTmpls.cate1("食品", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("水果"), CategoryTmpls.cate2("零食"), CategoryTmpls.cate2("生鲜"), CategoryTmpls.cate2("进口食品"), CategoryTmpls.cate2("粮油米面"), CategoryTmpls.cate2("茶饮"), CategoryTmpls.cate2("酒水"), CategoryTmpls.cate2("保健滋补")
                        )
                ),
                CategoryTmpls.cate1("女人", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("运动户外"), CategoryTmpls.cate2("上衣"), CategoryTmpls.cate2("裤子"), CategoryTmpls.cate2("裙装"), CategoryTmpls.cate2("鞋靴"), CategoryTmpls.cate2("箱包"), CategoryTmpls.cate2("配饰"), CategoryTmpls.cate2("内衣袜子")
                        )
                ),
                CategoryTmpls.cate1("美妆", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("美容护肤"), CategoryTmpls.cate2("香氛精油"), CategoryTmpls.cate2("彩妆"), CategoryTmpls.cate2("美发护理"), CategoryTmpls.cate2("面膜"), CategoryTmpls.cate2("BB霜"), CategoryTmpls.cate2("清洁"), CategoryTmpls.cate2("美白")
                        )
                ),
                CategoryTmpls.cate1("亲子", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("宝宝配饰"), CategoryTmpls.cate2("宝宝喂养"), CategoryTmpls.cate2("宝宝玩乐"), CategoryTmpls.cate2("宝宝洗护"), CategoryTmpls.cate2("穿衣/寝具"), CategoryTmpls.cate2("童车汽座"), CategoryTmpls.cate2("学习文具图书"), CategoryTmpls.cate2("妈妈专区")
                        )
                ),
                CategoryTmpls.cate1("男人", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("配饰"), CategoryTmpls.cate2("上衣"), CategoryTmpls.cate2("裤子"), CategoryTmpls.cate2("鞋靴"), CategoryTmpls.cate2("运动户外"), CategoryTmpls.cate2("箱包"), CategoryTmpls.cate2("内衣袜子")
                        )
                ),
                CategoryTmpls.cate1("数码家电", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("家用电器"), CategoryTmpls.cate2("数码配件"), CategoryTmpls.cate2("手机相机"), CategoryTmpls.cate2("电脑办公")
                        )
                ),
                CategoryTmpls.cate1("居家生活", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("床上用品"), CategoryTmpls.cate2("日用百货"), CategoryTmpls.cate2("家居饰品"), CategoryTmpls.cate2("居家布艺"), CategoryTmpls.cate2("收纳整理"), CategoryTmpls.cate2("厨房用具"), CategoryTmpls.cate2("餐饮用具"), CategoryTmpls.cate2("清洁工具")
                        )
                ),
                CategoryTmpls.cate1("生活娱乐", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("餐饮外卖"), CategoryTmpls.cate2("丽人健身"), CategoryTmpls.cate2("休闲娱乐"), CategoryTmpls.cate2("酒店客栈"), CategoryTmpls.cate2("家政服务"), CategoryTmpls.cate2("票务卡券")
                        )
                ),
                CategoryTmpls.cate1("其他", CategoryTmpls.DEFAULT_IMAGE_URL, ImmutableList.of(
                                CategoryTmpls.cate2("婚庆摄影"), CategoryTmpls.cate2("文娱用品"), CategoryTmpls.cate2("宠物用品"), CategoryTmpls.cate2("家装建材"), CategoryTmpls.cate2("教育培训"), CategoryTmpls.cate2("未分类")
                        )
                )
        );

        itemCategoryTmplManager.addItemCategoryTmpl(list);
    }

    @Test
    public void test009() {

        ItemCategoryTmplQTO qto = new ItemCategoryTmplQTO();
        qto.setCateLevel(2);


        try {
            List<ItemCategoryTmplDTO> list = itemCategoryTmplManager.queryItemCategoryTmpl(qto);

        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
}
