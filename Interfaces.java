import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;

public class Interfaces extends Items {
	ConnectionDb cnt = new ConnectionDb();
	JFrame frame = new JFrame();// PARENT FRAME
	static CardLayout c = new CardLayout();
	static JPanel parentPanel = new JPanel();

	// ------HomePanel

	public Component HomePanel() {

		// --------------Subdivise frame--------------

		JPanel panelGauche = new JPanel();
		JPanel panelDroite = new JPanel();

		panelGauche.setBackground(GrayColor);
		panelDroite.setBackground(BlueColor);

		panelGauche.setPreferredSize(new Dimension(300, 700));
		panelDroite.setPreferredSize(new Dimension(400, 700));

		// panelGauche
		JLabel title = LeftTitle();
		panelGauche.setLayout(new FlowLayout(1, 0, 50));
		panelGauche.add(title);
		// subdiviser panelDroite

		JPanel panelDroiteHaut = new JPanel();
		JPanel panelDroiteCentre = new JPanel();
		JPanel panelDroiteBas = new JPanel();

		panelDroiteHaut.setPreferredSize(new Dimension(700, 150));
		panelDroiteCentre.setPreferredSize(new Dimension(700, 300));
		panelDroiteBas.setPreferredSize(new Dimension(700, 150));

		panelDroiteHaut.setBackground(BlueColor);
		panelDroiteCentre.setBackground(BlueColor);
		panelDroiteBas.setBackground(BlueColor);

		panelDroite.add(panelDroiteHaut);
		panelDroite.add(panelDroiteCentre);
		panelDroite.add(panelDroiteBas);

		// subdivise panelDroiteHaut

		panelDroiteHaut.setLayout(new BorderLayout());

		JPanel panelDroiteHautDroite = new JPanel();
		panelDroiteHautDroite.setPreferredSize(new Dimension(199, 50));
		panelDroiteHautDroite.setBackground(BlueColor);
		// Ajoter boutton Admin
		Icon adminIcon = new ImageIcon(".\\icons\\admin.png");
		JButton btnAdmin = NewButton(ADMIN, 150, 50, null, null, null, adminIcon);
		panelDroiteHautDroite.setLayout(new FlowLayout(1, 0, 30));
		panelDroiteHautDroite.add(btnAdmin);
		panelDroiteHaut.add(panelDroiteHautDroite, BorderLayout.EAST);

		// subdivise panelDroiteCentre

		// CodeApoge
		JLabel CodeApoge = SmallTitle("Code Apogée ");
		JTextField CodeApoge_text = TextBox();

		// Email
		JLabel Email = SmallTitle("Email");
		JTextField Email_text = TextBox();

		// TypeDoc
		JLabel TypeDoc = SmallTitle("Type du document ");
		String choix[] = { "Attestation de scolarité", "Demande de stage" };
		JComboBox<String> TypeDoc_text = ChoiceBox(choix);

		// Ajouter au panelDroiteCentre
		panelDroiteCentre.add(CodeApoge);
		panelDroiteCentre.add(CodeApoge_text);
		panelDroiteCentre.add(Email);
		panelDroiteCentre.add(Email_text);
		panelDroiteCentre.add(TypeDoc);
		panelDroiteCentre.add(TypeDoc_text);

		// ------------- Subdiviser panelDroiteBas ------------------
		panelDroiteBas.setLayout(new BorderLayout());

		JPanel panelDroiteBasGauche = new JPanel();
		JPanel panelDroiteBasDroite = new JPanel();

		panelDroiteBasGauche.setPreferredSize(new Dimension(350, 149));
		panelDroiteBasDroite.setPreferredSize(new Dimension(350, 149));

		panelDroiteBasGauche.setBackground(BlueColor);
		panelDroiteBasDroite.setBackground(BlueColor);

		panelDroiteBas.add(panelDroiteBasGauche, BorderLayout.WEST);
		panelDroiteBas.add(panelDroiteBasDroite, BorderLayout.EAST);

		// Envoyer
		Icon send = new ImageIcon(".\\icons\\send.png");

		JButton submit = NewButton(ENVOYER, 198, 50, CodeApoge_text, Email_text, null, send);
		panelDroiteBasDroite.add(submit);

		JPanel home = MainPanel(panelGauche, panelDroite);
		return home;
	}
	// ---------DemandePanel

