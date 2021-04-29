package com.example.hashtag.echarts;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Easing;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;

public class EchartOptionUtil {

    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("title");
        option.legend("hashtag");
        option.tooltip().trigger(Trigger.axis);
        //animation
        option.animationDuration(0);
        option.animationDurationUpdate(3000);
        option.animationEasing(Easing.linear);
        option.animationEasingUpdate(Easing.linear);

        Bar bar = new Bar();
        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);
        bar.data(yAxis);
        option.series(bar);
        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);





//        ValueAxis valueAxis = new ValueAxis();
//        option.yAxis(valueAxis);
//
//        CategoryAxis categorxAxis = new CategoryAxis();
//        categorxAxis.axisLine().onZero(false);
//        categorxAxis.boundaryGap(true);
//        categorxAxis.data(xAxis);
//        option.xAxis(categorxAxis);
//
//        Line line = new Line();
//        line.smooth(false).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
//        option.series(line);
        return option;
    }
}
