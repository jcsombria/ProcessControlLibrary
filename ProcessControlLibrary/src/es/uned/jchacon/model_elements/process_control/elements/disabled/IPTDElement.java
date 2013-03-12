package es.uned.jchacon.model_elements.process_control.elements.disabled;

//package org.colos.ejs.model_elements.apache_numerics;

import javax.swing.ImageIcon;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.opensourcephysics.tools.ResourceLoader;

public class IPTDElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/IPTD.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "IntegratorProcess"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.IPTD"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Integrator Process Model";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/IPTDModel.html";
  }

}