	public Component DemandePanel() throws Exception {

		// logo
		JLabel Title = LeftTitle();
		// log out
		Icon logoutIcon = new ImageIcon(".\\icons\\logout.png");
		Icon histIcon = new ImageIcon(".\\icons\\history.png");

		JButton logout = NewButton(LOGOUT, 198, 50, null, null, null, logoutIcon);
		JButton Histo = NewButton(HISTO, 198, 50, null, null, null, histIcon);

		// Tight title
		JLabel RTitle = RightTitle("Liste des demandes");

		// ---Definition d 'un scrollPane :Liste des demandes
		JPanel TableDemande = new JPanel();
		TableDemande.setPreferredSize(new Dimension(680, 5000));
		TableDemande.setBackground(BlueColor);

		JScrollPane scrollPane = new JScrollPane(TableDemande);
		scrollPane.setPreferredSize(new Dimension(680, 470));

		// phrase + 2boutton :

		ResultSet rs = cnt.ShowDemandes();
		// for(int i=0;i<10;i++){
		int Count = 0;
		while (rs.next()) {
			Count += 110;
			JTextArea phrase = new JTextArea(rs.getString(4) + " " + rs.getString(5) + "(" + rs.getString(3)
					+ ")\na demandée : \" " + rs.getString(2) + " \"");

			phrase.setPreferredSize(new Dimension(430, 60));
			phrase.setBackground(BlueColor);
			phrase.setEditable(false);
			phrase.setFont(calibri);
			phrase.setForeground(PinkColor);

			String acceptBtn = ACCEPT;
			Icon accptIcon = new ImageIcon(".\\icons\\check.png");

			JButton Valider = NewButton(acceptBtn, 100, 30, null, null, null, accptIcon);
			Valider.putClientProperty("id", rs.getString(1));
			// Valider.putClientProperty("id", i);

			String refuseBtn = REJECT;
			Icon rejIcon = new ImageIcon(".\\icons\\cancel.png");

			JButton Refuser = NewButton(refuseBtn, 100, 30, null, null, null, rejIcon);
			Refuser.putClientProperty("id", rs.getString(1));
			// Refuser.putClientProperty("id", i);

			Refuser.setBackground(GrayColor);

			JPanel RowDemande = new JPanel();
			RowDemande.setPreferredSize(new Dimension(680, 100));
			RowDemande.setBackground(BlueColor);

			RowDemande.setLayout(new FlowLayout(1, 4, 30));
			RowDemande.add(phrase);
			RowDemande.add(Valider);
			RowDemande.add(Refuser);

			TableDemande.add(RowDemande);
		}
		TableDemande.setPreferredSize(new Dimension(640, Math.max(Count, 450)));

		// Subdivision des panels
		// Definition d'objet panel
		JPanel panelDroite = new JPanel();
		JPanel panelGauche = new JPanel();
		JPanel panelDroitehaut = new JPanel();
		JPanel panelDroiteCentre = new JPanel();
		// Coloration
		panelDroite.setBackground(BlueColor);
		panelGauche.setBackground(GrayColor);
		panelDroitehaut.setBackground(BlueColor);
		panelDroiteCentre.setBackground(BlueColor);
		// Dimensionnement de panels

		panelGauche.setPreferredSize(new Dimension(250, 600));
		panelDroite.setPreferredSize(new Dimension(750, 600));
		panelDroitehaut.setPreferredSize(new Dimension(700, 80));
		panelDroiteCentre.setPreferredSize(new Dimension(700, 520));
		// Ajout des objets

		// panelGauche:
		panelGauche.setLayout(new BorderLayout());

		JPanel TitlePanel = new JPanel();
		TitlePanel.setLayout(new FlowLayout(1, 0, 0));
		TitlePanel.setOpaque(false);
		TitlePanel.add(Title);
		JPanel histoPanel = new JPanel();
		histoPanel.setLayout(new FlowLayout(1, 0, 345));
		histoPanel.setOpaque(false);
		histoPanel.add(Histo);

		JPanel logoutPanel = new JPanel();
		logoutPanel.setLayout(new FlowLayout(1, 0, 30));
		logoutPanel.setOpaque(false);
		logoutPanel.add(logout);

		panelGauche.add(logoutPanel, BorderLayout.SOUTH);
		panelGauche.add(histoPanel, BorderLayout.CENTER);
		panelGauche.add(TitlePanel, BorderLayout.NORTH);

		// panelDroite:
		panelDroitehaut.add(RTitle);
		panelDroiteCentre.add(scrollPane);

		panelDroite.setLayout(new BorderLayout());
		panelDroite.add(panelDroitehaut, BorderLayout.NORTH);
		panelDroite.add(panelDroiteCentre, BorderLayout.CENTER);

		// demandePanel

		JPanel demandePanel = MainPanel(panelGauche, panelDroite);
		return demandePanel;
	}

