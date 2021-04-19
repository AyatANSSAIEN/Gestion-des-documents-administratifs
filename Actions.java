import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.itextpdf.html2pdf.HtmlConverter;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Actions extends Interfaces implements ActionListener, MouseListener {
	ConnectionDb cnt = new ConnectionDb();
	Interfaces interfaces = new Interfaces();

	public JButton btn;
	private JTextField jField1;
	private JTextField jField2;
	private Icon icon;
	private static String Text;
	// private JComboBox<String> chBox
	private static String str;

	Actions() {
	}

	Actions(String str) {
		Actions.str = str;
	}

	Actions(JButton btn, JTextField jField1, JTextField jField2, JComboBox<String> chBox) {
		this.btn = btn;
		this.jField1 = jField1;
		this.jField2 = jField2;
		// this.chBox = chBox;
	}

	public Actions(JButton btn2, Icon icon) {
		this.btn = btn2;
		this.icon = icon;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// ACCEPT AND REJECT BUTTONS
		for (int i = 0; i < numbutton; i++) {
			if (btn.getName().equals(ACCEPT + i)) {

				JPanel panel2 = new JPanel();

				Email.load.setUndecorated(true);
				panel2.setOpaque(false);
				Email.load.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
				Icon imgIcon = new ImageIcon(".\\icons\\Spinner-1s-200px (1).gif");
				JLabel label = new JLabel(imgIcon);
				panel2.add(label);
				label.setVisible(true);
				Email.load.add(panel2);
				Email.load.pack();
				Email.load.setBounds(520, 250, 200, 200);
				Email.load.setLocationRelativeTo(Main.frame);
				Email.load.setVisible(true);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {

						try {
							File FileIn;
							File FileOut;
							if (cnt.GetDocType(btn.getClientProperty("id")).equals("Attestation de scolarité")) {
								FileIn = new File(".\\documents\\Attesta.html");
								FileOut = new ReplaceText().replace(btn.getClientProperty("id"), FileIn);
								HtmlConverter.convertToPdf(new FileInputStream(".\\documents\\" + FileOut.getName()),
										new FileOutputStream(".\\documents\\Attestation.pdf"));
								new Email().SendEmail(cnt.getEmail(btn.getClientProperty("id")), "accept",
										new File(".\\documents\\Attestation.pdf"));
							} else {
								FileIn = new File(".\\documents\\demandeStage.html");
								FileOut = new ReplaceText().replace(btn.getClientProperty("id"), FileIn);
								HtmlConverter.convertToPdf(new FileInputStream(".\\documents\\" + FileOut.getName()),
										new FileOutputStream(".\\documents\\demandeStage.pdf"));
								new Email().SendEmail(cnt.getEmail(btn.getClientProperty("id")), "accept",
										new File(".\\documents\\demandeStage.pdf"));
							}
							cnt.ValideDemand(btn.getClientProperty("id"));
							parentPanel.add(interfaces.DemandePanel(), "demande");
							c.show(parentPanel, "demande");

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}, 1000);
			}

			if (btn.getName().equals(REJECT + i)) {
				JPanel panel2 = new JPanel();

				Email.load.setUndecorated(true);
				panel2.setOpaque(false);
				Email.load.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
				Icon imgIcon = new ImageIcon(".\\icons\\Spinner-1s-200px (1).gif");
				JLabel label = new JLabel(imgIcon);
				panel2.add(label);
				label.setVisible(true);
				Email.load.add(panel2);
				Email.load.pack();
				Email.load.setBounds(520, 250, 200, 200);
				Email.load.setLocationRelativeTo(Main.frame);
				Email.load.setVisible(true);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					public void run() {
						// send email "demande rejetée"
						try {
							cnt.RejeteDemand(btn.getClientProperty("id"));
							parentPanel.add(interfaces.DemandePanel(), "demande");
							c.show(parentPanel, "demande");
							new Email().SendEmail(cnt.getEmail(btn.getClientProperty("id")), "refuse", null);
						}

						catch (Exception e) {
							e.printStackTrace();
						}
					}

				}, 1000);
			}
		}

		// SEND DEMAND BUTTON
		if (btn.getName().equals(ENVOYER)) {
			// message err / demande enregistré
			try {
				if (!cnt.CheckStudent(jField1.getText()) && !new Email().isEmail(jField2.getText())) {
					jField1.setText("");
					jField2.setText("");
					JOptionPane.showMessageDialog(interfaces.frame, "Email et Code Apogee ne sont pas valide");
				}

				else {
					if (!new Email().isEmail(jField2.getText())) {
						JOptionPane.showMessageDialog(interfaces.frame, "Email n'est pas valide");
						jField2.setText("");

					} else if (!cnt.CheckStudent(jField1.getText())) {
						JOptionPane.showMessageDialog(interfaces.frame, "CodeApoge n'est pas valide");
						jField1.setText("");
					} else {
						if (cnt.saveDemande(jField1.getText(), jField2.getText(), Actions.str)) {
							JOptionPane.showMessageDialog(interfaces.frame, "Demande enregistré");
							parentPanel.add(interfaces.DemandePanel(), "demande");
						}

						else
							JOptionPane.showMessageDialog(interfaces.frame, "Vous avez déja demandé ce document");
						jField1.setText("");
						jField2.setText("");

					}
				}
			} catch (

			Exception e) {
				JOptionPane.showMessageDialog(interfaces.frame, "Echec d'envoi de la demande");
			}

		}
		// LOGIN BUTTON
		if (btn.getName().equals(LOGIN)) {
			try {
				if (cnt.isAdmin(jField1.getText(), jField2.getText())) {
					c.show(parentPanel, "demande");
				} else {
					JOptionPane.showMessageDialog(interfaces.frame, "Invalid login or password");
				}
				jField1.setText("");
				jField2.setText("");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(interfaces.frame, "Echec");
			}
		}
		if (btn.getName().equals(DEMENDE)) {

			try {
				c.show(parentPanel, "demande");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (btn.getName().equals(DROP)) {

			try {
				cnt.DeleteDemandes();
				Interfaces.parentPanel.add(HistoryPanel(), "histo");
				c.show(parentPanel, "histo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// HISTORY BUTTON
		if (btn.getName().equals(HISTO)) {
			try {
				Interfaces.parentPanel.add(HistoryPanel(), "histo");
				c.show(parentPanel, "histo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// BUTTONS DONT USE DATABASE
		// ADMIN BUTTON
		if (btn.getName().equals(ADMIN)) {
			c.show(parentPanel, "admin");
		}
		// CANCEL BUTTON
		if (btn.getName().equals(CANCEL)) {
			c.show(parentPanel, "home");
			jField1.setText("");
			jField2.setText("");
		}
		// LOGOUT BUTTON
		if (btn.getName().equals(LOGOUT)) {
			c.show(parentPanel, "admin");
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (btn != null) {
			if (btn.getText().equals(ACCEPT) || btn.getText().equals(REJECT)) {
				Text = btn.getText();
				btn.setText("");
			}
			btn.setForeground(new Color(0, 0, 0));
			btn.setIcon(icon);
		}
	}

	// effectuer un effet lorsque la souris sortie d'un button (changer des
	// couleurs)
	@Override
	public void mouseExited(MouseEvent e) {
		if (btn != null) {
			if (btn.getText().equals("")) {
				btn.setText(Text);
			}
			btn.setIcon(null);
			btn.setForeground(BlueColor);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
