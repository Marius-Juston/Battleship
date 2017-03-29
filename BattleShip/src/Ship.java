import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Ship {
    private final int length;
    private final List<Location> locations;
    private final List<Location> hitsTaken;

    Ship(final int length) {
        this.length = length;
        locations = new ArrayList<>(5);
        hitsTaken = new ArrayList<>(5);
    }

    final void addLocation(final Location... loc) {
        Collections.addAll(getLocations(), loc);
    }

    public final List<Location> getLocations() {
        return locations;
    }

    /**
     * Add Location loc to hitsTaken.
     *
     * @param loc
     */
    public final void takeHit(final Location loc) {
        getHitsTaken().add(loc);
    }

    /**
     * Returns true if the number of hits taken is
     * equal to the length of this ship.
     *
     * @return
     */
    public final boolean isSunk() {
        return getLocations().size() == getHitsTaken().size();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) return true;
        if ((obj == null) || (getClass() != obj.getClass())) return false;

        final Ship ship = (Ship) obj;

        return (getLength() == ship.getLength()) && ((getLocations() != null) ? getLocations().equals(ship.getLocations()) : ((ship.getLocations() == null) && ((getHitsTaken() != null) ? getHitsTaken().equals(ship.getHitsTaken()) : (ship.getHitsTaken() == null))));

    }

    @Override
    public final int hashCode() {
        int result = getLength();
        result = (31 * result) + ((getLocations() != null) ? getLocations().hashCode() : 0);
        result = (31 * result) + ((getHitsTaken() != null) ? getHitsTaken().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "length=" + getLength() +
                ", locations=" + getLocations() +
                ", hitsTaken=" + getHitsTaken() +
                '}';
    }

    private Collection<Location> getHitsTaken() {
        return hitsTaken;
    }

    private int getLength() {
        return length;
    }


}