	public Component HistoryPanel() throws Exception {

		// logo
		JLabel Title = LeftTitle();
		// log out
		Icon logoutIcon = new ImageIcon(".\\icons\\logout.png");
		Icon demandIcon = new ImageIcon(".\\icons\\list.png");

		JButton logout = NewButton(LOGOUT, 198, 50, null, null, null, logoutIcon);
		JButton demande = NewButton(DEMENDE, 198, 50, null, null, null, demandIcon);

		// Tight title
		JLabel RTitle = RightTitle("Historique");

		// ---Definition d 'un scrollPane :Liste des demandes
		JPanel TableHisto = new JPanel();
		TableHisto.setBackground(BlueColor);

		JScrollPane scrollPane = new JScrollPane(TableHisto);
		scrollPane.setPreferredSize(new Dimension(680, 470));

		ResultSet rs = cnt.ShowHisto();
		// for(int i=0;i<10;i++){
		int Count = 0;
		boolean addbutton = true;
		while (rs.next()) {

			if (addbutton) {
				Icon DropIcon = new ImageIcon(".\\icons\\delete.png");
				JButton Drop = NewButton(DROP, 198, 50, null, null, null, DropIcon);
				TableHisto.add(Drop);
				addbutton = false;
				Count += 80;
			}

			Count += 160;
			JTextArea phrase = new JTextArea(
					"_________________________" + rs.getString(6) + "________________________________\n\n"
							+ rs.getString(3) + " " + rs.getString(4) + " (" + rs.getString(2)
							+ ") \nType Du document : \" " + rs.getString(1) + " \" \nStatut : " + rs.getString(5));

			phrase.setPreferredSize(new Dimension(640, 140));
			phrase.setBackground(BlueColor);
			phrase.setEditable(false);
			phrase.setFont(calibri);
			phrase.setForeground(PinkColor);

			JPanel RowDemande = new JPanel();
			RowDemande.setPreferredSize(new Dimension(680, 150));
			RowDemande.setBackground(BlueColor);

			RowDemande.setLayout(new FlowLayout(1, 4, 30));
			RowDemande.add(phrase);
			TableHisto.add(RowDemande);
		}
		TableHisto.setPreferredSize(new Dimension(640, Math.max(Count, 450)));

		// Subdivision des panels
		// Definition d'objet panel
		JPanel panelDroite = new JPanel();
		JPanel panelGauche = new JPanel();
		JPanel panelDroitehaut = new JPanel();
		JPanel panelDroiteCentre = new JPanel();
		// Coloration
		panelDroite.setBackground(BlueColor);
		panelGauche.setBackground(GrayColor);
		panelDroitehaut.setBackground(BlueColor);
		panelDroiteCentre.setBackground(BlueColor);
		// Dimensionnement de panels

		panelGauche.setPreferredSize(new Dimension(250, 600));
		panelDroite.setPreferredSize(new Dimension(750, 600));
		panelDroitehaut.setPreferredSize(new Dimension(700, 80));
		panelDroiteCentre.setPreferredSize(new Dimension(700, 520));
		// Ajout des objets

		// panelGauche:
		panelGauche.setLayout(new BorderLayout());

		JPanel TitlePanel = new JPanel();
		TitlePanel.setLayout(new FlowLayout(1, 0, 0));
		TitlePanel.setOpaque(false);
		TitlePanel.add(Title);
		JPanel histoPanel = new JPanel();
		histoPanel.setLayout(new FlowLayout(1, 0, 345));
		histoPanel.setOpaque(false);
		histoPanel.add(demande);

		JPanel logoutPanel = new JPanel();
		logoutPanel.setLayout(new FlowLayout(1, 0, 30));
		logoutPanel.setOpaque(false);
		logoutPanel.add(logout);

		panelGauche.add(logoutPanel, BorderLayout.SOUTH);
		panelGauche.add(histoPanel, BorderLayout.CENTER);
		panelGauche.add(TitlePanel, BorderLayout.NORTH);

		// panelDroite:
		panelDroitehaut.add(RTitle);
		panelDroiteCentre.add(scrollPane);

		panelDroite.setLayout(new BorderLayout());
		panelDroite.add(panelDroitehaut, BorderLayout.NORTH);
		panelDroite.add(panelDroiteCentre, BorderLayout.CENTER);

		// demandePanel

		JPanel demandePanel = MainPanel(panelGauche, panelDroite);
		return demandePanel;
	}

