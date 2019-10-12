package com.dozenx.game.scene.plugins.ogre;

import com.dozenx.game.scene.Mesh;
import com.dozenx.game.scene.VertexBuffer;
import com.dozenx.game.scene.VertexBuffer.Usage;
import com.dozenx.game.scene.VertexBuffer.Type;
import com.dozenx.game.scene.VertexBuffer.Format;
import com.dozenx.game.util.BufferUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.dozenx.game.util.SAXUtil.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.dozenx.game.util.SAXUtil.parseBool;
import static com.dozenx.game.util.SAXUtil.parseInt;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:42 2019/10/11
 * @Modified By:
 */
public class MeshLoader {
    /**
     * @Author: dozen.zhang
     * @Description:
     * @Date: Created in 17:07 2019/10/9
     * @Modified By:
     */
    public static class ModelLoader extends DefaultHandler {

        private static final Logger logger = Logger.getLogger(MeshLoader.class.getName());
        public static boolean AUTO_INTERLEAVE = true;

        private String meshName;
        private String folderName;
        private boolean usesBigIndices;
        // Data per submesh/sharedgeom
        private ShortBuffer sb;
        private IntBuffer ib;
        private FloatBuffer fb;
        private VertexBuffer vb;
        private Mesh mesh;
        private Geometry geom;
        private String ignoreUntilEnd = null;
        private boolean submeshNamesHack;
        @Override
        public void startDocument() {
//            geoms.clear();
//            lodLevels.clear();

            sb = null;
            ib = null;
            fb = null;
            vb = null;
            mesh = null;
            geom = null;
//            sharedMesh = null;
//
//            usesSharedMesh.clear();
//            usesSharedVerts = false;
//            vertCount = 0;
//            meshIndex = 0;
//            texCoordIndex = 0;
            ignoreUntilEnd = null;

//            animData = null;
//
//            actuallyHasWeights = false;
//            submeshNamesHack = false;
//            indicesData = null;
//            weightsFloatData = null;
        }

        @Override
        public void endDocument() {
        }
        private void pushIndex(int index) {
            if (ib != null) {
                ib.put(index);
            } else {
                sb.put((short) index);
            }
        }

        private void pushFace(String v1, String v2, String v3) throws SAXException {
            // TODO: fan/strip support
            switch (mesh.getMode()) {
                case Triangles:
                    pushIndex(parseInt(v1));
                    pushIndex(parseInt(v2));
                    pushIndex(parseInt(v3));
                    break;
                case Lines:
                    pushIndex(parseInt(v1));
                    pushIndex(parseInt(v2));
                    break;
                case Points:
                    pushIndex(parseInt(v1));
                    break;
            }
        }
        private void startFaces(String count) throws SAXException {
            int numFaces = parseInt(count);
            int indicesPerFace = 0;

            switch (mesh.getMode()) {
                case Triangles:
                    indicesPerFace = 3;
                    break;
                case Lines:
                    indicesPerFace = 2;
                    break;
                case Points:
                    indicesPerFace = 1;
                    break;
                default:
                    throw new SAXException("Strips or fans not supported!");
            }

            int numIndices = indicesPerFace * numFaces;

            vb = new VertexBuffer(VertexBuffer.Type.Index);
            if (!usesBigIndices) {
                sb = BufferUtils.createShortBuffer(numIndices);
                ib = null;
                vb.setupData(Usage.Static, indicesPerFace, Format.UnsignedShort, sb);
            } else {
                ib = BufferUtils.createIntBuffer(numIndices);
                sb = null;
                vb.setupData(Usage.Static, indicesPerFace, Format.UnsignedInt, ib);
            }
            mesh.setBuffer(vb);
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attribs) throws SAXException {
            if (ignoreUntilEnd != null) {
                return;
            }

            if (qName.equals("texcoord")) {
                pushTexCoord(attribs);
            } else if (qName.equals("mesh")) {
                // ok
            }  else if (qName.equals("submeshes")) {
                // ok
            }else if (qName.equals("submesh")) {
                if (submeshNamesHack) {
                    // Hack for blender2ogre only  基本不用
//                    startSubmeshName(attribs.getValue("index"), attribs.getValue("name"));
                } else {
                    startSubMesh(attribs.getValue("material"),
                            attribs.getValue("usesharedvertices"),
                            attribs.getValue("use32bitindexes"),
                            attribs.getValue("operationtype"));
                }
            } else if (qName.equals("faces")) {
                startFaces(attribs.getValue("count"));
            }  else if (qName.equals("face")) {
                pushFace(attribs.getValue("v1"),
                        attribs.getValue("v2"),
                        attribs.getValue("v3"));
            }else if (qName.equals("vertexboneassignment")) {
                pushBoneAssign(attribs.getValue("vertexindex"),
                        attribs.getValue("boneindex"),
                        attribs.getValue("weight"));
            }  else if (qName.equals("position")) {
                pushAttrib(Type.Position, attribs);
            } else if (qName.equals("normal")) {
                pushAttrib(Type.Normal, attribs);
            } else if (qName.equals("tangent")) {
                pushTangent(attribs);
            } else if (qName.equals("binormal")) {
                pushAttrib(Type.Binormal, attribs);
            } else if (qName.equals("colour_diffuse")) {
                pushColor(attribs);
            } else if (qName.equals("vertex")) {
                startVertex();
            }else if (qName.equals("geometry")) {
                String count = attribs.getValue("vertexcount");
                if (count == null) {
                    count = attribs.getValue("count");
                }
                startGeometry(count);
            } else if (qName.equals("vertexbuffer")) {
                startVertexBuffer(attribs);
            } else if (qName.equals("lodfacelist")) {
                startLodFaceList(attribs.getValue("submeshindex"),
                        attribs.getValue("numfaces"));
            } else if (qName.equals("lodgenerated")) {
                startLodGenerated(attribs.getValue("fromdepthsquared"));
            } else if (qName.equals("levelofdetail")) {
                startLevelOfDetail(attribs.getValue("numlevels"));
            } else if (qName.equals("boneassignments")) {
                startBoneAssigns();
            } else if (qName.equals("sharedgeometry")) {
                String count = attribs.getValue("vertexcount");
                if (count == null) {
                    count = attribs.getValue("count");
                }

                if (count != null && !count.equals("0")) {
                    startSharedGeom(count);
                }
            } else if (qName.equals("skeletonlink")) {
                startSkeleton(attribs.getValue("name"));
            } else if (qName.equals("submeshnames")) {
                // ok
                // setting submeshNamesHack to true will make "submesh" tag be interpreted
                // as a "submeshname" tag.
                submeshNamesHack = true;
            } else if (qName.equals("submeshname")) {
                startSubmeshName(attribs.getValue("index"), attribs.getValue("name"));
            } else {
                logger.log(Level.WARNING, "Unknown tag: {0}. Ignoring.", qName);
                ignoreUntilEnd = qName;
            }
        }

