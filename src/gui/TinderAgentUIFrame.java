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
	 
	// Declaring Panel
    JPanel pnl_main = new JPanel();
    JButton btn_Exit = new JButton();
	
	// third component : btn stop
    Component component3;
    JButton btn_stop = new JButton();
	
	// second component : btn start
    Component component2;
    JButton btn_start = new JButton();
	
	// box for buttons
    Box box_buttons;
	
	// nombre d'agents
    JPanel pnl_numAgents = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel lbl_numAgents = new JLabel();
    Box box_numAgents;
	
	// compteur d'agents
    JLabel lbl_agentCount = new JLabel();
	// slider : permet de changer le nombre d'agents
    JSlider slide_numAgents = new JSlider();
    Component component1;
    Component component4;
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    //JLabel lbl_numIntroductions = new JLabel();
    JLabel jLabel4 = new JLabel();
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
	
	private void jbInit() throws Exception {
        component3 = Box.createHorizontalStrut(10);
        component2 = Box.createHorizontalStrut(5);
        box_buttons = Box.createHorizontalBox();

        box_numAgents = Box.createHorizontalBox();
        component1 = Box.createGlue();
        component4 = Box.createHorizontalStrut(5);
        box1 = Box.createVerticalBox();
        component6 = Box.createGlue();
        component5 = Box.createGlue();
        this.getContentPane().setLayout(borderLayout1);
        pnl_main.setLayout(gridLayout1);
        btn_Exit.setText("Exit");
        btn_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btn_Exit_actionPerformed(e);
            }
        });
        btn_stop.setEnabled(false);
        btn_stop.setText("Stop");
        btn_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btn_stop_actionPerformed(e);
            }
        });
        btn_start.setText("Start");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btn_start_actionPerformed(e);
            }
        });
        this.setTitle("Tinder");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnl_numAgents.setLayout(borderLayout3);
        lbl_numAgents.setText("No. of agents:");
        lbl_agentCount.setMaximumSize(new Dimension(30, 17));
        lbl_agentCount.setMinimumSize(new Dimension(30, 17));
        lbl_agentCount.setPreferredSize(new Dimension(30, 17));
        lbl_agentCount.setText("10");
        slide_numAgents.setValue(10);
        slide_numAgents.setMaximum(1000);
        slide_numAgents.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slide_numAgents_stateChanged(e);
            }
        });
        gridLayout1.setRows(4);
        gridLayout1.setColumns(2);
        jLabel1.setToolTipText("");
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("Matching state: ");
        //jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabel2.setText("No. of introductions: ");
        lbl_numIntroductions.setBackground(Color.white);
        lbl_numIntroductions.setText("0");
        jLabel4.setToolTipText("");
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText("No. of couples formed ");
        lbl_matchingState.setBackground(Color.white);
        lbl_matchingState.setText("Not started");
        prog_coupleCount.setForeground(new Color(0, 255, 128));
        prog_coupleCount.setStringPainted(true);
        jLabel3.setToolTipText("");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("Avg. satisfaction per user : ");
        lbl_agentAvg.setToolTipText("");
        lbl_agentAvg.setText("0.0");
        this.getContentPane().add(pnl_main, BorderLayout.CENTER);
        pnl_main.add(jLabel1, null);
        //pnl_main.add(lbl_partyState, null);
        pnl_main.add(jLabel2, null);
        pnl_main.add(lbl_numIntroductions, null);
        pnl_main.add(jLabel4, null);
        pnl_main.add(box1, null);
        box1.add(component5, null);
        box1.add(prog_rumourCount, null);
        box1.add(component6, null);
        pnl_main.add(jLabel3, null);
        pnl_main.add(lbl_agentAvg, null);
        this.getContentPane().add(pnl_numAgents, BorderLayout.NORTH);
        pnl_numAgents.add(box_numAgents, BorderLayout.CENTER);
        pnl_numAgents.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) ) );

        box_numAgents.add(lbl_numAgents, null);
        box_numAgents.add(slide_numAgents, null);
        box_numAgents.add(lbl_agentCount, null);
        this.getContentPane().add(box_buttons, BorderLayout.SOUTH);
        box_buttons.add(component2, null);
        box_buttons.add(btn_start, null);
        box_buttons.add(component3, null);
        box_buttons.add(btn_stop, null);
        box_buttons.add(component1, null);
        box_buttons.add(btn_Exit, null);
        box_buttons.add(component4, null);
        lbl_matchingState.setForeground( Color.black );
        //lbl_numIntroductions.setForeground( Color.black );
        lbl_agentAvg.setForeground( Color.black );
    }
	//update label when slider value is changed
	
	void slide_numAgents_stateChanged(ChangeEvent e) {
        lbl_agentCount.setText( Integer.toString( slide_numAgents.getValue() ) );
    }
	
	//Maintains the enbabled/disabled state of key controls, depending on whether the sim is running or stopped.
	void enableControls( boolean starting ) {
        btn_start.setEnabled( !starting );
        btn_stop.setEnabled( starting );
        slide_numAgents.setEnabled( !starting );
        btn_Exit.setEnabled( !starting );
    }
	
	
    // When the user clicks the exit button, tell the host to shut down
    void btn_Exit_actionPerformed(ActionEvent e) {
		// TODO : changer le behaviour oneshot ? le remplacer par le notre ?
        m_owner.addBehaviour( new OneShotBehaviour() {
                                  public void action() {
                                      ((TinderSupervisorAgent) myAgent).terminateHost();
                                  }
                              } );
    }

	// Start execution
	void btn_start_actionPerformed(ActionEvent e) {
        enableControls( true );

        // add a behaviour to the supervisor to start the matching process
		// TODO : change the behavior
        m_owner.addBehaviour( new OneShotBehaviour() {
                                  public void action() {
                                      //((TinderSupervisorAgent) myAgent).inviteGuests( slide_numGuests.getValue() );
                                  }
                              } );
    }


    // Stop execution
    void btn_stop_actionPerformed(ActionEvent e) {
        enableControls( false );

        // add a behaviour to the host to end the party
		// TODO : change the behaviour
        m_owner.addBehaviour( new OneShotBehaviour() {
                                  public void action() {
                                      //((TinderSupervisorAgent) myAgent).endParty();
                                  }
                              } );
    }
    
     //The window closing event is the same as clicking exit.
     
    void this_windowClosing(WindowEvent e) {
        // simulate the user having clicked exit
        btn_Exit_actionPerformed( null );
    }
}
