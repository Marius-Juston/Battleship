public class Location implements Comparable {
    private static final String COL = ", col=";
    private static final String ROW = "row=";
    private static final String LOCATION = "Location{";
    private final int row;
    private final int col;

    /*
    intialises the location rows and cols
     */

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /*
        Returns the row of the locationm
         */
    public final int getRow() {
        return row;
    }

    /*
    returns the column of the lcoation
     */
    public final int getCol() {
        return col;
    }

    /*
    equals method to compare locations
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) return true;
        if ((obj == null) || (getClass() != obj.getClass())) return false;

        final Location location = (Location) obj;

        return (getRow() == location.getRow()) && (getCol() == location.getCol());
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = (31 * result) + getCol();
        return result;
    }

    /**
     * To string. returns the object representation in string
     *
     * @return
     */
    @Override
    public final String toString() {
        return LOCATION +
                ROW + getRow() +
                COL + getCol() +
                '}';
    }

    /**
     * Allows you to sort the object location
     *
     * @param o
     * @return
     */
    @Override
    public final int compareTo(final Object o) {
        if (!(o instanceof Location))
            return 0;

        final Location location = (Location) o;

        return ((getRow() - location.getRow()) + getCol()) - location.getCol();
    }
}
