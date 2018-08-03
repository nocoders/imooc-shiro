package com.imooc.pojo;


public class Role {

  private long id;
  private String name;
  private String note;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Role() {
  }

  public Role(long id, String name, String note) {
    this.id = id;
    this.name = name;
    this.note = note;
  }

  @Override
  public String toString() {
    return "Role{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", note='" + note + '\'' +
            '}';
  }
}
