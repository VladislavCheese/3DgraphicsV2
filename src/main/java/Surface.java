import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс плоскости, содержащий список вершин tops, список ребер edges,
 * а также точку обзора viewPoint
 */
public class Surface {

    private final List<Top> tops;
    private final List<Edge> edges;
    private final List<Triangle> triangles;
    private static PointXYZ viewPoint;

    public Surface() {
        tops = new ArrayList<>();
        edges = new ArrayList<>();
        triangles = new ArrayList<>();
        viewPoint = new PointXYZ();
    }

    /**
     * Метод load загружает из файла данные и заполняет списки вершин и ребер
     *
     * @param dataFilePath путь к файлу с данными
     */
    public void load(String dataFilePath) {
        try (BufferedReader bf = new BufferedReader(new FileReader(dataFilePath))) {
            loadTops(bf);
            loadEdges(bf);
            loadTriangles(bf);
        } catch (IOException ignored) {
        }
    }

    private void loadTops(BufferedReader bf) throws IOException {
        //число вершин
        int numOfTops = Integer.parseInt(bf.readLine());
        for (int i = 0; i < numOfTops; i++) {
            //считаем координаты для вершины
            String anotherTop = bf.readLine();
            int[] topCoordinate = Arrays.stream(anotherTop.split(",")).mapToInt(Integer::parseInt).toArray();
            //создадим на их основе новую вершину
            Top top = new Top(topCoordinate[0], topCoordinate[1], topCoordinate[2], i + 1);
            //вычислим видовые координаты вершины и установим их
            top.setViewCoordinate(viewPoint);
            //добавим вершину в список
            tops.add(top);
        }
    }

    private void loadEdges(BufferedReader bf) throws IOException {
        //число ребер
        int numOfEdges = Integer.parseInt(bf.readLine());
        for (int i = 0; i < numOfEdges; i++) {
            //считаем координаты для вершин
            String anotherEdge = bf.readLine();
            int[] edgeCoordinate = Arrays.stream(anotherEdge.split(" ")).mapToInt(Integer::parseInt).toArray();
            //создадим и добавим в список ребер новое ребро
            edges.add(new Edge(tops.get(edgeCoordinate[0] - 1), tops.get(edgeCoordinate[1] - 1)));
        }
    }

    private void loadTriangles(BufferedReader bf) throws IOException {
        //число треугольников
        int numOfTriangles = Integer.parseInt(bf.readLine());
        for (int i = 0; i < numOfTriangles; i++) {
            String anotherTriangle = bf.readLine();
            int[] topsCoordinate = Arrays.stream(anotherTriangle.split(" ")).mapToInt(Integer::parseInt).toArray();
            //создадим и добавим в список треугольников новый экземпляр
            triangles.add(new Triangle(tops.get(topsCoordinate[0] - 1), tops.get(topsCoordinate[1] - 1), tops.get(topsCoordinate[2] - 1)));
        }
    }

    /**
     * Метод setViewPoint получает на вход to, teta и fi и устанавливает точку обзора
     */
    public void setViewPoint(double ro, double teta, double fi) {
        viewPoint.setX(ro);
        viewPoint.setY(teta);
        viewPoint.setZ(fi);
    }

    /**
     * Метод setNewViewPoint получает на вход to, teta и fi и устанавливает точку обзора,
     * а также пересчитывает видовые координаты для всех вершин
     */
    public void setNewViewPoint(double ro, double teta, double fi) {
        viewPoint.setX(ro);
        viewPoint.setY(teta);
        viewPoint.setZ(fi);
        for (Top v : tops) {
            v.setViewCoordinate(viewPoint);
        }
    }

    /**
     * Запускает цикл по всем ребрам и рисует их
     */
    public void drawSurface(Graphics g) {
        for (Edge e : edges) {
            e.drawEdge(g, triangles);
        }
    }
}

