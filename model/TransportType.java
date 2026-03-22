package model;
public enum TransportType {
    AIRPORT("✈"),
    SEAPORT("⛴"),
    LAND("🚗");


    public final String icon;
    TransportType(String icon) {
        this.icon = icon;
    }
    public String getIcon() {
        return icon;
    }
}
