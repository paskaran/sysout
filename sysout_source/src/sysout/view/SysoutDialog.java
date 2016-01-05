package sysout.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import sysout.handlers.SelectionHandler;

/**
 * 
 * @author Dinesh Paskaran 12.2015
 *
 */
public class SysoutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8861389510317524175L;
	private VariableList vl = new VariableList();
	private JButton setTextBtn = new JButton("Create SysOut");

	public SysoutDialog(final SelectionHandler selectionHandler) {
		setTitle("SysOut");
		setAlwaysOnTop(true);
		setSize(new Dimension(300, 400));
		
		getContentPane().add(new JLabel("SysOut Plugin By Paskaran (Germany)"), BorderLayout.NORTH);
		getContentPane().add(vl, BorderLayout.CENTER);
		getContentPane().add(setTextBtn, BorderLayout.SOUTH);
		setTextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectionHandler.setSysOutText(vl.getSysOutText());
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				vl.clearList();
				selectionHandler.removeSelectionListener();
			}
		});
	}

	public void addVariable(String variable) {
		vl.addVariable(variable);
	}

	public String getSysOutText() {
		return vl.getSysOutText();
	}
}
