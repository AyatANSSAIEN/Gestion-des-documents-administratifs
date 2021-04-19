import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

public class Items {
    
    final String ENVOYER = "Send";
    final String ADMIN = "Admin";
    final String LOGIN = "Log In";
    final String CANCEL = "Cancel";
    final String ACCEPT = "Accept";
    final String REJECT = "Reject";
    final String LOGOUT = "Log Out";
    final String HISTO = "History";
    final String DEMENDE = "Demand List";
    final String DROP = "Drop All";
    static int numbutton;
    // fonts and colors
    final Color BlueColor = new Color(3, 80, 111);
    final Color PinkColor = new Color(255, 219, 205);
    final Color GrayColor = new Color(187, 187, 187);
    final Font elephant = new Font("Elephant", Font.BOLD, 45);
    final Font calibri = new Font("Calibri", Font.BOLD, 20);

    // Titles
    public JLabel LeftTitle() {
        JLabel Title = new JLabel("GetDoc");
        Title.setFont(elephant);
        Title.setForeground(BlueColor);
        return Title;
    }

    public JLabel RightTitle(String title) {
        JLabel Title = new JLabel(title);
        Title.setFont(elephant);
        Title.setForeground(PinkColor);
        return Title;
    }

    public JLabel SmallTitle(String title) {
        JLabel smallTitle = new JLabel(title);
        smallTitle.setFont(calibri);
        smallTitle.setPreferredSize(new Dimension(165, 50));
        smallTitle.setForeground(PinkColor);

        return smallTitle;
    }

    // Boxes
    public JTextField TextBox() {
        JTextField textBox = new JTextField();
        textBox.setFont(calibri);
        textBox.setForeground(BlueColor);
        textBox.setBackground(GrayColor);
        textBox.setPreferredSize(new Dimension(400, 50));
        return textBox;
    }

    public JPasswordField PasswordBox() {
        JPasswordField passwordBox = new JPasswordField();
        passwordBox.setFont(calibri);
        passwordBox.setForeground(BlueColor);
        passwordBox.setBackground(GrayColor);
        passwordBox.setPreferredSize(new Dimension(400, 50));
        return passwordBox;
    }
    
    public JComboBox<String> ChoiceBox(String choix[]) {
        JComboBox<String> choiceBox = new JComboBox<String>(choix);
      
        choiceBox.setSelectedItem(null);
        choiceBox.setFont(calibri);
        choiceBox.setForeground(BlueColor);
        choiceBox.setBackground(GrayColor);
        choiceBox.setPreferredSize(new Dimension(400, 50));
        choiceBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String comboStr = (String)choiceBox.getSelectedItem();
                new Actions(comboStr);
                

            }
      
        });
        return choiceBox;
    }

    // Les Boutons
    public JButton NewButton(String name, int width, int height, JTextField jField1, JTextField jField2,
            String str,Icon icon) {

        LinkedList<Object> a = new LinkedList<Object>();
        a.add(0.7);
        a.add(0.3);
        a.add(new ColorUIResource(255, 200, 179));
        a.add(new ColorUIResource(255, 255, 255));
        a.add(new ColorUIResource(250, 219, 205));
        UIManager.put("Button.gradient", a);

        JButton btn = new JButton(name);        
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFont(calibri);
        btn.setForeground(BlueColor);
        btn.addMouseListener(new Actions(btn,icon));
       // btn.setBackground(PinkColor);
       
       if(name.equals(ACCEPT) || name.equals(REJECT))
        {
        btn.setName(name+String.valueOf(numbutton));
        numbutton++;
        }
        else
        btn.setName(name);

        // if (!btn.getName().equals(ENVOYER) && !btn.getName().equals(LOGIN))
        btn.addActionListener(new Actions(btn, jField1, jField2, null));
        return btn;
    }

    // Les Panels
    public JPanel MainPanel(JPanel panelGauche, JPanel panelDroite) {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(1000, 600);
        mainPanel.add(panelGauche, BorderLayout.WEST);
        mainPanel.add(panelDroite, BorderLayout.CENTER);
        return mainPanel;
    }
}