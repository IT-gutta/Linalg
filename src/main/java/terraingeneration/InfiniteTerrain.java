package terraingeneration;

import canvas3d.CanvasRenderer3D;
import canvas3d.GraphicsContext3D;
import canvas3d.Render3D;
import math3d.Vector3;

import java.util.HashMap;

public class InfiniteTerrain extends Render3D {
    private HashMap<String, TerrainChunk> terrainChunkHashMap;
    public final int chunkSize = 30;
    private final PerlinNoiseMap perlinNoiseMap = new PerlinNoiseMap();
    public InfiniteTerrain(){
        this.terrainChunkHashMap = new HashMap<>();
    }

    @Override
    public void render(GraphicsContext3D gc){
        int currentChunkX = (int) Math.round(CanvasRenderer3D.getCamera().getPosition().getX() / chunkSize);
        int currentChunkZ = (int) Math.round(CanvasRenderer3D.getCamera().getPosition().getZ() / chunkSize);

        for(int x = currentChunkX - 1; x <= currentChunkX + 1; x++){
            for(int z = currentChunkZ - 1; z <= currentChunkZ + 1; z++){
                String stringKey = ""+x+z;
                if(terrainChunkHashMap.containsKey(stringKey))
                    terrainChunkHashMap.get(stringKey).render(gc);
                else{
                    //System.out.println(terrainChunkHashMap.size() + ", " + stringKey);
                    TerrainChunk newChunk = new TerrainChunk(new Vector3(x*chunkSize, 0, z*chunkSize), chunkSize, perlinNoiseMap);
                    newChunk.render(gc);
                    terrainChunkHashMap.put(stringKey, newChunk);
                    CanvasRenderer3D.chunksSpawnedCount++;
                }
            }
        }
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public Object getMath() {
        return null;
    }
}
