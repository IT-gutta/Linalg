package canvas3d;

import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Mesh extends Renderer3D<Vector> {
    private List<Triangle> tris;
    private Vector3 mid;
    public Mesh(List<Triangle> tris, Vector3 mid){
        super(new Vector());
        this.tris = tris;
        this.mid = mid;
    }

    public static Mesh fromFile(String path, Vector3 mid){
        //try(Scanner sc = new Scanner(new File(Mesh.class.getResource(path).toExternalForm().substring(1)))){
        try(Scanner sc = new Scanner(new File("C:/Users/jorge/Documents/GitHub/Linalg/target/classes/canvas3d/" + path))){
            List<Vector3> vertices = new ArrayList<>();
            List<Triangle> triangles = new ArrayList<>();
            while(sc.hasNextLine()){
                String[] line = sc.nextLine().split(" ");
//                System.out.println(Arrays.toString(line));
//                System.out.println(vertices.size());
                if(line.equals("\n") || line.equals(""))
                    continue;
                if(line[0].equals("v"))
                    vertices.add(new Vector3(Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3])));

                else if(line[0].equals("f")){
                    try {
                        Triangle triangle = new Triangle("black", Vector3.add(mid, vertices.get(Integer.parseInt(line[1])-1)), Vector3.add(mid, vertices.get(Integer.parseInt(line[2])-1)), Vector3.add(mid, vertices.get(Integer.parseInt(line[3])-1)));
                        for(int i = 0; i < 3; i++) {
                            triangle.getVertices()[i] = Vector3.rotateZ(triangle.getVertices()[i], Math.PI);
                            triangle.getVertices()[i] = Vector3.rotateY(triangle.getVertices()[i], Math.PI);
                        }
                        triangles.add(triangle);
                    }
                    catch (Exception e){
                        System.out.println(Arrays.toString(line));
                        System.out.println(vertices.size());
                        //e.printStackTrace();
                    }
                }
            }
            return new Mesh(triangles, mid);
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void render(GraphicsContext3D gc, String name, Paint paint) {
        for(Triangle tri : tris){
            for(int i = 0; i < 3; i++){
                tri.getVertices()[i] = Vector3.rotateY(tri.getVertices()[i], 0.00005 * CanvasRenderer3D.deltaTime);
            }
            tri.render(gc);
        }
    }
}
