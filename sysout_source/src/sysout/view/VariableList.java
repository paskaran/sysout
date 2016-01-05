package sysout.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * @author Dinesh Paskaran 12.2015
 */

public class VariableList extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4077558985932146343L;
	private JPanel listPanel = new JPanel(new GridLayout(0, 1));
	private JScrollPane sp = new JScrollPane(listPanel);
	private ArrayList<String> variables = new ArrayList<String>();
	private static final String VARIABLE_PATTERN = "[a-zA-Z0-9_]+";

	public VariableList() {
		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);
	}

	public void addVariable(String variable) {
		variable = variable.trim().replaceAll("\\s+", "");
		if (validateVariable(variable)) {
			variables.add(variable);
		}
		updateList();
	}

	public void removeVariable(String variable) {
		if (variables.contains(variable)) {
			variables.remove(variable);
		}
		updateList();
	}

	private void updateList() {
		listPanel.removeAll();
		for (String var : variables) {
			listPanel.add(new VariableListElement(var, this));
		}
		updateUI();
	}

	private boolean validateVariable(String variable) {
		boolean res = true;
		if (variables.contains(variable)) {
			res = false;
		}
		if (!variable.matches(VARIABLE_PATTERN)) {
			res = false;
		}
		return res;
	}

	public String getSysOutText() {
		StringBuilder sb = new StringBuilder();
		sb.append("System.out.println( ");
		boolean first = true;
		for (String var : variables) {
			if (first) {
				first = false;
				sb.append("\"" + var + "= \"+" + var + "+");
			} else {
				sb.append("\"\\n" + var + "= \"+" + var + "+");
			}

		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	public void clearList() {
		variables.clear();
		updateList();
	}
}
