package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.Component;
//import java.awt.Font;
//
//import java.util.ArrayList;
//
import javax.swing.*;
import javax.swing.border.EtchedBorder;
//
import org.colos.ejs.model_elements.*;
//import org.colos.ejs.osejs.OsejsCommon;
//import org.opensourcephysics.tools.ResourceLoader;

import javax.swing.ImageIcon;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.opensourcephysics.tools.ResourceLoader;

public class DiscreteFilteredPidControllerElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/DiscretePidController.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
private ModelElementEditor regionRunEditor;

{
//  regionRunEditor = new ModelElementEditor (this,null, true, "Variables", "Run");
//  regionRunEditor.addPragma("CRITICAL", "region().critical (new edu.rit.pj.ParallelSection() { public void run() {\n","}});\n");
//  regionRunEditor.readPlainCode("// "+ RES.getString("ParallelJava.RegionRun.ToolTip"));
}
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "DiscreteFilteredPidController"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.DiscreteFilteredPidController"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Discrete PID controller with a first-order filter";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/DiscreteFilteredPidController.html";
  }

  @Override
  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {
    JComponent rrComp = regionRunEditor.getComponent(collection);
    rrComp.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    return rrComp;
  }

//  public java.util.List<ModelElementSearch> search (String info, String searchString, int mode, String name, ModelElementsCollection collection) {
//    java.util.List<ModelElementSearch> list = new ArrayList<ModelElementSearch>();
//    list.addAll(regionRunEditor.search(info, searchString, mode, name, collection));
//    return list;
//  }
}