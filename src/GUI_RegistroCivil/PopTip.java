
package GUI_RegistroCivil;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.PopOver;


public class PopTip {
    private String msj;
    
    public PopTip(String str){
        msj = str;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }
    
    public PopOver popTip(){
        Label tip = new Label(msj);
        tip.setStyle("-fx-text-fill: black");
        tip.setFont(Font.font("bold", FontWeight.NORMAL, 16));
        VBox tool = new VBox(tip);
        tool.setStyle("-fx-background-color : #DFD500");
        PopOver tooltip = new PopOver();
        tooltip.setContentNode(tool);
        return tooltip;
    }
}
