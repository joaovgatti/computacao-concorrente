import java.util.ArrayList;
import java.util.List;

public class LE {

    private int readers, writers;

    private List<TemperatureInfo> temperaturesInfo;

    public LE(){
        this.readers = 0;
        this.writers = 0;
        this.temperaturesInfo = new ArrayList<TemperatureInfo>(60);
    }

    public List<TemperatureInfo> getTemperaturesInfo() {
        return temperaturesInfo;
    }

    public synchronized void enterReader(int id){
        try{
            while(this.writers > 0){
                System.out.println("reader " + id + "blocked");
                wait();
            }
            this.writers++;
            System.out.println("reader" + id + "free to go: ");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitReader(int id){
        this.readers--;
        if(this.readers == 0){
            this.notify();
        }
        System.out.println("reader " + id + " exiting");
    }

    public synchronized void enterWriter(TemperatureInfo temperatureInfo){
        try{
            /*
            * Prioridade para escrita acontece aqui. Para um escritor entrar
            * depende apenas de nao ter outro escritor no momento, nao ligando se um
            * leitor quer entrar.
            *
            * */
            while(this.writers > 0){
                System.out.println("writer " + temperatureInfo.getSensorId() + " was blocked");
                wait();
            }
            this.writers++;
            System.out.println("writer " + temperatureInfo.getSensorId() + "is writing");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitWriter(TemperatureInfo temperatureInfo){
        this.writers--;
        notifyAll();
        System.out.println("writer " + temperatureInfo.getSensorId() + "is leaving");
    }



}
