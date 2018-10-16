/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.pdfs;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;


/**
 *
 * @author Jean
 */
public class !CertificadoNacimiento {
    
    public String DEST = "certificado_nacimiento.pdf";
    public String SRC = "Resources/nacimiento.pdf";
    
    public CertificadoNacimiento(){
        
    }
    
    public void crear() throws IOException{
            PDDocument doc = PDDocument.load(new File(SRC));
            doc.save("C:/my_doc.pdf");
            doc.close();
    }
    
}
