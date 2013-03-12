package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.colos.ejs.model_elements.ModelElementsCollection;
import org.opensourcephysics.tools.ResourceLoader;

public class SaturationElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/Saturation.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
  private JTextField textMinimum = new JTextField("-1");
  private JTextField textMaximum = new JTextField("+1");
 
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "Saturation"; } 
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.Saturation"; }
 
  public String getDisplayInfo() {
	  return "(" + textMinimum.getText() + ", " + textMaximum.getText() + ")"; 
  }

  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Saturation";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/Saturation.html";
  }

  public String getInitializationCode(String _name) {
	  String init = _name + " = new " + getConstructorName() + "(" 
			      + ");";
	  System.out.println(init);
	  return init;
  }
  
  @Override
  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {    
	  JPanel mainPanel = new JPanel(new BorderLayout());

	  mainPanel.setBounds(100, 100, 400, 80);

	  JPanel panel = new JPanel();
	  mainPanel.add(panel, BorderLayout.CENTER);
	  panel.setBorder(new TitledBorder(null, "Range", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	  SpringLayout sl_panel = new SpringLayout();
	  panel.setLayout(sl_panel);
	  panel.setMinimumSize(new Dimension(400, 80));
	  panel.setPreferredSize(new Dimension(400, 80));

	  JLabel lblMinimum = new JLabel("Minimum:");
	  lblMinimum.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblMinimum, 10, SpringLayout.NORTH, panel);
	  sl_panel.putConstraint(SpringLayout.WEST, lblMinimum, 10, SpringLayout.WEST, panel);
	  panel.add(lblMinimum);

	  JLabel lblMaximum = new JLabel("Maximum:");
	  lblMaximum.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblMaximum, 6, SpringLayout.SOUTH, lblMinimum);
	  sl_panel.putConstraint(SpringLayout.WEST, lblMaximum, 0, SpringLayout.WEST, lblMinimum);
	  panel.add(lblMaximum);

	  sl_panel.putConstraint(SpringLayout.WEST, textMinimum, 6, SpringLayout.EAST, lblMinimum);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textMinimum, 0, SpringLayout.SOUTH, lblMinimum);
	  sl_panel.putConstraint(SpringLayout.EAST, textMinimum, -10, SpringLayout.EAST, panel);
	  panel.add(textMinimum);
	  textMinimum.setColumns(5);

	  sl_panel.putConstraint(SpringLayout.WEST, textMaximum, 6, SpringLayout.EAST, lblMaximum);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textMaximum, 0, SpringLayout.SOUTH, lblMaximum);
	  sl_panel.putConstraint(SpringLayout.EAST, textMaximum, -10, SpringLayout.EAST, panel);
	  panel.add(textMaximum);
	  textMaximum.setColumns(5);
	  
      ActionListener verify = new AbstractAction() {
		  private String number = "(\\+|-)?((\\d+\\.)|(\\.?\\d))\\d*((e|E)(\\+|-)?\\d+)?";
		  private Pattern pattern = Pattern.compile(number);
		  
    	  public void actionPerformed(ActionEvent e) {
    		  JTextField text = (JTextField)e.getSource();

    		  if(text != null) {
    			  String input = text.getText();

   			      if(pattern.matcher(input).matches())
   			    	  text.setBackground(Color.white);
   			      else 
   			    	  text.setBackground(Color.red);
    		  } 
    	  }
      };

      textMinimum.addActionListener(verify);
      textMaximum.addActionListener(verify);

	  return mainPanel;   
  } 
  
  public String savetoXML() { return ""; }
  
  public void readfromXML(String _inputXML) { }  
}
