package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.awt.Font;
//
//import java.util.ArrayList;
//
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
//
import org.colos.ejs.model_elements.*;
//import org.colos.ejs.osejs.OsejsCommon;
//import org.opensourcephysics.tools.ResourceLoader;

import javax.swing.ImageIcon;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.opensourcephysics.tools.ResourceLoader;

public class StateSpaceModelElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/StateSpaceModel.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
  private ModelElementEditor regionRunEditor;
  private JTextField textA = new JTextField();
  private JTextField textB = new JTextField();
  private JTextField textC = new JTextField();
  private JTextField textD = new JTextField();

//{
//  regionRunEditor = new ModelElementEditor (this,null, true, "Variables", "Run");
//  regionRunEditor.addPragma("CRITICAL", "region().critical (new edu.rit.pj.ParallelSection() { public void run() {\n","}});\n");
//  regionRunEditor.readPlainCode("// "+ RES.getString("ParallelJava.RegionRun.ToolTip"));
//}
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "StateSpaceModel"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.StateSpaceModel"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "State-space process model";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/StateSpaceModel.html";
  }

  public String getInitializationCode(String _name) {
	  String A = textA.getText().isEmpty() ? "{}" : textA.getText(),
			 B = textB.getText().isEmpty() ? "{}" : textB.getText(),
			 C = textC.getText().isEmpty() ? "{}" : textC.getText(),
			 D = textD.getText().isEmpty() ? "{}" : textD.getText();

	  String init = _name + " = new " + getConstructorName() + "(" 
			      + "new double[][]" + A + ", " 
			      + "new double[][]" + B + ", "
			      + "new double[][]" + C + ", "
			      + "new double[][]" + D + ");";
	  System.out.println(init);
	  return init;
  }
  
  @Override
  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {    
	  JPanel mainPanel = new JPanel(new BorderLayout());

	  mainPanel.setBounds(100, 100, 450, 150);

	  JPanel panel = new JPanel();
	  mainPanel.add(panel, BorderLayout.CENTER);
	  panel.setBorder(new TitledBorder(null, "PID gains", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	  SpringLayout sl_panel = new SpringLayout();
	  panel.setLayout(sl_panel);
	  panel.setMinimumSize(new Dimension(400, 150));
	  panel.setPreferredSize(new Dimension(400, 150));

	  JLabel lblProportionalGain = new JLabel("A:");
	  lblProportionalGain.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblProportionalGain, 10, SpringLayout.NORTH, panel);
	  sl_panel.putConstraint(SpringLayout.WEST, lblProportionalGain, 10, SpringLayout.WEST, panel);
	  panel.add(lblProportionalGain);

	  JLabel lblIntegralGain = new JLabel("B:");
	  lblIntegralGain.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblIntegralGain, 6, SpringLayout.SOUTH, lblProportionalGain);
	  sl_panel.putConstraint(SpringLayout.WEST, lblIntegralGain, 0, SpringLayout.WEST, lblProportionalGain);
	  panel.add(lblIntegralGain);

	  JLabel lblDerivativeGain = new JLabel("C:");
	  lblDerivativeGain.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblDerivativeGain, 6, SpringLayout.SOUTH, lblIntegralGain);
	  sl_panel.putConstraint(SpringLayout.WEST, lblDerivativeGain, 0, SpringLayout.WEST, lblProportionalGain);
	  panel.add(lblDerivativeGain);

	  JLabel lblDerivativeFilterTime = new JLabel("D:");
	  lblDerivativeFilterTime.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblDerivativeFilterTime, 6, SpringLayout.SOUTH, lblDerivativeGain);
	  sl_panel.putConstraint(SpringLayout.WEST, lblDerivativeFilterTime, 0, SpringLayout.WEST, lblProportionalGain);
	  panel.add(lblDerivativeFilterTime);

