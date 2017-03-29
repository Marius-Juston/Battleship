class PatrolBoat extends Ship {
    /**
     * Construct a PatrolBoat with a length
     * of 2 and the specified Locations.
     *
     * @param locations
     */
    public PatrolBoat(final Location... locations) {
        super(locations.length);

        addLocation(locations);
    }
}