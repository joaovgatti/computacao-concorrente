import java.util.LinkedList;
import java.util.Queue;

public class LE {

    private int readers, writers, writersQueue;

    private Queue<TemperatureInfo> temperaturesInfo;

    public LE(){
        this.readers = 0;
        this.writers = 0;
        this.writersQueue = 0;
        this.temperaturesInfo = new LinkedList<TemperatureInfo>();
    }

    public Queue<TemperatureInfo> getTemperaturesInfo() {

        return temperaturesInfo;
    }

    public synchronized void enterReader(int id){
        try{
            while(this.writers > 0 || this.writersQueue > 0){
                System.out.println("atuador " + id + " bloqueado");
                wait();
            }
            this.readers++;
            System.out.println("atuador " + id + " pode ler as temperaturas");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitReader(int id){
        this.readers--;
        if(this.readers == 0){
            this.notify();
        }
        System.out.println("atuador " + id + " saindo");
    }

    public synchronized void enterWriter(TemperatureInfo temperatureInfo){
        try{
            this.writersQueue++;
            while(this.writers > 0 || this.readers > 0){
                System.out.println("sensor " + temperatureInfo.getSensorId() + " foi bloqueado");
                wait();
            }
            this.writersQueue--;
            this.writers++;
            System.out.println("sensor " + temperatureInfo.getSensorId() + " pode salvar a temperatura medida");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitWriter(TemperatureInfo temperatureInfo){
        this.writers--;
        this.notifyAll();
        System.out.println("sensor " + temperatureInfo.getSensorId() + " saindo");
    }
}
