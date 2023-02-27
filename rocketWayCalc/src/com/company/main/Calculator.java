package com.company.main;

import java.util.ArrayList;
import java.util.Collections;

public class Calculator {

    private ArrayList<Double> xAxisValues = new ArrayList<>();
    private ArrayList<Double> yAxisValues = new ArrayList<>();

    Double r = 6371000.0; // Радиус Земли в метрах

    double scaleRatio;

    public FirstStage firstStage = new FirstStage();
    public SecondStage secondStage = new SecondStage();
    public ThirdStage thirdStage = new ThirdStage();

    public Calculator() {
        evaluateParameters();
        calculateTrajectory();
//        countYspeed();
    }

    private void evaluateParameters() {
        secondStage.setFirstStage(firstStage);
        thirdStage.setSecondStage(secondStage);

        firstStage.calculateFunction();
        secondStage.calculateFunction();
        thirdStage.calculateFunction();
    }

    private void calculateTrajectory() {
        scaleRatio = 0.001;
        xAxisValues.addAll(firstStage.getMovementXValues());
        xAxisValues.addAll(secondStage.getMovementXValues());
        xAxisValues.addAll(thirdStage.getMovementXValues());
        yAxisValues.addAll(firstStage.getMovementYValues());
        yAxisValues.addAll(secondStage.getMovementYValues());
        yAxisValues.addAll(thirdStage.getMovementYValues());
        curvatureCorrection();

        System.out.println("Высота полёта после отстыковки второй ступени с учётом поправки кривизны: " + String.format("%.0f", yAxisValues.get(firstStage.getTimeValues().size() + secondStage.getTimeValues().size())/1000) + " км");
        System.out.println("Конечная высота орбиты с учётом поправки кривизны: " + String.format("%.0f", yAxisValues.get(yAxisValues.size() - 1)/1000) + " км");
        System.out.println("Vmax1 = " + Collections.max(firstStage.speedYValues));
        System.out.println("Vmax2 = " + Collections.max(secondStage.speedYValues));
    }

    // Расчёт поправки кривизны
    private void curvatureCorrection() {
        for(int i = 0; i < xAxisValues.size(); i++) {
            Double h = r/(Math.cos(Math.atan(xAxisValues.get(i)/r))) - r;
            yAxisValues.set(i, yAxisValues.get(i) + h);
        }
    }

    public ArrayList<Double> getyAxisValues() {
        return yAxisValues;
    }

    public ArrayList<Double> getxAxisValues() {
        return xAxisValues;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }
}
