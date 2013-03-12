package es.uned.jchacon.model_elements.process_control.elements;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.colos.ejs.model_elements.AbstractModelElement;
import org.colos.ejs.model_elements.ModelElementsCollection;
import org.opensourcephysics.tools.ResourceLoader;

public class PidControllerElement extends AbstractModelElement {
  public static final String ICON_FILE = "es/uned/jchacon/model_elements/process_control/elements/resources/PidController.png";
  static ImageIcon ELEMENT_ICON = ResourceLoader.getIcon(ICON_FILE);
  private JTextField textProportionalGain = new JTextField("1.0");
  private JTextField textIntegralGain = new JTextField("1.0");
  private JTextField textDerivativeGain = new JTextField("1.0");
  private JTextField textDerivativeFilter = new JTextField("20.0");
  private JTextField textAntiwindup = new JTextField("2.0");
  private JTextField textUmin = new JTextField("-1.0");
  private JTextField textUmax = new JTextField("1.0");
  private JCheckBox chckbxAntiwindup = new JCheckBox("enabled", false);
  
  // -------------------------------
  // Implementation of ModelElement
  // -------------------------------
  
  public ImageIcon getImageIcon() { return ELEMENT_ICON; }
  
  public String getGenericName() { return "PidController"; }
  
  public String getConstructorName() { return "es.uned.jchacon.model_elements.process_control.continuous.PidController"; }
  
  // -------------------------------
  // Help and edition
  // -------------------------------

  public String getTooltip() {
    return "Continuous PID Controller";
  }

  @Override
  protected String getHtmlPage() { 
	return "es/uned/jchacon/model_elements/process_control/elements/resources/PidController.html";
  }

  public String getInitializationCode(String _name) {
	  String init = _name + " = new es.uned.jchacon.model_elements.process_control.continuous.PidController(" 
			      + textProportionalGain.getText() + ", "
			      + textIntegralGain.getText() + ", "
			      + textDerivativeGain.getText() + ","
			      + textDerivativeFilter.getText() + ","
			      + chckbxAntiwindup.isSelected() + ","
			      + textAntiwindup.getText() + ","
			      + textUmin.getText() + ","
			      + textUmax.getText() + ");";

	  return init;
  }
 
  protected Component createEditor(String name, Component parentComponent, final ModelElementsCollection collection) {    
	  JPanel mainPanel = new JPanel(new BorderLayout());

	  mainPanel.setBounds(100, 100, 450, 300);

	  JPanel panel = new JPanel();
	  mainPanel.add(panel, BorderLayout.CENTER);
	  panel.setBorder(new TitledBorder(null, "PID gains", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	  SpringLayout sl_panel = new SpringLayout();
	  panel.setLayout(sl_panel);
	  panel.setMinimumSize(new Dimension(400, 200));
	  panel.setPreferredSize(new Dimension(400, 200));

	  JLabel lblProportionalGain = new JLabel("Proportional Gain (Kp):");
	  lblProportionalGain.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblProportionalGain, 10, SpringLayout.NORTH, panel);
	  sl_panel.putConstraint(SpringLayout.WEST, lblProportionalGain, 10, SpringLayout.WEST, panel);
	  panel.add(lblProportionalGain);

	  JLabel lblIntegralGain = new JLabel("Integral Gain (Ki):");
	  lblIntegralGain.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblIntegralGain, 6, SpringLayout.SOUTH, lblProportionalGain);
	  sl_panel.putConstraint(SpringLayout.WEST, lblIntegralGain, 0, SpringLayout.WEST, lblProportionalGain);
	  panel.add(lblIntegralGain);

	  JLabel lblDerivativeGain = new JLabel("Derivative Gain (Kd):");
	  lblDerivativeGain.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblDerivativeGain, 6, SpringLayout.SOUTH, lblIntegralGain);
	  sl_panel.putConstraint(SpringLayout.WEST, lblDerivativeGain, 0, SpringLayout.WEST, lblProportionalGain);
	  panel.add(lblDerivativeGain);

	  JLabel lblDerivativeFilterTime = new JLabel("Derivative Filter Time (N):");
	  lblDerivativeFilterTime.setHorizontalAlignment(SwingConstants.RIGHT);
	  sl_panel.putConstraint(SpringLayout.NORTH, lblDerivativeFilterTime, 6, SpringLayout.SOUTH, lblDerivativeGain);
	  sl_panel.putConstraint(SpringLayout.WEST, lblDerivativeFilterTime, 0, SpringLayout.WEST, lblProportionalGain);
	  panel.add(lblDerivativeFilterTime);

