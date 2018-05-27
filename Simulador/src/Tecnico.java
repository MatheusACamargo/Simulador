
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Tecnico {
    Ligacao lig;//Ligação que está sendo atendida pelo técnico neste momento

    public Tecnico() {
    
    }

    public boolean isOcupado() {
        return lig != null;
    }
    
}
