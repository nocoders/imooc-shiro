package com.imooc.pojo;


public class RolesPermissions {

  private long id;
  private String roleName;
  private String permission;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }


  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public RolesPermissions() {
  }

  public RolesPermissions(long id, String roleName, String permission) {
    this.id = id;
    this.roleName = roleName;
    this.permission = permission;
  }

  @Override
  public String toString() {
    return "RolesPermissions{" +
            "id=" + id +
            ", roleName='" + roleName + '\'' +
            ", permission='" + permission + '\'' +
            '}';
  }
}
