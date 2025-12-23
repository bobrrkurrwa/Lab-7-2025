package functions;

public class FunctionPoint {

    private double x;
    private double y;
    public static final double EPSILON = 1e-10;

    // геттеры точек
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    // сетеры значений точек
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }

    // конструктор с заданными координатами
    public FunctionPoint(double x, double y)
    {   this.x = x;
        this.y = y; }

    public FunctionPoint(FunctionPoint point){ // конструктор копирования
        this(point.x, point.y);
    }
    public FunctionPoint(){ // конструктор по умолчанию (точка 0,0)
        this(0.0, 0.0);
    }

    @Override
    public String toString(){
        return ("("+ x + "; " + y + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionPoint that = (FunctionPoint) o;
        return Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0;
    }

    @Override
    public int hashCode(){
        long bitsX = Double.doubleToLongBits(x);
        long bitsY = Double.doubleToLongBits(y);
        return ((int)(bitsX) ^ (int)(bitsX >> 32) ^ (int)(bitsY) ^ (int)(bitsY >> 32));
    }

    @Override
    public Object clone(){
        return new FunctionPoint(x,y);
    }
}
