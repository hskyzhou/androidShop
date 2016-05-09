package me.hsky.androidshop.data;

/**
 * Created by Administrator on 2016/5/2.
 */
public class Shop {
    private String name;
    private String price;
    private int img;
    private String oldPrice;
    private String desc;
    private String unit; //单位
    private String standard; //规格

    public Shop(String aName, String aSpeak, int aIcon){
        this.name = aName;
        this.price = aSpeak;
        this.img = aIcon;
    }
    public Shop(String aName, String aSpeak, int aIcon, String desc, String unit, String standard) {
        this.name = aName;
        this.price = aSpeak;
        this.img = aIcon;
        this.desc = desc;
        this.unit = unit;
        this.standard = standard;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
