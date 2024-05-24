import java.awt.*;  
import javax.swing.JFrame;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
  
public class DisplayGraphics extends Canvas {
    static Node[][] grid = make_grid(20, 20);
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void paint(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Node n = grid[j][i];
                g.setColor(n.color);
                g.fillRect(j * n.width, i * n.height, n.width, n.height);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.print('\u000C');
        DisplayGraphics m = new DisplayGraphics(); 
        JFrame f = new JFrame();  
        f.add(m);
        f.setSize(825,843); 
        f.setVisible(true);
        Random rand = new Random();

        make_walls(grid);
        ArrayList<Node> open = new ArrayList<Node>();
        Node current = grid[0][0];
        open.add(current);
        ArrayList<Node> closed = new ArrayList<Node>();
        ArrayList<Node> neighbors = new ArrayList<Node>();
        
        while (current.type != "end") {
            int timer = 0;
            for (int t = 0; t < 5; t++) {

                for (int i = 0; i < open.size(); i++) {
                    
                    if (open.get(i).type != "start" && open.get(i).type != "end") {
                        open.get(i).type = "open";
                        open.get(i).set_color();
                    }
                    
                }
                
                for (int i = 0; i < closed.size(); i++) {
                    
                    if (closed.get(i).type != "start" && closed.get(i).type != "end") {
                        closed.get(i).type = "closed";
                        closed.get(i).set_color();
                    }
                    
                }
                
                if (current.type == "end") {
                    break;
                }
                
                
                current = open.get(0);
                System.out.println(current);
                
                
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).f() < current.f()) {
                        current = open.get(i);
                    }
                    else if (open.get(i).f() == current.f()) {
                        if (open.get(i).g() < current.g()) {
                            current = open.get(i);
                        }
                    }
                    
                    closed.add(current);
                    open.remove(current);
                    
                    neighbors = current.get_neighbors(grid);
                    
                    for (int j = 0; j < neighbors.size(); j++) {
                        if (neighbors.get(j).type != "wall" && closed.contains(neighbors.get(j)) == false) {
                            
                            if (open.contains(neighbors.get(j)) == false) {
                                neighbors.get(j).parent = current;
                                
                                open.add(neighbors.get(j));
                                
                                neighbors.get(j).h_score = neighbors.get(j).h();
                                neighbors.get(j).g_score = neighbors.get(j).g();
                                neighbors.get(j).f_score = neighbors.get(j).f(); 
                                
                            } else {
                                if (neighbors.get(j).parent.g_score > current.g_score) {
                                    neighbors.get(j).parent = current;
                                }
                            }
                        }
                    }
                }
            }
                
            
            
            m.repaint();
            wait(1f);
            timer = 0;
            
            
            timer++;
        }
        
        while (grid[0][0].type != "path") {
            Node x;
            current = current.parent;
            current.type = "path";
            current.set_color();
            System.out.println(current);
            
        }
        m.repaint();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static void wait(float t) {
        try {
            TimeUnit.SECONDS.sleep((long) t);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static Node[][] make_grid(int width, int height) {
        Node[][] grid = new Node[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0) {
                    Node n = new Node(j, i, "start");
                    grid[i][j] = n;
                } else if (i == height-1 && j == width-1) {
                    Node n = new Node(j, i, "end");
                    grid[i][j] = n;
                } else {
                    Node n = new Node(j, i, "space");
                    grid[i][j] = n;
                }
            }
        }
        return grid;
    }
    
    public static void make_walls(Node[][] grid) {
        Random rand = new Random();
        int count = 0;
        boolean bool = rand.nextBoolean();
        Node x;
        for (int j = 1; j < grid.length*6; j++) {
            x = grid[rand.nextInt(19)][rand.nextInt(19)];
            x.type = "wall";
            x.set_color();
        }
        
        grid[0][0].type = "start";
        grid[0][0].set_color();
        grid[19][19].type = "end";
        grid[19][19].set_color();
        
    }
    
    
}