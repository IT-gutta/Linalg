package math;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<A,B,C,D>{
    D apply(A a, B b, C c);

//    default <V> TriFunction<A, B, C, V> andThen(
//            Function<? super D, ? extends V> after) {
//        Objects.requireNonNull(after);
//        return (A a, B b, C c) -> after.apply(apply(a, b, c));
//    }
}
