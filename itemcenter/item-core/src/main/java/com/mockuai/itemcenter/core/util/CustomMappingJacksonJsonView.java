package com.mockuai.itemcenter.core.util;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * BUG:MappingJacksonJsonView返回 {model类名:{内容}}
 */
public class CustomMappingJacksonJsonView extends MappingJacksonJsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {
        Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
        if (result.size() == 1) {
            return result.values().iterator().next();
        } else {
            return result;
        }
    }

    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object value = filterModel(model);
        response.setContentType("text/plain; charset=GBK");
        response.getOutputStream().write(value.toString().getBytes());
        response.getOutputStream().flush();
    }


}
