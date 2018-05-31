
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulador {
    //Constantes
    private static final int NUM_TECNICOS = 3;
    private static final int INI_LIGACOES =  75112;//Horário inicial de entrada das ligações
    private static final int FIM_LIGACOES_MANHA = 113741;//Horário final de entrada das ligações
    private static final int INI_LIGACOES_TARDE =  130127;//Horário inicial de entrada das ligações
    private static final int FIM_LIGACOES = 173038;//Horário final de entrada das ligações
    //Campos de controle
    private SimpleDateFormat dateFormat;
    private Date tempo;
    private Calendar cal;
    private Random generator_criaLig;
    private Random generator_duraLig;
    private ArrayList<Ligacao> ligEspera; //Ligações a serem atendidas
    private ArrayList<Ligacao> ligRetornar; //Ligações que foram abortadas mas vão retornar posteriormente
    private ArrayList<Ligacao> ligAbortadas; //Ligações abortadas que não retornam mais
    private ArrayList<Ligacao> ligEncerradas; //Ligações atendidas, uma vez concluídas
    private ArrayList<Tecnico> tecnicos; //Técnicos disponíveis
    private Date ligPrx; //Próximo horário previsto para entrada de ligação

    public Simulador() {
        tempo = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Gera instâncias de ArrayList
        ligEspera = new ArrayList<>();
        ligRetornar = new ArrayList<>();
        ligAbortadas = new ArrayList<>();
        ligEncerradas = new ArrayList<>();
        tecnicos = new ArrayList<>();
        //Gera técnicos padrão
        for (int i = 0; i < NUM_TECNICOS; i++) {
            tecnicos.add(new Tecnico());
        }
        try {
            tempo = dateFormat.parse("2018-01-01 00:00:00");
            cal = Calendar.getInstance();
            cal.setTime(tempo);
        } catch (ParseException ex) {
            Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start(){
        Date fin = null;
        try {
            fin = dateFormat.parse("2018-04-20 23:59:00");
        } catch (ParseException ex) {
            Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Prepara geradores de números aleatórios
        Random generator_seed = new Random(50);
        generator_criaLig = new Random(generator_seed.nextLong());
        generator_duraLig = new Random(generator_seed.nextLong());
        //Varre da data inical até que chegue na data final
        while(cal.getTime().before(fin)){
            try {
                iteracao();
                cal.add(Calendar.SECOND, 1);
            } catch (IOException ex) {
                Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Uma vez concluída a simulação, gera dados sobre os resultados (média, desvio padrão, etc...)
        estatisticaLigacoes();
    }

    private void iteracao() throws IOException{

        //System.out.println(cal.getTime().toString());
        //Gerar desistência das ligações ainda não atendidas
        //Gerar novas ligações conforme o horário (gerar inclusive mais de uma se necessário)
        criaLig();
        //Atualizar status dos técnicos (desligar ligações, passar para livre)
        encerraLig();
        //Gerar atendimentos (vincular uma ligação no técnico)
        atendeLig();

        //Três arrays para classificar as ligações
    }

    //Cria ligações conforme intervalo entre chegadas
    private void criaLig(){
        int horaAtual = cal.get(Calendar.HOUR_OF_DAY) * 10000 + 
                        cal.get(Calendar.MINUTE) * 100 +
                        cal.get(Calendar.SECOND);
        //Se a hora atual pertence ao intervalo em que houve chegada de ligações
        if ((horaAtual > INI_LIGACOES && horaAtual < FIM_LIGACOES_MANHA) ||
            (horaAtual > INI_LIGACOES_TARDE && horaAtual < FIM_LIGACOES)) {
            //Se já chegou na hora de criar a próxima ligação
            if (ligPrx != null && ligPrx.before(cal.getTime())) {
                //Insere uma nova ligação na fila de espera
                ligEspera.add(new Ligacao(cal.getTime()));
                ligPrx = null;
            }
            //Se não tem um horário da próxima ligação, verifica se já deve criá-la
            if (ligPrx == null) {
                //Distribuição Exponencial
                //x = log(1-u)/(−λ)
                float random_exp = generator_criaLig.nextFloat();
                double exp = Math.log(1 - random_exp) / (-13.17);
                ligPrx = new Date(cal.getTimeInMillis() + (long) (2653 * 1000 * exp));
            }
        }
    }

    private void encerraLig(){
        //Encontra técnicos para encerrar ligações
        for(Tecnico tec : tecnicos){
            //Se o técnico está com ligação em andamento
            if(tec.isOcupado()){
                //Se já chegou na data/hora do final do atendimento
                if(!cal.getTime().before(tec.getLig().getFimAtend())){
                    //Vincula a ligação na lista dos atendimentos concluídos
                    ligEncerradas.add(tec.getLig());
                    tec.desliga();
                }
            }
        }

    }

    private void atendeLig(){
        //Se há ligações em espera
        if(!ligEspera.isEmpty()){
            //Encontra um técnico livre que possa atender
            for(Tecnico tec : tecnicos){
                //Se o técnico está livre
                if(!tec.isOcupado()&& !ligEspera.isEmpty()){
                    //Gera distribuição para o valor de duração da ligação, em milissegundos
                    float random_exp = generator_duraLig.nextFloat();
                    double exp = Math.log(1-random_exp)/(-13.17);
                    long segundos = (long) (6660 * exp);
                    //Remove a ligação da lista de espera e define o horário previsto para o final da ligação
                    Ligacao lig = ligEspera.remove(0);
                    lig.atende(cal.getTime(), segundos);
                    //Gera o atendimento
                    tec.atende(lig);
                }
            }
        }
    }

    private void estatisticaLigacoes(){
        ArrayList<Double> data =  new ArrayList<>();
        for (Ligacao ligEncerrada : ligEncerradas) {
            //double espera = (ligEncerrada.getFimAtend().getTime() - ligEncerrada.getPrilig().getTime()) / 1000;
            double espera = ligEncerrada.getDuracao();
            data.add(espera);
        }
        Double[] vect = new Double[data.size()];
        vect = data.toArray(vect);
        new Histogram( vect, 100);
    }

    
}
