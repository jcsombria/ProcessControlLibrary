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

public class DiscretePidControllerElement extends AbstractModelElement {
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
  
  public String getGenericName() { return "DiscretePidController"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.DiscretePidController"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Discrete PID controller";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/DiscretePidController.html";
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
