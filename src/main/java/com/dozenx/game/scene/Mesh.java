package com.dozenx.game.scene;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:30 2019/10/11
 * @Modified By:
 */

//import com.dozenx.game.bounding.BoundingBox;
//import com.dozenx.game.bounding.BoundingVolume;
//import com.dozenx.game.collision.Collidable;
//import com.dozenx.game.collision.CollisionResults;
//import com.dozenx.game.collision.bih.BIHTree;

//import com.dozenx.game.material.Material;
//import com.dozenx.game.material.RenderState;

/**
 * <code>Mesh</code> is used to store rendering data.
 * <p>
 * All visible elements in a scene are represented by meshes.
 * Meshes may contain three types of geometric primitives:
 * <ul>
 * <li>Points - Every vertex represents a single point in space.
 * sprite} mode.</li>
 * <li>Lines - 2 vertices represent a line segment, with the width specified
 * <li>Triangles - 3 vertices represent a solid triangle primitive. </li>
 * </ul>
 *
 * @author Kirill Vainer
 */
public class Mesh /*implements Savable, Cloneable, JmeCloneable */ {

    /**
     * The mode of the Mesh specifies both the type of primitive represented
     * by the mesh and how the data should be interpreted.
     */
    public enum Mode {
        /**
         * A primitive is a single point in space. The size of {@link Mode#Points points} are
         * determined via the vertex shader's <code>gl_PointSize</code> output.
         */
        Points(true),

        /**
         * A primitive is a line segment. Every two vertices specify
         * to set the width of the lines.
         */
        Lines(true),

        /**
         * A primitive is a line segment. The first two vertices specify
         * a single line, while subsequent vertices are combined with the
         * be used to set the width of the lines.
         */
        LineStrip(false),

        /**
         * Identical to {@link #LineStrip} except that at the end
         * the last vertex is connected with the first to form a line.
         * to set the width of the lines.
         */
        LineLoop(false),

        /**
         * A primitive is a triangle. Each 3 vertices specify a single
         * triangle.
         */
        Triangles(true),

        /**
         * Similar to {@link #Triangles}, the first 3 vertices
         * specify a triangle, while subsequent vertices are combined with
         * the previous two to form a triangle.
         */
        TriangleStrip(false),

        /**
         * Similar to {@link #Triangles}, the first 3 vertices
         * specify a triangle, each 2 subsequent vertices are combined
         * with the very first vertex to make a triangle.
         */
        TriangleFan(false),

        /**
         * A combination of various triangle modes. It is best to avoid
         * using this mode as it may not be supported by all renderers.
         * be specified for this mode.
         */
        Hybrid(false),
        /**
         * Used for Tessellation only. Requires to set the number of vertices
         * for each patch (default is 3 for triangle tessellation)
         */
        Patch(true);
        private boolean listMode = false;

        private Mode(boolean listMode) {
            this.listMode = listMode;
        }

        /**
         * Returns true if the specified mode is a list mode (meaning
         * ,it specifies the indices as a linear list and not some special
         * format).
         * Will return true for the types {@link #Points}, {@link #Lines} and
         * {@link #Triangles}.
         *
         * @return true if the mode is a list type mode
         */
        public boolean isListMode() {
            return listMode;
        }
    }

    /**
     * The bounding volume that contains the mesh entirely.
     * By default a BoundingBox (AABB).
     */
//    private BoundingVolume meshBound =  new BoundingBox();
//
//    private CollisionData collisionTree = null;

//    private SafeArrayList<VertexBuffer> buffersList = new SafeArrayList<VertexBuffer>(VertexBuffer.class);
//    private IntMap<VertexBuffer> buffers = new IntMap<VertexBuffer>();
//    private VertexBuffer[] lodLevels;
    private float pointSize = 1;
    private float lineWidth = 1;

    private transient int vertexArrayID = -1;

    private int vertCount = -1;
    private int elementCount = -1;
    private int instanceCount = -1;
    private int patchVertexCount = 3; //only used for tessellation
    private int maxNumWeights = -1; // only if using skeletal animation

    private int[] elementLengths;
    private int[] modeStart;

    private Mode mode = Mode.Triangles;

    public Mode getMode() {
        return mode;
    }

}