package threads;

import functions.Function;

public class Task {
    private Function function;
    private double leftBorder;
    private double rightBorder;
    private double step;
    private int taskCount;

    public Task(Function func, double leftBorder, double rightBorder, double step, int taskCount) {
        this.function = func;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.step = step;
        this.taskCount = taskCount;
    }

    public Task(){}

    // геттеры
    public Function getFunction(){ return function; }
    public double getLeftBorder() { return leftBorder; }
    public double getRightBorder() { return rightBorder; }
    public double getStep() { return step; }
    public int getTaskCount() { return taskCount; }

    // сеттеры
    public void setFunction( Function function ){ this.function = function; }
    public void setLeftBorder( double leftBorder ) { this.leftBorder = leftBorder; }
    public void setRightBorder( double rightBorder ) { this.rightBorder = rightBorder; }
    public void setStep(double step) { this.step = step; }
    public void setTaskCount(int taskCount) { this.taskCount = taskCount; }

}
