package es.uned.jchacon.model_elements.process_control.elements.disabled;

//package org.colos.ejs.model_elements.apache_numerics;

import javax.swing.ImageIcon;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.opensourcephysics.tools.ResourceLoader;

public class DIPTDElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/DIPTD.png";
	
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "DoubleIntegratorProcess"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.DIPTD"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Double integrator process model";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/DIPTDModel.html";
  }

}