	  JLabel lblAntiwindup = new JLabel("Anti-windup:");
	  sl_panel.putConstraint(SpringLayout.NORTH, lblAntiwindup, 6, SpringLayout.SOUTH, lblDerivativeFilterTime);
	  sl_panel.putConstraint(SpringLayout.WEST, lblAntiwindup, 0, SpringLayout.WEST, lblProportionalGain);
	  lblAntiwindup.setHorizontalAlignment(SwingConstants.RIGHT);
	  panel.add(lblAntiwindup);

	  JLabel lblIntegratorResetTime = new JLabel("Integrator Reset Time (Ks):");
	  sl_panel.putConstraint(SpringLayout.NORTH, lblIntegratorResetTime, 6, SpringLayout.SOUTH, lblAntiwindup);
	  sl_panel.putConstraint(SpringLayout.WEST, lblIntegratorResetTime, 10, SpringLayout.WEST, panel);
	  panel.add(lblIntegratorResetTime);
	  
	  JLabel lblRangeMin = new JLabel("Minimum input (Umin):");
	  sl_panel.putConstraint(SpringLayout.NORTH, lblRangeMin, 6, SpringLayout.SOUTH, lblIntegratorResetTime);
	  sl_panel.putConstraint(SpringLayout.WEST, lblRangeMin, 10, SpringLayout.WEST, panel);
	  panel.add(lblRangeMin);

	  JLabel lblRangeMax = new JLabel("Maximum input (Umax):");
	  sl_panel.putConstraint(SpringLayout.NORTH, lblRangeMax, 6, SpringLayout.SOUTH, lblRangeMin);
	  sl_panel.putConstraint(SpringLayout.WEST, lblRangeMax, 10, SpringLayout.WEST, panel);
	  panel.add(lblRangeMax);
	    
	  sl_panel.putConstraint(SpringLayout.WEST, textProportionalGain, 50, SpringLayout.EAST, lblProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textProportionalGain, 0, SpringLayout.SOUTH, lblProportionalGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textProportionalGain, -10, SpringLayout.EAST, panel);
	  panel.add(textProportionalGain);
	  textProportionalGain.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textIntegralGain, 0, SpringLayout.WEST, textProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textIntegralGain, 0, SpringLayout.SOUTH, lblIntegralGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textIntegralGain, -10, SpringLayout.EAST, panel);
	  panel.add(textIntegralGain);
	  textIntegralGain.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textDerivativeGain, 0, SpringLayout.WEST, textProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textDerivativeGain, 0, SpringLayout.SOUTH, lblDerivativeGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textDerivativeGain, -10, SpringLayout.EAST, panel);
	  panel.add(textDerivativeGain);
	  textDerivativeGain.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textDerivativeFilter, 0, SpringLayout.WEST, textProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textDerivativeFilter, 0, SpringLayout.SOUTH, lblDerivativeFilterTime);
	  sl_panel.putConstraint(SpringLayout.EAST, textDerivativeFilter, -10, SpringLayout.EAST, panel);
	  panel.add(textDerivativeFilter);
	  textDerivativeFilter.setColumns(10);

	  chckbxAntiwindup = new JCheckBox("Enabled");
	  chckbxAntiwindup.setSelected(true);
	  sl_panel.putConstraint(SpringLayout.NORTH, chckbxAntiwindup, -4, SpringLayout.NORTH, lblAntiwindup);
	  sl_panel.putConstraint(SpringLayout.WEST, chckbxAntiwindup, 0, SpringLayout.WEST, textProportionalGain);
	  panel.add(chckbxAntiwindup);

	  sl_panel.putConstraint(SpringLayout.NORTH, textAntiwindup, -2, SpringLayout.NORTH, lblIntegratorResetTime);
	  sl_panel.putConstraint(SpringLayout.WEST, textAntiwindup, 0, SpringLayout.WEST, textProportionalGain);
	  sl_panel.putConstraint(SpringLayout.EAST, textAntiwindup, -10, SpringLayout.EAST, panel);
	  panel.add(textAntiwindup);
	  ((JTextField) textAntiwindup).setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textUmin, 0, SpringLayout.WEST, textProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textUmin, 0, SpringLayout.SOUTH, lblRangeMin);
	  sl_panel.putConstraint(SpringLayout.EAST, textUmin, -10, SpringLayout.EAST, panel);
	  panel.add(textUmax);
	  textUmin.setColumns(10);

	  sl_panel.putConstraint(SpringLayout.WEST, textUmax, 0, SpringLayout.WEST, textProportionalGain);
	  sl_panel.putConstraint(SpringLayout.SOUTH, textUmax, 0, SpringLayout.SOUTH, lblRangeMax);
	  sl_panel.putConstraint(SpringLayout.EAST, textUmax, -10, SpringLayout.EAST, panel);
	  panel.add(textUmin);
	  textUmax.setColumns(10);	  
	  
	  return mainPanel;   
  } 

  
}