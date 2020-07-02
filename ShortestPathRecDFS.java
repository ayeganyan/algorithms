import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ShortestPathRecDFS {

    static class Point {
        int row;
        int column;

        public Point(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row &&
                    column == point.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) throws Exception {
        int[][] matrix = {
                //0  1  2  3  4  5  6  7  8  9  10  11 12 13 14 15 16 17 18 19
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 1, 1, 1, 0, 0}, //0
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 1, 0, 1, 0, 0}, //1
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 1, 1, 1, 0, 0}, //2
                { 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1,  0, 1, 0, 0, 0, 0, 0, 0, 0}, //3
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,  0, 1, 0, 0, 0, 0, 0, 0, 0}, //4
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0}, //5
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0,  0, 0, 0, 1, 1, 1, 0, 0, 0}, //6
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 1, 0, 0, 0, 0, 0, 0, 0}, //7
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 1, 0, 0, 0, 0, 0, 0, 0}, //8
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0}, //9
        };
        dfsShortestPath(matrix, new Point(3, 1), new Point(8, 15));
    }

    static void dfsShortestPath(int[][] matrix, Point start, Point end) throws Exception {
        if(matrix[start.row][start.column] == 1 || matrix[end.row][end.column] == 1) {
            throw new Exception("Invalid input");
        }
        HashMap<Point, List<Point>> pathMap = new HashMap<>();

        List<Point> currentPath = new ArrayList<>();
        currentPath.add(start);
        pathMap.put(start, currentPath);
        dfsShortestPathRec(matrix, start, pathMap);

        print(pathMap.get(end));
        System.out.println(pathMap.get(end).size());
    }

    private static void dfsShortestPathRec(int[][] matrix, Point currentPoint,
                                           HashMap<Point, List<Point>> pathMap) {
        List<Point> currentPath = pathMap.get(currentPoint);
        for (Point adjacent : getAdjacent(matrix, currentPoint)) {
            List<Point> adjacentPath = pathMap.get(adjacent);
            if(adjacentPath == null || adjacentPath.size() > currentPath.size() + 1) {
                adjacentPath = new ArrayList<>(currentPath);
                adjacentPath.add(adjacent);
                pathMap.put(adjacent, adjacentPath);
                dfsShortestPathRec(matrix, adjacent, pathMap);
            }
        }
    }

    static private List<Point> getAdjacent(int[][] matrix, Point point) {
        List<Point> result = new ArrayList<>();
        if(point.row > 0 && matrix[point.row - 1][point.column] == 0) {
            result.add(new Point(point.row - 1, point.column));
        }
        if(point.row < matrix.length - 1 && matrix[point.row + 1][point.column] == 0){
            result.add(new Point(point.row + 1, point.column));
        }
        if(point.column > 0 && matrix[point.row][point.column - 1] == 0) {
            result.add(new Point(point.row, point.column - 1));
        }
        if(point.column < matrix[0].length - 1 && matrix[point.row][point.column + 1] == 0) {
            result.add(new Point(point.row, point.column + 1));
        }
        return result;
    }

    private static void print(List<Point> path) {
        if(path == null) {
            System.out.println("Node is unreachable");
            return;
        }
        StringJoiner sj = new StringJoiner( " -> ");
        for (Point point : path) {
            sj.add("(" + point.column + ", " + point.row + ")");
        }
        System.out.println(sj.toString());
    }
}

