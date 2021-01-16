package math;

public class Vector {
    private double[] vector;
    public static void main(String[] args) {
        Vector v1 = new Vector(1,2,3);
        v1.setMagnitude(1);
        System.out.println(v1.toString());
    }

    public Vector(double... args){
        vector = args;
    }

    public String toString(){
        String s = "";
        for(double element:vector){
            s+=Double.toString(element)+", ";
        }
        return "("+s.substring(0,s.length()-1)+")";
    }

    public double getElement(int index){
        return vector[index];
    }

    public void setElement(int index, double value){
        vector[index] = value;
    }

    public double getMagnitude(){
        double ans = 0;
        for(double element:vector){
            ans+=Math.pow(element,2);
        }
        return Math.sqrt(ans);
    }

    public void setMagnitude(double m){
        double sum = 0;
        for(double element:vector){
            sum+=Math.pow(element,2);
        }
        double scale = Math.sqrt(m/sum);
        for(int i = 0; i<vector.length; i++){
            vector[i]*=scale;
        }
    }

    public int getDimensions(){
        return vector.length;
    }

    public void scale(double s){
        for(int i = 0; i<vector.length; i++){
            vector[i]*=s;
        }
    }


}
