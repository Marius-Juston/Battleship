class Submarine extends Ship {
    /**
     * Construct a Submarine with a length
     * of 3 and the specified Locations.
     *
     * @param locations
     */
    public Submarine(final Location... locations) {
        super(locations.length);

        addLocation(locations);
    }
}