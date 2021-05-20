public class LE {

    private int readers, writers;

    private TemperatureInfo[] temperaturesInfo;

    public LE(){
        this.readers = 0;
        this.writers = 0;
        this.temperaturesInfo = new TemperatureInfo[60];
    }

    public synchronized void enterReader(TemperatureInfo temperatureInfo){
        try{
            while(this.writers > 0){
                System.out.println("reader " + temperatureInfo.getSensorId() + "blocked");
                wait();
            }
            this.writers++;
            System.out.println("reader" + temperatureInfo.getSensorId() + "free to go: ");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitReader(TemperatureInfo temperatureInfo){
        this.readers--;
        if(this.readers == 0){
            this.notify();
        }
        System.out.println("reader " + temperatureInfo.getSensorId() + " exiting");
    }

    public synchronized void enterWriter(int id){
        try{
            /*
            * Prioridade para escrita acontece aqui. Para um escritor entrar
            * depende apenas de nao ter outro escritor no momento, nao ligando se um
            * leitor quer entrar.
            *
            * */
            while(this.writers > 0){
                System.out.println("writer " + id + " was blocked");
                wait();
            }
            this.writers++;
            System.out.println("writer " + id + "is writing");
        }catch (InterruptedException ignored){}
    }

    public synchronized void exitWriter(int id){
        this.writers--;
        notifyAll();
        System.out.println("writer " + id + "is leaving");
    }









































}
