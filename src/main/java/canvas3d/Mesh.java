package canvas3d;

import javafx.scene.paint.Color;
import math3d.Vector3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Mesh extends Render3D{
    //TODO vector arrow mesh based on magnitude

    public Mesh(String path, Vector3 position, double scale){
        super(position);

        //try(Scanner sc = new Scanner(new File(Mesh.class.getResource(path).toExternalForm().substring(1)))){
        try(Scanner sc = new Scanner(new File("C:\\Users\\jorge\\Documents\\GitHub\\Linalg\\target\\classes\\canvas3d\\" + path))){

            List<Vector3> vertices = new ArrayList<>();
            List<Triangle> triangles = new ArrayList<>();
            while(sc.hasNextLine()){
                String[] line = sc.nextLine().split(" ");

                if(line[0].equals("v"))
                    vertices.add(Vector3.scale(new Vector3(Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3])), scale));

                else if(line[0].equals("f")){
                    try {
                        Triangle triangle = new Triangle(vertices.get(Integer.parseInt(line[1])-1), vertices.get(Integer.parseInt(line[2])-1), vertices.get(Integer.parseInt(line[3])-1), Color.MEDIUMVIOLETRED, Color.MEDIUMVIOLETRED, Color.MEDIUMVIOLETRED);
                        triangles.add(triangle);
                    }
                    catch (Exception e){
                        System.out.println(Arrays.toString(line));
                        System.out.println(vertices.size());
                        //e.printStackTrace();
                    }
                }
            }
            this.vertices = vertices.toArray(new Vector3[0]);
            this.triangles = triangles.toArray(new Triangle[0]);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void scale(double scalar){
        for(Vector3 vertex : vertices)
            vertex.scale(scalar);
    }


    @Override
    public void update(String name, Color color) {

    }

    @Override
    public Object getMath() {
        return null;
    }
}
