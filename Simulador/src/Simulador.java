import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulador {
    //Parâmetros
    private Integer fimAtendimentos;
    private Integer fimAtendimentosTarde;
    private Integer fimLigacoes;
    private Integer fimLigacoesTarde;
    private Integer iniAtendimentos;
    private Integer iniAtendimentosTarde;
    private Integer iniLigacoes;
    private Integer iniLigacoesTarde;
    private Integer numTecnicos;
    private Float taxaAtend;
    private Float taxaRegistro;
    private boolean zeraFila;
    private boolean estagManha;
    private boolean estagTarde;
    private Integer semente;
    //Constantes
    private static final int DURACAO_INDISPONIBILIDADE = 1100;
    //Campos de controle
    private SimpleDateFormat dateFormat;
    private Date tempo;
    private Calendar cal;
    private int horaAtual;
    private Random generator_criaLig;
    private Random generator_abortaLig;
    private Random generator_abortaEscolheLig;
    private Random generator_abortaTempoRetorno;
    private Random generator_duraLig;
    private Random generator_duraLigExp;
    private Random generator_duraLigLin;
    private Random generator_duraLigNor;
    private Random generator_tecnicoAtivo;
    private ArrayList<Ligacao> ligGeral; //Todas as ligações geradas (usado para cálculo de intervalo entre ligações)
    private ArrayList<Ligacao> ligEspera; //Ligações a serem atendidas
    private ArrayList<Ligacao> ligRetornar; //Ligações que foram abortadas mas vão retornar posteriormente
    private ArrayList<Ligacao> ligAbortadas; //Ligações abortadas que não retornam mais
    private ArrayList<Ligacao> ligEncerradas; //Ligações atendidas, uma vez concluídas
    private ArrayList<Tecnico> tecnicos; //Técnicos disponíveis
    private Date ligPrx; //Próximo horário previsto para entrada de ligação

    public Simulador(Integer fimAtendimentos, Integer fimAtendimentosTarde, Integer fimLigacoes,
            Integer fimLigacoesTarde, Integer iniAtendimentos, Integer iniAtendimentosTarde, Integer iniLigacoes,
            Integer iniLigacoesTarde, Integer numTecnicos, Float taxaAtend, Float taxaRegistro, boolean zeraFila,
            boolean estagManha, boolean estagTarde, Integer semente) {
        this.fimAtendimentos = fimAtendimentos;
        this.fimAtendimentosTarde = fimAtendimentosTarde;
        this.fimLigacoes = fimLigacoes;
        this.fimLigacoesTarde = fimLigacoesTarde;
        this.iniAtendimentos = iniAtendimentos;
        this.iniAtendimentosTarde = iniAtendimentosTarde;
        this.iniLigacoes = iniLigacoes;
        this.iniLigacoesTarde = iniLigacoesTarde;
        this.numTecnicos = numTecnicos;
        this.taxaAtend = taxaAtend;
        this.taxaRegistro = taxaRegistro;
        this.zeraFila = zeraFila;
        this.estagManha = estagManha;
        this.estagTarde = estagTarde;
        this.semente = semente;
    }

    public Simulador() {

    }

    public void start(){
        int diaSem = 0;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tempo = new Date();
        //Gera instâncias de ArrayList
        ligGeral = new ArrayList<>();
        ligEspera = new ArrayList<>();
        ligRetornar = new ArrayList<>();
        ligAbortadas = new ArrayList<>();
        ligEncerradas = new ArrayList<>();
        tecnicos = new ArrayList<>();
        //Gera técnicos padrão, que trabalha em ambos os turnos
        for (int i = 0; i < numTecnicos; i++) {
            tecnicos.add(new Tecnico());
        }
        //Gera estagiário da manhã
        if(estagManha){
            tecnicos.add(new Tecnico(true, false));
        }
        //Gera estagiário da tarde
        if(estagTarde){
            tecnicos.add(new Tecnico(false, true));
        }
        try {
            tempo = dateFormat.parse("2018-01-01 00:00:00");
            cal = Calendar.getInstance();
            cal.setTime(tempo);
        } catch (ParseException ex) {
            Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date fin = null;
        try {
            fin = dateFormat.parse("2018-04-20 23:59:00");
        } catch (ParseException ex) {
            Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Prepara geradores de números aleatórios
        Random generator_seed;
        if(semente != 0){
            generator_seed = new Random(semente);
        }else{
            generator_seed = new Random();
        }
        generator_criaLig = new Random(generator_seed.nextLong());
        generator_abortaLig = new Random(generator_seed.nextLong());
        generator_abortaEscolheLig = new Random(generator_seed.nextLong());
        generator_abortaTempoRetorno = new Random(generator_seed.nextLong());
        generator_duraLig = new Random(generator_seed.nextLong());
        generator_duraLigExp = new Random(generator_seed.nextLong());
        generator_duraLigLin = new Random(generator_seed.nextLong());
        generator_duraLigNor = new Random(generator_seed.nextLong());
        generator_tecnicoAtivo = new Random(generator_seed.nextLong());
        //Varre da data inical até que chegue na data final
        while(cal.getTime().before(fin)){
            //Horário formatado para simplificar teste: hhmmss
            horaAtual = cal.get(Calendar.HOUR_OF_DAY) * 10000 +
                             cal.get(Calendar.MINUTE) * 100 +
                             cal.get(Calendar.SECOND);
            if(horaAtual == 0){
                if(zeraFila){
                    ligEspera.clear();
                }
                diaSem = cal.get(Calendar.DAY_OF_WEEK);
            }
            //Se for dia de semana
            if(diaSem > 1 && diaSem < 7){
                iteracao();
            }
            cal.add(Calendar.SECOND, 1);
        }
        //Uma vez concluída a simulação, gera dados sobre os resultados (média, desvio padrão, etc...)
        estatisticaLigacoes();
        estatisticaAtendimentos();
        estatisticaEspera();
    }

    private void iteracao(){
        //Gera novas ligações conforme o horário
        criaLig();
        //Atualiza status dos técnicos
        encerraLig();
        //Libera técnicos que estavam indisponíveis
        liberaTecnico();
        //Gera atendimentos
        atendeLig();
    }

    //Cria ligações conforme intervalo entre chegadas
    private void criaLig(){
        //Ligação a ser gerada
        Ligacao nova;
        //Se a hora atual pertence ao intervalo em que houve chegada de ligações
        if ((horaAtual > iniLigacoes && horaAtual < fimLigacoes) ||
            (horaAtual > iniLigacoesTarde && horaAtual < fimLigacoesTarde)) {
            //Verifica se deve recriar ligações que foram abortadas
            recriaLig();
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
            }
        }
    }

    //Seleciona ligações em espera e aborta se necessário (cliente desiste da ligação atual)
    private boolean abortaLig(){
        //Cria taxas de desistências medidas
        final float TAXA_ABORTA = (float) 85/1949;
        final float TAXA_RETORNA = (float) 76/1949;
        int indiceAborta;
        int tempoRetorno;
        Ligacao lig;
        //Se caiu na chance de abortar uma ligação (com base na quantidade total de ligações nesta situação)
        if(!ligEspera.isEmpty() && generator_abortaLig.nextFloat() < (TAXA_ABORTA)){
            //Seleciona aleatoriamente uma das ligações em espera
            indiceAborta = (int) generator_abortaEscolheLig.nextFloat()*ligEspera.size();
            tempoRetorno = (int) geraRandomExp(generator_abortaTempoRetorno, 3.24, 170);
            //Se está no grupo de ligações que pode retornar posteriormente
            if(generator_abortaLig.nextFloat() < (TAXA_RETORNA)){
                lig = new Ligacao(new Date(cal.getTimeInMillis() + tempoRetorno), ligEspera.remove(indiceAborta));
                ligRetornar.add(lig);
            }else{
                ligAbortadas.add(ligEspera.remove(indiceAborta));
            }
            return true;
        }
        return false;
    }

    //Seleciona ligações abortadas e gera novo registro de ligação (cliente retoma ligação abortada anteriormente)
    private void recriaLig(){
        Iterator<Ligacao> iter;
        if(!ligRetornar.isEmpty()){
            iter = ligRetornar.iterator();
            while (iter.hasNext()) {
                Ligacao lig = iter.next();
                //Se já chegou na hora desta ligação abortada voltar para a fila (cliente ligou de volta)
                if(lig.getInicio().before(cal.getTime())){
                    //Insere uma nova ligação na fila de espera e na lista geral de ligações criadas
                    ligEspera.add(lig);
                    ligGeral.add(lig);
                    //Retira da lista de ligações a retornar
                    iter.remove();
                }
            }            
        }
    }

    private void liberaTecnico() {
        //Encontra técnicos para liberá-los
        for(Tecnico tec : tecnicos){
            //Se o técnico está com ligação em andamento
            if(!tec.isDisponivel()){
                //Se já chegou na data/hora que o técnico ficou disponível para atendimentos novamente
                if(!cal.getTime().before(tec.getInicioDisponibilidade())){
                    tec.setDisponivel();
                }
            }
        }
    }

    private void encerraLig(){
        //Data final em que o técnico será liberado com base no tempo de atendimento e no tempo de registro do atendimento
        Date finalCalc;
        Ligacao lig;
        //Encontra técnicos para encerrar ligações
        for(Tecnico tec : tecnicos){
            //Se o técnico está com ligação em andamento
            if(tec.isAtendendo()){
                lig = tec.getLig();
                float tempoRegistro = lig.getDuracao() * taxaRegistro * 1000;
                //Incrementa o tempo que o técnico ficou ocupado com base no tempo que durou a ligação (tempo de registro)
                finalCalc = new Date(lig.getFimAtend().getTime() + (int) tempoRegistro );
                //Se já chegou na data/hora do final do atendimento
                if(!cal.getTime().before(finalCalc)){
                    //Vincula a ligação na lista dos atendimentos concluídos
                    ligEncerradas.add(tec.getLig());
                    tec.desliga();
                    if(generator_tecnicoAtivo.nextFloat()> (taxaAtend)){
                        tec.setIndisponivel(cal.getTime(), DURACAO_INDISPONIBILIDADE);
                    }
                }
            }
        }
    }

    private void atendeLig(){
        boolean turnoManha = false;
        boolean turnoTarde = false;
        //Se está no turno da manhã
        if (horaAtual > iniAtendimentos && horaAtual < fimAtendimentos){
            turnoManha = true;
        }
        //Se está no turno da tarde
        if (horaAtual > iniAtendimentosTarde && horaAtual < fimAtendimentosTarde){
            turnoTarde = true;
        }
        //Se a hora atual pertence ao intervalo em que houve atendimento de ligações
        if (turnoManha || turnoTarde) {
            //Se há ligações em espera
            if(!ligEspera.isEmpty()){
                //Encontra um técnico livre que possa atender
                for(Tecnico tec : tecnicos){
                    //Se o técnico está livre e está atendendo
                    if(tec.isLivre() && ((tec.atendeManha() && turnoManha) || (tec.atendeTarde() && turnoTarde))){
                        //Seleciona ligações em espera e aborta se necessário (cliente desiste da ligação atual)
                        if(abortaLig())break;
                        //Gera distribuição para o valor de duração da ligação, em milissegundos
                        double duraLigSeg = geraDuracaoLigacao();
                        //Remove a ligação da lista de espera e define o horário previsto para o final da ligação
                        Ligacao lig = ligEspera.remove(0);
                        lig.atende(cal.getTime(), (long) duraLigSeg);
                        //Gera o atendimento
                        tec.atende(lig);
                        break;
                    }
                }
            }
        }
    }

    //Gera distribuição da duração das ligações em três intervalos, um exponencial crescente, um decrescente e outro contínuo
    private double geraDuracaoLigacao(){
        //Ponto de corte entre as duas distribuições, de 0 até 1
        final double SPLIT_1 = 0.1;
        final double SPLIT_2 = 0.99;
        //Valor máximo do ponto de corte
        final int MINUTE_SPLIT = 240;
        double duraLigCalc;
        //Divide a distribuição em três seções
        if(generator_duraLig.nextFloat() < SPLIT_1){
            duraLigCalc = MINUTE_SPLIT * Math.log(1 - (1 - Math.exp(6)) * generator_duraLigLin.nextFloat()) / 6;
        }else{
            if(generator_duraLig.nextFloat() < SPLIT_2){
                duraLigCalc = geraRandomExp(generator_duraLigExp, 14.17, 6660) + MINUTE_SPLIT;
            }else{
                duraLigCalc = 4200 + generator_duraLigNor.nextFloat() * 2460;
            }
        }
        return duraLigCalc;
    }

    //Gera número aleatório com distribuição exponencial utilizando média e valor máximo especificados
    private double geraRandomExp(Random gerador, double ref_media, double max){
        //Distribuição Exponencial
        // -ln(1 - (1 - exp(-λ)) * U) / λ
        float random_exp = gerador.nextFloat();
        //Calcula com base no máximo valor possível e na taxa de queda que definirá tmbém a média
        double exp = -1 * Math.log(1 - (1 - Math.exp(-ref_media)) * random_exp) / ref_media;
        return exp * max;
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
                        data.add(intervalo/60000);
                    }else{
                        data.add(0.0);
                    }
                }else{
                    data.add(0.0);
                }
            }
            dataUlt.setTime(dataLig.getTime());
            horaFormatUlt = horaFormatLig;
            horaUlt = horaLig;
        }
        Double[] vect = new Double[data.size()];
        data.toArray(vect);
        new Histogram( vect, 100, "Tempo entre ligações (minutos)");
    }

    private void estatisticaAtendimentos(){
        ArrayList<Double> data =  new ArrayList<>();
        for (Ligacao lig : ligEncerradas) {
            data.add((double) lig.getDuracao() / 60);
        }
        Double[] vect = new Double[data.size()];
        data.toArray(vect);
        new Histogram( vect, 100, "Tempo de duração dos atendimentos (minutos)");
    }

    private void estatisticaEspera(){
        ArrayList<Double> data =  new ArrayList<>();
        double tempoMili;
        double espera;
        for (Ligacao lig : ligEncerradas) {
            tempoMili = lig.getIniAtend().getTime() - lig.getPrilig().getTime();
            espera = tempoMili / 60000;
            data.add(espera);
        }
        Double[] vect = new Double[data.size()];
        data.toArray(vect);
        new Histogram( vect, 100, "Tempo de espera até atendimento (minutos)");
    }
}
