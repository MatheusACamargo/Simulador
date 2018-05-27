
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulador {
    private SimpleDateFormat dateFormat;
    private Date tempo;
    private Calendar cal;
    
    
    public Simulador() {
        tempo = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        //Varre da data inical até que chegue na data final
        while(cal.getTime().before(fin)){
            try {
                iteracao();
                cal.add(Calendar.SECOND, 10);
            } catch (IOException ex) {
                Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Uma vez concluída a simulação, gera dados sobre os resultados (média, desvio padrão, etc...)
    }
    
    private void iteracao() throws IOException{
        
        System.out.println(cal.getTime().toString());
        //Gerar desistência das ligações ainda não atendidas
        //Gerar novas ligações conforme o horário (gerar inclusive mais de uma se necessário)
        //Atualizar status dos técnicos (desligar ligações, passar para livre)
        //Gerar atendimentos (vincular uma ligação no técnico)
        
        //Três arrays para classificar as ligações
    }
        
}
