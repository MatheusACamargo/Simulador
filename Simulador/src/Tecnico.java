
import java.util.Date;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Tecnico {
    Ligacao lig;//Ligação que está sendo atendida pelo técnico neste momento
    boolean disponivel = true;
    boolean turnoManha = true;
    boolean turnoTarde = true;
    Date inicioDisponibilidade;

    public Tecnico() {

    }

    public Tecnico(boolean turnoManha, boolean turnoTarde) {
        this.turnoManha = turnoManha;
        this.turnoTarde = turnoTarde;
    }
    
    public boolean atendeManha(){
        return turnoManha;
    }
    
    public boolean atendeTarde(){
        return turnoTarde;
    }

    public boolean isLivre() {
        return disponivel && !isAtendendo();
    }

     public boolean isDisponivel() {
        return disponivel;
    }

    public void setIndisponivel(Date inicioIndisponibilidade, int duracaoIndisponibilidade) {
        inicioDisponibilidade = new Date(inicioIndisponibilidade.getTime() + duracaoIndisponibilidade * 1000);
        disponivel = false;
    }

    public void setDisponivel() {
        disponivel = true;
    }

    public Date getInicioDisponibilidade() {
        return inicioDisponibilidade;
    }

    public boolean isAtendendo() {
        return lig != null;
    }

    public void atende(Ligacao lig){
        this.lig = lig;
    }

    public void desliga(){
        lig = null;
    }

    public Ligacao getLig() {
        return lig;
    }

}
