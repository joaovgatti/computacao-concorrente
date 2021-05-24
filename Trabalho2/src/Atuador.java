import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Atuador extends Thread {

    private Sensor associatedSensor;

    private final LE monitor;

    private int id;

    public Atuador(int id,Sensor associatedSensor, LE monitor){
        this.associatedSensor = associatedSensor;
        this.monitor = monitor;
        this.id = id;
    }


    private void checkRedAlert(List<TemperatureInfo> temperatureList){
        if(temperatureList.size() < 5){
            //System.out.println("The sensor" + this.associatedSensor.getSensorID() + " doesnt has sufficient info");
            return;
        }
        for(int i = 0; i < 5; i++) {
            if (temperatureList.get(i).getTemperature() < 35) {
                    System.out.println("CNTP Conditions - All Good");
                    return;
                }
        }
        System.out.println("----- RED ALERT - WATCH OUT -------");
    }

    private void checkYellowAlert(List<TemperatureInfo> temperatureList){
        if(temperatureList.size() < 15){
            //System.out.println("The sensor" + this.associatedSensor.getSensorID() +" doesnt has sufficient info");
            return;
        }
        List<Integer> highTemperaturesList = new ArrayList<Integer>();

        for(int i = 0; i < 15; i++){
            if(temperatureList.get(i).getTemperature() > 35){
                highTemperaturesList.add(temperatureList.get(i).getTemperature());
            }
        }
        if(highTemperaturesList.size() >= 5){
            System.out.println("------YELLOW ALERT - WATCH OUT------");
        }else{
            System.out.println("CNTP Conditions - All good");
        }
    }

    private int getMediumTemperature(Queue<TemperatureInfo> temperatureList){
        if(temperatureList.size() < 1){
            return 0;
        }
        int temperatureSum = 0;

        for(TemperatureInfo temp : temperatureList){
            temperatureSum += temp.getTemperature();
        }

        return temperatureSum / temperatureList.size();
    }


    private List<TemperatureInfo> getSensorInfo(){

        Queue<TemperatureInfo> temperatureInfoList = this.monitor.getTemperaturesInfo();

        List<TemperatureInfo> sensorTemperatureList = new ArrayList<TemperatureInfo>();

        for (TemperatureInfo temperatureInfo : temperatureInfoList) {

            if (temperatureInfo.getSensorId() == this.associatedSensor.getSensorID()) {
                sensorTemperatureList.add(temperatureInfo);
            }
        }
        return sensorTemperatureList;
    }

    public void run(){

        for(;;){

            this.monitor.enterReader(this.id);

            List<TemperatureInfo> sensorTemperature = getSensorInfo();

            checkRedAlert(sensorTemperature);

            checkYellowAlert(sensorTemperature);

            int overallTemperature = getMediumTemperature(this.monitor.getTemperaturesInfo());

            System.out.println("The overall temperature is " + overallTemperature );

            this.monitor.exitReader(this.id);

            try {
                sleep(2000);
            }catch (InterruptedException ignored){}

        }
    }





























}
