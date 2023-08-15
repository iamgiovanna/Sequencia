package controle;

import java.awt.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.table.DefaultTableColumnModel;

import conexao.Conexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class controle extends JFrame
{
    Conexao con_cliente;
    
    JLabel rCodigo, rNome, rEmail, rTel, rData;
    JTextField tcodigo, tnome, temail;
    JFormattedTextField tel, data;
    MaskFormatter mTel, mData;
    JButton bt1,bt2,bt3,bt4;
    JTable tblClientes; //datagrid
    JScrollPane scp_tabela; //container para o datagrid
    
    public controle()
    {
        con_cliente = new Conexao(); // inicialização do objeto
        con_cliente.conecta(); // chama o método que conecta

        setTitle("Conexão Java com Mysql");
        setResizable(false);
        Container tela = getContentPane();
        
        rCodigo = new JLabel("Código");
        rNome= new JLabel("Nome");
        rEmail = new JLabel("Email");
        rTel = new JLabel("Telefone");
        rData = new JLabel("Data");
        tcodigo= new JTextField(20);
        tnome = new JTextField(50);
        temail = new JTextField(50);
        bt1 = new JButton("Primeiro");
        bt2 = new JButton("Anterior");
        bt3 = new JButton("Próximo");
        bt4 = new JButton("Último");
        //config mascara
        
        try
        {

            mTel= new MaskFormatter("(##)####--####");
            mData = new MaskFormatter("##/##/####");
            mTel.setPlaceholderCharacter('_');
            mData.setPlaceholderCharacter('_');
        }
        catch(ParseException excp){}
        tel = new JFormattedTextField(mTel);
        data = new JFormattedTextField(mData);
        
        //config tela
        rCodigo.setBounds(80,240,100,20);
        tcodigo.setBounds(150,240,100,20);
        rNome.setBounds(100,285,100,20);
        tnome.setBounds(180,285,100,20);
        rEmail.setBounds(140,350,100,20); 
        temail.setBounds(200,350,100,20);
        rData.setBounds(160,385,100,20);
        data.setBounds(240,385,100,20);
        rTel.setBounds(200,420,100,20);
        tel.setBounds(260,420,100,20);
        bt1.setBounds(300,480,100,20);
        bt2.setBounds(350,500,100,20);
        bt3.setBounds(400,550,100,20);
        bt4.setBounds(450,590,100,20);
        
        tela.add(bt1);
        bt1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                try {
                    con_cliente.resultset.first();
                    mostrar_Dados();
                    
                }catch(SQLException erro){
                    JOptionPane.showMessageDialog(null, "Não foi possível realizar essa tarefa! Tente novamente mais tarde!");
                }
            }
        });
        bt2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                try {
                    con_cliente.resultset.previous();
                    mostrar_Dados();
                    
                }catch(SQLException erro){
                    JOptionPane.showMessageDialog(null, "Não foi possível realizar essa tarefa! Tente novamente mais tarde!");
                }
            }
        });
        bt3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                try {
                    con_cliente.resultset.next();
                    mostrar_Dados();
                    
                }catch(SQLException erro){
                    JOptionPane.showMessageDialog(null, "Não foi possível realizar essa tarefa! Tente novamente mais tarde!");
                }
            }
        });
        bt4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                try {
                    con_cliente.resultset.last();
                    mostrar_Dados();
                    
                }catch(SQLException erro){
                    JOptionPane.showMessageDialog(null, "Não foi possível realizar essa tarefa! Tente novamente mais tarde!");
                }
            }
        });
        tela.add(bt2);
        tela.add(bt3);
        tela.add(bt4);
        tela.add(rCodigo);
        tela.add(rNome);
        tela.add(rEmail);
        tela.add(rTel);
        tela.add(rData);
        tela.add(tcodigo);
        tela.add(tnome);
        tela.add(temail);
        tela.add(tel);
        tela.add(data);
        
        //tabela
        tblClientes = new javax.swing.JTable();
        scp_tabela = new javax.swing.JScrollPane();
        
        tblClientes.setBounds(50,200,550,200);
        scp_tabela.setBounds(50,200,550,200);
        
        tela.add(tblClientes);
        tela.add(scp_tabela);
        
        tblClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0)));
       
        tblClientes.setFont(new java.awt.Font("Arial",1,12));
        
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][]{
            {null, null, null, null, null},
            {null,null,null,null,null},
            {null,null,null,null,null},
            {null,null,null,null,null}
        },
        new String []{ "Código", "Nome", "Data Nascimento", "Telefone", "Email"})
        {
            boolean[] canEdit = new boolean[]{false,false,false,false,false};
            
            public boolean isCellEditable(int rowIndex, int columnIdex){
            return canEdit [columnIdex];}
        });
        scp_tabela.setViewportView(tblClientes);
        
        tblClientes.setAutoCreateRowSorter(true);
        
        setSize(1000,1000);
        setVisible(true);
        setLocationRelativeTo(null);
       
        con_cliente.executaSQL("select * from tbclientes order by cod");
        preencherTabela();   
        posicionarRegistro();
    }
        public void preencherTabela() 
        {
            tblClientes.getColumnModel() .getColumn(0) .setPreferredWidth(4);
            tblClientes.getColumnModel() .getColumn(1) .setPreferredWidth(150);
            tblClientes.getColumnModel() .getColumn(2) .setPreferredWidth(11);
            tblClientes.getColumnModel() .getColumn(3) .setPreferredWidth(14);
            tblClientes.getColumnModel() .getColumn(4) .setPreferredWidth(100);
            
            DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
            modelo.setNumRows(0);
            
            try {
                con_cliente.resultset.beforeFirst();
                    while(con_cliente.resultset.next()){
                        modelo.addRow(new Object[]{
                           con_cliente.resultset.getString("cod"),con_cliente.resultset.getString("nome"),con_cliente.resultset.getString("dt_nasc"),con_cliente.resultset.getString("telefone"), con_cliente.resultset.getString("email")
                        });
                    }
            }catch(SQLException erro){
            
                JOptionPane.showMessageDialog(null,"\n Erro ao listar dados da tabela!! :\n "+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        public void posicionarRegistro()
        {
            try{
                 con_cliente.resultset.first();
                 mostrar_Dados();
            }
            catch(SQLException erro){
                JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro: "+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
        }     
}
       public void mostrar_Dados()
        {
            try{
                rCodigo.setText(con_cliente.resultset.getString("cod"));
                rNome.setText(con_cliente.resultset.getString("Nome"));
                rEmail.setText(con_cliente.resultset.getString("Email"));
                rTel.setText(con_cliente.resultset.getString("Telefone"));
                data.setText(con_cliente.resultset.getString("dt_nasc"));
            }
            catch(SQLException erro){
            JOptionPane.showMessageDialog(null,"Não localizou dados: "+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
            }
        }
}
