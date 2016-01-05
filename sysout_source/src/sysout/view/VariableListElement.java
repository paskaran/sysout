package sysout.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VariableListElement extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7827244540604679333L;
	private String variable;
	private VariableList parent;
	private JButton removeBtn = new JButton("X");

	public VariableListElement(final String variable, final VariableList parent) {
		this.variable = variable;
		this.parent = parent;
		setLayout(new BorderLayout());
		add(new JLabel(variable, JLabel.CENTER), BorderLayout.CENTER);
		add(removeBtn, BorderLayout.EAST);
		removeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.removeVariable(variable);
			}
		});
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	}

	public String getVariable() {
		return variable;
	}

}
