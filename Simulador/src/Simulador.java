
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private Random generator_abortaLig;
    private Random generator_abortaEscolheLig;
    private Random generator_duraLig;
    private ArrayList<Ligacao> ligGeral; //Todas as ligações geradas (usado para cálculo de intervalo entre ligações)
    private ArrayList<Ligacao> ligEspera; //Ligações a serem atendidas
    private ArrayList<Ligacao> ligRetornar; //Ligações que foram abortadas mas vão retornar posteriormente
    private ArrayList<Ligacao> ligAbortadas; //Ligações abortadas que não retornam mais
    private ArrayList<Ligacao> ligEncerradas; //Ligações atendidas, uma vez concluídas
    private ArrayList<Tecnico> tecnicos; //Técnicos disponíveis
    private Date ligPrx; //Próximo horário previsto para entrada de ligação

    private ArrayList<Double> test = new ArrayList<>();

    public Simulador() {
        tempo = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Gera instâncias de ArrayList
        ligGeral = new ArrayList<>();
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
        generator_abortaLig = new Random(generator_seed.nextLong());
        generator_abortaEscolheLig = new Random(generator_seed.nextLong());
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
        estatisticaTestes();
        estatisticaLigacoes();
        estatisticaAtendimentos();
        estatisticaEspera();
    }

    private void iteracao() throws IOException{

        //System.out.println(cal.getTime().toString());
        //Gerar desistência das ligações ainda não atendidas
        //Gerar novas ligações conforme o horário (gerar inclusive mais de uma se necessário)
        criaLig();
        //Seleciona ligações em espera e aborta se necessário (cliente desiste da ligação atual)
        abortaLig();
        //Seleciona ligações abortadas e gera novo registro de ligação (cliente retoma ligação abortada anteriormente)
        recriaLig();
        //Atualizar status dos técnicos (desligar ligações, passar para livre)
        encerraLig();
        //Gerar atendimentos (vincular uma ligação no técnico)
        atendeLig();

        //Três arrays para classificar as ligações
    }

    //Cria ligações conforme intervalo entre chegadas
    private void criaLig(){
        //Ligação a sere gerada
        Ligacao nova;
        //Horário formatado para simplificar teste: hhmmss
        int horaAtual = cal.get(Calendar.HOUR_OF_DAY) * 10000 +
                        cal.get(Calendar.MINUTE) * 100 +
                        cal.get(Calendar.SECOND);
        //Se a hora atual pertence ao intervalo em que houve chegada de ligações
        if ((horaAtual > INI_LIGACOES && horaAtual < FIM_LIGACOES_MANHA) ||
            (horaAtual > INI_LIGACOES_TARDE && horaAtual < FIM_LIGACOES)) {
            //Se já chegou na hora de criar a próxima ligação
            if (ligPrx != null && ligPrx.before(cal.getTime())) {
                nova = new Ligacao(cal.getTime());
                //Insere uma nova ligação na fila de espera e na lista geral de ligações criadas
                ligEspera.add(nova);
                ligGeral.add(nova);
                //Habilita a criação da próxima ligação
                ligPrx = null;
            }
            //Se não tem um horário da próxima ligação, verifica se já deve criá-la
            if (ligPrx == null) {
                double proxLigMili = geraRandomExp(generator_criaLig, 3.24, 2653) * 1000;
                ligPrx = new Date(cal.getTimeInMillis() + (long) (proxLigMili));
                test.add(proxLigMili/1000);
            }
        }
    }

    //Seleciona ligações em espera e aborta se necessário (cliente desiste da ligação atual)
    private void abortaLig(){
        int indiceAborta;
        //Se caiu na chance de abortar uma ligação (com base na quantidade total de ligações nesta situação)
        if(generator_abortaLig.nextFloat() < (85/1949)){
            //Seleciona aleatoriamente uma das ligações em espera
            indiceAborta = (int) generator_abortaEscolheLig.nextFloat()*ligEspera.size();
            //Se está no grupo de ligações que pode retornar posteriormente
            if(generator_abortaLig.nextFloat() < (76/1949)){
                ligRetornar.add(ligEspera.remove(indiceAborta));
            }else{
                ligAbortadas.add(ligEspera.remove(indiceAborta));
            }
        }
    }

    //Seleciona ligações abortadas e gera novo registro de ligação (cliente retoma ligação abortada anteriormente)
    private void recriaLig(){

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
                    double duraLigSeg = geraRandomExp(generator_duraLig, 14.17, 6660);
                    //Remove a ligação da lista de espera e define o horário previsto para o final da ligação
                    Ligacao lig = ligEspera.remove(0);
                    lig.atende(cal.getTime(), (long) duraLigSeg);
                    //Gera o atendimento
                    tec.atende(lig);
                }
            }
        }
    }
    
    //Gera número aleatório com distribuição exponencial utilizando média e valor máximo especificados
    private double geraRandomExp(Random gerador, double ref_media, double max){
        //Distribuição Exponencial
        // -ln(1 - (1 - exp(-λ)) * U) / λ
        float random_exp = gerador.nextFloat();
        //double exp = Math.log(1 - random_exp) / (-13.17);
        double exp = -1 * Math.log(1 - (1 - Math.exp(-ref_media)) * random_exp) / ref_media;
        return exp * max;
    }
    
    //Esta rotina é usada para testar a geração de algum ponto qualquer do simulador
    private void estatisticaTestes(){
        Double[] vect = new Double[test.size()];
        test.toArray(vect);
        new Histogram( vect, 100, "Testes");
    }

    private void estatisticaLigacoes(){
        ArrayList<Double> data =  new ArrayList<>();
        double intervalo;
        Calendar dataLig = Calendar.getInstance();
        Calendar dataUlt = Calendar.getInstance();
        int horaFormatLig = 0;
        int horaFormatUlt = 0;
        double horaLig = 0;
        double horaUlt = 0;
        for (Ligacao lig : ligGeral) {
            dataLig.setTime(lig.getInicio());
            horaFormatLig = dataLig.get(Calendar.HOUR_OF_DAY) * 10000 +
                            dataLig.get(Calendar.MINUTE) * 100 +
                            dataLig.get(Calendar.MILLISECOND);
            horaLig = lig.getInicio().getTime();
            //Se já tem data da última ligação
            if(dataUlt != null){
                //Se data da última ligação é no mesmo dia que a atual
                if(dataLig.get(Calendar.DAY_OF_MONTH) == dataUlt.get(Calendar.DAY_OF_MONTH)){
                    //Se não for uma ligação antes das 12:30 e a outra após (convenção adotada para desconsiderar intervalos entre turnos)
                    if(!(horaFormatLig>123000 && horaFormatUlt <=123000)){
                        //Calcula o intervalo em milissegundos entre as datas das ligações
                        intervalo = horaLig - horaUlt;
                        data.add(intervalo/1000);
                    }
                }
            }
            dataUlt.setTime(dataLig.getTime());
            horaFormatUlt = horaFormatLig;
            horaUlt = horaLig;
        }
        Double[] vect = new Double[data.size()];
        data.toArray(vect);
        new Histogram( vect, 100, "Tempo entre ligações (segundos)");
    }

    private void estatisticaAtendimentos(){
    
    }

    private void estatisticaEspera(){
//            double espera = (ligEncerrada.getIniAtend().getTime() - ligEncerrada.getPrilig().getTime()) / 1000;
//        new Histogram( vect, 100, "Tempo de espera (segundos)");
    
    }

}
