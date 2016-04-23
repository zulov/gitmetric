/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric;

import pl.agh.kro.gitmetric.git.GitUtils;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import pl.agh.kro.gitmetric.validators.SliderValidator;

/**
 *
 * @author Tomek
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        try {
            initMyComponents();
        } catch (GitAPIException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSetting = new javax.swing.JPanel();
        txtPath = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnCalculate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstUsers = new javax.swing.JList<>();
        lblMinCommit = new javax.swing.JLabel();
        lblMaxCommit = new javax.swing.JLabel();
        prbCompute = new javax.swing.JProgressBar();
        sldMinCommit = new javax.swing.JSlider();
        sldMaxCommit = new javax.swing.JSlider();
        cobMetric = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cobBranches = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstExt = new javax.swing.JList<>();
        lblMaxSize = new javax.swing.JLabel();
        lblMinSize = new javax.swing.JLabel();
        spnMaxSize = new javax.swing.JSpinner();
        spnMinSize = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstFileType = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlExt = new javax.swing.JPanel();
        pnlAuthors = new javax.swing.JPanel();
        pnlFileType = new javax.swing.JPanel();
        lblFiles = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSetting.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ustawienia"));

        txtPath.setText("D:\\Nauka\\SmartParkowanie\\.git");

        jLabel1.setText("Ścieżka repozytorium:");

        btnCalculate.setText("Oblicz");
        btnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateActionPerformed(evt);
            }
        });

        lstUsers.setBorder(javax.swing.BorderFactory.createTitledBorder("Użytkownicy"));
        lstUsers.setToolTipText("");
        lstUsers.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jScrollPane1.setViewportView(lstUsers);

        lblMinCommit.setText("Od:");

        lblMaxCommit.setText("Do:");

        sldMinCommit.setPaintTicks(true);
        sldMinCommit.setValue(0);
        sldMinCommit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldMinCommitStateChanged(evt);
            }
        });

        sldMaxCommit.setPaintTicks(true);
        sldMaxCommit.setValue(100);
        sldMaxCommit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldMaxCommitStateChanged(evt);
            }
        });

        cobMetric.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Linie kodu", "Bez pustych" }));

        jLabel2.setText("Metryka:");

        cobBranches.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cobBranchesItemStateChanged(evt);
            }
        });

        jLabel3.setText("Branche:");

        lstExt.setBorder(javax.swing.BorderFactory.createTitledBorder("Rozszerzenia"));
        lstExt.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        jScrollPane2.setViewportView(lstExt);

        lblMaxSize.setText("Do:");

        lblMinSize.setText("Od:");

        spnMaxSize.setValue(50000);
        spnMaxSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnMaxSizeStateChanged(evt);
            }
        });

        spnMinSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnMinSizeStateChanged(evt);
            }
        });

        jLabel4.setText("Wielkość pliku:");

        jLabel5.setText("Czas obliczeń:");

        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTime.setText("0000.00s");

        lstFileType.setBorder(javax.swing.BorderFactory.createTitledBorder("Typy plików"));
        lstFileType.setToolTipText("");
        lstFileType.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jScrollPane3.setViewportView(lstFileType);
        lstFileType.getAccessibleContext().setAccessibleName("fileType");

        javax.swing.GroupLayout pnlSettingLayout = new javax.swing.GroupLayout(pnlSetting);
        pnlSetting.setLayout(pnlSettingLayout);
        pnlSettingLayout.setHorizontalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPath)
                    .addComponent(btnCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prbCompute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cobBranches, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cobMetric, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(lblMinSize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnMinSize, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(lblMaxSize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnMaxSize, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblMinCommit, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addComponent(lblMaxCommit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sldMaxCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(sldMinCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pnlSettingLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {spnMaxSize, spnMinSize});

        pnlSettingLayout.setVerticalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMinCommit))
                    .addComponent(sldMinCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaxCommit)
                    .addComponent(sldMaxCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cobMetric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cobBranches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMinSize)
                    .addComponent(spnMinSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaxSize)
                    .addComponent(spnMaxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btnCalculate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prbCompute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTime))
                .addGap(6, 6, 6))
        );

        pnlExt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pnlExtLayout = new javax.swing.GroupLayout(pnlExt);
        pnlExt.setLayout(pnlExtLayout);
        pnlExtLayout.setHorizontalGroup(
            pnlExtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 721, Short.MAX_VALUE)
        );
        pnlExtLayout.setVerticalGroup(
            pnlExtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Rozszerzenia", pnlExt);

        javax.swing.GroupLayout pnlAuthorsLayout = new javax.swing.GroupLayout(pnlAuthors);
        pnlAuthors.setLayout(pnlAuthorsLayout);
        pnlAuthorsLayout.setHorizontalGroup(
            pnlAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        pnlAuthorsLayout.setVerticalGroup(
            pnlAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Rozkład autorstwa", pnlAuthors);

        javax.swing.GroupLayout pnlFileTypeLayout = new javax.swing.GroupLayout(pnlFileType);
        pnlFileType.setLayout(pnlFileTypeLayout);
        pnlFileTypeLayout.setHorizontalGroup(
            pnlFileTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        pnlFileTypeLayout.setVerticalGroup(
            pnlFileTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Typy plików", pnlFileType);

        lblFiles.setText("-");
        lblFiles.setToolTipText("");
        lblFiles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFiles.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1)
                    .addComponent(lblFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateActionPerformed
        ComputeThread computeThread = new ComputeThread();
        computeThread.setCommits(sldMinCommit.getValue(), sldMaxCommit.getValue());
        computeThread.setLabels(lblFiles, lblTime);
        computeThread.setLists(lstExt, lstUsers, lstFileType);
        computeThread.setPanels(pnlAuthors, pnlExt, pnlFileType);
        computeThread.setReposData(txtPath.getText(), cobBranches.getSelectedItem().toString());
        computeThread.setSetExt(lstExt.getSelectedValuesList(), lstFileType.getSelectedValuesList());
        computeThread.setPrbCompute(prbCompute);
        computeThread.setMetric(cobMetric.getSelectedItem().toString());
        computeThread.setSizes((Integer) spnMaxSize.getValue(), (Integer) spnMinSize.getValue());
        computeThread.start();
    }//GEN-LAST:event_btnCalculateActionPerformed

    private void sldMinCommitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldMinCommitStateChanged
        if (sldMaxCommit.getValue() <= sldMinCommit.getValue()) {
            sldMinCommit.setValue(sldMaxCommit.getValue() - 1);
        }
        lblMinCommit.setText("Od: " + sldMinCommit.getValue());
        lblMaxCommit.setText("Do: " + sldMaxCommit.getValue());
    }//GEN-LAST:event_sldMinCommitStateChanged

    private void sldMaxCommitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldMaxCommitStateChanged
        if (sldMaxCommit.getValue() <= sldMinCommit.getValue()) {
            sldMaxCommit.setValue(sldMinCommit.getValue() + 1);
        }
        lblMinCommit.setText("Od: " + sldMinCommit.getValue());
        lblMaxCommit.setText("Do: " + sldMaxCommit.getValue());
    }//GEN-LAST:event_sldMaxCommitStateChanged

    private void cobBranchesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cobBranchesItemStateChanged
        if (cobBranches.getSelectedIndex() == -1) {
            return;
        }
        String path = txtPath.getText();
        String branch = cobBranches.getSelectedItem().toString();
        List<RevCommit> logs = GitUtils.getLogs(path, branch);

        sliderValidator.validate(sldMinCommit, sldMaxCommit, logs.size());
    }//GEN-LAST:event_cobBranchesItemStateChanged

    private void spnMinSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnMinSizeStateChanged
        if (((Integer) spnMaxSize.getValue()) <= ((Integer) spnMinSize.getValue())) {
            spnMinSize.setValue(((Integer) spnMaxSize.getValue()) - 1);
        }
    }//GEN-LAST:event_spnMinSizeStateChanged

    private void spnMaxSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnMaxSizeStateChanged
        if (((Integer) spnMaxSize.getValue()) <= ((Integer) spnMinSize.getValue())) {
            spnMaxSize.setValue(((Integer) spnMinSize.getValue()) + 1);
        }
    }//GEN-LAST:event_spnMaxSizeStateChanged

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalculate;
    private javax.swing.JComboBox<String> cobBranches;
    private javax.swing.JComboBox<String> cobMetric;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFiles;
    private javax.swing.JLabel lblMaxCommit;
    private javax.swing.JLabel lblMaxSize;
    private javax.swing.JLabel lblMinCommit;
    private javax.swing.JLabel lblMinSize;
    private javax.swing.JLabel lblTime;
    private javax.swing.JList<String> lstExt;
    private javax.swing.JList<String> lstFileType;
    private javax.swing.JList<String> lstUsers;
    private javax.swing.JPanel pnlAuthors;
    private javax.swing.JPanel pnlExt;
    private javax.swing.JPanel pnlFileType;
    private javax.swing.JPanel pnlSetting;
    private javax.swing.JProgressBar prbCompute;
    private javax.swing.JSlider sldMaxCommit;
    private javax.swing.JSlider sldMinCommit;
    private javax.swing.JSpinner spnMaxSize;
    private javax.swing.JSpinner spnMinSize;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables

    private SliderValidator sliderValidator = new SliderValidator();

    private void initMyComponents() throws GitAPIException, IOException {
        Git git = GitUtils.getGit(txtPath.getText());
        List<Ref> call = git.branchList().call();
        cobBranches.removeAllItems();
        for (Ref ref : call) {
            cobBranches.addItem(ref.getName());
        }

        System.out.println("Now including remote branches:");
        call = git.branchList().setListMode(ListMode.ALL).call();
        for (Ref ref : call) {
            cobBranches.addItem(ref.getName());
        }

    }

}
