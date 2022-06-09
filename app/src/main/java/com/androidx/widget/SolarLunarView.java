package com.androidx.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 阳历农历日历View
 */
public class SolarLunarView extends View {

    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 日
     */
    private int day;
    /**
     * Item背景半径
     */
    private float dayRadius = dpToPx(20F);
    /**
     * 阳历字体大小
     */
    private float solarTextSize = dpToPx(14F);
    /**
     * 阳历字体颜色
     */
    private int solarTextColor = Color.BLACK;
    /**
     * 农历字体大小
     */
    private float lunarTextSize = dpToPx(10F);
    /**
     * 农历颜色
     */
    private int lunarTextColor = Color.GRAY;
    /**
     * 农历上间距
     */
    private float lunarMarginTop = dpToPx(3);
    /**
     * 标题栏字体大小
     */
    private float titleTextSize = dpToPx(14F);
    /**
     * 标题栏上间距
     */
    private float titleMarginTop = dpToPx(10);
    /**
     * 标题栏底部间距
     */
    private float titleMarginBottom = dpToPx(30);
    /**
     * 选中颜色
     */
    private int selectBackgroundColor = Color.parseColor("#D44AEB");
    /**
     * 阳历选中颜色
     */
    private int selectSolarTextColor = Color.WHITE;
    /**
     * 农历选中颜色
     */
    private int selectLunarTextColor = Color.WHITE;
    /**
     * 标题文字颜色
     */
    private int titleTextColor = Color.WHITE;
    /**
     * 渐变开始颜色
     */
    private int gradientStartColor = Color.parseColor("#F5CE74");
    /**
     * 渐变结束颜色
     */
    private int gradientEndColor = Color.parseColor("#F42850");
    /**
     * 工作日期标题背景颜色
     */
    private int titleBackgroundColor = Color.parseColor("#F5CE74");

    /**
     * 行数
     */
    private int row = 6;
    /**
     * 列数
     */
    private int column = 7;
    /**
     * 控件宽度
     */
    private float width;
    /**
     * 控件高度
     */
    private float height;
    /**
     * 第一天周几
     */
    private int firstDayWeek;
    /**
     * 月份总天数
     */
    private int monthDayTotal;
    /**
     * Item宽度
     */
    private float itemWidth;
    /**
     * Item高度
     */
    private float itemHeight;
    /**
     * 标题高度
     */
    private float titleHeight;
    /**
     * Item位置
     */
    private int[] selectPosition;
    /**
     * Item链表
     */
    private List<Day> items = new ArrayList<>();
    /**
     * 选中的天
     */
    private Day selectedDay;
    private long time;


    public SolarLunarView(Context context) {
        super(context);
        initAttributeSet(context, null);
    }

