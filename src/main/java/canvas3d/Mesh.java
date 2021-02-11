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
    public Mesh(List<Triangle> tris){
        super(new Vector());
        this.tris = tris;
    }

    public static Mesh fromFile(String path){
        //try(Scanner sc = new Scanner(new File(Mesh.class.getResource(path).toExternalForm().substring(1)))){
        try(Scanner sc = new Scanner(new File("D:/GitHub/Linalg/target/classes/canvas3d/" + path))){
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
                        triangles.add(new Triangle("black", vertices.get(Integer.parseInt(line[1])), vertices.get(Integer.parseInt(line[2])), vertices.get(Integer.parseInt(line[3]))));
                    }
                    catch (Exception e){
                        System.out.println(triangles.size());
                    }
                }
            }

            return new Mesh(triangles);
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
                tri.getVertices()[i] = Vector3.rotateZ(tri.getVertices()[i], 0.0005 * CanvasRenderer3D.deltaTime);
            }
            tri.render(gc);
        }
    }
}
