package threads;

import functions.Function;
import functions.basic.Log;

public class SimpleGenerator implements Runnable {
    private Task task;
    public SimpleGenerator(Task task){
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getTaskCount(); i++) {

            double a = Math.random() * 100;
            double b = 100 + Math.random() * 100;

            Function function = new Log(Math.random() * 10 + 1);
            double step = Math.random();

            synchronized (task) {
                task.setLeftBorder(a);
                task.setRightBorder(b);
                task.setStep(step);
                task.setFunction(function);
            }

            System.out.printf("Source: left border: %f \n        right border: %f \n        step: %f\n\n\n",
                    a, b, step);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
