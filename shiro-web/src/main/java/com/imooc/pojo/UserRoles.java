package com.imooc.pojo;


public class UserRoles {

  private long id;
  private String username;
  private String roleName;


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


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public UserRoles() {
  }

  public UserRoles(long id, String username, String roleName) {
    this.id = id;
    this.username = username;
    this.roleName = roleName;
  }

  @Override
  public String toString() {
    return "UserRoles{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", roleName='" + roleName + '\'' +
            '}';
  }
}
