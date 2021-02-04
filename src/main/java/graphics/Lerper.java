package graphics;

public class Lerper {
    private double[] starts;
    private double[] ends;
    private double[] actuals;
    private double progress;
    private double progressAngle;
    private double millis;

    public Lerper(double millis, double[] starts, double[] ends){
        if(ends.length != starts.length)
            throw new IllegalArgumentException("Invalid length");
        this.millis = millis;
        this.starts = starts;
        this.ends = ends;
        this.actuals = new double[ends.length];
    }

    public void handle(){
        progressAngle += Math.PI/2 / millis * CanvasRenderer.deltaTime;
        progress = Math.sin(progressAngle);

        //lerping finished
        if(progressAngle >= Math.PI/2) //fix ending state
            progress = 1f;

        for(int i = 0; i < actuals.length; i++){
            actuals[i] = starts[i] + progress * (ends[i] - starts[i]);
        }
    }

    public boolean isFinished(){
        return progress == 1;
    }

    public double get(int i){
        return actuals[i];
    }
}
