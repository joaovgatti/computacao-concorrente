import org.junit.*;
import static org.junit.Assert.assertEquals;
import java.util.List;



public class Teste {

    LE monitor = new LE();

    TemperatureInfo temperatureInfo;

    @Test
    public void mediumTemperature(){
        Sensor sensor = new Sensor(0, monitor);
        Atuador atuador = new Atuador(0, sensor, monitor);

        System.out.println("\n");

        temperatureInfo = new TemperatureInfo(35, 0, 0);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(37, 0, 1);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        System.out.println("\n");

        assertEquals(atuador.getMediumTemperature(monitor.getTemperaturesInfo()), 36);
        //sensor.start();
        //atuador.start();
    }


    @Test
    public void redAlert(){
        Sensor sensor = new Sensor(1, monitor);
        Atuador atuador = new Atuador(1, sensor, monitor);

        temperatureInfo = new TemperatureInfo(36, 1, 0); //testa tambem a seperacao entre cada id
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(37, 1, 1);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(38, 1, 2);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(39, 1, 3);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(40, 1, 4);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        monitor.enterReader(1);
        List<TemperatureInfo> sensorTemperature = atuador.getSensorInfo();

        assertEquals(atuador.checkRedAlert(sensorTemperature), "----- RED ALERT - WATCH OUT -------\n");

    }

    @Test
    public void yellowAlert(){
        Sensor sensor = new Sensor(2, monitor);
        Atuador atuador = new Atuador(2, sensor, monitor);

        temperatureInfo = new TemperatureInfo(36, 2, 0);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(37, 2, 1);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(38, 2, 2);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(39, 2, 3);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(40, 2, 4);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(36, 2, 5);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(37, 2, 6);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(38, 2, 7);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(39, 2, 8);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(40, 2, 9);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(36, 2, 10);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(37, 2, 11);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(38, 2, 12);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(39, 2, 13);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(40, 2, 14);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        monitor.enterReader(2);
        List<TemperatureInfo> sensorTemperature = atuador.getSensorInfo();

        assertEquals(atuador.checkYellowAlert(sensorTemperature), "------YELLOW ALERT - WATCH OUT------\n");
    }

    @Test
    public void normalCondition(){
        Sensor sensor = new Sensor(3, monitor);
        Atuador atuador = new Atuador(3, sensor, monitor);

        temperatureInfo = new TemperatureInfo(33, 3, 0);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(33, 3, 1);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(38, 3, 2);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(33, 3, 3);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(32, 3, 4);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(32, 3, 5);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(31, 3, 6);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(32, 3, 7);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(32, 3, 8);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(34, 3, 9);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(34, 3, 10);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(34, 3, 11);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(32, 3, 12);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(35, 3, 13);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        temperatureInfo = new TemperatureInfo(33, 3, 14);
        monitor.enterWriter(temperatureInfo);
        sensor.saveTemperatureInfo(temperatureInfo);
        monitor.exitWriter(temperatureInfo);

        monitor.enterReader(3);
        List<TemperatureInfo> sensorTemperature = atuador.getSensorInfo();
        assertEquals(atuador.checkYellowAlert(sensorTemperature), "CNTP Conditions - All good (YELLOW)\n");
        assertEquals(atuador.checkRedAlert(sensorTemperature), "CNTP Conditions - All good (RED)\n");
    }

}
