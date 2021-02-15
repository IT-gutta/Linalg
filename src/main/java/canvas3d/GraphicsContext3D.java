package canvas3d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;
import math3d.Vector4;

public class GraphicsContext3D {
    //TODO fix rasterizer when outside canvas
    //TODO optimize rasterizer
    //TODO implement coloring / texturing
    private GraphicsContext graphicsContext2D;
    private Camera3D camera;
    private double[] depthBuffer;
    private int width;
    private int height;

    public GraphicsContext3D(GraphicsContext graphicsContext2D, Camera3D camera){
        this.graphicsContext2D = graphicsContext2D;
        this.camera = camera;
    }

    public void strokeLine(double x1, double y1, double z1, double x2, double y2, double z2){
        Vector4 start = camera.project(new Vector3(x1, y1, z1));
        Vector4 end = camera.project(new Vector3(x2, y2, z2));

        /*System.out.println("start" + start.getVector().toString());
        System.out.println("end" + end.getVector().toString());

        System.out.println("startX: " + start.getX() + ", startY: " +start.getY());
        System.out.println("endX: " + end.getX() + ", endY: " +end.getY());*/

        graphicsContext2D.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public void strokeLine(Vector3 start, Vector3 end){
        Vector4 start4 = camera.project(start);
        Vector4 end4 = camera.project(end);

        graphicsContext2D.strokeLine(start4.getX(), start4.getY(), end4.getX(), end4.getY());
    }

    public void fillTriangle(Vector3 p1, Vector3 p2, Vector3 p3, Color[] colors){
        Vector4[] projectedPoints = new Vector4[]{
                camera.project(p1),
                camera.project(p2),
                camera.project(p3),
        };

        //rasterizeTriangle(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ(), p3.getX(), p3.getY(), p3.getZ());
        edgeCheckRasterize(
                projectedPoints[0].getX(), projectedPoints[0].getY(), projectedPoints[0].getZ(), colors[0],
                projectedPoints[1].getX(), projectedPoints[1].getY(), projectedPoints[1].getZ(), colors[1],
                projectedPoints[2].getX(), projectedPoints[2].getY(), projectedPoints[2].getZ(), colors[2]);

    }

    public void fillTriangle(Vector3 p1, Vector3 p2, Vector3 p3){
        Vector4[] projectedPoints = new Vector4[]{
                camera.project(p1),
                camera.project(p2),
                camera.project(p3),
        };
        edgeCheckRasterize(
                projectedPoints[0].getX(), projectedPoints[0].getY(), projectedPoints[0].getZ(),
                projectedPoints[1].getX(), projectedPoints[1].getY(), projectedPoints[1].getZ(),
                projectedPoints[2].getX(), projectedPoints[2].getY(), projectedPoints[2].getZ()
        );
    }

    public void fillSphere(Vector position, double radius){

    }


    public void clearPolygons(){
        width = (int)CanvasRenderer3D.getCanvasWidth();
        height = (int) CanvasRenderer3D.getCanvasHeight();
        this.depthBuffer = new double[width*height];
        for(int i = 0; i < depthBuffer.length; i++){
            depthBuffer[i] = 0;
        }
    }

    public void rasterizeTriangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3){
        Color fill = Color.valueOf(graphicsContext2D.getFill().toString());
        double a1 = (y3 - y1) / (x3 - x1);
        double b1 = y1 - a1*x1;

        double a2 = (y2 - y1) / (x2 - x1);
        double b2 = y1 - a2*x1;

        double a3 = (y2 - y3) / (x2 - x3);
        double b3 = y3 - a3*x3;



        int minY = (int) Math.round(Math.max(Math.min(Math.min(y1, y2), y3), 0));
        int maxY = (int) Math.round(Math.min(Math.max(Math.max(y1, y2), y3), height-1));

        if(maxY == minY)
            return;//FIX
        int[] minXes = new int[maxY-minY];
        int[] maxXes = new int[maxY-minY];


        for(int i = minY; i < maxY; i++) {
            minXes[i - minY] = Math.round(Math.max(Math.min(Math.min(Math.round((y1-b1)/a1), Math.round((y2-b2)/a2)), Math.round((y3-b3)/a3)), 0));
            maxXes[i - minY] = Math.round(Math.min(Math.max(Math.max(Math.round((y1-b1)/a1), Math.round((y2-b2)/a2)), Math.round((y3-b3)/a3)), width-1));
        }

        for(int y = minY; y < maxY; y++){
            for(int x = minXes[y-minY]; x < maxXes[y-minY]; x++){
                double depth = z1;//fikse dette
                if(depth > depthBuffer[y*width + x]) {
                    graphicsContext2D.getPixelWriter().setColor(x, y, fill);
                    depthBuffer[y*width + x] = depth;
                }
            }
        }
    }

