
import java.time.Duration;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Ligacao {
    private Date inicio; //Data e hora de início da ligação
    private Date prilig; //Data e hora de início da ligação
    private Date atendido; //Data e hora que a ligação foi atendida
    private Duration duracao; //Duração da ligação uma vez atendida

    public Ligacao(Date inicio) {
        this.inicio = inicio;
        prilig = inicio;
    }

    public Ligacao(Date inicio, Ligacao orig) {
        this.inicio = inicio;
        prilig = orig.getPrilig();
    }

    public Date getPrilig() {
        return prilig;
    }
    
    
    
}
