package dmit2015.entity;

public enum GamingPlatform {
    PLAYSTATION("Playstation"),
    NINTENDO("Nintendo"),
    XBOX("Xbox"),
    PC_GAMING("PC Gaming");

    public final String name;

    GamingPlatform(String platformName) {
        name = platformName;
    }
}