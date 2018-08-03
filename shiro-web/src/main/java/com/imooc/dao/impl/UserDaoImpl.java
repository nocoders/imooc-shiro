package com.imooc.dao.impl;

import com.imooc.dao.UserDao;
import com.imooc.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
//    用于查询
    @Resource
    private JdbcTemplate jdbcTemplate;
//    查询用户
    public User getuserByUsername(String username) {
        String sql="select username,password from users where username =?";
//        传入username
        List<User>list=jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
//    查询角色
    public List<String> getRolesByUsername(String username) {
        String sql="select role_name from user_roles where username=?";

        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString("role_name");
                    }
                }
        );
    }
//  查询权限，根据用户名
    public List<String> getpermissionByUsername(String username) {
        String sql="select permission from roles_permissions where role_name in (select role_name from user_roles where username = ?)";

        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString("permission");
                    }
                }
        );
    }
}
