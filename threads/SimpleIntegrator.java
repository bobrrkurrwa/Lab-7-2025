package threads;

import functions.Function;
import functions.Functions;

public class SimpleIntegrator implements Runnable{
    private Task task;
    public SimpleIntegrator(Task task){
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getTaskCount(); i++) {
            while (true) {
                Function function = null;
                double a = 0, b = 0, step = 0;
                boolean flag = false;

                synchronized (task) {
                    function = task.getFunction();
                    if (function != null) {
                        a = task.getLeftBorder();
                        b = task.getRightBorder();
                        step = task.getStep();
                        flag = true;

                        task.setFunction(null);
                    }
                }

                if (flag) {
                    double result = Functions.integrate(function, a, b, step);

                    System.out.printf("Result: left border: %f \n        right border: %f \n        step: %f \n        integration result: %f\n\n\n",
                            a, b, step, result);
                    break;
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