/*	  JLabel lblAntiwindup = new JLabel("Anti-windup:");
	  sl_panel.putConstraint(SpringLayout.NORTH, lblAntiwindup, 6, SpringLayout.SOUTH, lblDerivativeFilterTime);
	  sl_panel.putConstraint(SpringLayout.WEST, lblAntiwindup, 0, SpringLayout.WEST, lblProportionalGain);
	  lblAntiwindup.setHorizontalAlignment(SwingConstants.RIGHT);
	  panel.add(lblAntiwindup);

	  JLabel lblIntegratorResetTime = new JLabel("Integrator Reset (Ks):");
	  sl_panel.putConstraint(SpringLayout.NORTH, lblIntegratorResetTime, 6, SpringLayout.SOUTH, lblAntiwindup);
	  sl_panel.putConstraint(SpringLayout.WEST, lblIntegratorResetTime, 10, SpringLayout.WEST, panel);
	  panel.add(lblIntegratorResetTime);
*/
	  sl_panel.putConstraint(SpringLayout.WEST, textA, 6, SpringLayout.EAST, lblProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textA, 0, SpringLayout.SOUTH, lblProportionalGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textA, -10, SpringLayout.EAST, panel);
	  panel.add(textA);
	  textA.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textB, 0, SpringLayout.WEST, textA);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textB, 0, SpringLayout.SOUTH, lblIntegralGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textB, -10, SpringLayout.EAST, panel);
	  panel.add(textB);
	  textB.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textC, 0, SpringLayout.WEST, textA);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textC, 0, SpringLayout.SOUTH, lblDerivativeGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textC, -10, SpringLayout.EAST, panel);
	  panel.add(textC);
	  textC.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textD, 0, SpringLayout.WEST, textA);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textD, 0, SpringLayout.SOUTH, lblDerivativeFilterTime);
	  sl_panel.putConstraint(SpringLayout.EAST, textD, -10, SpringLayout.EAST, panel);
	  panel.add(textD);
	  textD.setColumns(10);

      ActionListener verify = new AbstractAction() { 		
		  private String number = "(\\+|-)?((\\d+\\.)|(\\.?\\d))\\d*((e|E)(\\+|-)?\\d+)?";
		  private String row = "\\{" + number + "(," + number + ")*\\}";
		  private String matrix = "\\{" + row + "(," + row + ")*\\}";
		  private Pattern pattern = Pattern.compile(matrix);
		  
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

      textA.addActionListener(verify);
      textB.addActionListener(verify);
      textC.addActionListener(verify);
      textD.addActionListener(verify);

	  return mainPanel;   
  } 

//  public java.util.List<ModelElementSearch> search (String info, String searchString, int mode, String name, ModelElementsCollection collection) {
//    java.util.List<ModelElementSearch> list = new ArrayList<ModelElementSearch>();
//    list.addAll(regionRunEditor.search(info, searchString, mode, name, collection));
//    return list;
//  }

  public String savetoXML() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<StateSpaceModel>");
    buffer.append("<A>" + textA.getText() + "</A>");
    buffer.append("<B>" + textB.getText() + "</B>");
    buffer.append("<C>" + textC.getText() + "</C>");
    buffer.append("<D>" + textD.getText() + "</D>");
    buffer.append("</StateSpaceModel>");
    return buffer.toString();
   }

  public void readfromXML(String _inputXML) {
	textA.setText(_inputXML.substring(_inputXML.indexOf("<A>")+3, _inputXML.indexOf("</A>")));
	textB.setText(_inputXML.substring(_inputXML.indexOf("<B>")+3, _inputXML.indexOf("</B>")));
	textC.setText(_inputXML.substring(_inputXML.indexOf("<C>")+3, _inputXML.indexOf("</C>")));
	textD.setText(_inputXML.substring(_inputXML.indexOf("<D>")+3, _inputXML.indexOf("</D>")));
  }

}


