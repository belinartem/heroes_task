package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    
    private static final int WIDTH = 27;
    private static final int HEIGHT = 21;
    
    private static final int[] DX = {-1, 1, 0, 0, -1, -1, 1, 1};
    private static final int[] DY = {0, 0, -1, 1, -1, 1, -1, 1};
    
    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();
        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();
        
        if (startX == targetX && startY == targetY) {
            return Collections.singletonList(new Edge(targetX, targetY));
        }
        
        boolean[][] blocked = new boolean[WIDTH][HEIGHT];
        if (existingUnitList != null) {
            for (Unit unit : existingUnitList) {
                if (unit != null && !unit.equals(targetUnit) && unit.isAlive()) {
                    int x = unit.getxCoordinate();
                    int y = unit.getyCoordinate();
                    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                        blocked[x][y] = true;
                    }
                }
            }
        }
        
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[WIDTH][HEIGHT];
        Point[][] parent = new Point[WIDTH][HEIGHT];
        
        Point start = new Point(startX, startY);
        queue.offer(start);
        visited[startX][startY] = true;
        
        boolean found = false;
        
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            
            if (current.x == targetX && current.y == targetY) {
                found = true;
                break;
            }
            
            for (int i = 0; i < 8; i++) {
                int nx = current.x + DX[i];
                int ny = current.y + DY[i];
                
                if (nx < 0 || nx >= WIDTH || ny < 0 || ny >= HEIGHT) {
                    continue;
                }
                
                if (visited[nx][ny]) {
                    continue;
                }
                
                if (blocked[nx][ny]) {
                    continue;
                }
                
                if (i >= 4) {
                    int checkX1 = current.x + DX[i];
                    int checkY1 = current.y;
                    int checkX2 = current.x;
                    int checkY2 = current.y + DY[i];
                    
                    boolean blocked1 = (checkX1 >= 0 && checkX1 < WIDTH && checkY1 >= 0 && checkY1 < HEIGHT) && blocked[checkX1][checkY1];
                    boolean blocked2 = (checkX2 >= 0 && checkX2 < WIDTH && checkY2 >= 0 && checkY2 < HEIGHT) && blocked[checkX2][checkY2];
                    
                    if (blocked1 || blocked2) {
                        continue;
                    }
                }
                
                visited[nx][ny] = true;
                parent[nx][ny] = current;
                queue.offer(new Point(nx, ny));
            }
        }
        
        if (!found) {
            return Collections.emptyList();
        }
        
        List<Edge> path = new ArrayList<>();
        Point current = new Point(targetX, targetY);
        
        while (current != null) {
            path.add(new Edge(current.x, current.y));
            current = parent[current.x][current.y];
        }

        Collections.reverse(path);
        
        return path;
    }

    private static class Point {
        final int x;
        final int y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
