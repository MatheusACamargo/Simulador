/*
 * Simulador
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */

/**
 *
 * @author Matheus-camargo
 */
public class Tela extends javax.swing.JFrame {

    /** Creates new form Tela */
    public Tela() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jIniAtendimentosTarde = new javax.swing.JTextField();
        jFimAtendimentosTarde = new javax.swing.JTextField();
        jNumTecnicos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTaxaAtend = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTaxaRegistro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jZeraFila = new javax.swing.JCheckBox();
        jSimular = new javax.swing.JButton();
        jIniLigacoes = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jFimLigacoes = new javax.swing.JTextField();
        jIniLigacoesTarde = new javax.swing.JTextField();
        jFimLigacoesTarde = new javax.swing.JTextField();
        jIniAtendimentos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jFimAtendimentos = new javax.swing.JTextField();
        jPadroes = new javax.swing.JButton();
        jEstagManha = new javax.swing.JCheckBox();
        jEstagTarde = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jSemente = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulador de Atendimentos");

        jIniAtendimentosTarde.setText("130200");
        jIniAtendimentosTarde.setToolTipText("Horário do início dos atendimentos da tarde (hhmmss)");

        jFimAtendimentosTarde.setText("173200");
        jFimAtendimentosTarde.setToolTipText("Horário do fim dos atendimentos da tarde (hhmmss)");

        jNumTecnicos.setText("3");
        jNumTecnicos.setToolTipText("Quantidade de técnicos disponíveis para atendimentos");

        jLabel3.setText("Nº de técnicos");

        jTaxaAtend.setText("0.47");
        jTaxaAtend.setToolTipText("Taxa de tempo em que o técnico está disponível para atender (de 0 até 1)");

        jLabel4.setText("Taxa atendimento");

        jTaxaRegistro.setText("0.1");
        jTaxaRegistro.setToolTipText("Taxa de tempo que o técnico demora para registrar o atendimento (de 0 até 1 em relação ao tempo do atendimento)");

        jLabel5.setText("Tempo registro");

