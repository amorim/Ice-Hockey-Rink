/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocg;

import util.AppState;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import drawing.strategy.BresenhamCircleStrategy;
import drawing.strategy.BresenhamLineStrategy;
import drawing.Drawer;
import drawing.strategy.EquationCircleStrategy;
import drawing.strategy.EquationLineStrategy;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.DrawerOptions;
import model.Grandstand;
import model.Line;
import model.Point;
import model.RendererOptions;
import util.Util;

/**
 *
 * @author Lucas Amorim
 */
public class Form extends javax.swing.JFrame {

    Point[] ps = new Point[2];
    AppState state = AppState.WAITING_RINK_POINTS;
    ArrayList<Grandstand> grandstands;
    int pointsUntilNow = 0, width, height;
    DrawerOptions doptions = new DrawerOptions();
    Drawer drawer = new Drawer(doptions);
    RendererOptions roptions = new RendererOptions();
    ProjetoCG glListener;
    Color color = Color.BLACK;
    private static final String helpText = "Please pick two points in the canvas using your mouse.";
    private static final String grandstandText = "<html><center>Click where you want the vertices of your grandstands to be.<br>When you're finished, press the right button of your mouse. You can remove vertices of your current grandstand pressing r in your keyboard.</center></html>";
    

    public Form() {
        initComponents();
        grandstands = new ArrayList<>();
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        width = jPanel1.getWidth();
        height = jPanel1.getHeight();
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glListener = new ProjetoCG(new GLU(), width, height, drawer, roptions, grandstands);
        updateColor();
        glcanvas.setSize(width, height);
        glcanvas.addGLEventListener(glListener);
        glcanvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (state == AppState.WAITING_GRANDSTAND_POINTS) {
                    if (grandstands.isEmpty()) {
                        grandstands.add(new Grandstand());
                    }
                    Grandstand g = grandstands.get(grandstands.size() - 1);
                    if (e.getButton() == MouseEvent.BUTTON3 && g.size() > 2) {
                        if (Util.collision(grandstands, new Line(g.points.get(0), g.points.get(g.size() - 1)), ps, true)) {
                            JOptionPane.showMessageDialog(Form.this, "Sorry, invalid shape.", "Intersection error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        g.add(g.points.get(0));
                        grandstands.add(new Grandstand());
                    }
                    else if (e.getButton() == MouseEvent.BUTTON1) {
                        if (g.points.size() > 0 && Util.collision(grandstands, new Line(new Point(e.getX(), height - e.getY()), g.points.get(g.points.size() - 1)), ps, false)) {
                            JOptionPane.showMessageDialog(Form.this, "Your line would intersect other lines. Please select another point.", "Intersection error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        g.add(new Point(e.getX(), height - e.getY()));
                    }
                    return;
                }

                if (pointsUntilNow > 1) {
                    return;
                }
                if (pointsUntilNow == 1) {
                    if (e.getX() < ps[0].x || height - e.getY() > ps[0].y) {
                        JOptionPane.showMessageDialog(Form.this, "Second point must be down and to the right of first point. Re-pick the points.", "Invalid point", JOptionPane.ERROR_MESSAGE);
                        pointsUntilNow = 0;
                        return;
                    }
                }
                ps[pointsUntilNow++] = new Point(e.getX(), height - e.getY());
                if (pointsUntilNow == 2) {
                    startDraw();
                    labelStatus.setText("");
                    state = AppState.WAITING_GRANDSTAND_POINTS;
                    labelStatus.setText(grandstandText);
                    roptions.setDrawGuide(true);
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

            }

        });
        glcanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                roptions.setCursorPos(new Point(e.getX(), height - e.getY()));
            }
        });
        glcanvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    if (grandstands.isEmpty())
                        return;
                    Grandstand g = grandstands.get(grandstands.size() - 1);
                    if (g.size() < 1)
                        return;
                    g.points.remove(g.size() - 1);
                }
            }
            
        });
        Animator animator = new Animator(glcanvas);
        jPanel1.add(glcanvas);
        animator.start();
    }

    private void startDraw() {
        double radius = (Math.abs(ps[0].x - ps[1].x) / 2.0 + Math.abs(ps[0].y - ps[1].y) / 2.0f) / 8.0f;
        roptions.startPoints = ps;
        roptions.borderRadius = radius;
        updateThickness();
        updateAlgorithm();
        updateColor();
        updateAngle();
        glListener.setDrawing(true);
    }

    private void updateThickness() {
        int thickness = sliderLineThickness.getValue();
        doptions.setSquareSize(thickness);
    }

    private void updateColor() {
        jPanelShowSelectedColor.setBackground(color);
        glListener.setColor(color);
        roptions.setColor(color);
    }

    private void updateAlgorithm() {
        int algo = comboBoxAlgorithm.getSelectedIndex();
        if (algo == 0) {
            glListener.setLineStrategy(new BresenhamLineStrategy(drawer));
            glListener.setCircleStrategy(new BresenhamCircleStrategy(drawer));
        } else {
            glListener.setLineStrategy(new EquationLineStrategy(drawer));
            glListener.setCircleStrategy(new EquationCircleStrategy(drawer));
        }
    }

    private void updateAngle() {
        int angle = sliderRinkAngle.getValue();
        roptions.setAngle(angle);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelShowSelectedColor = new javax.swing.JPanel();
        buttonPickColor = new javax.swing.JButton();
        sliderLineThickness = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        comboBoxAlgorithm = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sliderRinkAngle = new javax.swing.JSlider();
        labelStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(953, 966));
        setPreferredSize(new java.awt.Dimension(953, 966));
        setResizable(false);
        setSize(new java.awt.Dimension(953, 966));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 800));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 470, 800));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ice Hockey Rink");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 10, 950, -1));

        jLabel1.setText("Color");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, -1, 30));

        jPanelShowSelectedColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jPanelShowSelectedColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 30, 30));

        buttonPickColor.setText("Pick Color");
        buttonPickColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPickColorActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPickColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 130, 30));

        sliderLineThickness.setMaximum(10);
        sliderLineThickness.setMinimum(1);
        sliderLineThickness.setValue(1);
        sliderLineThickness.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderLineThicknessStateChanged(evt);
            }
        });
        getContentPane().add(sliderLineThickness, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, 280, -1));

        jLabel3.setText("Line Thickness");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, -1, 30));

        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 760, 110, 50));

        comboBoxAlgorithm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bresenham", "Equation of Line/Circle" }));
        comboBoxAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxAlgorithmActionPerformed(evt);
            }
        });
        getContentPane().add(comboBoxAlgorithm, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 620, 180, -1));

        jLabel4.setText("Algorithm");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 620, -1, 20));

        jLabel5.setText("Rink Angle");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 490, -1, 30));

        sliderRinkAngle.setMaximum(179);
        sliderRinkAngle.setValue(0);
        sliderRinkAngle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderRinkAngleStateChanged(evt);
            }
        });
        getContentPane().add(sliderRinkAngle, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 490, 270, -1));

        labelStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelStatus.setForeground(new java.awt.Color(255, 51, 51));
        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelStatus.setText("Please pick two points in the canvas using your mouse.");
        getContentPane().add(labelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 940, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked

    }//GEN-LAST:event_jPanel1MouseClicked

    private void sliderLineThicknessStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderLineThicknessStateChanged
        updateThickness();
    }//GEN-LAST:event_sliderLineThicknessStateChanged

    private void comboBoxAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxAlgorithmActionPerformed
        updateAlgorithm();
    }//GEN-LAST:event_comboBoxAlgorithmActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        glListener.setDrawing(false);
        labelStatus.setText(helpText);
        pointsUntilNow = 0;
        comboBoxAlgorithm.setSelectedIndex(0);
        sliderLineThickness.setValue(1);
        sliderRinkAngle.setValue(0);
        color = Color.BLACK;
        updateColor();
        grandstands.clear();
        state = AppState.WAITING_RINK_POINTS;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void buttonPickColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPickColorActionPerformed
        Color newColor = JColorChooser.showDialog(
                Form.this,
                "Choose a color for lines and circles",
                color);
        if (newColor == null) {
            return;
        }
        color = newColor;
        updateColor();
    }//GEN-LAST:event_buttonPickColorActionPerformed

    private void sliderRinkAngleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderRinkAngleStateChanged
        updateAngle();
    }//GEN-LAST:event_sliderRinkAngleStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPickColor;
    private javax.swing.JComboBox<String> comboBoxAlgorithm;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelShowSelectedColor;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JSlider sliderLineThickness;
    private javax.swing.JSlider sliderRinkAngle;
    // End of variables declaration//GEN-END:variables

}
