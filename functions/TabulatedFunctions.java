package functions;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {

    private TabulatedFunctions(){};
    // класс обновлен с исп. фабричного метода

    // проинициализировала через array
    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();
    // или можно через list
    /*private static TabulatedFunctionFactory factory = new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory();*/

    //сетфектори
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {TabulatedFunctions.factory = factory;}

    // перегруженные методы создания таб.функ. с исп. фабрики
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return factory.createTabulatedFunction(points);
    }

    // оригинальный табулейт теперь создает объект через create
    public static TabulatedFunction tabulate(Function func, double leftX, double rightX, int pointsCount){
        if (func == null) throw new IllegalArgumentException("Function must exist");
        if (leftX > rightX) throw new IllegalArgumentException("Left border must be less than right border");
        if (pointsCount < 2) throw new IllegalArgumentException("Points count must be more than 2");

        FunctionPoint point[] = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i<pointsCount; i++){
            double x = leftX + i*step;
            double y = func.getFunctionValue(x);
            point[i] = new FunctionPoint(x,y);
        }
        return createTabulatedFunction(point);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {

        DataOutputStream cout = new DataOutputStream(out);
        cout.writeInt(function.getPointsCount());
        for (int i = 0; i<function.getPointsCount(); i++){
            FunctionPoint point = function.getPoint(i);
            cout.writeDouble(point.getX());
            cout.writeDouble(point.getY());
        }
        cout.flush();
    }

    // также теперь создает объект таб.функ. через create
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException{
        DataInputStream cin = new DataInputStream(in);
        int pointsCount = cin.readInt();
        FunctionPoint points[] = new FunctionPoint[pointsCount];
        for (int i =0; i<pointsCount; i++){
            double x = cin.readDouble();
            double y = cin.readDouble();
            points[i] = new FunctionPoint(x,y);
        }
        return createTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        PrintWriter cout = new PrintWriter(out);
        int pointsCount = function.getPointsCount();
        cout.print(pointsCount);
        cout.print(" ");

        for (int i = 0; i < pointsCount; i++) {
            cout.print(function.getPointX(i));
            cout.print(" ");
            cout.print(function.getPointY(i));
            cout.print(" ");
        }
        cout.flush();
    }

    // теперь создает объект таб.функ через create
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int pointsCount = (int) tokenizer.nval;

        FunctionPoint[] points = new FunctionPoint[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            tokenizer.nextToken();
            double x = tokenizer.nval;
            tokenizer.nextToken();
            double y = tokenizer.nval;
            points[i] = new FunctionPoint(x, y);
        }
        return createTabulatedFunction(points);
    }

    // рефлексия методов (помимо прочих параметров получают ссылку class)

    public static TabulatedFunction createTabulatedFunction( Class<? extends TabulatedFunction> functionClass,
                                                             double leftX, double rightX, int pointsCount) {
        try { // конструктор создает объект таб.функ тк расширяет TabulatedFunction и в последующих методах тоже
            Constructor<? extends TabulatedFunction>
                    constructor = functionClass.getConstructor(double.class, double.class, int.class);
            return constructor.newInstance(leftX, rightX, pointsCount);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("Cannot create instance of " + functionClass.getName(), e);
            }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass,
                                                            double leftX, double rightX, double[] values) {
        try {
            Constructor<? extends TabulatedFunction>
                    constructor = functionClass.getConstructor(double.class, double.class, double[].class);
            return constructor.newInstance(leftX, rightX, values);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Cannot create instance of " + functionClass.getName(), e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass,
                                                            FunctionPoint[] points) {
        try {
            Constructor<? extends TabulatedFunction>
                    constructor = functionClass.getConstructor(FunctionPoint[].class);
            return constructor.newInstance((Object)points);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Cannot create instance of " + functionClass.getName(), e);
        }
    }

    // этот метод tabulate уже с использованием рефлексии
    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> functionClass,
                                             Function func, double leftX, double rightX, int pointsCount) {
        if (func == null) throw new IllegalArgumentException("Function must exist");
        if (leftX > rightX) throw new IllegalArgumentException("Left border must be less than right border");
        if (pointsCount < 2) throw new IllegalArgumentException("Points count must be more than 2");

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            double y = func.getFunctionValue(x);
            points[i] = new FunctionPoint(x, y);
        }
        return createTabulatedFunction(functionClass, points);
    }

    // перегрузка метода, теперь принимает ссылку class
    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> functionClass, InputStream in)
            throws IOException {
        DataInputStream cin = new DataInputStream(in);
        int pointsCount = cin.readInt();
        FunctionPoint points[] = new FunctionPoint[pointsCount];
        for (int i =0; i<pointsCount; i++){
            double x = cin.readDouble();
            double y = cin.readDouble();
            points[i] = new FunctionPoint(x,y);
        }
        return createTabulatedFunction(functionClass, points);
    }


    // перегрузка метода, теперь принимает ссылку class
    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> functionClass, Reader in)
            throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int pointsCount = (int) tokenizer.nval;

        FunctionPoint[] points = new FunctionPoint[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            tokenizer.nextToken();
            double x = tokenizer.nval;
            tokenizer.nextToken();
            double y = tokenizer.nval;
            points[i] = new FunctionPoint(x, y);
        }
        return createTabulatedFunction(functionClass, points);
    }

}
