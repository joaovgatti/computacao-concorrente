import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE {

    private int readers, writers;

    private Queue<TemperatureInfo> temperaturesInfo;

    public LE(){
        this.readers = 0;
        this.writers = 0;
        this.temperaturesInfo = new LinkedList<TemperatureInfo>();
    }

    public Queue<TemperatureInfo> getTemperaturesInfo() {
        return temperaturesInfo;
    }

    public synchronized void enterReader(int id){
        try{
            while(this.writers > 0){
                System.out.println("atuador " + id + " bloqueado");
                wait();
            }
            this.readers++;
            System.out.println("atuador " + id + " pode ser executado");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitReader(int id){
        this.readers--;
        if(this.readers == 0){
            notifyAll();
        }
        System.out.println("atuador " + id + " saindo");
    }

    public synchronized void enterWriter(TemperatureInfo temperatureInfo){
        try{
            /*
            * Prioridade para escrita acontece aqui. Para um escritor entrar
            * depende apenas de nao ter outro escritor no momento, nao ligando se um
            * leitor quer entrar.
            *
            * */
            while(this.writers > 0 || this.readers > 0){
                System.out.println("sensor " + temperatureInfo.getSensorId() + " was blocked");
                wait();
            }
            this.writers++;
            System.out.println("sensor " + temperatureInfo.getSensorId() + " is writing");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitWriter(TemperatureInfo temperatureInfo){
        this.writers--;
        notifyAll();
        System.out.println("sensor " + temperatureInfo.getSensorId() + "is leaving");
    }
}
