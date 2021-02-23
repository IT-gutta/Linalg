package terraingeneration;

import canvas3d.Mesh;
import canvas3d.Triangle;
import javafx.scene.paint.Color;
import math3d.Vector3;

import java.util.ArrayList;
import java.util.List;

public class NoiseTerrainMesh extends Mesh {
    private double[] heightMap;
    private Color[] colorMap;
    private PerlinNoiseMap perlinNoise;
    public NoiseTerrainMesh(int size){
        super();
        perlinNoise = new PerlinNoiseMap();
        heightMap = new double[size*size];
        colorMap = new Color[size*size];
        vertices = new Vector3[size*size];

        for(int z = 0; z < size; z++){
            for(int x = 0; x < size; x++) {
                double height = perlinNoise.get((double)z/15, (double)x/15);
                if(height < 0) //f0r å få vannet til å bli flatt
                    height = 0;
                heightMap[z*size + x] = height;
                colorMap[z*size + x] = heightToColor(height);
                vertices[z*size + x] = new Vector3((x-size/2)*0.1, (1 + heightMap[z*size + x]) * 2 - 4, (z-size/2)*0.1);
            }
        }
//        this.triangles = new Triangle[(size-1)*(size-1) * 2];
        List<Triangle> triangles = new ArrayList<>();
        for(int y = 0; y < size-1; y++){
            for(int x = 0; x < size-1; x++){
                triangles.add(new Triangle(
                        vertices[y*size+x], vertices[(y+1)*size +x], vertices[(y+1)*size + x + 1],
                        colorMap[y*size+x], colorMap[(y+1)*size +x], colorMap[(y+1)*size + x + 1]));


                triangles.add(new Triangle(
                        vertices[y*size+x], vertices[(y+1)*size +x + 1], vertices[y*size + x + 1],
                        colorMap[y*size+x], colorMap[(y+1)*size +x + 1], colorMap[y*size + x + 1]));
            }
        }
        //faktiske triangle array som renderes av superklassen
        this.triangles = triangles.toArray(new Triangle[0]);
    }

    private Color heightToColor(double height){
        if(height <= 0) //water
            return Color.LIGHTBLUE;
        if(height < 0.15) //sand
            return Color.rgb(237, 201, 175);
//        if(height < 0.3) //dirt
//            return Color.rgb(131,101,57);

        if(height < 0.6) //fjell
            return Color.rgb(	50,	50,	50);

        //snø
        return Color.rgb(255, 255, 255);
    }
}
