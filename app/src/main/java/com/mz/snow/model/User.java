package com.mz.snow.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/12/7.
 */

public class User extends BmobUser {
    //            <string name="gender">性别</string>
//            <string name="birthday">生日</string>
//            <string name="constellation">星座</string>
//            <string name="age">年龄</string>
    private String nickname;            //昵称
    private String gender;             //性别
    private String birthday;          //生日
    private String constellation;    //星座
    private String age;             //年龄


    private String touXiangPath;  //头像路径
    private String backgroudPath;  //头像路径
    private int integral;  //积分


    public String getBackgroudPath() {
        return backgroudPath;
    }

    public void setBackgroudPath(String backgroudPath) {
        this.backgroudPath = backgroudPath;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getTouXiangPath() {
        return touXiangPath;
    }

    public void setTouXiangPath(String touXiangPath) {
        this.touXiangPath = touXiangPath;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", constellation='" + constellation + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
