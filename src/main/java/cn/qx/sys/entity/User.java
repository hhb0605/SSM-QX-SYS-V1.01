package cn.qx.sys.entity;

import java.io.Serializable;

/**
 * 用户实体类
 * @author Satone
 * @date 2019年2月20日下午9:23:00
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3426828648882890403L;
    private long id; //编号
    private String username; //用户名
    private String nickname; //昵称
    private String password; //密码
    private String salt; //盐
    private String email; //邮箱
    private String avatar; //头像
 // 0禁用，1启用
    private Integer valid = 1;
    private Integer deptId;

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    private String checkPass; //用于旧密码校验的属性

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "SysUser [id=" + id + ", username=" + username + ", nickname=" + nickname + ", password=" + password
                + ", salt=" + salt + ", email=" + email + ", avatar=" + avatar + ", valid=" + valid + ", deptId="
                + deptId + ", checkPass=" + checkPass + "]";
    }


}