        @Override
        public void endElement(String uri, String name, String qName) {
            if (ignoreUntilEnd != null) {
                if (ignoreUntilEnd.equals(qName)) {
                    ignoreUntilEnd = null;
                }
                return;
            }


            // If submesh hack is enabled, ignore any submesh/submeshes
            // end tags.
            if (qName.equals("submesh") && !submeshNamesHack) {
                usesBigIndices = false;
                geom = null;
                mesh = null;
            } else if (qName.equals("submeshes") && !submeshNamesHack) {
                // IMPORTANT: restore sharedmesh, for use with shared boneweights
                geom = null;
                mesh = sharedMesh;
                usesSharedVerts = false;
            } else if (qName.equals("faces")) {
                if (ib != null) {
                    ib.flip();
                } else {
                    sb.flip();
                }

                vb = null;
                ib = null;
                sb = null;
            } else if (qName.equals("vertexbuffer")) {
                fb = null;
                vb = null;
            } else if (qName.equals("geometry")
                    || qName.equals("sharedgeometry")) {
                // finish writing to buffers
                for (VertexBuffer buf : mesh.getBufferList().getArray()) {
                    Buffer data = buf.getData();
                    if (data.position() != 0) {
                        data.flip();
                    }
                }
                mesh.updateBound();
                mesh.setStatic();

                if (qName.equals("sharedgeometry")) {
                    geom = null;
                    mesh = null;
                }
            } else if (qName.equals("lodfacelist")) {
                sb.flip();
                vb = null;
                sb = null;
            } else if (qName.equals("levelofdetail")) {
                endLevelOfDetail();
            } else if (qName.equals("boneassignments")) {
                endBoneAssigns();
            } else if (qName.equals("submeshnames")) {
                // Restore default handling for "submesh" tag.
                submeshNamesHack = false;
            }
        }
    }
    boolean usesBigIndices;
    boolean usesSharedVerts;
    List<Boolean> usesSharedMesh =new ArrayList<>();
    private void startSubMesh(String matName, String usesharedvertices, String use32bitIndices, String opType) throws SAXException {
        mesh = new Mesh();
        if (opType == null || opType.equals("triangle_list")) {
            mesh.setMode(Mesh.Mode.Triangles);
            //} else if (opType.equals("triangle_strip")) {
            //    mesh.setMode(Mesh.Mode.TriangleStrip);
            //} else if (opType.equals("triangle_fan")) {
            //    mesh.setMode(Mesh.Mode.TriangleFan);
        } else if (opType.equals("line_list")) {
            mesh.setMode(Mesh.Mode.Lines);
        } else {
            throw new SAXException("Unsupported operation type: " + opType);
        }

        usesBigIndices = parseBool(use32bitIndices, false);
        usesSharedVerts = parseBool(usesharedvertices, false);
        if (usesSharedVerts) {
            usesSharedMesh.add(true);

            // Old code for buffer sharer
            // import vertexbuffers from shared geom
//            IntMap<VertexBuffer> sharedBufs = sharedMesh.getBuffers();
//            for (Entry<VertexBuffer> entry : sharedBufs) {
//                mesh.setBuffer(entry.getValue());
//            }
        } else {
            usesSharedMesh.add(false);
        }

        if (meshName == null) {
            geom = new Geometry("OgreSubmesh-" + (++meshIndex), mesh);
        } else {
            geom = new Geometry(meshName + "-geom-" + (++meshIndex), mesh);
        }

        if (usesSharedVerts) {
            // Old code for buffer sharer
            // this mesh is shared!
            //geom.setUserData(UserData.JME_SHAREDMESH, sharedMesh);
        }

        applyMaterial(geom, matName);
        geoms.add(geom);
    }
}