	// ---------AdminPanel
	public Component AdminPanel() {

		// Logo
		JLabel Title = LeftTitle();

		// Se connecter
		JLabel Title2 = RightTitle("Se connecter");

		// Username
		JLabel user_label = SmallTitle("Nom utilisateur  ");
		JTextField userName_text = TextBox();

		// Password
		JLabel password_label = SmallTitle("Mot de passe     ");
		JPasswordField password_text = PasswordBox();

		// Log in
		Icon loginIcon = new ImageIcon(".\\icons\\login.png");

		JButton LogIn = NewButton(LOGIN, 198, 50, userName_text, password_text, null, loginIcon);
		// Cancel
		Icon cancelIcon = new ImageIcon(".\\icons\\close.png");

		JButton cancel = NewButton(CANCEL, 198, 50, userName_text, password_text, null, cancelIcon);
		// vide
		JLabel vide = SmallTitle("");

		// Subdivision des panels
		// Definition des objets

		JPanel panelDroite = new JPanel();
		JPanel panelGauche = new JPanel();
		JPanel panelDroiteHaut = new JPanel();
		JPanel panelDroiteCentre = new JPanel();
		JPanel panelDroiteBas = new JPanel();

		// ------------------------Coloration-----------------------------------------------
		panelDroite.setBackground(BlueColor);
		panelGauche.setBackground(GrayColor);
		panelDroiteHaut.setBackground(BlueColor);
		panelDroiteCentre.setBackground(BlueColor);
		panelDroiteBas.setBackground(BlueColor);

		// Dimensionnement :

		panelDroite.setPreferredSize(new Dimension(400, 700));
		panelGauche.setPreferredSize(new Dimension(300, 700));
		panelDroiteHaut.setPreferredSize(new Dimension(700, 233));
		panelDroiteCentre.setPreferredSize(new Dimension(600, 233));
		panelDroiteBas.setPreferredSize(new Dimension(700, 233));

		panelDroiteHaut.setLayout(new FlowLayout(1, 0, 50));
		panelDroiteCentre.setLayout(new FlowLayout(1, 0, 20));

		// Ajout des objets

		panelGauche.add(Title);

		panelDroite.add(panelDroiteHaut);
		panelDroite.add(panelDroiteCentre);
		panelDroite.add(panelDroiteBas);

		panelDroiteHaut.add(Title2);

		panelDroiteCentre.add(user_label);
		panelDroiteCentre.add(userName_text);
		panelDroiteCentre.add(password_label);
		panelDroiteCentre.add(password_text);

		panelDroiteBas.add(vide);
		panelDroiteBas.add(cancel);
		panelDroiteBas.add(LogIn);
		// Ajout de listeners to components
		// Ajouter au MainPanel
		JPanel adminPanel = MainPanel(panelGauche, panelDroite);
		return adminPanel;
	}
}
