class AircraftCarrier extends Ship {
    /**
     * Construct an AircraftCarrier with a length
     * of 5 and the specified Locations.
     *
     * @param locations
     */
    public AircraftCarrier(final Location... locations) {
        super(locations.length);

        addLocation(locations);
    }
}
