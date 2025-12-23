package threads;

import functions.Function;
import functions.Functions;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;

    }

    @Override
    public void run() {
        /*System.out.println("Integrator STARTS");*/
        int i = 0;

        while (i < task.getTaskCount() && !isInterrupted()) {
            Function function = null;
            double a = 0, b = 0, step = 0;
            boolean hasData = false;
                /*System.out.println("Integrator is trying to get data, processed: " + i );*/

            try {
                if (semaphore.tryAcquire(100, TimeUnit.MILLISECONDS)) {
                    try {
                        /*System.out.println("Integrator acquired semaphore");*/
                        function = task.getFunction();
                        if (function != null) {
                            a = task.getLeftBorder();
                            b = task.getRightBorder();
                            step = task.getStep();
                            task.setFunction(null);
                            hasData = true;
                        /*System.out.println("Integrator got data from task");*/
                        /*} else {
                            System.out.println("Integrator: no data available");*/
                    }
                    } finally {
                        semaphore.release();
                        /*System.out.println("Integrator released semaphore");*/
                    }

                    if (hasData) {

                        /*System.out.println("Integrator is calculating");*/
                        double result = Functions.integrate(function, a, b, step);

                        i++;
                        System.out.printf("Result: left border: %.2f \n        right border: %.2f \n        step: %.2f \n        integration result: %.2f\n\n\n",
                                a, b, step, result);


                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            break;
                        }
                    } else {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                } else {
                    /*System.out.println("Integrator failed to acquire semaphore");*/
                    if (isInterrupted()) break;
                }
            } catch (InterruptedException e) {
                break;
            }
        }

        /*System.out.println("Integrator finished working. Task count: " + i);*/
    }
}
