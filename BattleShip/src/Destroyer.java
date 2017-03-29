class Destroyer extends Ship {
    /**
     * Construct a Destroyer with a length
     * of 4 and the specified Locations.
     *
     * @param locations
     */
    public Destroyer(final Location... locations) {
        super(locations.length);

        addLocation(locations);
    }
}