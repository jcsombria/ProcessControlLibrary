package es.uned.jchacon.model_elements.process_control.elements.disabled;

//package org.colos.ejs.model_elements.apache_numerics;

import javax.swing.ImageIcon;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.opensourcephysics.tools.ResourceLoader;

public class SOPTDElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/SOPTD.png";
	
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "SecondOrderProcess"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.SOPTD"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Second-order process model";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/SOPTDModel.html";
  }

}
