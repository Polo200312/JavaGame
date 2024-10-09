package edu.usu.graphics;

public class Rectangle {
    public float left;
    public float top;
    public float width;
    public float height;
    public float z;

    public Rectangle(float left, float top, float width, float height) {
        this(left, top, width, height, 0.0f);
    }

    public Rectangle(float left, float top, float width, float height, float z) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.z = z;
    }
}