        jZeraFila.setSelected(true);
        jZeraFila.setText("Inicia fila a cada dia");
        jZeraFila.setToolTipText("Desconsidera horário final de atendimentos, atendendo até esgotar a fila");
        jZeraFila.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jSimular.setText("Simular");
        jSimular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSimularActionPerformed(evt);
            }
        });

        jIniLigacoes.setText("075112");
        jIniLigacoes.setToolTipText("Horário do início das ligações da manhã (hhmmss)");

        jLabel1.setText("Turno ligações");

        jFimLigacoes.setText("113741");
        jFimLigacoes.setToolTipText("Horário do fim das ligações da manhã (hhmmss)");

        jIniLigacoesTarde.setText("130127");
        jIniLigacoesTarde.setToolTipText("Horário do início das ligações da tarde (hhmmss)");

        jFimLigacoesTarde.setText("173038");
        jFimLigacoesTarde.setToolTipText("Horário do fim das ligações da tarde (hhmmss)");

        jIniAtendimentos.setText("080100");
        jIniAtendimentos.setToolTipText("Horário do início dos atendimentos da manhã (hhmmss)");

        jLabel2.setText("Turno atendimentos");

        jFimAtendimentos.setText("115100");
        jFimAtendimentos.setToolTipText("Horário do fim dos atendimentos da manhã (hhmmss)");

        jPadroes.setText("Restaurar padrões");
        jPadroes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPadroesActionPerformed(evt);
            }
        });

        jEstagManha.setText("Estagiário manhã");
        jEstagManha.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jEstagTarde.setText("Estagiário tarde");
        jEstagTarde.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel6.setText("Semente");

        jSemente.setText("50");
        jSemente.setToolTipText("Semente para número aleatório da simulação. Zerado para gerar semente aleatória");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jIniLigacoes, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                    .addComponent(jIniAtendimentos))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFimLigacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFimAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jNumTecnicos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTaxaAtend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jEstagManha)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jEstagTarde))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jIniLigacoesTarde)
                                            .addComponent(jIniAtendimentosTarde, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jFimLigacoesTarde)
                                            .addComponent(jFimAtendimentosTarde, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jZeraFila))
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTaxaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPadroes, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSimular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSemente, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jIniLigacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jIniAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFimLigacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jIniLigacoesTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFimAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jIniAtendimentosTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFimLigacoesTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFimAtendimentosTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNumTecnicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jZeraFila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTaxaAtend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTaxaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEstagManha)
                    .addComponent(jEstagTarde))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jSemente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPadroes)
                    .addComponent(jSimular))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSimularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSimularActionPerformed
        Integer fimAtendimentos = Integer.parseInt(jFimAtendimentos.getText());
        Integer fimAtendimentosTarde = Integer.parseInt(jFimAtendimentosTarde.getText());
        Integer fimLigacoes = Integer.parseInt(jFimLigacoes.getText());
        Integer fimLigacoesTarde = Integer.parseInt(jFimLigacoesTarde.getText());
        Integer iniAtendimentos = Integer.parseInt(jIniAtendimentos.getText());
        Integer iniAtendimentosTarde = Integer.parseInt(jIniAtendimentosTarde.getText());
        Integer iniLigacoes = Integer.parseInt(jIniLigacoes.getText());
        Integer iniLigacoesTarde = Integer.parseInt(jIniLigacoesTarde.getText());
        Integer numTecnicos = Integer.parseInt(jNumTecnicos.getText());
        Float taxaAtend = Float.parseFloat(jTaxaAtend.getText());
        Float taxaRegistro = Float.parseFloat(jTaxaRegistro.getText());
        Integer semente = Integer.parseInt(jSemente.getText());
        Simulador sim  = new Simulador(fimAtendimentos, fimAtendimentosTarde, fimLigacoes, fimLigacoesTarde, iniAtendimentos, iniAtendimentosTarde, iniLigacoes, iniLigacoesTarde, 
                                       numTecnicos, taxaAtend, taxaRegistro, jZeraFila.isSelected(), jEstagManha.isSelected(), jEstagTarde.isSelected(), semente);
        sim.start();
    }//GEN-LAST:event_jSimularActionPerformed

    private void jPadroesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPadroesActionPerformed
        jIniLigacoes.setText("075112");
        jFimLigacoes.setText("113741");
        jIniLigacoesTarde.setText("130127");
        jFimLigacoesTarde.setText("173038");
        jIniAtendimentos.setText("080100");
        jFimAtendimentos.setText("115100");
        jIniAtendimentosTarde.setText("130200");
        jFimAtendimentosTarde.setText("173200");
        jNumTecnicos.setText("3");
        jTaxaAtend.setText("0.47");
        jTaxaRegistro.setText("0.1");
        jZeraFila.setSelected(true);
        jEstagManha.setSelected(false);
        jEstagTarde.setSelected(false);
        jSemente.setText("50");
    }//GEN-LAST:event_jPadroesActionPerformed

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
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jEstagManha;
    private javax.swing.JCheckBox jEstagTarde;
    private javax.swing.JTextField jFimAtendimentos;
    private javax.swing.JTextField jFimAtendimentosTarde;
    private javax.swing.JTextField jFimLigacoes;
    private javax.swing.JTextField jFimLigacoesTarde;
    private javax.swing.JTextField jIniAtendimentos;
    private javax.swing.JTextField jIniAtendimentosTarde;
    private javax.swing.JTextField jIniLigacoes;
    private javax.swing.JTextField jIniLigacoesTarde;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jNumTecnicos;
    private javax.swing.JButton jPadroes;
    private javax.swing.JTextField jSemente;
    private javax.swing.JButton jSimular;
    private javax.swing.JTextField jTaxaAtend;
    private javax.swing.JTextField jTaxaRegistro;
    private javax.swing.JCheckBox jZeraFila;
    // End of variables declaration//GEN-END:variables
}
