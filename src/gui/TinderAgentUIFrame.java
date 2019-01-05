package gui;

import java.awt.*;
import javax.swing.*;
import java.beans.*;
import javax.swing.event.*;
import java.awt.event.*;

//importer les behaviour
import jade.core.behaviours.FirstMatchBehaviour;

public class TinderAgentUIFrame extends JFrame {
	 BorderLayout borderLayout1 = new BorderLayout();
    JPanel pnl_main = new JPanel();
    JButton btn_Exit = new JButton();
    Component component3;
    JButton btn_stop = new JButton();
    Component component2;
    JButton btn_start = new JButton();
    Box box_buttons;
    JPanel pnl_numAgents = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel lbl_numAgents = new JLabel();
    Box box_numAgents;
    JLabel lbl_agentCount = new JLabel();
    JSlider slide_numAgents = new JSlider();
    Component component1;
    Component component4;
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel lbl_numIntroductions = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel lbl_partyState = new JLabel();
    Box box1;
    JProgressBar prog_coupleCount = new JProgressBar();
    Component component6;
    Component component5;
    JLabel jLabel3 = new JLabel();
    JLabel lbl_coupleAvg = new JLabel();


    protected TinderSupervisorAgent m_owner;
	
	// constructeur
	public TinderAgentUIFrame(TinderSupervisorAgent owner ) {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        m_owner = owner;
    }
	
	//Maintains the enbabled/disabled state of key controls, depending on whether the sim is running or stopped.
	void enableControls( boolean starting ) {
        btn_start.setEnabled( !starting );
        btn_stop.setEnabled( starting );
        slide_numGuests.setEnabled( !starting );
        btn_Exit.setEnabled( !starting );
    }
	
	
    // When the user clicks the exit button, tell the host to shut down
    void btn_Exit_actionPerformed(ActionEvent e) {
        m_owner.addBehaviour( new OneShotBehaviour() {
                                  public void action() {
                                      ((TinderSupervisorAgent) myAgent).terminateHost();
                                  }
                              } );
    }


    
     //The window closing event is the same as clicking exit.
     
    void this_windowClosing(WindowEvent e) {
        // simulate the user having clicked exit
        btn_Exit_actionPerformed( null );
    }
}
