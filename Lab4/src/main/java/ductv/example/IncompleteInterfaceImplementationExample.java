package ductv.example;

import java.util.logging.Logger;

interface Shape {
    void draw();
    void resize();
}

class Square implements Shape {
    private static final Logger logger = Logger.getLogger(Square.class.getName());

    @Override
    public void draw() {
        logger.info("Drawing square");
    }

    @Override
    public void resize() {
        // ví dụ implement đơn giản: chỉ log hành động resize
        logger.info("Resizing square");
    }

    // demo
    public static void main(String[] args) {
        Shape s = new Square();
        s.draw();
        s.resize();
    }
}
