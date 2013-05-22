package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.colos.ejs.model_elements.ModelElementsCollection;
import org.opensourcephysics.tools.ResourceLoader;

public class SourceElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/Source.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  private JTextField varText = new JTextField("");
 
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { 
	  return ELEMENT_ICON; 
  }
  
  public String getGenericName() { 
	  return "Source"; 
  } 
  
  public String getConstructorName() {
	  return "es.uned.jchacon.model_elements.process_control.continuous.Source";
  }
  
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
    JLabel varLabel = new JLabel("EJS Model Variable:");
	    
    mainPanel.setBorder(BorderFactory.createTitledBorder("SOD Sampler settings"));    
    mainPanel.add(varLabel);
    mainPanel.add(varText);
    mainPanel.setPreferredSize(new Dimension(200,60));
	   
    return mainPanel;
  } 
  
  public String savetoXML() { 
	  return "<var>" + varText.getText() + "</var>";
  }
  
  public void readfromXML(String _inputXML) { 
	  int begin = _inputXML.indexOf("<var>")+ "<var>".length(), end = _inputXML.indexOf("</var>");
	  if(begin != -1 && end != -1) {
		  varText.setText(_inputXML.substring(begin, end));
	  }
  } 

  @Override
  public String getInitializationCode(String _name) {
	return  _name + "= new " + getConstructorName() + "();";
//			{" +
//			"  public double getOutput(int id) { " +
//			"    return (double)" + varText.getText() + ";" + 
//			"  } " + 
//			"};";	  
  }
}