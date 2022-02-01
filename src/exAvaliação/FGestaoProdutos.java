package exAvaliação;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class FGestaoProdutos {
    private JPanel panelProdutos;
    private JTextField textFieldName;
    private JTextField textFieldPrice;
    private JTextField textFieldQuant;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textFieldProductID;
    private JButton searchPIDButton;
    private JButton newButton;
    private JTextField textFieldCategoryID;
    private JTextField textFieldCat;
    private JButton searchCIDButton;
    private JTextArea textAreaCID;
    private JTextArea textAreaProduto;
    private JTextArea textAreaCategories;
    private JButton showCategoriesButton;
    private JButton createCategoryButton;
    private JButton showProductsButton;
    private JLabel labelImagem;

    String name, price, quant, cat;
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

    public FGestaoProdutos() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                name = textFieldName.getText();
                price = textFieldPrice.getText();
                quant = textFieldQuant.getText();
                cat=textFieldCat.getText();
                try {
                    String sSQL = ("insert into produtos(NomeProd,Preco,quant,cod_cat)values(?,?,?,?)");
                    pst = conn.prepareStatement(sSQL);
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, quant);
                    pst.setString(4,cat);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuant.setText("");
                    textFieldCat.setText("");
                    textFieldName.requestFocus();
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO! " + ex.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid;
                bid = textFieldProductID.getText();
                try {
                    pst = conn.prepareStatement("Delete from produtos where produtoID = ?");
                    pst.setString(1, bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");
                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuant.setText("");
                    textFieldName.requestFocus();
                    textFieldProductID.setText("");
                    textFieldCat.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,name,price,qty, cat;
                pid = textFieldProductID.getText();
                name = textFieldName.getText();
                price = textFieldPrice.getText();
                qty = textFieldQuant.getText();
                cat = textFieldCat.getText();
                try {
                    pst = conn.prepareStatement("update produtos set NomeProd = ?,Preco = ?,quant = ?,cod_cat=?" +
                            " where produtoID = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, cat);
                    pst.setString(5, pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuant.setText("");
                    textFieldName.requestFocus();
                    textFieldProductID.setText("");
                    textFieldCat.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        searchPIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    String pID = textFieldProductID.getText();
                    pst = conn.prepareStatement("select NomeProd, Preco, quant, cod_cat from produtos where produtoID = ?");
                    pst.setString(1, pID);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next())
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String quant = rs.getString(3);
                        String cat=rs.getString(4);
                        textFieldName.setText(name);
                        textFieldPrice.setText(price);
                        textFieldQuant.setText(quant);
                        textFieldCat.setText(cat);
                    }
                    else
                    {
                        textFieldName.setText("");
                        textFieldPrice.setText("");
                        textFieldQuant.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        searchCIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    String cID = textFieldCategoryID.getText();
                    pst = conn.prepareStatement("select NomeProd, Preco, quant, cod_cat from produtos where cod_cat = ?");
                    pst.setString(1, cID);
                    ResultSet rs = pst.executeQuery();
                    textAreaCID.setText("");
                    while (rs.next()) {
                        String prod = "Produto: " + rs.getString(1) + ", Preço: "
                                + rs.getString(2) + ", Quantidade: " + rs.getString(3)
                                + ", Categoria: " + rs.getString(4);
                        textAreaCID.append(prod + "\n");
                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro! Insira uma categoria existente.");
                    ex.printStackTrace();
                }
            }
        });

        showProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    pst = conn.prepareStatement("select produtoID, NomeProd, Preco, quant, cod_cat from produtos");
                    ResultSet rs = pst.executeQuery();
                    textAreaProduto.setText("");
                    while (rs.next()) {
                        String prod = "ID: " + rs.getString(1) + ", Nome: "
                                + rs.getString(2) + ", Preço: " + rs.getString(3)
                                + ", Quantidade: " + rs.getString(4) + ", Categoria: " + rs.getString(5);
                        textAreaProduto.append(prod + "\n");
                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro! Insira uma categoria existente.");
                    ex.printStackTrace();
                }
            }
        });

        showCategoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    pst = conn.prepareStatement("select categoriaID, NomeCat from categoria");
                    ResultSet rs = pst.executeQuery();
                    textAreaCategories.setText("");
                    while (rs.next()) {
                        String prod = "ID: " + rs.getString(1) + ", Categoria: "
                                + rs.getString(2);
                        textAreaCategories.append(prod + "\n");
                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro! Insira uma categoria existente.");
                    ex.printStackTrace();
                }
            }
        });

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldName.setText("");
                textFieldPrice.setText("");
                textFieldQuant.setText("");
                textFieldCat.setText("");
                textFieldProductID.setText("");
                textFieldCategoryID.setText("");
                textAreaCID.setText("");
                textAreaProduto.setText("");
                textAreaCategories.setText("");
            }
        });

        createCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    new FGestaoCategoria().setVisible(true);
            }
        });
    }

    public void SetVisible(boolean b)
    {
        JFrame frame=new JFrame("Gestão de produtos");
        frame.setContentPane(new FGestaoProdutos().panelProdutos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,700);
        frame.setLocationRelativeTo(null); //centrar
        frame.setVisible(true);
    }
}
