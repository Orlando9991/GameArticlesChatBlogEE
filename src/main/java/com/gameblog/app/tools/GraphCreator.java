/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.tools;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author orlan
 */
@RequestScoped
public abstract class GraphCreator {
   public static LineChartModel createLinearGraph(String title, String subtitle, List<Object> values, List<String> labels){
       LineChartModel linerGraphModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Posts");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(1);
        
        
        data.addChartDataSet(dataSet);       
        data.setLabels(labels);
        

        //Options
        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);
        Title t = new Title();
        t.setDisplay(true);
        t.setText(title);
        options.setTitle(t);

        Title st = new Title();
        st.setDisplay(true);
        st.setText(subtitle);
        options.setSubtitle(st);

        linerGraphModel.setOptions(options);
        linerGraphModel.setData(data);
        
        return linerGraphModel;
   }
   
   public static DonutChartModel createDonutGraph(Integer numberOfSections, List<Number> values, List<String> labels){
        DonutChartModel donutModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        options.setMaintainAspectRatio(false);
        donutModel.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        for (int i = 0; i < numberOfSections; i++) {
            int randomNumberR = (int)((Math.random())*255);
            int randomNumberG = (int)((Math.random())*255);
            int randomNumberB = (int)((Math.random())*255); 
            System.out.println(randomNumberR + " " + randomNumberG + " "+ randomNumberB);
            bgColors.add("rgb("+randomNumberR+","+randomNumberG+", "+randomNumberB+")");
        }
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        donutModel.setData(data);
        return donutModel;
   }
    
    
    
}
