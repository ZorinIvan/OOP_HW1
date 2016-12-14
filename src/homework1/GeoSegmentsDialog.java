package homework1;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;
		
		lstSegments = new JList<GeoSegment>(ExampleGeoSegments.segments);
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane segmentScroller = new JScrollPane(lstSegments);
		
		JLabel segmentLable = new JLabel("Geo Segments:");
		segmentLable.setLabelFor(lstSegments);
		
		JButton addButton = new JButton("Add");
	    JButton cancelButton = new JButton("cancel");
	     
	    addButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          if(lstSegments.getSelectedValue()!=null){
	  
	        	  parent.addSegment(lstSegments.getSelectedValue());
	        
	        	  
	          }
	        }
	    });
	    
	    cancelButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	setVisible(false);
	        	
	        }
	    });
	    
	   
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(segmentLable, c);
		this.add(segmentLable);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(segmentScroller, c);
		this.add(segmentScroller);
		
		c.fill = GridBagConstraints.NONE;
		
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,20,0);
		gridbag.setConstraints(addButton, c);
		this.add(addButton);
		
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,20,0);
		gridbag.setConstraints(cancelButton, c);
		this.add(cancelButton);
	}
}
