
import java.awt.*;  
import javax.swing.JFrame;
import java.util.ArrayList;

public class Node
{
    int x_pos;
    int y_pos;
    int width = 40;
    int height = 40;
    String type;
    Color color;
    double g_score;
    double h_score;
    double f_score;
    ArrayList<Node> neighbors = new ArrayList<Node>();
    Node parent;
    
    
    public Node(int x, int y, String t) {
        this.x_pos = x;
        this.y_pos = y;
        this.type = t;
        this.set_color();
    }
    
    public void set_color() {
        if (this.type.equals("start")) {
            this.color = Color.GREEN;
        } else if (this.type.equals("end")) {
            this.color = Color.RED;
        } else if (this.type.equals("space")) {
            this.color = Color.WHITE;
        } else if (this.type.equals("wall")) {
            this.color = Color.BLACK;
        } else if (this.type.equals("open")) {
            this.color = new Color(200, 200, 0);
        } else if (this.type.equals("closed")) {
            this.color = new Color(0, 200, 200);
        } else if (this.type.equals("path")) {
            this.color = new Color(100, 100, 200);
        }
    }
    
    public ArrayList get_neighbors(Node[][] grid) {
        ArrayList<Node> n = new ArrayList<Node>();
        Node n1;
        Node n2;
        Node n3;
        Node n4;
        
        if (this.x_pos > 0) {
            n1 = grid[this.y_pos][this.x_pos - 1];
            n.add(n1);
        }
        if (this.x_pos < 19) {
            n2 = grid[this.y_pos][this.x_pos + 1];
            n.add(n2);
        }
        if (this.y_pos > 0) {
            n3 = grid[this.y_pos - 1][this.x_pos];
            n.add(n3);
        }
        if (this.y_pos < 19) {
            n4 = grid[this.y_pos + 1][this.x_pos];
            n.add(n4);
        }
        
        return n;
    }
    
    public int[] get_pos() {
        int[] pos = {this.x_pos, this.y_pos};
        return pos;
    }
    public static Color get_col(Node n) {
        return n.color;
    }
    public int g() {
        int g;
        
        int[] pos = this.get_pos();
        g = (20 - pos[0]) + (20 - pos[1]);
        
        return g;
    }
    
    public double h() {
        
        int[] pos = this.get_pos();
        double h = Math.pow(pos[0], 2) + Math.pow(pos[1], 2);
        
        return Math.sqrt(h);
    }
    
    public double f() {
        return this.h() + this.g();
    }
    
    public String toString() {
        String txt = "[" + this.x_pos + ", " + this.y_pos + "] Type: " + this.type;
        
        return txt;
    }
}
