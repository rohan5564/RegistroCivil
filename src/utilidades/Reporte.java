
package utilidades;

import javafx.scene.control.TextArea;


public class Reporte {
    private static Reporte reporte;
    private TextArea log;
    
    private Reporte(){
        this.log = new TextArea();
    }
    
    private Reporte(String msj){
        this.log = new TextArea(msj);
    }
    
    public static Reporte getInstancia(){
        if(reporte == null)
            reporte = new Reporte();
        return reporte;
    }
    
    public static Reporte getInstancia(String msj){
        if(reporte == null)
            reporte = new Reporte(msj);
        return reporte;
    }

    public TextArea getLog() {
        return log;
    }

    public void setLog(TextArea log) {
        this.log = log;
    }
        
}
