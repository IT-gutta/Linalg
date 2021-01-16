package math;

public class Utils {
    public static void main(String[] args) {
        System.out.println(gcd(21,7));
    }
    public static int gcd(int a, int b){
        if(b==0){
            return a;
        }
        return gcd(b, a%b);
    }

    public static int lcm(int a, int b){
        return a*b/gcd(a,b);
    }

    public static boolean isWhole(double n){
        return (int)Math.floor(n)==(int)n;
    }
}
