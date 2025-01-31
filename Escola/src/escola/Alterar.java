package escola;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Alterar extends javax.swing.JFrame {
    private String matricula;
    private Conectar banco = new Conectar("jdbc:postgresql://localhost:5432/LP_teste", "postgres", "postgres");
    
    public void recebeMatricula(String mat) {
        matricula = mat;
    }
    
    private void atualizar() {
        if (!tfNome.getText().trim().isEmpty()) {
            if (!tfRua.getText().trim().isEmpty()) {
                if (!tfBairro.getText().trim().isEmpty()) {
                    if (validaNumCasa()) {
                        if (cbTurno.getSelectedIndex() > 0) {
                            update();
                            this.dispose();
                        }
                    }
                }
            }
        }
    }
    
    private void update() {
        try {
            String query = "UPDATE aluno SET nome = '" + tfNome.getText() + "', "
                        + "rua = '" + tfRua.getText() + "', "
                        + "bairro = '" + tfBairro.getText() + "', " 
                        + "num_casa = '" + numeroCasa() + "', " 
                        + "turno = " + cbTurno.getSelectedIndex()
                        + "WHERE id_aluno = " + matricula;
            banco.conectar.createStatement().execute(query);
            JOptionPane.showMessageDialog(this, "Atualizado com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro Banco de Dados - ALTERAR");
        }
    }
    
    private boolean validaNumCasa() {
        return !tfNum.getText().isEmpty() || cbSemNumero.isSelected();
    }
    
    private String numeroCasa() {
        if (cbSemNumero.isSelected()) {
            return "S/N";
        } return String.valueOf(tfNum.getText());
    }
    
    public Alterar() {
        initComponents();
        // altera icon da barra de tarefas e da barra superior do frame
        this.setIconImage(new ImageIcon(getClass().getResource("escola.png")).getImage());
        // altera a cor do frame 
        this.getContentPane().setBackground(new Color(253, 250, 235));       
        JOptionPane.showMessageDialog(this, "Os unicos campos que podem ser alterados são: nome e endereço");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfRua = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbSemNumero = new javax.swing.JCheckBox();
        cbTurno = new javax.swing.JComboBox<>();
        btConfirmar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        tfNum = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(400, 150));
        setUndecorated(true);

        pnAdd.setBackground(new java.awt.Color(165, 200, 202));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(36, 39, 57));
        jLabel2.setText("Nome");

        tfNome.setBackground(new java.awt.Color(189, 214, 210));
        tfNome.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfNome.setForeground(new java.awt.Color(36, 39, 57));

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

        btConfirmar.setBackground(new java.awt.Color(34, 139, 34));
        btConfirmar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btConfirmar.setText("CONFIRMAR");
        btConfirmar.setBorder(null);
        btConfirmar.setBorderPainted(false);
        btConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btConfirmar.setFocusPainted(false);
        btConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btConfirmarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btConfirmarMouseExited(evt);
            }
        });
        btConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmarActionPerformed(evt);
            }
        });

        btCancelar.setBackground(new java.awt.Color(152, 36, 36));
        btCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("CANCELAR");
        btCancelar.setBorder(null);
        btCancelar.setBorderPainted(false);
        btCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btCancelar.setFocusPainted(false);
        btCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btCancelarMouseExited(evt);
            }
        });
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        tfNum.setBackground(new java.awt.Color(189, 214, 210));
        tfNum.setForeground(new java.awt.Color(36, 39, 57));
        tfNum.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        tfNum.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        javax.swing.GroupLayout pnAddLayout = new javax.swing.GroupLayout(pnAdd);
        pnAdd.setLayout(pnAddLayout);
        pnAddLayout.setHorizontalGroup(
            pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAddLayout.createSequentialGroup()
                        .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnAddLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnAddLayout.createSequentialGroup()
                                        .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfNum, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnAddLayout.createSequentialGroup()
                                        .addComponent(cbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(231, 231, 231)
                                        .addComponent(cbSemNumero))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnAddLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnAddLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAddLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
        );
        pnAddLayout.setVerticalGroup(
            pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(tfNum, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAddLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSemNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnAddLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(cbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(pnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbSemNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSemNumeroActionPerformed
        if (cbSemNumero.isSelected()) {
            tfNum.setEditable(false);
            tfNum.setText("");
        } else {
            tfNum.setEditable(true);
        }
    }//GEN-LAST:event_cbSemNumeroActionPerformed

    private void btConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btConfirmarMouseEntered
        btConfirmar.setBackground(new Color(235, 235, 235));
        btConfirmar.setForeground(new Color(34,139,34));
    }//GEN-LAST:event_btConfirmarMouseEntered

    private void btConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btConfirmarMouseExited
        btConfirmar.setBackground(new Color(34,139,34));
        btConfirmar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btConfirmarMouseExited

    private void btConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmarActionPerformed
        atualizar();
    }//GEN-LAST:event_btConfirmarActionPerformed

    private void btCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelarMouseEntered
        btCancelar.setBackground(new Color(235, 235, 235));
        btCancelar.setForeground(new Color(152,36,36));
    }//GEN-LAST:event_btCancelarMouseEntered

    private void btCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelarMouseExited
        btCancelar.setBackground(new Color(152,36,36));
        btCancelar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btCancelarMouseExited

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(Alterar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alterar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alterar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alterar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Alterar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btConfirmar;
    private javax.swing.JCheckBox cbSemNumero;
    private javax.swing.JComboBox<String> cbTurno;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pnAdd;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JTextField tfNome;
    private javax.swing.JFormattedTextField tfNum;
    private javax.swing.JTextField tfRua;
    // End of variables declaration//GEN-END:variables
}
