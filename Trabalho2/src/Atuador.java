import java.util.LinkedList;
import java.util.List;

public class Atuador extends Thread {

    private final int timeOut = 2000;

    private Sensor associatedSensor;

    private final LE monitor;

    private int id;

    public Atuador(int id,Sensor associatedSensor, LE monitor){
        this.associatedSensor = associatedSensor;
        this.monitor = monitor;
        this.id = id;
    }

    private List<TemperatureInfo> getSensorInfo(){

        List<TemperatureInfo> temperatureInfoList = this.monitor.getTemperaturesInfo();

        //cria array para armazenar os valores do sensor especifico.

        //faz o for e filtra para o novo array os valores do sensor especifico.retorna esse novo array.

        for(int i = 0; i < temperatureInfoList.size(); i++){

        }
    }

    /*
    Uma vez com os valores do sensor, basta fazer os calculos para emitir sinal amarelo, vermelho ou nenhum.

    Construir o run() com todas as funcoes la dentro, e com os metodos de entra leitor e sai leitor.
    * */



    private void checkYellowAlert(){

        List<TemperatureInfo> temperatureInfoList = new LinkedList<TemperatureInfo>();



    }
























}
