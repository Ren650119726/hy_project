package com.mockuai.itemcenter.core.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * Created by yindingyu on 15/9/28.
 */
public class ItemObjectMapper extends  ObjectMapper{

    ItemObjectMapper(){
        super();
        super.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
        //super.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }


}
