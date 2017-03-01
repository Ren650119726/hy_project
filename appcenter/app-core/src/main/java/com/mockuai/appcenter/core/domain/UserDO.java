package com.mockuai.appcenter.core.domain;

/**
 * Created by zengzhangqiang on 4/6/16.
 */
public class UserDO {
    private Long id;
    private String name;
    private String password;
    private Integer level;

    public UserDO(String name, String password, int level){
        this.name = name;
        this.password = password;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
