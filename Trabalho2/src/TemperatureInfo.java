public class TemperatureInfo {


    private int temperature;
    private int sensorId;
    private int scannerId;


    public TemperatureInfo(int temperature, int sensorId, int scannerId) {
        this.temperature = temperature;
        this.sensorId = sensorId;
        this.scannerId = scannerId;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getSensorId() {
        return sensorId;
    }

    public int getScannerId() {
        return scannerId;
    }
}
