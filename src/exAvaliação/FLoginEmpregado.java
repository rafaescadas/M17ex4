package exAvaliação;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FLoginEmpregado {
    private JPanel panelLogin;
    private JTextField textFieldUser;
    private JButton CancelButton;
    private JButton OKButton;
    private JPasswordField textFieldPass;

    Connection conn;
    PreparedStatement pst;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/bdlojarafa", "root", "1234");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public FLoginEmpregado() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser = textFieldUser.getText();
                String txtPass = String.valueOf(textFieldPass.getPassword());
                try {
                    Connect();
                    String sql = "Select nome, nomeUtilizador, palavraPasse FROM empregados";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    boolean b = false;
                    while (rs.next()) {
                        String nome = rs.getString(1);
                        String user = rs.getString("nomeUtilizador");
                        String pass = rs.getString("palavraPasse");
                        if(txtUser.equals(user) && txtPass.equals(pass))
                        {
                            new FGestaoProdutos().SetVisible(true);
                            b=true;
                        }
                    }
                    if(!b)
                        JOptionPane.showMessageDialog(null, "Erro! Login incorreto!" +
                                " Username/Password incorreta");
                }
                catch (SQLException ex){
                    JOptionPane.showMessageDialog(null,"ERRO! " + ex.getMessage());
                }
            }
        });
    }

    public static void main(String[]args){
        JFrame frame = new JFrame("Login Empregados");
        frame.setContentPane(new FLoginEmpregado().panelLogin);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
