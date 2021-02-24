package terraingeneration;

import canvas3d.Mesh;
import canvas3d.Triangle;
import javafx.scene.paint.Color;
import math3d.Vector3;

import java.util.ArrayList;
import java.util.List;

public class TerrainChunk extends Mesh {
    private double[] heightMap;
    private Color[] colorMap;
    private PerlinNoiseMap perlinNoise;
    public TerrainChunk(Vector3 position, int size, PerlinNoiseMap perlinNoise){
        super(position);
        int nVertices = size +1;
        this.perlinNoise = perlinNoise;
        heightMap = new double[nVertices*nVertices];
        colorMap = new Color[nVertices*nVertices];
        vertices = new Vector3[nVertices*nVertices];

        for(int z = 0; z < nVertices; z++){
            for(int x = 0; x < nVertices; x++) {
                double height = perlinNoise.get((z+position.getZ())/15, (x+position.getX())/15);
                if(height < 0) //f0r å få vannet til å bli flatt
                    height = 0;
                heightMap[z*nVertices + x] = height;
                colorMap[z*nVertices + x] = heightToColor(height);
                vertices[z*nVertices + x] = new Vector3((x-nVertices/2), (1 + heightMap[z*nVertices + x]) * 10 - 10, (z-nVertices/2));
            }
        }
//        this.triangles = new Triangle[(size-1)*(size-1) * 2];
        List<Triangle> triangles = new ArrayList<>();
        for(int y = 0; y < nVertices-1; y++){
            for(int x = 0; x < nVertices-1; x++){
                triangles.add(new Triangle(
                        vertices[y*nVertices+x], vertices[(y+1)*nVertices +x], vertices[(y+1)*nVertices + x + 1],
                        colorMap[y*nVertices+x], colorMap[(y+1)*nVertices +x], colorMap[(y+1)*nVertices + x + 1]));


                triangles.add(new Triangle(
                        vertices[y*nVertices+x], vertices[(y+1)*nVertices +x + 1], vertices[y*nVertices + x + 1],
                        colorMap[y*nVertices+x], colorMap[(y+1)*nVertices +x + 1], colorMap[y*nVertices + x + 1]));
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
