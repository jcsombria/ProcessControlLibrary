package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.colos.ejs.model_elements.ModelElementsCollection;
import org.opensourcephysics.tools.ResourceLoader;

public class SourceElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/Source.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
 
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "Source"; } 
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.Source"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Source";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/Source.html";
  }

  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {    
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /*JLabel deltaLabel = new JLabel("Delta Threshold:");
	    
    mainPanel.setBorder(BorderFactory.createTitledBorder("SOD Sampler settings"));    
    mainPanel.add(deltaLabel);
    mainPanel.add(deltaText);
    mainPanel.setPreferredSize(new Dimension(200,60));
	    */
    return mainPanel;
	    
  } 
  
  public String savetoXML() { return ""; }
  
  public void readfromXML(String _inputXML) { }  
}
