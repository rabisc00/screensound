package br.com.alura.screensound.model;

public enum ArtistType {
    SOLO("Solo"),
    DUO("Duo"),
    BAND("Band");

    private String stringType;

    ArtistType(String stringType) {
        this.stringType = stringType;
    }

    public static ArtistType fromString(String text) {
        for (ArtistType type : ArtistType.values()) {
            if (type.stringType.equalsIgnoreCase(text)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Couldn't find a type that equals the one given");
    }
}
