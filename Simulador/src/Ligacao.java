
import java.time.Duration;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Ligacao {
    private Date inicio; //Data e hora de início da ligação
    private Date prilig; //Data e hora de início da primeira ligação
    private Date iniAtend; //Data e hora em que a ligação atendimento
    private Date fimAtend; //Data e hora previstos para o final do atendimento
    private long duracao; //Duração da ligação em segundos uma vez atendida

    public Ligacao(Date inicio) {
        this.inicio = inicio;
        prilig = inicio;
    }

    public Ligacao(Date inicio, Ligacao orig) {
        this.inicio = inicio;
        prilig = orig.getPrilig();
    }
    
    public void atende(Date iniAtend, long duracao){
        this.iniAtend = iniAtend;
        this.duracao = duracao;
        //Define a data do final do atendimento com base na data/hora inicial e a duração prevista
        fimAtend = new Date(iniAtend.getTime() + duracao*1000);
    }

    public Date getInicio() {
        return inicio;
    }

    public Date getPrilig() {
        return prilig;
    }

    public long getDuracao() {
        return duracao;
    }

    public Date getIniAtend() {
        return iniAtend;
    }

    public Date getFimAtend() {
        return fimAtend;
    }

    
}
