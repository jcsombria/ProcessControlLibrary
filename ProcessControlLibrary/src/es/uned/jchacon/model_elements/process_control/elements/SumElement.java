package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.colos.ejs.model_elements.ModelElementsCollection;
import org.opensourcephysics.tools.ResourceLoader;

import es.uned.jchacon.model_elements.process_control.continuous.Sum.Sign;

public class SumElement extends AbstractModelElement {
	public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/Sum.png";
	static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
	private JTextField signsText = new JTextField("+-", 4);
 
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "Sum"; } 
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.Sum"; }
  
  public String getDisplayInfo() {
	  return "(" + signsText.getText() + ")"; 
  }

  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Sum";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/Sum.html";
  }

  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {    
	    JPanel mainPanel = new JPanel();
	    mainPanel.setBorder(BorderFactory.createTitledBorder("Sum settings"));    
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);

	    JLabel signsLabel = new JLabel("Input signs (+-...):");
		sl_mainPanel.putConstraint(SpringLayout.NORTH, signsLabel, 7, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, signsLabel, 5, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.VERTICAL_CENTER, signsText, 0, SpringLayout.VERTICAL_CENTER, signsLabel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, signsText, 7, SpringLayout.EAST, signsLabel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, signsText, -7, SpringLayout.EAST, mainPanel);
	    
	    mainPanel.add(signsLabel);
	    mainPanel.add(signsText);
	    mainPanel.setPreferredSize(new Dimension(200,50));
	    mainPanel.setMaximumSize(new Dimension(200,50));
	    
	    return mainPanel;
  } 
  
  public String savetoXML() { return ""; }
  
  public void readfromXML(String _inputXML) { } 
  
  public String getInitializationCode(String _name) {
	  String signsString = signsText.getText(); 
	  int size = signsString.length();
	  Sign[] sign = new Sign[size];
	  
	  for(int i=0; i<size; i++) sign[i] = (signsString.charAt(i) == '-') ? Sign.NEGATIVE : Sign.POSITIVE;
	  String init = _name + " = new " + getConstructorName() + "(" 
			      + ");";
	  System.out.println(init);
	  return init;
  }
}
