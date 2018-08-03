package com.imooc.dao;

import com.imooc.pojo.User;

import java.util.List;

public interface UserDao {
    User getuserByUsername(String username);

    List<String> getRolesByUsername(String username);

    List<String> getpermissionByUsername(String username);
}