    public void edgeCheckRasterize(double x1, double y1, double z1, Color color1, double x2, double y2, double z2, Color color2, double x3, double y3, double z3, Color color3){
        int yMin = (int) Math.round(max(0, min(y1, y2, y3)));
        int yMax = (int) Math.round(min(height-1, max(y1, y2, y3)));

        int xMin = (int) Math.round(max(0, min(x1, x2, x3)));
        int xMax = (int) Math.round(min(width-1, max(x1, x2, x3)));


        double oneOverArea = 1 / cross(x2-x1, y2-y1, x3-x1, y3-y1);

        for(int y = yMin; y <= yMax; y++){
            for(int x = xMin; x <= xMax; x++){
                double l1 = edgeFunction(x2, y2, x3, y3, x, y) * oneOverArea;
                double l2 = edgeFunction(x3, y3, x1, y1, x, y) * oneOverArea;
                double l3 = edgeFunction(x1, y1, x2, y2, x, y) * oneOverArea;

                //System.out.println(l1+l2+l3);

                if(l1 < 0 || l2 < 0 || l3 < 0) {
                    continue;
                }

                double z = 1/(l1/z1 + l2/z2 + l3/z3);
                Color color = Color.color(
                        color1.getRed() * l1 + color2.getRed() * l2 + color3.getRed() * l3,
                        color1.getGreen() * l1 + color2.getGreen() * l2 + color3.getGreen() * l3,
                        color1.getBlue() * l1 + color2.getBlue() * l2 + color3.getBlue() * l3);

                if(z < depthBuffer[y*width + x]){
                    graphicsContext2D.getPixelWriter().setColor(x, y, color);
                    depthBuffer[y*width + x] = z;
                }
            }
        }
    }


    public void edgeCheckRasterize(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3){
        Color fill = Color.valueOf(graphicsContext2D.getFill().toString());
        int yMin = (int) Math.round(max(0, min(y1, y2, y3)));
        int yMax = (int) Math.round(min(height-1, max(y1, y2, y3)));

        int xMin = (int) Math.round(max(0, min(x1, x2, x3)));
        int xMax = (int) Math.round(min(width-1, max(x1, x2, x3)));


        double oneOverArea = 1 / cross(x2-x1, y2-y1, x3-x1, y3-y1);

        for(int y = yMin; y <= yMax; y++){
            for(int x = xMin; x <= xMax; x++){
                double l1 = edgeFunction(x2, y2, x3, y3, x, y) * oneOverArea;
                double l2 = edgeFunction(x3, y3, x1, y1, x, y) * oneOverArea;
                double l3 = edgeFunction(x1, y1, x2, y2, x, y) * oneOverArea;

                //System.out.println(l1+l2+l3);

                if(l1 < 0 || l2 < 0 || l3 < 0) {
                    continue;
                }

                double z = 1/(l1/z1 + l2/z2 + l3/z3);
                if(z < depthBuffer[y*width + x]){
                    graphicsContext2D.getPixelWriter().setColor(x, y, fill);
                    depthBuffer[y*width + x] = z;
                }
            }
        }
    }

    private double edgeFunction(double x1, double y1, double x2, double y2, double x3, double y3){
        return cross(x2-x1, y2-y1, x3-x1, y3-y1);
    }

    private double cross(double x1, double y1, double x2, double y2){
        return (x1*y2) - (y1*x2);
    }



    private double max(double v1, double v2, double v3){
        if (v1 > v2) {
            if (v3 > v1)
                return v3;
            return v1;
        }

        if (v3 > v2)
            return v3;
        return v2;
    }

    private double min(double v1, double v2, double v3){
        if (v1 < v2) {
            if (v3 < v1)
                return v3;
            return v1;
        }

        if (v3 < v2)
            return v3;
        return v2;
    }

    private double max(double v1, double v2){
        return v1 > v2 ? v1 : v2;
    }
    private double min(double v1, double v2){
        return v1 < v2 ? v1 : v2;
    }


    public void setAlpha(double alpha){
        graphicsContext2D.setGlobalAlpha(alpha);
    }
    public void restoreAlpha(){
        graphicsContext2D.setGlobalAlpha(1);
    }



    public void setFill(Paint paint){
        graphicsContext2D.setFill(paint);
    }
    public void setFill(String color){
        graphicsContext2D.setFill(Paint.valueOf(color));
    }
    public void setFill(Color color){
        graphicsContext2D.setFill(Paint.valueOf(color.toString()));
    }
    public void setStroke(Paint paint){
        graphicsContext2D.setStroke(paint);
    }
    public void setStroke(String color){
        graphicsContext2D.setStroke(Paint.valueOf(color));
    }
    public void setColor(Color color){
        graphicsContext2D.setStroke(Paint.valueOf(color.toString()));
    }


    public void clearRect(double x, double y, double w, double h){
        graphicsContext2D.clearRect(x, y, w, h);
    }
}
