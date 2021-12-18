public class Triangle {
    public Top a;
    public Top b;
    public Top c;
    public boolean front;//true-лицевая false-не лицевая

    Triangle(Top A, Top B, Top C) {
        this.a = A.clone();
        this.b = B.clone();
        this.c = C.clone();
        this.front = this.isFront();
    }

    public boolean isFront() {
        double nz;
        nz = a.getViewCoordinate().getX() * b.getViewCoordinate().getY() +
                c.getViewCoordinate().getX() * a.getViewCoordinate().getY() +
                b.getViewCoordinate().getX() * c.getViewCoordinate().getY() -
                c.getViewCoordinate().getX() * b.getViewCoordinate().getY() -
                b.getViewCoordinate().getX() * a.getViewCoordinate().getY() -
                a.getViewCoordinate().getX() * c.getViewCoordinate().getY();
        return nz <= 0;
    }
}
