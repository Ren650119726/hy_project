package itemcategory;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;

import java.util.List;

/**
 * Created by yindingyu on 16/3/14.
 */
public class CategoryTmpls {

    static final String DEFAULT_IMAGE_URL = "http://img.mockuai.com/images/201512/25/10/20151225101521227.png";

    public static ItemCategoryTmplDTO cate1(String cateName, String imageUrl, List<ItemCategoryTmplDTO> itemCategoryTmplDTOList) {

        ItemCategoryTmplDTO itemCategoryTmplDTO = new ItemCategoryTmplDTO();
        itemCategoryTmplDTO.setCateLevel(1);
        itemCategoryTmplDTO.setCateName(cateName);
        itemCategoryTmplDTO.setImageUrl(imageUrl);
        itemCategoryTmplDTO.setParentId(0L);
        itemCategoryTmplDTO.setTopId(0L);
        itemCategoryTmplDTO.setSubCategorys(itemCategoryTmplDTOList);

        return itemCategoryTmplDTO;
    }

    public static ItemCategoryTmplDTO cate1(String cateName, String imageUrl) {

        ItemCategoryTmplDTO itemCategoryTmplDTO = new ItemCategoryTmplDTO();
        itemCategoryTmplDTO.setCateLevel(1);
        itemCategoryTmplDTO.setCateName(cateName);
        itemCategoryTmplDTO.setImageUrl(imageUrl);

        return itemCategoryTmplDTO;
    }

    public static ItemCategoryTmplDTO cate1(String cateName) {

        ItemCategoryTmplDTO itemCategoryTmplDTO = new ItemCategoryTmplDTO();
        itemCategoryTmplDTO.setCateLevel(1);
        itemCategoryTmplDTO.setCateName(cateName);
        itemCategoryTmplDTO.setImageUrl(DEFAULT_IMAGE_URL);
        return itemCategoryTmplDTO;
    }

    public static ItemCategoryTmplDTO cate2(String cateName, String imageUrl) {

        ItemCategoryTmplDTO itemCategoryTmplDTO = new ItemCategoryTmplDTO();
        itemCategoryTmplDTO.setCateLevel(2);
        itemCategoryTmplDTO.setCateName(cateName);
        itemCategoryTmplDTO.setImageUrl(imageUrl);
        return itemCategoryTmplDTO;
    }

    public static ItemCategoryTmplDTO cate2(String cateName) {

        ItemCategoryTmplDTO itemCategoryTmplDTO = new ItemCategoryTmplDTO();
        itemCategoryTmplDTO.setCateLevel(2);
        itemCategoryTmplDTO.setCateName(cateName);
        itemCategoryTmplDTO.setImageUrl(DEFAULT_IMAGE_URL);
        return itemCategoryTmplDTO;
    }
}
