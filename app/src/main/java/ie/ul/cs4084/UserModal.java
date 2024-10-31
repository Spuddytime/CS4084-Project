package ie.ul.cs4084;

import android.media.Image;

public class UserModal {
    private String name;
    private int age;
    private String bio;
    private int imgId;

    public int getImgId(){
        return imgId;
    }

    public void setImgId(int imgId){
        this.imgId = imgId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getBio(){
        return bio;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public UserModal(String name, int age, String bio/*, int imgId*/){
        this.name = name;
        this.age = age;
        this.bio = bio;
       // this.imgId = imgId;
    }

}
