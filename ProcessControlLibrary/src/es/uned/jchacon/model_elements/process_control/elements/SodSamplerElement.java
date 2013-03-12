package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.colos.ejs.library.utils.ModelElementsUtilities;
import org.colos.ejs.model_elements.AbstractModelElement;
import org.colos.ejs.model_elements.ModelElementsCollection;
import org.colos.ejs.osejs.edition.ModelEditor;
import org.opensourcephysics.display.OSPRuntime;
import org.opensourcephysics.tools.ResourceLoader;

public class SodSamplerElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/SodSampler.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
  public final String XML_NODE_LABEL = "SodSampler"; 
  public final String XML_DELTA_LABEL = "delta"; 
  public final String XML_ALPHA_LABEL = "alpha"; 
  
  // Editor variables
  private JTextField deltaText = new JTextField("1", 4);
  private JTextField alphaText = new JTextField("0.5", 4); 
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "SodSampler"; } 
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.SodSampler"; }
  
  public String getDisplayInfo() {
	  return "(delta="+deltaText.getText()+", alpha="+alphaText.getText()+")"; 
  }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Send-on-delta sampler";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/SodSampler.html";
  }

  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {    
    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(BorderFactory.createTitledBorder("SOD Sampler settings"));    
	SpringLayout sl_mainPanel = new SpringLayout();
	mainPanel.setLayout(sl_mainPanel);

    JLabel deltaLabel = new JLabel("Threshold (delta):");
	sl_mainPanel.putConstraint(SpringLayout.NORTH, deltaLabel, 7, SpringLayout.NORTH, mainPanel);
	sl_mainPanel.putConstraint(SpringLayout.WEST, deltaLabel, 5, SpringLayout.WEST, mainPanel);
	sl_mainPanel.putConstraint(SpringLayout.VERTICAL_CENTER, deltaText, 0, SpringLayout.VERTICAL_CENTER, deltaLabel);
	sl_mainPanel.putConstraint(SpringLayout.WEST, deltaText, 7, SpringLayout.EAST, deltaLabel);
	sl_mainPanel.putConstraint(SpringLayout.EAST, deltaText, -7, SpringLayout.EAST, mainPanel);
	
	JLabel alphaLabel = new JLabel("Offset (alpha):");
	sl_mainPanel.putConstraint(SpringLayout.NORTH, alphaLabel, 7, SpringLayout.SOUTH, deltaLabel);
	sl_mainPanel.putConstraint(SpringLayout.WEST, alphaLabel, 5, SpringLayout.WEST, mainPanel);
	sl_mainPanel.putConstraint(SpringLayout.VERTICAL_CENTER, alphaText, 0, SpringLayout.VERTICAL_CENTER, alphaLabel);
	sl_mainPanel.putConstraint(SpringLayout.WEST, alphaText, 0, SpringLayout.WEST, deltaText);
	sl_mainPanel.putConstraint(SpringLayout.EAST, alphaText, 0, SpringLayout.EAST, deltaText);
	    
    mainPanel.add(deltaLabel);
    mainPanel.add(deltaText);
    mainPanel.add(alphaLabel);
    mainPanel.add(alphaText);
    mainPanel.setPreferredSize(new Dimension(200,80));
    mainPanel.setMaximumSize(new Dimension(200,80));
    
    return mainPanel; 
  } 
  
  public String savetoXML() {
	  return "<" + XML_NODE_LABEL + ">" + 
			 "<" + XML_DELTA_LABEL + ">" + deltaText.getText() +  "</" + XML_DELTA_LABEL + ">" +
			 "<" + XML_ALPHA_LABEL + ">"+ alphaText.getText() + "</" + XML_ALPHA_LABEL + ">" +
			 "</" + XML_NODE_LABEL + ">";
  }
  
  public void readfromXML(String _inputXML) {
	  int begin = _inputXML.indexOf("<"+XML_DELTA_LABEL+">") + XML_ALPHA_LABEL.length() + 2,
		  end = _inputXML.indexOf("</"+XML_DELTA_LABEL+">");
	  deltaText.setText(_inputXML.substring(begin, end));

	  begin = _inputXML.indexOf("<"+XML_ALPHA_LABEL+">") + XML_ALPHA_LABEL.length() + 2;
	  end = _inputXML.indexOf("</"+XML_ALPHA_LABEL+">");
	  alphaText.setText(_inputXML.substring(begin, end));
  }  
  
  public String getInitializationCode(String _name) {
	  String init = _name + " = new " + getConstructorName() + "(" + deltaText.getText() + ", " + alphaText.getText() + ");";
	  return init;
  }
}
