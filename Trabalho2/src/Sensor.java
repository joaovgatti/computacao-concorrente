import java.util.concurrent.ThreadLocalRandom;

public class Sensor extends Thread {

    private final int sensorID;
    private final LE monitor;


    public Sensor(int sensorID, LE monitor){
        this.sensorID = sensorID;
        this.monitor = monitor;
    }


    public int getSensorID() {
        return sensorID;
    }


    void saveTemperatureInfo(TemperatureInfo temperaturesInfo){
        if(this.monitor.getTemperaturesInfo().size() == 60){
            this.monitor.getTemperaturesInfo().poll();
        }
        this.monitor.getTemperaturesInfo().add(temperaturesInfo);
    }

    private int scannerId = 0;

    public void run() {

        int temperature;
        try{
            for (; ;) {

                //externalizar geracao de temp
                temperature = ThreadLocalRandom.current().nextInt(25, 41);

                if (temperature > 30) {
                    TemperatureInfo temperatureInfo = new TemperatureInfo(temperature, this.sensorID, scannerId);
                    this.monitor.enterWriter(temperatureInfo);
                    saveTemperatureInfo(temperatureInfo);
                    this.monitor.exitWriter(temperatureInfo);
                }
                scannerId++;
                sleep(2000);
            }
        }catch (InterruptedException ignored){}
    }
}
