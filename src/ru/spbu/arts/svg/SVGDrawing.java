package ru.spbu.arts.svg;

public class SVGDrawing {
    public static void main(String[] args) {
        Tag rect1 = new Tag("rect");
        rect1.set("x", "10");
        rect1.set("y", "10");
        rect1.set("width", "100");
        rect1.set("height", "100");
        rect1.set("style", "stroke: #ff0000; fill: #0000ff");

        Tag rect2 = new Tag("rect");
        rect2.set("x", "20");
        rect2.set("y", "20");
        rect2.set("width", "100");
        rect2.set("height", "100");
        rect2.set("style", "stroke: #ff0000; fill: #00ff00");

        Tag g = new Tag("g", TagType.OPEN);
        g.set("transform", "translate(150, 150)");
        Tag gClose = new Tag("g", TagType.CLOSE);

        try (
            SVG svg = new SVG("c.svg")) {
            svg.addTag(rect1);
            svg.addTag(rect2);
            svg.addTag(g);
            svg.addTag(rect1);
            svg.addTag(rect2);
            svg.addTag(gClose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
