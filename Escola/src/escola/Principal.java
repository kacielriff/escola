package escola;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Principal extends javax.swing.JFrame {
    
    private int indexAba = 0;
    private String matricula;
    private Aluno aluno;
    private Conectar banco = new Conectar("jdbc:postgresql://localhost:5432/LP_teste", "postgres", "postgres");
    
    private boolean validaNumCasa() {
        return !tfNumero.getText().isEmpty() || cbSemNumero.isSelected();
    }
    
    private void adicionar() {
        if (!tfNome.getText().trim().isEmpty()) {
            if (!tfRG.getText().isEmpty()) {
                if (cbTurno.getSelectedIndex() > 0) {
                    if (!tfRua.getText().isEmpty()) {
                        if (!tfBairro.getText().isEmpty()) {
                            if (validaNumCasa()) {
                                aluno = new Aluno(-1, tfNome.getText(), tfRG.getText(),
                                        tfCPF.getText(), tfDataNascimento.getText(), " ", tfTelefone.getText(),
                                        turno(), tfRua.getText(), tfBairro.getText(), numeroCasa());
                                inserirAluno();
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void inserirAluno() {
        try {
            String incluir = "INSERT INTO aluno(nome, rg, cpf, data_nascimento, telefone, turno, rua, bairro, num_casa)" 
                    + " VALUES (" + aluno.toString() + ");";
            banco.conectar.createStatement().execute(incluir);
            atualizarIdade();
            JOptionPane.showMessageDialog(this, "Adicionado com sucesso");
            clear();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro Banco de Dados - INSERIR");
        }
    }
    
    private void atualizarIdade() throws SQLException {
        banco.conectar.createStatement().execute("UPDATE aluno SET idade = TO_CHAR(AGE(CURRENT_DATE, "
                + "data_nascimento), 'YY \"ano(s)\" MM \"mes(es) e \" DD \" dia(s)\" ' );");
    }
    
    private int turno() {
        return cbTurno.getSelectedIndex();
    }
    
    private String numeroCasa() {
        if (cbSemNumero.isSelected()) {
            return "S/N";
        } return String.valueOf(tfNumero.getText());
    }
    
    private void listar() {
        lbListar.setText("");
        String query = "SELECT id_aluno, nome, TO_CHAR(data_nascimento, 'DD/MM/YYYY') as data_nascimento, idade FROM aluno ORDER BY id_aluno";
        try {
            banco.ps = banco.conectar.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            banco.rs = banco.ps.executeQuery();
            while (banco.rs.next()) {
                String result = banco.rs.getString("id_aluno") + " - " 
                        + banco.rs.getString("nome") + "\t " 
                        + banco.rs.getString("data_nascimento") + "\t"
                        + banco.rs.getString("idade") + "\n";
                lbListar.setText(lbListar.getText() + result);
            }
        } catch (SQLException ex) {
            System.out.println("Erro Banco de Dados - LISTAR");
        }
    }
    
    private void alterar() {
        if (matricula.equals("-1")) {
            JOptionPane.showMessageDialog(this, "Aluno não encontrado");
        } else {
            Alterar alterar = new Alterar();
            alterar.recebeMatricula(matricula);
            alterar.setVisible(true);
        }
        tfAltMatricula.setText("");
        taAlterar.setText("");
        matricula = "-1";
    }
    
    private void remover() {
        String query = "DELETE FROM aluno WHERE id_aluno = " + matricula;
        try {
                banco.conectar.createStatement().execute(query);
                JOptionPane.showMessageDialog(this, "Aluno removido");
        } catch (SQLException ex) {
                System.out.println("Erro Banco de Dados - EXCLUIR");
        }
        tfRemMatricula.setText("");
        taRemove.setText("");
        matricula = "-1";
    }
    
    private String procurarAluno(String mat) {
        matricula = mat;
        String query = "SELECT id_aluno, nome, TO_CHAR(data_nascimento, 'DD/MM/YYYY') "
                + "as data_nascimento FROM aluno WHERE id_aluno = " + matricula;
        String result = "";
        try {
            banco.ps = banco.conectar.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            banco.rs = banco.ps.executeQuery();
            while (banco.rs.next()) {
                result = banco.rs.getString("id_aluno") + " - " 
                        + banco.rs.getString("nome") + "\t " 
                        + banco.rs.getString("data_nascimento") + "\n";
            }
            if (!result.isEmpty()) {
                return result;   
            }         
        } catch (SQLException ex) {
            System.out.println("Erro Banco de Dados - PROCURAR ALUNO");
        }
        matricula = "-1";
        return "\t    *nenhum aluno encontrado*";
    }
    
    private void clear() {
        tfNome.setText("");
        tfRG.setText("");
        tfCPF.setText("");
        tfDataNascimento.setText("");
        tfTelefone.setText("");
        tfRua.setText("");
        tfBairro.setText("");
        tfNumero.setText("");
        lbListar.setText("");
        cbSemNumero.setSelected(false);
        cbTurno.setSelectedIndex(0);
        tfNumero.setEditable(true);
    }
    
    private void labelBotao(int index) {
        switch (index) {
            case 0: {
                btAdd.setText("ADICIONAR");
                btAdd.setVisible(true);
                btLimpar.setText("LIMPAR");
                btLimpar.setVisible(true);
                break;
            }
            
            case 1: {
                btAdd.setText("ATUALIZAR");
                btAdd.setVisible(true);
                btLimpar.setVisible(false);
                break;
            }
            
            case 2: {
                btAdd.setText("CONFIRMAR");
                btAdd.setVisible(true);
                btLimpar.setText("CANCELAR");
                btLimpar.setVisible(true);
                break;
            }
            
            case 3: {
                btAdd.setText("REMOVER");
                btAdd.setVisible(true);
                btLimpar.setText("CANCELAR");
                btLimpar.setVisible(true);
                break;
            }
        }
    }

    public Principal(){
        initComponents();
        // altera icon da barra de tarefas e da barra superior do frame
        this.setIconImage(new ImageIcon(getClass().getResource("escola.png")).getImage());
        // altera a cor do frame 
        this.getContentPane().setBackground(new Color(253, 250, 235));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbEscola = new javax.swing.JLabel();
        tpMain = new javax.swing.JTabbedPane();
        pnAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfRG = new javax.swing.JFormattedTextField();
        tfCPF = new javax.swing.JFormattedTextField();
        tfDataNascimento = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        tfRua = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfNumero = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        tfTelefone = new javax.swing.JFormattedTextField();
        cbSemNumero = new javax.swing.JCheckBox();
        cbTurno = new javax.swing.JComboBox<>();
        pnList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbListar = new javax.swing.JTextArea();
        pnAlt = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taAlterar = new javax.swing.JTextArea();
        btAltMatricula = new javax.swing.JButton();
        tfAltMatricula = new javax.swing.JFormattedTextField();
        pnRemove = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taRemove = new javax.swing.JTextArea();
        btRemMatricula = new javax.swing.JButton();
        tfRemMatricula = new javax.swing.JFormattedTextField();
        pnBotoes = new javax.swing.JPanel();
        btAdd = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Escola");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setLocation(new java.awt.Point(400, 100));
        setMinimumSize(new java.awt.Dimension(600, 640));
        setPreferredSize(new java.awt.Dimension(600, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(600, 600));

        lbEscola.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lbEscola.setForeground(new java.awt.Color(170, 54, 229));
        lbEscola.setIcon(new javax.swing.ImageIcon(getClass().getResource("/escola/escola.png"))); // NOI18N
        lbEscola.setText("ESCOLA");
        lbEscola.setMaximumSize(new java.awt.Dimension(553, 512));

        tpMain.setToolTipText("");
        tpMain.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        tpMain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpMainStateChanged(evt);
            }
        });

        pnAdd.setBackground(new java.awt.Color(165, 200, 202));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(36, 39, 57));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(36, 39, 57));
        jLabel3.setText("RG");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 39, 57));
        jLabel4.setText("CPF");

        tfNome.setBackground(new java.awt.Color(189, 214, 210));
        tfNome.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfNome.setForeground(new java.awt.Color(36, 39, 57));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(36, 39, 57));
        jLabel6.setText("Data de nascimento");

        tfRG.setBackground(new java.awt.Color(189, 214, 210));
        tfRG.setForeground(new java.awt.Color(36, 39, 57));
        try {
            tfRG.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfRG.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        tfCPF.setBackground(new java.awt.Color(189, 214, 210));
        tfCPF.setForeground(new java.awt.Color(36, 39, 57));
        try {
            tfCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfCPF.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        tfDataNascimento.setBackground(new java.awt.Color(189, 214, 210));
        tfDataNascimento.setForeground(new java.awt.Color(36, 39, 57));
        try {
            tfDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfDataNascimento.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(36, 39, 57));
        jLabel7.setText("Rua");

        tfRua.setBackground(new java.awt.Color(189, 214, 210));
        tfRua.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfRua.setForeground(new java.awt.Color(36, 39, 57));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 39, 57));
        jLabel8.setText("Número");

        tfBairro.setBackground(new java.awt.Color(189, 214, 210));
        tfBairro.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfBairro.setForeground(new java.awt.Color(36, 39, 57));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(36, 39, 57));
        jLabel9.setText("Bairro");

        tfNumero.setBackground(new java.awt.Color(189, 214, 210));
        tfNumero.setForeground(new java.awt.Color(36, 39, 57));
        tfNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        tfNumero.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(36, 39, 57));
        jLabel10.setText("Telefone");

        tfTelefone.setBackground(new java.awt.Color(189, 214, 210));
        tfTelefone.setForeground(new java.awt.Color(36, 39, 57));
        try {
            tfTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfTelefone.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        cbSemNumero.setBackground(new java.awt.Color(165, 200, 202));
        cbSemNumero.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cbSemNumero.setForeground(new java.awt.Color(36, 39, 57));
        cbSemNumero.setText("Sem número");
        cbSemNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSemNumeroActionPerformed(evt);
            }
        });

        cbTurno.setBackground(new java.awt.Color(165, 200, 202));
        cbTurno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "***turno***", "Manhã", "Tarde", "Noite" }));
        cbTurno.setBorder(null);

        javax.swing.GroupLayout pnAddLayout = new javax.swing.GroupLayout(pnAdd);
        pnAdd.setLayout(pnAddLayout);
        pnAddLayout.setHorizontalGroup(
            pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnAddLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(282, 282, 282))
                    .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnAddLayout.createSequentialGroup()
                            .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addGap(12, 12, 12)
                            .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tfRG, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnAddLayout.createSequentialGroup()
                            .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addGroup(pnAddLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnAddLayout.createSequentialGroup()
                            .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel7))
                            .addGap(18, 18, 18)
                            .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tfRua)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAddLayout.createSequentialGroup()
                                    .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbSemNumero)
                                        .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        pnAddLayout.setVerticalGroup(
            pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(tfRG, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(cbTurno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbSemNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pnAddLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, tfNome});

        tpMain.addTab("ADICIONAR", pnAdd);

        pnList.setBackground(new java.awt.Color(165, 200, 202));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lbListar.setEditable(false);
        lbListar.setBackground(new java.awt.Color(189, 214, 210));
        lbListar.setColumns(20);
        lbListar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbListar.setRows(5);
        jScrollPane1.setViewportView(lbListar);

        javax.swing.GroupLayout pnListLayout = new javax.swing.GroupLayout(pnList);
        pnList.setLayout(pnListLayout);
        pnListLayout.setHorizontalGroup(
            pnListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        pnListLayout.setVerticalGroup(
            pnListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnListLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        tpMain.addTab("LISTAR", pnList);

        pnAlt.setBackground(new java.awt.Color(165, 200, 202));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(36, 39, 57));
        jLabel11.setText("Insira a matrícula do aluno");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(36, 39, 57));
        jLabel12.setText("Confirme se é o aluno desejado");

        taAlterar.setEditable(false);
        taAlterar.setBackground(new java.awt.Color(189, 214, 210));
        taAlterar.setColumns(20);
        taAlterar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        taAlterar.setForeground(new java.awt.Color(36, 39, 57));
        taAlterar.setRows(5);
        taAlterar.setFocusable(false);
        jScrollPane2.setViewportView(taAlterar);

        btAltMatricula.setBackground(new java.awt.Color(34, 139, 34));
        btAltMatricula.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btAltMatricula.setForeground(new java.awt.Color(255, 255, 255));
        btAltMatricula.setText("OK");
        btAltMatricula.setFocusPainted(false);
        btAltMatricula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btAltMatriculaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btAltMatriculaMouseExited(evt);
            }
        });
        btAltMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAltMatriculaActionPerformed(evt);
            }
        });

        tfAltMatricula.setBackground(new java.awt.Color(189, 214, 210));
        tfAltMatricula.setForeground(new java.awt.Color(36, 39, 57));
        tfAltMatricula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####"))));
        tfAltMatricula.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        javax.swing.GroupLayout pnAltLayout = new javax.swing.GroupLayout(pnAlt);
        pnAlt.setLayout(pnAltLayout);
        pnAltLayout.setHorizontalGroup(
            pnAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAltLayout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addGroup(pnAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAltLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAltLayout.createSequentialGroup()
                        .addComponent(tfAltMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btAltMatricula)
                        .addGap(148, 148, 148))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnAltLayout.createSequentialGroup()
                            .addGap(88, 88, 88)
                            .addComponent(jLabel12))))
                .addGap(72, 72, 72))
        );
        pnAltLayout.setVerticalGroup(
            pnAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAltLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAltMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfAltMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        tpMain.addTab("ALTERAR", pnAlt);

        jPanel5.setBackground(new java.awt.Color(165, 200, 202));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(36, 39, 57));
        jLabel13.setText("Insira a matrícula do aluno");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(36, 39, 57));
        jLabel14.setText("Confirme se é o aluno desejado");

        taRemove.setEditable(false);
        taRemove.setBackground(new java.awt.Color(189, 214, 210));
        taRemove.setColumns(20);
        taRemove.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        taRemove.setForeground(new java.awt.Color(36, 39, 57));
        taRemove.setRows(5);
        taRemove.setFocusable(false);
        jScrollPane3.setViewportView(taRemove);

        btRemMatricula.setBackground(new java.awt.Color(34, 139, 34));
        btRemMatricula.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btRemMatricula.setForeground(new java.awt.Color(255, 255, 255));
        btRemMatricula.setText("OK");
        btRemMatricula.setFocusPainted(false);
        btRemMatricula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btRemMatriculaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btRemMatriculaMouseExited(evt);
            }
        });
        btRemMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemMatriculaActionPerformed(evt);
            }
        });

        tfRemMatricula.setBackground(new java.awt.Color(189, 214, 210));
        tfRemMatricula.setForeground(new java.awt.Color(36, 39, 57));
        tfRemMatricula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####"))));
        tfRemMatricula.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfRemMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btRemMatricula)
                        .addGap(157, 157, 157))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel13))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel14)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRemMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfRemMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnRemoveLayout = new javax.swing.GroupLayout(pnRemove);
        pnRemove.setLayout(pnRemoveLayout);
        pnRemoveLayout.setHorizontalGroup(
            pnRemoveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnRemoveLayout.setVerticalGroup(
            pnRemoveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tpMain.addTab("REMOVER", pnRemove);

        pnBotoes.setBackground(new java.awt.Color(88, 104, 117));

        btAdd.setBackground(new java.awt.Color(10, 52, 110));
        btAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btAdd.setForeground(new java.awt.Color(255, 255, 255));
        btAdd.setText("ADICIONAR");
        btAdd.setBorder(null);
        btAdd.setBorderPainted(false);
        btAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btAdd.setFocusPainted(false);
        btAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btAddMouseExited(evt);
            }
        });
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btLimpar.setBackground(new java.awt.Color(152, 36, 36));
        btLimpar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btLimpar.setForeground(new java.awt.Color(255, 255, 255));
        btLimpar.setText("LIMPAR");
        btLimpar.setBorder(null);
        btLimpar.setBorderPainted(false);
        btLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btLimpar.setFocusPainted(false);
        btLimpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btLimparMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btLimparMouseExited(evt);
            }
        });
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnBotoesLayout = new javax.swing.GroupLayout(pnBotoes);
        pnBotoes.setLayout(pnBotoesLayout);
        pnBotoesLayout.setHorizontalGroup(
            pnBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBotoesLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(pnBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        pnBotoesLayout.setVerticalGroup(
            pnBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(lbEscola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(tpMain)
            .addComponent(pnBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbEscola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tpMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAddMouseEntered
        btAdd.setBackground(new Color(235, 235, 235));
        btAdd.setForeground(new Color(10,52,110));
    }//GEN-LAST:event_btAddMouseEntered

    private void btAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAddMouseExited
        btAdd.setBackground(new Color(10,52,110));
        btAdd.setForeground(Color.WHITE);
    }//GEN-LAST:event_btAddMouseExited

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        switch (indexAba) {
            case 0: {
                adicionar();
                break;
            }
            
            case 1: {
                listar();
                break;
            }
            
            case 2: {
                alterar();
                break;
            }
            
            case 3: {
                if (!matricula.equals("-1")) {
                    remover();
                } else {
                    JOptionPane.showMessageDialog(this, "Matrícula não encontrada");
                    tfRemMatricula.setText("");
                    taRemove.setText("");
                    matricula = "-1";
                }
                break;
            }
        }
    }//GEN-LAST:event_btAddActionPerformed

    private void btLimparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLimparMouseEntered
        btLimpar.setBackground(new Color(235, 235, 235));
        btLimpar.setForeground(new Color(152,36,36));
    }//GEN-LAST:event_btLimparMouseEntered

    private void btLimparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLimparMouseExited
        btLimpar.setBackground(new Color(152,36,36));
        btLimpar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btLimparMouseExited

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        switch (indexAba) {
            case 0: {
                clear();
                break;
            }
            
            case 2: {
                tfAltMatricula.setText("");
                taAlterar.setText("");
                break;
            }
            
            case 3: {
                tfRemMatricula.setText("");
                taRemove.setText("");
                matricula = "-1";
                break;
            }
        }
    }//GEN-LAST:event_btLimparActionPerformed

    private void tpMainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpMainStateChanged
        indexAba = tpMain.getSelectedIndex();
        labelBotao(indexAba);
        if (indexAba == 1) {
            listar();
        }
    }//GEN-LAST:event_tpMainStateChanged

    private void btAltMatriculaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAltMatriculaMouseEntered
        btAltMatricula.setBackground(new Color(235, 235, 235));
        btAltMatricula.setForeground(new Color(34,139,34));
    }//GEN-LAST:event_btAltMatriculaMouseEntered

    private void btAltMatriculaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAltMatriculaMouseExited
        btAltMatricula.setBackground(new Color(34,139,34));
        btAltMatricula.setForeground(Color.WHITE);
    }//GEN-LAST:event_btAltMatriculaMouseExited

    private void btRemMatriculaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemMatriculaMouseEntered
        btRemMatricula.setBackground(new Color(235, 235, 235));
        btRemMatricula.setForeground(new Color(34,139,34));
    }//GEN-LAST:event_btRemMatriculaMouseEntered

    private void btRemMatriculaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemMatriculaMouseExited
        btRemMatricula.setBackground(new Color(34,139,34));
        btRemMatricula.setForeground(Color.WHITE);
    }//GEN-LAST:event_btRemMatriculaMouseExited

    private void cbSemNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSemNumeroActionPerformed
        if (cbSemNumero.isSelected()) {
            tfNumero.setEditable(false);
            tfNumero.setText("");
        } else {
            tfNumero.setEditable(true);
            tfNumero.setText("");
        }
    }//GEN-LAST:event_cbSemNumeroActionPerformed

    private void btRemMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemMatriculaActionPerformed
        if (tfRemMatricula.getText().trim().length() > 0) {
            taRemove.setText(procurarAluno(tfRemMatricula.getText().trim()));
        }
    }//GEN-LAST:event_btRemMatriculaActionPerformed

    private void btAltMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAltMatriculaActionPerformed
        if (tfAltMatricula.getText().trim().length() > 0) {
            taAlterar.setText(procurarAluno(tfAltMatricula.getText().trim()));
        }
    }//GEN-LAST:event_btAltMatriculaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btAltMatricula;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btRemMatricula;
    private javax.swing.JCheckBox cbSemNumero;
    private javax.swing.JComboBox<String> cbTurno;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbEscola;
    private javax.swing.JTextArea lbListar;
    private javax.swing.JPanel pnAdd;
    private javax.swing.JPanel pnAlt;
    private javax.swing.JPanel pnBotoes;
    private javax.swing.JPanel pnList;
    private javax.swing.JPanel pnRemove;
    private javax.swing.JTextArea taAlterar;
    private javax.swing.JTextArea taRemove;
    private javax.swing.JFormattedTextField tfAltMatricula;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JFormattedTextField tfCPF;
    private javax.swing.JFormattedTextField tfDataNascimento;
    private javax.swing.JTextField tfNome;
    private javax.swing.JFormattedTextField tfNumero;
    private javax.swing.JFormattedTextField tfRG;
    private javax.swing.JFormattedTextField tfRemMatricula;
    private javax.swing.JTextField tfRua;
    private javax.swing.JFormattedTextField tfTelefone;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

}
