import java.util.Objects;

import static java.lang.Math.*;

/**
 * Класс вершины, содержащий её мировые координаты, видовые координаты, номер и расстояние ro
 */
public class Top implements Cloneable {

    private final PointXYZ factCoordinate;
    private final PointXYZ viewCoordinate;
    private final int num;
    private double ro;

    /**
     * @param x   мировая координата
     * @param y   мировая координата
     * @param z   мировая координата
     * @param num номер точки
     */
    public Top(double x, double y, double z, int num) {
        factCoordinate = new PointXYZ(x, y, z);
        viewCoordinate = new PointXYZ();
        this.num = num;
    }

    @Override
    public Top clone() {
        try {
            return (Top) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Видовое преобразование для точки.
     */
    public void setViewCoordinate(PointXYZ viewPoint) {
        this.ro = viewPoint.getX();//В точке viewPoint x отвечает за ro
        final double ST = sin(toRadians(viewPoint.getY()));//sin(teta) - синус тета
        final double CT = cos(toRadians(viewPoint.getY()));//cos(teta) - косинус тета
        final double SF = sin(toRadians(viewPoint.getZ()));//sin(fi) - синус фи
        final double CF = cos(toRadians(viewPoint.getZ()));//cos(fi) - косинус фи

        //вычисляем координаты по соответствующим формулам
        viewCoordinate.setX(-factCoordinate.getX() * ST + factCoordinate.getY() * CT);
        viewCoordinate.setY(-factCoordinate.getX() * CF * CT - factCoordinate.getY() * CF * ST + factCoordinate.getZ() * SF);
        viewCoordinate.setZ(-factCoordinate.getX() * SF * CT - factCoordinate.getY() * SF * ST - factCoordinate.getZ() * CF + ro);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Top) {
            return this.num == ((Top) o).getNum();
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factCoordinate, viewCoordinate, num, ro);
    }

    @Override
    public String toString() {
        return "Num = " + num +
                "\nWorld Coordinateinates = (" + factCoordinate.getX() + " " + factCoordinate.getY() + " " + factCoordinate.getZ() + ")" +
                "\nView Coordinateinates = (" + viewCoordinate.getX() + " " + viewCoordinate.getY() + " " + viewCoordinate.getZ() + ")";
    }

    public PointXYZ getFactCoordinate() {
        return factCoordinate;
    }

    public PointXYZ getViewCoordinate() {
        return viewCoordinate;
    }

    public double getRo() {
        return ro;
    }

    public int getNum() {
        return num;
    }
}
