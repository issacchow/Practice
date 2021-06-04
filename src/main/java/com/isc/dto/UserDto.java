package com.isc.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserDto {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField
    private Integer age;
    @TableField
    private String name;
    @TableField
    private String password;
    @TableField
    private String address;
    @TableField
    private String phone;
    @TableField
    private String nickname;
    @TableField(value = "last_login")
    private Date lastLogin;

    @TableField(value = "create_at")
    private Date createAt;

}
