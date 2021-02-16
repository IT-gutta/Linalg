package math;

import java.util.HashMap;
import java.util.function.Function;

public class Differentiator {
    private static HashMap<Function<Double, Double>, Function<Double, Double>> derivatives = new HashMap<>();

    public static void fillDerivatives(){
        derivatives.put(Math::sin, Math::cos);
    }
    public static Expression getDerivative(Expression expression){
        return expression;
    }
}
