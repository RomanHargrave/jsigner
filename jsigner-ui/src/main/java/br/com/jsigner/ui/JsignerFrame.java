package br.com.jsigner.ui;

import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

import br.com.jsigner.tree.JarLoader;

public class JsignerFrame extends JFrame {

	private ResourceBundle messages = ResourceBundle.getBundle("messages");
	JFileChooser fileChooser = createFileChooser();

	public JsignerFrame() throws HeadlessException {
		super("Jsigner");
		setSize(1024, 768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setJMenuBar(createMenu());
		add(fileChooser);

	}

	private JFileChooser createFileChooser() {
		final JFileChooser jarChooser = new JFileChooser();
		jarChooser.setVisible(false);
		jarChooser.setDialogTitle(messages.getString("fileChooser.jars"));
		jarChooser.setMultiSelectionEnabled(true);

		jarChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith("jar")
						|| f.getName().endsWith("ear")
						|| f.getName().endsWith("war")
						|| f.getName().endsWith("rar");
			}

			@Override
			public String getDescription() {
				return messages.getString("fileChooser.filter.description");
			}

		});

		return jarChooser;
	}

	public JMenuBar createMenu() {
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu(messages.getString("menu.file"));
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem fileClose = new JMenuItem(messages.getString("menu.close"));
		fileClose.setToolTipText("tooltip.exit.application");
		fileClose.setMnemonic(KeyEvent.VK_C);
		fileClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		JMenuItem addJar = new JMenuItem(messages.getString("menu.addJar"));
		addJar.setToolTipText(messages.getString("tooltip.addJar"));
		addJar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fileChooser.setVisible(true);
				int returnVal = fileChooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File[] selectedJars = fileChooser.getSelectedFiles();
					for (File jar : selectedJars) {
						new JarLoader().loadJar(jar);
					}
				}

			}

		});

		file.add(addJar);
		file.add(fileClose);

		menubar.add(file);
		return menubar;
	}

	public static void main(String[] args) {
		JsignerFrame frame = new JsignerFrame();
		frame.setVisible(true);

	}
}
