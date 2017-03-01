package util;

/**
 * Created by yindingyu on 16/1/28.
 */
public  enum AppKeyEnum {

    MOCKUAI_DEMO("mockuai_demo", "4f5508d72d9d78c9242bf1c867ac1063"),

    HAODADA("haodada", "34b73d7ccc7d9784bf76277e3c4421c3"),

    YANGDONGXI("yangdongxi", "eb1b83c003bb6f2a938a5815e47e77f7"),

    HAIYUN("hanshu","6562b5ddf0aed2aad8fe471ce2a2c8a0");



    private String appKey;
    private String bizCode;

    AppKeyEnum(String bizCode, String appKey) {
        this.appKey = appKey;
        this.bizCode = bizCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public  String bizCode() {
        return bizCode;
    }
}