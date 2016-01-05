package sysout.handlers;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import sysout.view.SysoutDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SelectionHandler extends AbstractHandler {

	private SysoutDialog sysoutDialog;
	private IWorkbenchWindow lastIWW;

	private ISelectionListener isl = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			if (selection instanceof TextSelection) {
				TextSelection iss = (TextSelection) selection;
				String selectedText = iss.getText();

				if (!selectedText.isEmpty()) {
					sysoutDialog.addVariable(selectedText);
				}
			}
		}

	};

	private boolean added = false;

	/**
	 * The constructor.
	 */
	public SelectionHandler() {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		sysoutDialog = new SysoutDialog(this);
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		lastIWW = window;
		if (added) {
			window.getSelectionService().removeSelectionListener(isl);
		} else {
			window.getSelectionService().addSelectionListener(isl);
		}
		added = !added;
		sysoutDialog.setVisible(added);
		return null;
	}

	private void createSysOutTextWithSelectedVariables(String text) {

		IWorkbenchWindow window = lastIWW;

		ISelectionService is = window.getSelectionService();
		ISelection selection = is.getSelection();
		if (selection instanceof TextSelection) {
			TextSelection ts = ((TextSelection) selection);
			int offset = ts.getOffset();
			int length = ts.getLength();
			IEditorPart iep = window.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			ITextEditor editor = (ITextEditor) iep.getAdapter(ITextEditor.class);
			IDocumentProvider provider = editor.getDocumentProvider();
			IDocument document = provider.getDocument(iep.getEditorInput());

			String documentContent = document.get();
			StringBuilder sb = new StringBuilder();
			String part1 = documentContent.substring(0, offset + length);
			String part2 = documentContent.substring(offset + length);
			sb.append(part1);
			sb.append(text);
			sb.append(part2);
			document.set(sb.toString());
		}

	}

	public void setSysOutText(final String sysout) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				createSysOutTextWithSelectedVariables(sysout);
			}
		});

	}

	public void removeSelectionListener() {
		lastIWW.getSelectionService().removeSelectionListener(isl);
		added = false;
		sysoutDialog.setVisible(false);
	}
}
