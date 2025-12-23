package threads;

import functions.Function;
import functions.basic.Log;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Generator extends Thread{
    private Task task;
    private Semaphore semaphore;
     public Generator(Task task, Semaphore semaphore){
         this.task = task;
         this.semaphore = semaphore;
     }
     @Override
     public void run() {
         /*System.out.println("Generator STARTS");*/
         try {
             for (int i = 0; i < task.getTaskCount(); i++) {
                 if (isInterrupted()) {
                     break;
                 }
                /* System.out.println("Generator is working, iteration " + (i+1) );*/
                 double a = Math.random() * 100;
                 double b = 100 + Math.random() * 100;
                 Function function = new Log(Math.random() * 10 + 1);

                 if (!semaphore.tryAcquire(100, TimeUnit.MILLISECONDS)) {
                     if (isInterrupted()) break;
                     continue;
                 }
                 try {
                     /*System.out.println("Generator acquired semaphore");*/
                     double step = Math.random();
                     task.setLeftBorder(a);
                     task.setRightBorder(b);
                     task.setStep(step);
                     task.setFunction(function);
                     System.out.printf("Source: left border: %.2f \n        right border: %.2f \n        step: %.2f\n\n\n",
                             a, b, step);
                 } finally {
                     /*System.out.println("Generator released semaphore");*/
                     semaphore.release();
                 }
                 try {
                     Thread.sleep(10);
                 } catch (InterruptedException e) {
                     /*System.out.println("Generator interrupted during sleep >:( ");*/
                     break;
                 }
             }
         } catch (InterruptedException e) {
             /*System.out.println("Generator interrupted during semaphore's working");*/
             Thread.currentThread().interrupt();
         } finally {
             /*System.out.println("Generator finished");*/
         }
     }
}
