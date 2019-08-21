package com.uestc.nowcoder.wenda.model;

public class User {
    // 用户id
    private int id;
    // 用户名
    private String name;
    // 密码
    private String password;
    // 密码的附加字段，与password一起被MD5加密后存储到数据库，为了安全着想，数据库不能保存明文密码
    private String salt;
    // 用户头像的url
    private String headUrl;

    public User() {

    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
