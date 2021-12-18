import java.awt.*;
import java.util.List;

/**
 * Класс ребра, содержащий его начальную и конечную вершину, а также параметры рамки.
 */
public class Edge {

    public final Top startTop;
    public final Top finishTop;

    private static final int ir = 1299;
    private static final int il = 0;
    private static final double xr = (double) 10_392 / 7000;
    private static final double xl = (double) -10_392 / 7000;
    private static final int ju = 0;
    private static final int jd = 699;
    private static final double yd = -0.8;
    private static final double yu = 0.8;

    /**
     * @param startTop  стартовая вершина
     * @param finishTop финишная вершина
     */
    public Edge(Top startTop, Top finishTop) {
        this.startTop = startTop.clone();
        this.finishTop = finishTop.clone();
    }

    /**
     * Сначала производит перспективные преобразования
     * Затем преобразует получившиеся координаты в экранные
     * И рисует линию - ребро каркасной модели.
     * Применяются значения рамки и окна, описанные выше.
     *
     * @param graphics для рисования
     */
    public void drawEdge(Graphics graphics, List<Triangle> triangles) {

        //перспективные преобразования
        double tmp1 = (startTop.getRo() / (2 * startTop.getViewCoordinate().getZ()));
        double xStart = (startTop.getRo() / (2 * startTop.getViewCoordinate().getZ())) * startTop.getViewCoordinate().getX();
        double yStart = (startTop.getRo() / (2 * startTop.getViewCoordinate().getZ())) * startTop.getViewCoordinate().getY();
        double xFinish = (finishTop.getRo() / (2 * finishTop.getViewCoordinate().getZ())) * finishTop.getViewCoordinate().getX();
        double yFinish = (finishTop.getRo() / (2 * finishTop.getViewCoordinate().getZ())) * finishTop.getViewCoordinate().getY();

        double iStart = ir + ((xStart - xr) * (il - ir)) / (xl - xr);
        double jStart = ju + ((yStart - yu) * (jd - ju)) / (yd - yu);
        double iFinish = ir + ((xFinish - xr) * (il - ir)) / (xl - xr);
        double jFinish = ju + ((yFinish - yu) * (jd - ju)) / (yd - yu);
        //логирование
        logData(xStart, yStart, xFinish, yFinish, iStart, jStart, iFinish, jFinish);

        for (Triangle t : triangles) {
            if ((t.isFront()) && (startTop.equals(t.a) || startTop.equals(t.b) || startTop.equals(t.c)) &&
                    (finishTop.equals(t.a) || finishTop.equals(t.b) || finishTop.equals(t.c))) {
                graphics.drawLine((int) iStart, (int) jStart, (int) iFinish, (int) jFinish);
                break;
            }
        }
    }

    private void logData(double xStart, double yStart, double xFinish, double yFinish, double iStart, double jStart, double iFinish, double jFinish) {
        String sb = "xStart = " + xStart + " yStart = " + yStart +
                "\nxFinish = " + xFinish + " yFinish = " + yFinish +
                "\niStart = " + iStart + " jStart = " + jStart +
                "\niFinish = " + iFinish + " jFinish = " + jFinish;
        System.out.println(sb + "\n");
    }
}
