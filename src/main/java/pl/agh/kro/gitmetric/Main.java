/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
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
        jList1 = new javax.swing.JList<>();
        lblMinCommit = new javax.swing.JLabel();
        lblMaxCommit = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
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
        pnlResult = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblCommits = new javax.swing.JLabel();
        btnTest = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlExt = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

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

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder("Użytkownicy"));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

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

        cobMetric.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Linie kodu", "Cos innego" }));

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

        javax.swing.GroupLayout pnlSettingLayout = new javax.swing.GroupLayout(pnlSetting);
        pnlSetting.setLayout(pnlSettingLayout);
        pnlSettingLayout.setHorizontalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPath, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(btnCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cobBranches, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cobMetric, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSettingLayout.createSequentialGroup()
                        .addComponent(lblMinCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sldMinCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(lblMaxCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sldMaxCommit, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(lblMinSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spnMinSize, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addComponent(lblMaxSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spnMaxSize, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMinSize)
                    .addComponent(spnMinSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxSize)
                    .addComponent(spnMaxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCalculate)
                .addContainerGap())
        );

        pnlResult.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Wyniki"));

        jLabel4.setText("Ilość comitów:");

        lblCommits.setText("0000");

        btnTest.setText("TEST");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlResultLayout = new javax.swing.GroupLayout(pnlResult);
        pnlResult.setLayout(pnlResultLayout);
        pnlResultLayout.setHorizontalGroup(
            pnlResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResultLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCommits, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnTest)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlResultLayout.setVerticalGroup(
            pnlResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResultLayout.createSequentialGroup()
                .addGroup(pnlResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblCommits)
                    .addComponent(btnTest))
                .addGap(0, 81, Short.MAX_VALUE))
        );

        pnlExt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pnlExtLayout = new javax.swing.GroupLayout(pnlExt);
        pnlExt.setLayout(pnlExtLayout);
        pnlExtLayout.setHorizontalGroup(
            pnlExtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        pnlExtLayout.setVerticalGroup(
            pnlExtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Rozszerzenia", pnlExt);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 784, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Słupki", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTabbedPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateActionPerformed
        Iterable<RevCommit> logs = getLogs(txtPath.getText(), cobBranches.getSelectedItem().toString());
        if(logs==null){return;}
        int count = 0;
        for (RevCommit rev : logs) {
            //System.out.println("Commit: " + rev  + ", name: " + rev.getFullMessage() + ", id: " + rev.getId().getName() );
            count++;
        }
        lblCommits.setText(Integer.toString(count));
    }//GEN-LAST:event_btnCalculateActionPerformed

    private Iterable<RevCommit> getLogs(String repoPath, String branchName) {
        if(repoPath.isEmpty() || branchName.isEmpty()){return null;}
        Repository repository = GitUtils.getRepository(repoPath);
        Git git = new Git(repository);
        Iterable<RevCommit> logs = null;
        try {
            logs = git.log().add(repository.resolve(branchName)).call();
        } catch (MissingObjectException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectObjectTypeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RevisionSyntaxException | IOException | GitAPIException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logs;
    }

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        Repository repository = GitUtils.getRepository(txtPath.getText());
        Git git = new Git(repository);
        
        Iterable<RevCommit> logs = getLogs(txtPath.getText(), cobBranches.getSelectedItem().toString());
        if(logs==null){return;}
        List<RevCommit> logsList = new ArrayList<>();
        for (RevCommit rev : logs) {
            logsList.add(rev);
        }
        RevCommit oldCommit = logsList.get(sldMinCommit.getValue());
        RevCommit newCommit = logsList.get(sldMaxCommit.getValue());

        // Obtain tree iterators to traverse the tree of the old/new commit
        ObjectReader reader = git.getRepository().newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        try {
            oldTreeIter.reset(reader, oldCommit.getTree());
            newTreeIter.reset(reader, newCommit.getTree());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Use a DiffFormatter to compare new and old tree and return a list of changes
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(git.getRepository());
        diffFormatter.setContext(0);
        List<String> selectedExt = lstExt.getSelectedValuesList();
       
        Set<String> setExt = new HashSet<String>(selectedExt);
        try {
            List<DiffEntry> entries = diffFormatter.scan(newTreeIter, oldTreeIter);
            // Print the contents of the DiffEntries
            Map<String,ExtData> map = new HashMap<>();
            for (DiffEntry entry : entries) {
                FileHeader fileHeader = diffFormatter.toFileHeader(entry);
                String name = fileHeader.getNewPath();
                int index = name.lastIndexOf('.');
                if(index<0){continue;}
                String ext = name.substring(index);
  
                List<? extends HunkHeader> hunks = fileHeader.getHunks();
                for (HunkHeader hunk : hunks) {  
                    if(hunk.getNewLineCount()>=(Integer)spnMaxSize.getValue()  || hunk.getNewLineCount()<=(Integer)spnMinSize.getValue()){continue;}
                    if(map.containsKey(ext)){
                        map.get(ext).lines+=hunk.getNewLineCount();
                    }else{
                        map.put(ext, new ExtData(ext,hunk.getNewLineCount()));
                    }
                }
            }
            paintPieChart(pnlExt,map.values(),setExt);
            Iterator<ExtData> iterator = map.values().iterator();
            lstExt.removeAll();
            DefaultListModel listModel = new DefaultListModel();
            while(iterator.hasNext()) {
                ExtData data = iterator.next();
                listModel.addElement(data.name);
            }
            lstExt.setModel(listModel);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTestActionPerformed

    private void sldMinCommitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldMinCommitStateChanged
        if (sldMaxCommit.getValue()<=sldMinCommit.getValue()){
            sldMinCommit.setValue(sldMaxCommit.getValue()-1);
        }
        lblMinCommit.setText("Od: "+sldMinCommit.getValue());
        lblMaxCommit.setText("Do: "+sldMaxCommit.getValue()); 
    }//GEN-LAST:event_sldMinCommitStateChanged

    private void sldMaxCommitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldMaxCommitStateChanged
        if (sldMaxCommit.getValue()<=sldMinCommit.getValue()){
            sldMaxCommit.setValue(sldMinCommit.getValue()+1);
        }
        lblMinCommit.setText("Od: "+sldMinCommit.getValue());
        lblMaxCommit.setText("Do: "+sldMaxCommit.getValue());   
    }//GEN-LAST:event_sldMaxCommitStateChanged

    private void cobBranchesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cobBranchesItemStateChanged
        String path = txtPath.getText();
        if(cobBranches.getSelectedIndex()==-1){return;}
        String branch = cobBranches.getSelectedItem().toString();
        Iterable<RevCommit> logs = getLogs(path, branch);
        if(logs==null){return;}
        List<RevCommit> logsList = new ArrayList<>();
        for (RevCommit rev : logs) {
            logsList.add(rev);
        }
        sliderValidator.validate(sldMinCommit, sldMaxCommit, logsList.size());
    }//GEN-LAST:event_cobBranchesItemStateChanged

    private void spnMinSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnMinSizeStateChanged
        if (((Integer)spnMaxSize.getValue())<=((Integer)spnMinSize.getValue())){
            spnMinSize.setValue(((Integer)spnMaxSize.getValue())-1);
        }
        lblMinCommit.setText("Od: "+spnMinSize.getValue());
        lblMaxCommit.setText("Do: "+spnMaxSize.getValue()); 
    }//GEN-LAST:event_spnMinSizeStateChanged

    private void spnMaxSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnMaxSizeStateChanged
        if (((Integer)spnMaxSize.getValue())<=((Integer)spnMinSize.getValue())){
            spnMaxSize.setValue(((Integer)spnMinSize.getValue())+1);
        }
        lblMinCommit.setText("Od: "+spnMinSize.getValue());
        lblMaxCommit.setText("Do: "+spnMaxSize.getValue()); 
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
    private javax.swing.JButton btnTest;
    private javax.swing.JComboBox<String> cobBranches;
    private javax.swing.JComboBox<String> cobMetric;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCommits;
    private javax.swing.JLabel lblMaxCommit;
    private javax.swing.JLabel lblMaxSize;
    private javax.swing.JLabel lblMinCommit;
    private javax.swing.JLabel lblMinSize;
    private javax.swing.JList<String> lstExt;
    private javax.swing.JPanel pnlExt;
    private javax.swing.JPanel pnlResult;
    private javax.swing.JPanel pnlSetting;
    private javax.swing.JSlider sldMaxCommit;
    private javax.swing.JSlider sldMinCommit;
    private javax.swing.JSpinner spnMaxSize;
    private javax.swing.JSpinner spnMinSize;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables
    
    private SliderValidator sliderValidator = new SliderValidator();
    
    private void initMyComponents() throws GitAPIException, IOException {
        try (Repository repository = GitUtils.getRepository(txtPath.getText())) {
            try (Git git = new Git(repository)) {
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
    }
    void paintPieChart(javax.swing.JPanel panel,Collection<ExtData> datas,Set<String> setExt) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Iterator<ExtData> iterator = datas.iterator();
        while(iterator.hasNext()) {
            ExtData data = iterator.next();
            if(!setExt.isEmpty()&& !setExt.contains(data.name)){continue;}  
            dataset.setValue(data.name +" - "+data.lines, data.lines);
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Rozkład rozszerzeń", // chart title                   
                dataset, // data 
                true, // include legend                   
                true,
                false);

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(0);
        plot.setForegroundAlpha(0.75f);
        plot.setInteriorGap(0.05);

//        for (int i = 0; i < datas.size(); i++) {
//            plot.setSectionPaint(datas.get(i).name, partie[i].color);
//        }
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(panel.getSize()));
        chartPanel.setVisible(true);
        panel.setLayout(new java.awt.BorderLayout());
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
    }
    
}
