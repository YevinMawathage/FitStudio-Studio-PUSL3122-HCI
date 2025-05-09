import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf; // FlatLaf for modern UI
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static org.lwjgl.assimp.Assimp.*;

public class OBJViewer extends JFrame implements GLEventListener, MouseMotionListener, MouseListener, KeyListener {

    private GLCanvas glCanvas;
    private GLU glu = new GLU();
    private List<String> furniturePaths = new ArrayList<>();
    private List<String> furnitureNames = new ArrayList<>();
    private List<AIScene> furnitureScenes = new ArrayList<>();
    private List<float[]> furniturePositions = new ArrayList<>();
    private List<float[]> furnitureRotations = new ArrayList<>();
    private Texture furnitureTexture;
    private float roomLength = 10.0f;
    private float roomWidth = 10.0f;
    private float rotationX = 0.0f;
    private float rotationY = 0.0f;
    private int lastMouseX, lastMouseY;
    private boolean isRotating = false;
    private float moveSpeed = 0.1f;
    private JList<String> furnitureList;
    private boolean is2DView = false;
    private JButton viewButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf()); // Set modern UI theme
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            new OBJViewer();
        });
    }

    public OBJViewer() {
        System.setProperty("jogl.disable.openglcore", "true");
        System.setProperty("jogl.gldebug", "true");
        System.setProperty("sun.java2d.d3d", "false");

        setTitle("Room Designer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GLProfile.initSingleton();
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glCanvas = new GLCanvas(glCapabilities);
        glCanvas.addGLEventListener(this);
        glCanvas.addMouseMotionListener(this);
        glCanvas.addMouseListener(this);
        glCanvas.addKeyListener(this);

        initializeFurniture();

        add(glCanvas, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(createRoomPanel());
        bottomPanel.add(createFurniturePanel());

        add(bottomPanel, BorderLayout.SOUTH);

        setSize(1080, 1000);  // Set the window size
        setLocationRelativeTo(null);  // Center the window
        setVisible(true);

        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();
    }

    private void initializeFurniture() {
        String basePath = "furniture-fit/assets/";

        String[] filenames = {
                "couch.fbx",
                "chair_A.fbx",
                "bed_double_A.fbx",
                "cabinet_medium.fbx",
                "lamp_standing.fbx",
                "table_medium.fbx"
        };

        String[] displayNames = {
                "Couch",
                "Chair",
                "Bed Double",
                "Cabinet",
                "Lamp",
                "Table Medium"
        };



        for (int i = 0; i < filenames.length; i++) {
            furniturePaths.add(basePath + filenames[i]);
            furnitureNames.add(displayNames[i]);
            furniturePositions.add(new float[]{0.0f, 0.0f, 0.0f});
            furnitureRotations.add(new float[]{0.0f, 0.0f, 0.0f});
        }
    }

    // Panel for Room Settings


    // Panel for Furniture Catalogue
    private JPanel createFurniturePanel() {
        // Create the main furniture panel and set background to black
        JPanel furniturePanel = new JPanel(new BorderLayout());
        furniturePanel.setBackground(new Color(0, 0, 0));  // Black background
        furniturePanel.setBorder(BorderFactory.createTitledBorder(null, "Furniture Catalogue",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 14), Color.WHITE));

        // Create the list model for furniture names
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String name : furnitureNames) {
            listModel.addElement(name);
        }
        furnitureList = new JList<>(listModel);
        furnitureList.setVisibleRowCount(5);

        // Set background for the furniture list and scroll pane to black
        furnitureList.setBackground(new Color(0, 0, 0));  // Black background for list
        furnitureList.setForeground(Color.WHITE);  // White text for list
        JScrollPane scrollPane = new JScrollPane(furnitureList);
        scrollPane.setPreferredSize(new Dimension(200, 120));
        scrollPane.setBackground(new Color(0, 0, 0));  // Set scrollPane background to black
        scrollPane.getViewport().setBackground(new Color(0, 0, 0));  // Set viewport background to black

        // Create center panel and set background to black
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(0, 0, 0));  // Set center panel background to black
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Create and set the label for "Furniture" and change its color to white
        JLabel listLabel = new JLabel("Furniture:");
        listLabel.setForeground(Color.WHITE);  // Set label text color to white
        listLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(listLabel);
        centerPanel.add(scrollPane);

        // Create button panel and set background to black
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 0, 0));  // Set button panel background to black
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Create the "Add Furniture" button and set its background to blue and text to white
        JButton addButton = new JButton("Add Furniture");
        addButton.setPreferredSize(new Dimension(200, 40));
        addButton.setBackground(new Color(0x007BFF));  // Blue background
        addButton.setForeground(Color.WHITE);  // White text
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> {
            int selectedIndex = furnitureList.getSelectedIndex();
            if (selectedIndex != -1) {
                String furniturePath = furniturePaths.get(selectedIndex);
                AIScene scene = aiImportFile(furniturePath, aiProcess_Triangulate | aiProcess_FlipUVs);
                if (scene != null) {
                    furnitureScenes.add(scene);
                    furniturePositions.add(new float[]{0.0f, 0.0f, 0.0f});
                    furnitureRotations.add(new float[]{0.0f, 0.0f, 0.0f});

                    // Automatically select the newly added item
                    int newIndex = furnitureScenes.size() - 1;
                    furnitureList.setSelectedIndex(newIndex);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to load furniture: " + aiGetErrorString());
                }
            }
        });

        // Create the "2D/3D" view button and set its background and text color
        JButton viewButton = new JButton("2D");
        viewButton.setPreferredSize(new Dimension(200, 40));  // Same size as Add Furniture button
        viewButton.setBackground(new Color(0x007BFF));  // Same blue background
        viewButton.setForeground(Color.WHITE);  // White text
        viewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        viewButton.addActionListener(e -> {
            is2DView = !is2DView;
            if (is2DView) {
                viewButton.setText("3D");
                rotationX = 66.0f;
                rotationY = -0.5f;
            } else {
                viewButton.setText("2D");
                rotationX = 0.0f;
                rotationY = 0.0f;
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(viewButton);

        // Add the center panel and button panel to the furniture panel
        furniturePanel.add(centerPanel, BorderLayout.CENTER);
        furniturePanel.add(buttonPanel, BorderLayout.SOUTH);

        return furniturePanel;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        try {
            BufferedImage textureImage = ImageIO.read(new File("furniture-fit/assets/furniturebits_texture.png"));
            furnitureTexture = AWTTextureIO.newTexture(drawable.getGL().getGLProfile(), textureImage, true);
            furnitureTexture.setTexParameteri(drawable.getGL(), GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            furnitureTexture.setTexParameteri(drawable.getGL(), GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, (double) getWidth() / (double) getHeight(), 1.0, 100.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0, 10, 20, 0, 0, 0, 0, 1, 0);

        if (!is2DView) {
            gl.glRotatef(rotationX, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(rotationY, 0.0f, 1.0f, 0.0f);
        } else {
            gl.glRotatef(66.0f, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(-0.5f, 0.0f, 1.0f, 0.0f);
        }

        renderFloor(gl);
        renderFurniture(gl);
    }

    private void renderFloor(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-roomWidth / 2, 0.0f, -roomLength / 2);
        gl.glVertex3f(roomWidth / 2, 0.0f, -roomLength / 2);
        gl.glVertex3f(roomWidth / 2, 0.0f, roomLength / 2);
        gl.glVertex3f(-roomWidth / 2, 0.0f, roomLength / 2);
        gl.glEnd();
    }

    private void renderFurniture(GL2 gl) {
        for (int i = 0; i < furnitureScenes.size(); i++) {
            AIScene scene = furnitureScenes.get(i);
            float[] position = furniturePositions.get(i);
            float[] rotation = furnitureRotations.get(i);
            if (scene != null && position != null && rotation != null) {
                gl.glPushMatrix();
                gl.glTranslatef(position[0], position[1], position[2]);
                gl.glRotatef(rotation[1], 0.0f, 1.0f, 0.0f);
                gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                renderScene(gl, scene);
                gl.glPopMatrix();
            }
        }
    }

    private void renderScene(GL2 gl, AIScene scene) {
        int numMeshes = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();
        for (int i = 0; i < numMeshes; i++) {
            AIMesh mesh = AIMesh.create(meshesBuffer.get(i));
            renderMesh(gl, mesh);
        }
    }

    private void renderMesh(GL2 gl, AIMesh mesh) {
        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();
        AIVector3D.Buffer textureCoords = mesh.mTextureCoords(0);
        AIFace.Buffer faces = mesh.mFaces();

        furnitureTexture.enable(gl);
        furnitureTexture.bind(gl);

        gl.glBegin(GL2.GL_TRIANGLES);
        for (int i = 0; i < faces.limit(); i++) {
            AIFace face = faces.get(i);
            IntBuffer indices = face.mIndices();
            for (int j = 0; j < indices.limit(); j++) {
                int index = indices.get(j);
                AIVector3D vertex = vertices.get(index);
                AIVector3D normal = normals.get(index);
                AIVector3D texCoord = textureCoords != null ? textureCoords.get(index) : null;

                if (texCoord != null) {
                    gl.glTexCoord2f(texCoord.x(), texCoord.y());
                }
                gl.glNormal3f(normal.x(), normal.y(), normal.z());
                gl.glVertex3f(vertex.x(), vertex.y(), vertex.z());
            }
        }
        gl.glEnd();

        furnitureTexture.disable(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        for (AIScene scene : furnitureScenes) {
            aiReleaseImport(scene);
        }
        furnitureTexture.destroy(drawable.getGL());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isRotating && !is2DView) {
            int dx = e.getX() - lastMouseX;
            int dy = e.getY() - lastMouseY;
            rotationX += dy * 0.5f;
            rotationY += dx * 0.5f;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !is2DView) {
            isRotating = true;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isRotating = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            moveFurniture(-moveSpeed, 0.0f, 0.0f);
        } else if (keyCode == KeyEvent.VK_D) {
            moveFurniture(moveSpeed, 0.0f, 0.0f);
        } else if (keyCode == KeyEvent.VK_W) {
            moveFurniture(0.0f, 0.0f, -moveSpeed);
        } else if (keyCode == KeyEvent.VK_S) {
            moveFurniture(0.0f, 0.0f, moveSpeed);
        } else if (keyCode == KeyEvent.VK_Q) {
            rotateFurniture(-5.0f);
        } else if (keyCode == KeyEvent.VK_E) {
            rotateFurniture(5.0f);
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            removeSelectedFurniture();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void moveFurniture(float dx, float dy, float dz) {
        int selectedIndex = furnitureList.getSelectedIndex();
        if (selectedIndex != -1) {
            float[] position = furniturePositions.get(selectedIndex);
            position[0] += dx;
            position[1] += dy;
            position[2] += dz;
        }
    }


    private void rotateFurniture(float angle) {
        int selectedIndex = furnitureList.getSelectedIndex();
        if (selectedIndex != -1) {
            float[] rotation = furnitureRotations.get(selectedIndex);
            rotation[1] += angle;
        }
    }

    private void removeSelectedFurniture() {
        int selectedIndex = furnitureList.getSelectedIndex();
        if (selectedIndex != -1) {
            furnitureScenes.remove(selectedIndex);
            furniturePositions.remove(selectedIndex);
            furnitureRotations.remove(selectedIndex);
        }
    }
}