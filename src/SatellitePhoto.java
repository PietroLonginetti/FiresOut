enum SatellitePhotoDescription{CampFire, WoodFire, HouseFire}

public class SatellitePhoto {
    public SatellitePhotoDescription description;
    public Coordinates spot;

    public SatellitePhoto(SatellitePhotoDescription description, Coordinates spot) {
        this.description = description;
        this.spot = spot;
    }
}


