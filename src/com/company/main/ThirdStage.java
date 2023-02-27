package com.company.main;

public class ThirdStage extends Stages {

    private SecondStage secondStage;

    public ThirdStage() {
        t = 224.0; // Время работы третьей ступени, с
        F = 294000.0; // Тяга двигателя третьей ступени, Н
        M = 30693.0; // Масса ракеты с космическим кораблём после отделения второй ступени, кг
        angle = 8.0; // Конечный угол поворота ракеты относительно вертикальной оси за время работы третьей ступени, град
        k = 84.7; // Расход массы третьей ступени, кг/с
        calculateTimeValues();
    }

    public void calculateFunction() {
        for(int i = 0; i < timeValues.size(); i++) {
            accelerationXValues.add(xFunction(timeValues.get(i)));
            accelerationYValues.add(yFunction(timeValues.get(i)));
            speedXValues.add(euler(setStartValues(speedXValues, secondStage.getSpeedXValues(), i), accelerationXValues.get(i)));
            speedYValues.add(euler(setStartValues(speedYValues, secondStage.getSpeedYValues(), i), accelerationYValues.get(i)));
            movementXValues.add(euler(setStartValues(movementXValues, secondStage.getMovementXValues(), i), speedXValues.get(i)));
            movementYValues.add(euler(setStartValues(movementYValues, secondStage.getMovementYValues(), i), speedYValues.get(i)));
        }
        printParameters();
    }

    public Double xFunction(Double arg) {
        return (F * Math.sin(secondStage.getFirstStage().getEndAngle() + secondStage.getEndAngle() + rotationAngleFunction()*arg))/(M - k *arg);
    }

    public Double yFunction(Double arg) {
        return (F * Math.cos(secondStage.getFirstStage().getEndAngle() + secondStage.getEndAngle() + rotationAngleFunction()*arg))/(M - k *arg) - g;
    }

    public void setSecondStage(SecondStage secondStage) {
        this.secondStage = secondStage;
    }
}
