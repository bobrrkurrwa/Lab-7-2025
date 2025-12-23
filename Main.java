import functions.*;
import functions.basic.*;

public class Main {
    public static void main(String[] args){
        /*System.out.println("Task 1");

        TabulatedFunction f = new ArrayTabulatedFunction(1, 100, 10);
        for(FunctionPoint p : f) { System.out.println(p); }
        TabulatedFunction f1 = new LinkedListTabulatedFunction(1.0, 100.0, 10);
        for (FunctionPoint p : f1) { System.out.println(f1); }*/

        /*System.out.println("\nTask 2");
        Function f2 = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(f2, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new
                LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f2, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new
                ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f2, 0, Math.PI, 11);
        System.out.println(tf.getClass());*/

        System.out.println("\nTask 3");
        TabulatedFunction f;

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[] {
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.tabulate(
                LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f.getClass());
        System.out.println(f);

    }
}
