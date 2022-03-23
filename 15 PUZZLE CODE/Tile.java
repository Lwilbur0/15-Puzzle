import pkg.*;

public class Tile {
        private Rectangle tile;
        public Text label;
        private int tileVal;
    public Tile(int col, int row, int n) {
        tile = new Rectangle(10 + (90 * col), 40 + (90 * row), 80, 80);
        tile.setColor(new Color(85, 199, 255));

        label = new Text(tile.getX(), tile.getY(), String.valueOf(n));
        label.grow(8, 8);
        label.center(tile);
        tileVal = n;
    } 
    public int getVal() {
        if (tileVal == 16) {
            return 0;
        }
        return tileVal;
    }
    public void draw() {
        tile.fill();
        label.draw();   
        label.center(tile);
    }
    public void undraw() {
        tile.clear();
        label.setText("");
    }
    public void translate(int x, int y) {
        tile.translate(x, y);
        label.translate(x, y);
    }
    public int getX() {
        return tile.getX();
    }
    public int getY() {
        return tile.getY();
    }
}