import java.util.concurrent.ThreadLocalRandom;

public class Sensor extends Thread {

    private final int delay = 1000;

    private final int id;
    private final LE monitor;


    public Sensor(int id, LE monitor){
        this.id = id;
        this.monitor = monitor;
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
                    TemperatureInfo temperatureInfo = new TemperatureInfo(temperature, this.id, scannerId);
                    this.monitor.enterWriter(temperatureInfo);
                    this.monitor.saveTemperatureInfo(temperatureInfo);
                    this.monitor.exitWriter(temperatureInfo);
                }
                scannerId++;
                sleep(delay);
            }
        }catch (InterruptedException ignored){}
    }
}
