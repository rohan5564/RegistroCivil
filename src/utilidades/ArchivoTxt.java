
package utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ArchivoTxt {
    
    private Stage ventana;
    private String directorio;
    private FileWriter log;
    
    public ArchivoTxt(Stage ventana){
        this.ventana = ventana;
        directorio = new String();
    }

    public String getDirectorio() {
        return directorio;
    }
    
    public void setDirectorio(String str){
        FileChooser dir = new FileChooser();
        dir.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de texto (*.txt)", "*.txt"));
        dir.setInitialDirectory(new File(System.getProperty("user.home")+"/desktop"));
        File archivo = dir.showSaveDialog(ventana);;
        if(archivo != null)
            try{
                log = new FileWriter(archivo, true);
                archivoLog(str);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            directorio = archivo.toString();
        }
    }
    
    public void archivoLog(String str){
        try{
            //log.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("KK:mm:ss a")) + ": " + str);
            log.append(str.replace("\n",System.getProperty("line.separator")));
            log.flush();
            log.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
