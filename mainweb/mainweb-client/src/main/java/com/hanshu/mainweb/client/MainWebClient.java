package com.hanshu.mainweb.client;

import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PageSeckillDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;

import java.util.List;

/**
 * Created by jiguansheng on 16/5/20.
 */
public interface MainWebClient {

    Response<IndexPageDTO> addPage(IndexPageDTO indexPageDTO, String appKey);

    Response<IndexPageDTO> getPage(Long id, String appKey);

    Response<Boolean> updatePage(IndexPageDTO indexPageDTO, String appKey);

    Response<Boolean> deletePage(Long id,String appKey);

    Response<List<IndexPageDTO>>  queryPageNames(String appKey);

    Response<PublishPageDTO> addPublishPage(Long pageId, String appKey);
//删除并且发布
    Response<Void> deleteThenPublish(Long pageId,String appKey);

    //添加秒杀块 然后再发布一下
    Response<Void> addSeckillThenPublish(List<PageSeckillDTO> pageSeckillDTOs,Boolean isMainPage, String appKey) ;
    //删除秒杀块 然后再发布一下
    Response<Void> deleteSeckillThenPublish(PageSeckillDTO pageSeckillDTO,Boolean isMainPage ,String appKey) ;
    //页面列表展示
    Response<List<IndexPageDTO>> getPageList(PageQTO pageQTO,String appKey);
    //撤销页面
    Response<Void> cancelPage(Long id,String appKey);
    //预览页面
    Response<PublishPageDTO> PreviewPage(Long id,String appKey);

}
