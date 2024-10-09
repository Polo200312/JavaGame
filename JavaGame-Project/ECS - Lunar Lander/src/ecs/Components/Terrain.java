package ecs.Components;

import java.util.ArrayList;

public class Terrain extends Component {
    /**
     * Not creating a record type because I don't like having to use accessor methods
     */
    public static class TerrainPoint {
        public TerrainPoint(float x, float y, boolean safe) {
            this.x = x;
            this.y = y;
            this.safe = safe;
        }

        public float x;
        public float y;
        public boolean safe;
    }

    public ArrayList<TerrainPoint> terrain;
}
