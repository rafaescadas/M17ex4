package exAvaliação;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FGestaoCategoria {
    private JPanel panelCategoria;
    private JPanel panelProdutos;
    private JTextField textFieldCatName;
    private JTextField textFieldCatID;
    private JButton buttonCatNew;
    private JButton buttonCatSave;
    private JButton buttonCatDelete;
    private JButton buttonCatUpdate;
    private JButton buttonCatExit;
    private JButton buttonCatSearch;

    String name,catID;
    Connection conn;
    PreparedStatement pst;


    public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/bdlojarafa", "root","1234");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Gestão de categorias");
        frame.setContentPane(new FGestaoCategoria().panelCategoria);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); //centrar
        frame.setVisible(true);
    }


    public FGestaoCategoria() {
        buttonCatSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    String cID = textFieldCatID.getText();
                    pst = conn.prepareStatement("select NomeCat from categoria where categoriaID = ?");
                    pst.setString(1, cID);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next())
                    {
                        String name = rs.getString(1);
                        textFieldCatName.setText(name);
                    }
                    else
                    {
                        textFieldCatName.setText("");
                        textFieldCatID.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Category ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        buttonCatSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                catID = textFieldCatID.getText();
                name = textFieldCatName.getText();
                try {
                    String sSQL = ("insert into categoria(NomeCat)values(?)");
                    pst = conn.prepareStatement(sSQL);
                    pst.setString(1,name);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    textFieldCatName.setText("");
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO! " + ex.getMessage());
                }
            }
        });

        buttonCatDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cid;
                cid = textFieldCatID.getText();
                try {
                    pst = conn.prepareStatement("Delete from categoria where categoriaID = ?");
                    pst.setString(1, cid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");
                    textFieldCatName.setText("");
                    textFieldCatID.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        buttonCatUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cid,name;
                cid = textFieldCatID.getText();
                name = textFieldCatName.getText();
                try {
                    pst = conn.prepareStatement("update categoria set NomeCat = ? where categoriaID = ?");
                    pst.setString(1, name);
                    pst.setString(2, cid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    textFieldCatName.setText("");
                    textFieldCatID.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        buttonCatNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldCatName.setText("");
                textFieldCatID.setText("");
            }
        });
    }
}