    public SolarLunarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    public SolarLunarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SolarLunarView);
            year = array.getInt(R.styleable.SolarLunarView_solarYear, year);
            month = array.getInt(R.styleable.SolarLunarView_solarMonth, month);
            day = array.getInt(R.styleable.SolarLunarView_solarDay, day);
            dayRadius = array.getDimension(R.styleable.SolarLunarView_dayRadius, dayRadius);
            solarTextColor = array.getColor(R.styleable.SolarLunarView_solarTextColor, solarTextColor);
            lunarTextSize = array.getDimension(R.styleable.SolarLunarView_lunarTextSize, lunarTextSize);
            lunarTextColor = array.getColor(R.styleable.SolarLunarView_lunarTextColor, lunarTextColor);
            lunarMarginTop = array.getDimension(R.styleable.SolarLunarView_LunarMarginTop, lunarMarginTop);
            titleTextSize = array.getDimension(R.styleable.SolarLunarView_titleTextSize, titleTextSize);
            titleMarginTop = array.getDimension(R.styleable.SolarLunarView_titleMarginTop, titleMarginTop);
            titleMarginBottom = array.getDimension(R.styleable.SolarLunarView_titleMarginBottom, titleMarginBottom);
            selectBackgroundColor = array.getColor(R.styleable.SolarLunarView_selectBackgroundColor, selectBackgroundColor);
            selectSolarTextColor = array.getColor(R.styleable.SolarLunarView_selectSolarTextColor, selectSolarTextColor);
            selectLunarTextColor = array.getColor(R.styleable.SolarLunarView_selectLunarTextColor, selectLunarTextColor);
            titleTextColor = array.getColor(R.styleable.SolarLunarView_titleTextColor, titleTextColor);
            gradientStartColor = array.getColor(R.styleable.SolarLunarView_gradientStartColor, gradientStartColor);
            gradientEndColor = array.getColor(R.styleable.SolarLunarView_gradientEndColor, gradientEndColor);
            titleBackgroundColor = array.getColor(R.styleable.SolarLunarView_titleBackgroundColor, titleBackgroundColor);
            array.recycle();
        }
        updateCalendar();
    }

    /**
     * 更新日历
     */
    private void updateCalendar() {
        Calendar calendar = Calendar.getInstance();
        if (year != 0) {
            calendar.set(Calendar.YEAR, year);
        }
        if (month != 0) {
            calendar.set(Calendar.MONTH, month - 1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (day != 0) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        monthDayTotal = Converter.monthOfLastDay(year, month);
        //大于周四6行
        row = firstDayWeek > 5 ? 6 : 5;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        titleHeight = titleMarginTop + dayRadius + titleMarginBottom;
        height = getMeasuredHeight() - titleHeight;
        itemWidth = width / column;
        itemHeight = height / row;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float upX = event.getX();
                float upY = event.getY();
                for (int i = 0; i < items.size(); i++) {
                    Day item = items.get(i);
                    float minX = item.getMinCoordinates()[0];
                    float maxX = item.getMaxCoordinates()[0];
                    float minY = item.getMinCoordinates()[1];
                    float maxY = item.getMaxCoordinates()[1];
                    long pass = System.currentTimeMillis() - time;
                    if (upX >= minX && upX <= maxX && upY >= minY && upY <= maxY && pass > 50) {
                        if (selectedDay != null && selectedDay.getSolar().equals(item.getSolar())) {
                            break;
                        }
                        selectPosition = item.getPosition();
                        if (listener != null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month - 1);
                            calendar.set(Calendar.DAY_OF_MONTH, item.getValue());
                            listener.onItemClickListener(this, calendar.getTime());
                        }
                        selectedDay = item;
                        time = System.currentTimeMillis();
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTitle(canvas);
        drawCalendar(canvas);
    }

    /**
     * 绘制日历标题
     */
    private void drawTitle(Canvas canvas) {
        drawTitleWeekItem(canvas, "日", itemWidth / 2);
        String workItems[] = new String[]{"一", "二", "三", "四", "五"};
        for (int i = 1; i <= workItems.length; i++) {
            drawTitleWorkItem(canvas, workItems[i - 1], itemWidth * (i + 1) - itemWidth / 2);
        }
        drawTitleWeekItem(canvas, "六", itemWidth * 7 - itemWidth / 2);
    }

    /**
     * @return 标题画笔
     */
    private Paint buildTitlePaint() {
        Paint titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setColor(titleTextColor);
        titlePaint.setTextSize(titleTextSize);
        return titlePaint;
    }

    /**
     * @return 渐变画笔
     */
    private Paint buildGradientPaint() {
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        int colors[] = new int[]{gradientStartColor, gradientEndColor};
        float positions[] = new float[]{0.2F, 0.8F};
        circlePaint.setShader(new LinearGradient(0, 0, 0, dayRadius * 2, colors, positions, Shader.TileMode.CLAMP));
        return circlePaint;
    }

    /**
     * 绘制周末
     *
     * @param canvas
     * @param text
     * @param offsetX
     */
    private void drawTitleWeekItem(Canvas canvas, String text, float offsetX) {
        Paint titlePaint = buildTitlePaint();
        Rect bounds = new Rect();
        titlePaint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawCircle(offsetX, dayRadius + titleMarginTop, dayRadius, buildGradientPaint());
        canvas.drawText(text, offsetX - bounds.centerX(), dayRadius - bounds.centerY() + titleMarginTop, titlePaint);
    }

    /**
     * 绘制周一到周五
     *
     * @param canvas
     * @param text
     * @param offsetX
     */
    private void drawTitleWorkItem(Canvas canvas, String text, float offsetX) {
        Paint titlePaint = buildTitlePaint();
        Rect bounds = new Rect();
        titlePaint.getTextBounds(text, 0, text.length(), bounds);
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(titleBackgroundColor);
        canvas.drawCircle(offsetX, dayRadius + titleMarginTop, dayRadius, circlePaint);
        canvas.drawText(text, offsetX - bounds.centerX(), dayRadius - bounds.centerY() + titleMarginTop, titlePaint);
    }

    /**
     * 绘制日历
     *
     * @param canvas
     */
    private void drawCalendar(Canvas canvas) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                //Item显示的日
                int itemDay = i * column + j - firstDayWeek + 2;
                //默认选中今天日期
                if (selectPosition == null && day == itemDay) {
                    selectPosition = new int[]{i, j};
                }
                //初始化Item
                Day item = new Day();
                item.setSolarTextColor(solarTextColor);
                item.setSolarTextSize(solarTextSize);
                item.setLunarTextColor(lunarTextColor);
                item.setLunarTextSize(lunarTextSize);
                item.setSelectColor(selectBackgroundColor);
                item.setSelectSolarTextColor(selectSolarTextColor);
                item.setSelectLunarTextColor(selectLunarTextColor);
                item.setPosition(new int[]{i, j});
                item.setSelect(false);
                //如果选中位置不为空就设置选中Item
                if (selectPosition != null) {
                    item.setSelect(item.getPosition()[0] == selectPosition[0] && item.getPosition()[1] == selectPosition[1] ? true : false);
                }
                if (j < (firstDayWeek - 1) && i == 0 || itemDay > monthDayTotal) {//其他月份不显示
                    item.setSolar("");
                    item.setLunar("");
                } else {//本月份的日期
                    //设置农历日期
                    item.setLunar(solarToLunar(itemDay));
                    //设置阳历日期
                    item.setSolar(String.valueOf(itemDay));
                    //设置阳历坐标
                    item.setSolarX((2 * j + 1) * itemWidth / 2 - measureText(item.getSolar(), solarTextSize)[0] / 2);
                    item.setSolarY((2 * i + 1) * itemHeight / 2 - measureText(item.getSolar(), solarTextSize)[1] + titleHeight);
                    //设置农历坐标
                    item.setLunarX((2 * j + 1) * itemWidth / 2 - measureText(item.getLunar(), lunarTextSize)[0] / 2);
                    item.setLunarY((2 * i + 1) * itemHeight / 2 - measureText(item.getSolar(), solarTextSize)[1] + measureText(item.getLunar(), lunarTextSize)[1] + titleHeight + lunarMarginTop);
                }
                //设置Item坐标范围
                item.setMinCoordinates(new float[]{item.getSolarX() - itemWidth / 2, item.getSolarY() - itemHeight / 2});
                item.setMaxCoordinates(new float[]{item.getSolarX() + itemWidth / 2, item.getSolarY() + itemHeight / 2});
                //绘制Item
                drawItem(canvas, item);
            }
        }
    }

    /**
     * 阳历天转农历天
     *
     * @param day
     * @return
     */
    private String solarToLunar(int day) {
        Converter.Solar solar = new Converter.Solar();
        solar.solarYear = year;
        solar.solarMonth = month;
        solar.solarDay = day;
        Converter.Lunar lunar = Converter.solarToLunar(solar);
        return Converter.solarToLunarDay(lunar.lunarDay);
    }

    /**
     * 测量字体大小
     *
     * @param text
     * @return
     */
    private float[] measureText(String text, float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return new float[]{bounds.width(), bounds.height()};
    }

    /**
     * 绘制Item
     *
     * @param canvas 画布
     * @param item   item
     */
    private void drawItem(Canvas canvas, Day item) {
        items.add(item);
        boolean isSelected = item.getLunar().length() != 0 && item.isSelect();
        //绘制圆形背景
        if (isSelected) {
            selectedDay = item;
            Paint paint = new Paint();
            paint.setColor(item.getSelectColor());
            canvas.drawCircle(item.getSolarX() + measureText(item.getSolar(), item.getSolarTextSize())[0] / 2, item.getSolarY() + lunarMarginTop, dayRadius, paint);
        }
        //阳历文字
        drawText(canvas, item.getSolarX(), item.getSolarY(), item.getSolar(), isSelected ? item.getSelectSolarTextColor() : item.getSolarTextColor(), item.getSolarTextSize());
        //农历文字
        String lunarText = item.getLunar();
        drawText(canvas, item.getLunarX(), item.getLunarY(), lunarText, isSelected ? item.getSelectLunarTextColor() : item.getLunarTextColor(), item.getLunarTextSize());
    }

    /**
     * 绘制文字
     *
     * @param canvas   画布
     * @param x        x坐标
     * @param y        y坐标
     * @param text     文字内容
     * @param color    文字颜色
     * @param textSize 文字大小
     */
    private float drawText(Canvas canvas, float x, float y, String text, int color, float textSize) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
        return paint.measureText(text);
    }

    /**
     * Get the screen of density
     *
     * @return
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * dp to px
     *
     * @param dp
     * @return
     */
    public static float dpToPx(float dp) {
        return dp * getScreenDensity();
    }

    /**
     * Item点击事件
     */
    public OnItemClickListener listener;

    /**
     * 设置Item点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClickListener(SolarLunarView view, Date time);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        updateCalendar();
        invalidate();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        updateCalendar();
        invalidate();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        updateCalendar();
        invalidate();
    }

    public float getDayRadius() {
        return dayRadius;
    }

    public void setDayRadius(float dayRadius) {
        this.dayRadius = dayRadius;
        invalidate();
    }

    public float getSolarTextSize() {
        return solarTextSize;
    }

    public void setSolarTextSize(float solarTextSize) {
        this.solarTextSize = solarTextSize;
        invalidate();
    }

    public int getSolarTextColor() {
        return solarTextColor;
    }

    public void setSolarTextColor(int solarTextColor) {
        this.solarTextColor = solarTextColor;
        invalidate();
    }

    public float getLunarTextSize() {
        return lunarTextSize;
    }

    public void setLunarTextSize(float lunarTextSize) {
        this.lunarTextSize = lunarTextSize;
        invalidate();
    }

    public int getLunarTextColor() {
        return lunarTextColor;
    }

    public void setLunarTextColor(int lunarTextColor) {
        this.lunarTextColor = lunarTextColor;
        invalidate();
    }

    public float getLunarMarginTop() {
        return lunarMarginTop;
    }

    public void setLunarMarginTop(float lunarMarginTop) {
        this.lunarMarginTop = lunarMarginTop;
        invalidate();
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
        invalidate();
    }

    public float getTitleMarginTop() {
        return titleMarginTop;
    }

    public void setTitleMarginTop(float titleMarginTop) {
        this.titleMarginTop = titleMarginTop;
        invalidate();
    }

    public float getTitleMarginBottom() {
        return titleMarginBottom;
    }

    public void setTitleMarginBottom(float titleMarginBottom) {
        this.titleMarginBottom = titleMarginBottom;
        invalidate();
    }

    public int getSelectBackgroundColor() {
        return selectBackgroundColor;
    }

    public void setSelectBackgroundColor(int selectBackgroundColor) {
        this.selectBackgroundColor = selectBackgroundColor;
        invalidate();
    }

    public int getSelectSolarTextColor() {
        return selectSolarTextColor;
    }

    public void setSelectSolarTextColor(int selectSolarTextColor) {
        this.selectSolarTextColor = selectSolarTextColor;
        invalidate();
    }

    public int getSelectLunarTextColor() {
        return selectLunarTextColor;
    }

    public void setSelectLunarTextColor(int selectLunarTextColor) {
        this.selectLunarTextColor = selectLunarTextColor;
        invalidate();
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        invalidate();
    }

    public int getGradientStartColor() {
        return gradientStartColor;
    }

    public void setGradientStartColor(int gradientStartColor) {
        this.gradientStartColor = gradientStartColor;
        invalidate();
    }

    public int getGradientEndColor() {
        return gradientEndColor;
    }

    public void setGradientEndColor(int gradientEndColor) {
        this.gradientEndColor = gradientEndColor;
        invalidate();
    }

    public int getTitleBackgroundColor() {
        return titleBackgroundColor;
    }

    public void setTitleBackgroundColor(int titleBackgroundColor) {
        this.titleBackgroundColor = titleBackgroundColor;
        invalidate();
    }

}