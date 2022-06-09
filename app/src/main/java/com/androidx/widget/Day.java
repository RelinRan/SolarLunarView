package com.androidx.widget;

public class Day {

    //阳历
    private String solar;
    //农历
    private String lunar;
    //是否是节日
    private boolean isFestival;
    //节日名称
    private String festivalName;
    //坐标值
    private float solarX = 0F;
    private float solarY = 0F;
    //坐标值
    private float lunarX = 0F;
    private float lunarY = 0F;
    //坐标范围
    private float minCoordinates[];
    private float maxCoordinates[];
    //宽高
    private float width;
    private float height;
    //阳历字体大小
    private float solarTextSize;
    //农历字体大小
    private float lunarTextSize;
    //阳历字体颜色
    private int solarTextColor;
    private int selectSolarTextColor;
    //农历字体颜色
    private int lunarTextColor;
    private int selectLunarTextColor;
    //节日字体颜色
    private int festivalTextColor;
    //是选中
    private boolean isSelect;
    //选中颜色
    private int selectColor;
    //Item位置
    private int position[];

    public String getSolar() {
        return solar;
    }

    public int getValue() {
        if (solar == null || solar.length() == 0) {
            return 0;
        }
        return Integer.parseInt(solar);
    }

    public void setSolar(String solar) {
        this.solar = solar;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public boolean isFestival() {
        return isFestival;
    }

    public void setFestival(boolean festival) {
        isFestival = festival;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }


    public float getSolarX() {
        return solarX;
    }

    public void setSolarX(float solarX) {
        this.solarX = solarX;
    }

    public float getSolarY() {
        return solarY;
    }

    public void setSolarY(float solarY) {
        this.solarY = solarY;
    }

    public float getLunarX() {
        return lunarX;
    }

    public void setLunarX(float lunarX) {
        this.lunarX = lunarX;
    }

    public float getLunarY() {
        return lunarY;
    }

    public void setLunarY(float lunarY) {
        this.lunarY = lunarY;
    }

    public float[] getMinCoordinates() {
        return minCoordinates;
    }

    public void setMinCoordinates(float[] minCoordinates) {
        this.minCoordinates = minCoordinates;
    }

    public float[] getMaxCoordinates() {
        return maxCoordinates;
    }

    public void setMaxCoordinates(float[] maxCoordinates) {
        this.maxCoordinates = maxCoordinates;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSolarTextSize() {
        return solarTextSize;
    }

    public void setSolarTextSize(float solarTextSize) {
        this.solarTextSize = solarTextSize;
    }

    public float getLunarTextSize() {
        return lunarTextSize;
    }

    public void setLunarTextSize(float lunarTextSize) {
        this.lunarTextSize = lunarTextSize;
    }

    public int getSolarTextColor() {
        return solarTextColor;
    }

    public void setSolarTextColor(int solarTextColor) {
        this.solarTextColor = solarTextColor;
    }

    public int getLunarTextColor() {
        return lunarTextColor;
    }

    public void setLunarTextColor(int lunarTextColor) {
        this.lunarTextColor = lunarTextColor;
    }

    public int getFestivalTextColor() {
        return festivalTextColor;
    }

    public void setFestivalTextColor(int festivalTextColor) {
        this.festivalTextColor = festivalTextColor;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }


    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getSelectSolarTextColor() {
        return selectSolarTextColor;
    }

    public void setSelectSolarTextColor(int selectSolarTextColor) {
        this.selectSolarTextColor = selectSolarTextColor;
    }

    public int getSelectLunarTextColor() {
        return selectLunarTextColor;
    }

    public void setSelectLunarTextColor(int selectLunarTextColor) {
        this.selectLunarTextColor = selectLunarTextColor;
    }
}
