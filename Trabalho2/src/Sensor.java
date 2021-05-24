import java.util.concurrent.ThreadLocalRandom;

public class Sensor extends Thread {

    private final int delay = 1000;

    private final int sensorID;
    private final LE monitor;


    public Sensor(int sensorID, LE monitor){
        this.sensorID = sensorID;
        this.monitor = monitor;
    }


    public int getSensorID() {
        return sensorID;
    }

    //testar se eh logico incluir no construtor;
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
                    this.monitor.saveTemperatureInfo(temperatureInfo);
                    this.monitor.exitWriter(temperatureInfo);
                }
                scannerId++;
                sleep(2000);
            }
        }catch (InterruptedException ignored){}
    }
}
