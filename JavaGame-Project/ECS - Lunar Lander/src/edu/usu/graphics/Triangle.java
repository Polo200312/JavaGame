package edu.usu.graphics;

import org.joml.Vector3f;

public class Triangle {
    public Vector3f pt1;
    public Vector3f pt2;
    public Vector3f pt3;

    public Triangle(Vector3f pt1, Vector3f pt2, Vector3f pt3) {
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.pt3 = pt3;
    }
}
