package ru.cos.nissan.gui;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class MessageDialog extends JDialog {

	private java.awt.Frame parent;
	private Component component;
	private String message =  "Default message";
	private JOptionPane optionPane;
	private static int width = 294;
	private static int height = 131;

	public MessageDialog(String message,java.awt.Frame parent,Component pc, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        this.component = component;
        this.message = message;
        initComponents();
    }
	
    public MessageDialog(String message,java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        this.message = message;
        initComponents();
    }

  private void initComponents() {
    	
        jOptionPane1 = new javax.swing.JOptionPane();
        this.optionPane = jOptionPane1;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        //if (component != null) setLocationRelativeTo(component); else
        	//setLocationRelativeTo(parent);
        if (this.parent != null){
        	int x = this.parent.getX() + (this.parent.getWidth() - width)/2; 
            int y = this.parent.getY() + (this.parent.getHeight() - height)/2;
            setBounds(x, y, 294, 131);
            
        }
        setResizable(false);
       // this.set
        setIconImage(null);

        jOptionPane1.setMessage(this.message);
        jOptionPane1.setMessageType(javax.swing.JOptionPane.INFORMATION_MESSAGE);
        jOptionPane1.setOptionType(javax.swing.JOptionPane.DEFAULT_OPTION);

        jOptionPane1.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        String prop = e.getPropertyName();
                        if (JOptionPane.VALUE_PROPERTY.equals(prop)){
                        	handleButtons();
                        }
                    }
                });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jOptionPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jOptionPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               MessageDialog dialog = new MessageDialog("You MUST use UTF-8 encoding for Java files! \\zroslaw",new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    private void handleButtons()
    {
    	if (this.isVisible()) this.setVisible(false);
    }
    
    public boolean getValue()
    {
    	try {
        	int value = ((Integer)jOptionPane1.getValue()).intValue();
        	if (value == JOptionPane.YES_OPTION) return true; else return false;
    	} catch (Exception e)
    	{
    		return false;
    	}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JOptionPane jOptionPane1;
    // End of variables declaration//GEN-END:variables

}
