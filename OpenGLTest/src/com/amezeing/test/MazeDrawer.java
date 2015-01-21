/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amezeing.test;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

/**
 *
 * @author Bomber
 */
public class MazeDrawer implements GLEventListener, KeyListener {

    private int WIDTH = 1024;
    private int HEIGHT = 768;

    private int N;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;

    Frame frame;
    GLCanvas canvas;
    Animator animator;

    public static void main(String[] args) {
        MazeDrawer mazeDrawer = new MazeDrawer(127);

        mazeDrawer.draw();
//        mazeDrawer.solve();
    }

    public MazeDrawer(int N) {
        this.N = N;

//~ StdDraw.setScale(0, N + 2);
        init();
        generate();
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            visited[x][0] = visited[x][N + 1] = true;
        }
        for (int y = 0; y < N + 2; y++) {
            visited[0][y] = visited[N + 1][y] = true;
        }

        // initialze all walls as present
        north = new boolean[N + 2][N + 2];
        east = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            for (int y = 0; y < N + 2; y++) {
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
            }
        }

        this.frame = new Frame("Simple Maze Drawer");
        this.canvas = new GLCanvas();
        this.animator = new Animator(canvas);

        canvas.addGLEventListener(this);
        frame.addKeyListener(this);
        canvas.addKeyListener(this);

        frame.add(canvas);
        frame.setSize(WIDTH, HEIGHT);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
    }

    // generate the maze
    private void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1] || !visited[x - 1][y]) {

            // pick random neighbor (could use Knuth's trick instead)
            while (true) {
                double r = Math.random();
                if (r < 0.25 && !visited[x][y + 1]) {
                    north[x][y] = south[x][y + 1] = false;
                    generate(x, y + 1);
                    break;
                } else if (r >= 0.25 && r < 0.50 && !visited[x + 1][y]) {
                    east[x][y] = west[x + 1][y] = false;
                    generate(x + 1, y);
                    break;
                } else if (r >= 0.5 && r < 0.75 && !visited[x][y - 1]) {
                    south[x][y] = north[x][y - 1] = false;
                    generate(x, y - 1);
                    break;
                } else if (r >= 0.75 && r < 1.00 && !visited[x - 1][y]) {
                    west[x][y] = east[x - 1][y] = false;
                    generate(x - 1, y);
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void generate() {
        generate(1, 1);
    }

    // solve the maze using depth-first search
    private void solve(int x, int y) {
        if (x == 0 || y == 0 || x == N + 1 || y == N + 1) {
            return;
        }
        if (done || visited[x][y]) {
            return;
        }
        visited[x][y] = true;
//~ StdDraw.setPenColor(StdDraw.BLUE);
//~ StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
//~ StdDraw.show(3);

        // reached middle
        if (x == N / 2 && y == N / 2) {
//            done = true;
        }

        if (!north[x][y]) {
            solve(x, y + 1);
        }
        if (!east[x][y]) {
            solve(x + 1, y);
        }
        if (!south[x][y]) {
            solve(x, y - 1);
        }
        if (!west[x][y]) {
            solve(x - 1, y);
        }

        if (done) {
            return;
        }
//~ StdDraw.setPenColor(StdDraw.ORANGE);
//~ StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
//~ StdDraw.show(3);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                visited[x][y] = false;
            }
        }
        done = false;
        solve(1, 1);
    }

    // draw the maze
    public void draw() {
        frame.setVisible(true);
        animator.start();

        display(canvas);
//~ StdDraw.show(100);
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

//        gl.glClearColor(.13f, .78f, .52f, 1);
        gl.glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        WIDTH = width;
        HEIGHT = height;

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, N + 2, 0, N + 2, -1, 1);

// toutes les transformations suivantes s´appliquent au modèle de vue
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        render_scene(drawable);

// trace la scène graphique qui vient juste d'être définie
        gl.glFlush();
    }

    private void render_scene(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
//        gl.glColor3i(200, 50, 50);
//        StdDraw.setPenColor(StdDraw.RED);
//~ StdDraw.filledCircle(N / 2.0 + 0.5, N / 2.0 + 0.5, 0.375);
//~ StdDraw.filledCircle(1.5, 1.5, 0.375);

        gl.glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);

//        StdDraw.setPenColor(StdDraw.BLACK);
        gl.glColor3d(Math.random(), Math.random(), Math.random());
        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                gl.glLoadIdentity();
                gl.glTranslatef(x, y, 0);
                if (south[x][y]) {
//                    StdDraw.line(x, y, x + 1, y);
                    gl.glBegin(GL.GL_LINES);
                    gl.glVertex2i(0, 0);   // Top
                    gl.glVertex2i(1, 0); // Bottom Left
                    gl.glEnd();
                }
                if (north[x][y]) {
//                    StdDraw.line(x, y + 1, x + 1, y + 1);
                    gl.glBegin(GL.GL_LINES);
                    gl.glVertex2i(0, 1);   // Top
                    gl.glVertex2i(1, 1); // Bottom Left
                    gl.glEnd();
                }
                if (west[x][y]) {
//                    StdDraw.line(x, y, x, y + 1);
                    gl.glBegin(GL.GL_LINES);
                    gl.glVertex2i(0, 0);   // Top
                    gl.glVertex2i(0, 1); // Bottom Left
                    gl.glEnd();
                }
                if (east[x][y]) {
//                    StdDraw.line(x + 1, y, x + 1, y + 1);
                    gl.glBegin(GL.GL_LINES);
                    gl.glVertex2i(1, 0);   // Top
                    gl.glVertex2i(1, 1); // Bottom Left
                    gl.glEnd();
                }
            }
        }
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}