//package org.colos.ejs.model_elements.parallel_ejs;
//
//import java.awt.Component;
//import java.awt.Font;
//
//import java.util.ArrayList;
//
//import javax.swing.*;
//import javax.swing.border.EtchedBorder;
//
//import org.colos.ejs.model_elements.*;
//import org.colos.ejs.osejs.OsejsCommon;
//import org.opensourcephysics.tools.ResourceLoader;
//
//public class PJRegionElement extends AbstractModelElement {
//  static private ImageIcon ELEMENT_ICON = ResourceLoader.getIcon("org/colos/ejs/model_elements/parallel_ejs/PJRegion.png");
//
//  static private final String BEGIN_REGION_HEADER = "<RegionRun>";
//  static private final String END_REGION_HEADER = "</RegionRun>";
//
//  private ModelElementEditor regionRunEditor;
//  
//  {
//    regionRunEditor = new ModelElementEditor (this,null, true, "Variables", "Run");
//    regionRunEditor.addPragma("CRITICAL", "region().critical (new edu.rit.pj.ParallelSection() { public void run() {\n","}});\n");
//    regionRunEditor.readPlainCode("// "+ RES.getString("ParallelJava.RegionRun.ToolTip"));
//  }
//
//  // -------------------------------
//  // Implementation of ModelElement
//  // -------------------------------
//  
//  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
//  
//  public String getGenericName() { return "pjRegion"; }
//  
//  public String getConstructorName() { return "org.colos.ejs.model_elements.parallel_ejs.PJTeam"; }
//  
//  public String getInitializationCode(String _name) {
//    StringBuffer buffer = new StringBuffer();
//    buffer.append(_name + " = new " + getConstructorName() + "(new edu.rit.pj.ParallelRegion() {\n");
//    // Region run
//    buffer.append("  private int _firstThreadIndex(int _rawFirstIndex, int _rawLastIndex) {\n");
//    buffer.append("    int _threadIndex = getThreadIndex();\n");
//    buffer.append("    if (_threadIndex==0) return _rawFirstIndex;\n");
//    buffer.append("    return _rawFirstIndex + _threadIndex*(_rawLastIndex-_rawFirstIndex) / getThreadCount() + 1;\n");
//    buffer.append("  }\n");
//    buffer.append("  private int _lastThreadIndex(int _rawFirstIndex, int _rawLastIndex) {\n");
//    buffer.append("    return _rawFirstIndex + (getThreadIndex()+1)*(_rawLastIndex-_rawFirstIndex) / getThreadCount();\n");
//    buffer.append("  }\n");
//
//    buffer.append("  public void run() throws Exception { // Region's run method\n");
//    buffer.append("    int _threadCount = getThreadCount();\n");
//    buffer.append("    int _threadIndex = getThreadIndex();\n");
//    buffer.append(regionRunEditor.generateCode(_name, "    "));
//    buffer.append("  }\n\n");
//
//    buffer.append("}); // end of ParallelRegion\n");
//    return buffer.toString();
//  }
//  
//  public String savetoXML() {
//    StringBuffer buffer = new StringBuffer();
//    buffer.append(BEGIN_REGION_HEADER);
//    buffer.append(regionRunEditor.saveStringBuffer());
//    buffer.append(END_REGION_HEADER);
//    return buffer.toString();
//  }
//
//  public void readfromXML(String _inputXML) {
//    regionRunEditor.readXmlString(OsejsCommon.getPiece(_inputXML,BEGIN_REGION_HEADER,END_REGION_HEADER,false));
//  }
//  
//  // -------------------------------
//  // Help and edition
//  // -------------------------------
//
//  public String getTooltip() { return "defines a code that can be run in parallel"; }
//  
//  public void setFont(Font font) {
//    regionRunEditor.setFont(font);
//  }
//
//  protected String getHtmlPage() { return "org/colos/ejs/model_elements/parallel_ejs/PJRegion.html"; }
//
//  @Override
//  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {
//    JComponent rrComp = regionRunEditor.getComponent(collection);
//    rrComp.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//    return rrComp;
//  }
//
//  public java.util.List<ModelElementSearch> search (String info, String searchString, int mode, String name, ModelElementsCollection collection) {
//    java.util.List<ModelElementSearch> list = new ArrayList<ModelElementSearch>();
//    list.addAll(regionRunEditor.search(info, searchString, mode, name, collection));
//    return list;
//  }
//  
//}
