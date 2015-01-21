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
public class Primitive1 implements GLEventListener, KeyListener {

    private final Frame frame;
    private final GLCanvas canvas;
    private Animator animator;

    public static void main(String[] args) {
        new Primitive1().draw();
    }

    public Primitive1() {
        super();

        this.frame = new Frame("Simple Maze Drawer");
        this.canvas = new GLCanvas();
        this.animator = new Animator(canvas);

        canvas.addGLEventListener(this);
        frame.addKeyListener(this);
        canvas.addKeyListener(this);

        frame.add(canvas);
        frame.setSize(1024, 768);
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

    public void draw() {
        frame.setVisible(true);
        animator.start();

        display(canvas);
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClearColor(0, 0, 0, 1);
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
// définit la couleur de tracé
        gl.glColor3f(1.0f, 1.0f, 1.0f);

// initialise la matrice de modélisation
        gl.glLoadIdentity();
// Translate l'objet
        gl.glTranslatef(-5.0f, 5.0f, 0.0f);
// dessine les sommets selon la primitive GL.GL_LINES
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glVertex2f(-2.0f, -1.0f);
        gl.glEnd();

//on décale de 5 unités sur Oy le prochain tracé
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 5.0f, 0.0f);

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(5.0f, 5.0f, 0.0f);

        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(-5.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(5.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(-5.0f, -5.0f, 0.0f);

        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, -5.0f, 0.0f);

        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();

        gl.glLoadIdentity();
        gl.glTranslatef(5.0f, -5.0f, 0.0f);

        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 2.0f);
        gl.glVertex2f(2.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(-2.0f, -2.0f);
        gl.glEnd();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-8.0, 8.0, -8.0, 8.0, -8.0, 8.0);

// toutes les transformations suivantes s´appliquent au modèle de vue
        gl.glMatrixMode(GL.GL_MODELVIEW);
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
