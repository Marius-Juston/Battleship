class Cruiser extends Ship {
    /**
     * Construct a Cruiser with a length
     * of 3 and the specified Locations.
     *
     * @param locations
     */
    public Cruiser(final Location... locations) {
        super(locations.length);

        addLocation(locations);
    }
}