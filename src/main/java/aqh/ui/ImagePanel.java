package aqh.ui;


import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;

import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.swing.GroupLayout;

public class ImagePanel extends javax.swing.JPanel {

    private BufferedImage bImage;
    private double zoom = 1.0;
    private Point offset = new Point(0, 0);
    private Point dragStart;
    
    public ImagePanel() {
        initComponents();
        bImage = null;
        repaint();
    }

   private void initComponents() {

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseWheelListener((MouseWheelEvent evt) -> {
            formMouseWheelMoved(evt);
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                formMousePressed(evt);
            }
            @Override
            public void mouseReleased(MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    private void formMousePressed(MouseEvent evt) {
        dragStart = evt.getPoint();
    }

    private void formMouseReleased(MouseEvent evt) {
        dragStart = null;
    }

    private void formMouseDragged(MouseEvent evt) {
        Point dragEnd = evt.getPoint();
        int dx = dragEnd.x - dragStart.x;
        int dy = dragEnd.y - dragStart.y;
        offset.translate(dx, dy);
        dragStart = dragEnd;
        repaint();
    }

    private void formMouseWheelMoved(MouseWheelEvent evt) {
        if (evt.getWheelRotation() < 0) {
            zoomIn();
        } else {
            zoomOut();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Apply zoom and offset transforms
        AffineTransform tx = new AffineTransform();
        tx.translate(offset.getX(), offset.getY());
        tx.scale(zoom, zoom);
        g2.setTransform(tx);

        // Draw image
        g2.drawImage(bImage, 0, 0, null);
    }
    
    public void setImage(BufferedImage image) {
        this.bImage = image;
        this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        this.revalidate();
        this.repaint();
    }
    
    public void zoomIn() {
        zoom *= 1.1;
        this.revalidate();
        this.repaint();
    }

    public void zoomOut() {
        zoom /= 1.1;
        this.revalidate();
        this.repaint();
    }

    public void setOffset(int x, int y) {
        this.offset = new Point(x, y);
        this.repaint();
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
        this.revalidate();
        this.repaint();
    }
}
