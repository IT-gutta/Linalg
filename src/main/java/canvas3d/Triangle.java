package canvas3d;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import math3d.Line3;
import math3d.Vector3;

public class Triangle {
    private Vector3[] vertices;
    private Color[] colors;
    private Color[] adjustedColors;
    private Color color;

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, String color){
        this(p1, p2, p3, Color.valueOf(color));
    }
    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Color color){
        this.vertices = new Vector3[]{p1, p2, p3};
        this.color = color;
    }

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.vertices = new Vector3[]{p1, p2, p3};
    }

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Color c1, Color c2, Color c3){
        this.vertices = new Vector3[]{p1, p2, p3};
        this.colors = new Color[]{c1, c2, c3};
        this.adjustedColors = new Color[3];
    }

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, String c1, String c2, String c3){
        this.vertices = new Vector3[]{p1, p2, p3};
        this.colors = new Color[]{Color.valueOf(c1), Color.valueOf(c2), Color.valueOf(c3)};
        this.adjustedColors = new Color[3];
    }




    public void renderAbsolute(GraphicsContext3D gc){
        if(!facingCamera(getRelativeNormal(), vertices[0]))
            return;

        Vector3 normal = getRelativeNormal();
        //color interpolation between vertices
        if(colors != null){
            for (int i = 0; i < 3; i++) {
                adjustedColors[i] = Color.hsb(colors[i].getHue(), colors[i].getSaturation(), brightness(vertices[i], normal));
            }
            gc.fillTriangle(vertices[0], vertices[1], vertices[2], adjustedColors);
        }

        //simple fill color
        else if(color != null){
            gc.setFill(Color.hsb(color.getHue(), color.getSaturation(), brightness(getAverage(), normal)));
            gc.fillTriangle(vertices[0], vertices[1], vertices[2]);
        }
        //simple grayscale fill based on brightness from lightSource
        else
            gc.setFill(Color.grayRgb((int) brightness(getAverage(), normal)));
    }

    //render med relativt til objektets posisjon og retning
    public void render(GraphicsContext3D gc, Vector3 origin, Vector3 forward, Vector3 up, Vector3 right){

        Vector3 pos1 = Vector3.add(origin, Vector3.scale(right, vertices[0].getX()), Vector3.scale(up, vertices[0].getY()), Vector3.scale(forward, vertices[0].getZ()));
        Vector3 pos2 = Vector3.add(origin, Vector3.scale(right, vertices[1].getX()), Vector3.scale(up, vertices[1].getY()), Vector3.scale(forward, vertices[1].getZ()));
        Vector3 pos3 = Vector3.add(origin, Vector3.scale(right, vertices[2].getX()), Vector3.scale(up, vertices[2].getY()), Vector3.scale(forward, vertices[2].getZ()));

        Vector3 normal = Vector3.cross(Vector3.subtract(pos2, pos1), Vector3.subtract(pos3, pos1));

        //TODO fikse facingCamera funksjonen!!!
        if(!facingCamera(normal, pos1))
            return;

        //color interpolation between vertices
        if(colors != null){
            adjustedColors[0] = Color.hsb(colors[0].getHue(), colors[0].getSaturation(), brightness(pos1, normal));
            adjustedColors[1] = Color.hsb(colors[1].getHue(), colors[1].getSaturation(), brightness(pos2, normal));
            adjustedColors[2] = Color.hsb(colors[2].getHue(), colors[2].getSaturation(), brightness(pos3, normal));

            gc.fillTriangle(pos1, pos2, pos3, adjustedColors);
        }

        //simple fill color
        else if(color != null){

            gc.setFill(Color.hsb(color.getHue(), color.getSaturation(), brightness(Vector3.scale(Vector3.add(pos1, pos2, pos3), 0.33333333), normal)));
            gc.fillTriangle(pos1, pos2, pos3);
        }
        //simple grayscale fill based on brightness from lightSource
        else {
            gc.setFill(Color.grayRgb((int) brightness(Vector3.scale(Vector3.add(pos1, pos2, pos3), 0.33333333), normal)));
            gc.fillTriangle(pos1, pos2, pos3);
        }
    }

    public boolean facingCamera(Vector3 normal, Vector3 arbitraryPointOnTriangle){
        try {
            return normal.dot(Vector3.subtract(arbitraryPointOnTriangle, CanvasRenderer3D.getCamera().position)) < 0;
        }
        catch (Exception e){
            System.out.println("FEIL I FACING CAMERA FUNKSJONEN!");
            e.printStackTrace();
        }
        return false;
    }

    public Vector3 getRelativeNormal(){
        try {
            return Vector3.cross(Vector3.subtract(vertices[1], vertices[0]), Vector3.subtract(vertices[2], vertices[0]));
        }
        catch (Exception e){
            System.out.println("Error i getNormal funksjonen");
            e.printStackTrace();
        }
        return null;
    }

    public Vector3 getCentroid(){
        Vector3 normal = getRelativeNormal();
        Vector3 r1 = new Vector3(vertices[0].getX()-vertices[1].getX(), vertices[0].getY()-vertices[1].getY(), vertices[0].getZ()-vertices[1].getZ());
        Vector3 r2 = new Vector3(vertices[0].getX()-vertices[2].getX(), vertices[0].getY()-vertices[2].getY(), vertices[0].getZ()-vertices[2].getZ());
        Vector3 v1 = Vector3.cross(normal, r1);
        Vector3 v2 = Vector3.cross(normal, r2);

        Line3 line1 = new Line3(new Vector3((vertices[0].getX() + vertices[1].getX())/2, (vertices[0].getY() + vertices[1].getY())/2, (vertices[0].getZ() + vertices[1].getZ())/2), v1);
        Line3 line2 = new Line3(new Vector3((vertices[0].getX() + vertices[2].getX())/2, (vertices[0].getY() + vertices[2].getY())/2, (vertices[0].getZ() + vertices[2].getZ())/2), v2);

        Vector3 intersection = line1.intersection(line2);
//        if(intersection == null)
//            return null;
        if(intersection == null)
            throw new Error("FEIL I getCentroid()-funksjonen");

        return intersection;
    }

    private Vector3 getAverage(){
        return Vector3.scale(Vector3.add(vertices[0], vertices[1], vertices[2]), 0.3333333333);
    }

    public Vector3[] getVertices(){
        return vertices;
    }

    private double brightness(Vector3 point, Vector3 normal){
        return Math.sqrt(CanvasRenderer3D.getCamera().getLightSource().getBrightness(point, normal));
    }


    public void setColor(Color color){
        this.color = color;
    }

    public void setColors(Color[] colors){
        this.colors = colors;
    }
}
