package math;

import java.util.function.Function;

public class Mapping {
    private final Function<Double,Double> mapping;
    private double start;
    private double end;
    public Mapping(Function<Double, Double> fun, double start, double end){
        mapping = fun;
        this.start = start;
        this.end = end;
    }
    public Mapping(Function<Double, Double> fun){
        mapping = fun;
        start = -10;
        end = 10;
    }
    public double evaluate(double x){
        return mapping.apply(x);
    }
}
