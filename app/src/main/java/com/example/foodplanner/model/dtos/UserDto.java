package com.example.foodplanner.model.dtos;

public class UserDto
{
    String uId;
    String name;
    String phone;
    String img;
    String email;
    String password;

    public UserDto() {
    }

    public UserDto(String uId, String name, String phone, String img, String email, String password) {
        this.uId = uId;
        this.name = name;
        this.phone = phone;
        this.img = img;
        this.email = email;
        this.password = password;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
