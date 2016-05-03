package me.hsky.androidshop.data;

/**
 * Created by Administrator on 2016/5/2.
 */
public class Shop {
    private String name;
    private String price;
    private int img;
    private String oldPrice;

    public Shop(String aName, String aSpeak, int aIcon) {
        this.name = aName;
        this.price = aSpeak;
        this.img = aIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }
}
