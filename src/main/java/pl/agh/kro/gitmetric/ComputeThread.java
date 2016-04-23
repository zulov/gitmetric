package pl.agh.kro.gitmetric;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import pl.agh.kro.gitmetric.git.GitUtils;
import pl.agh.kro.gitmetric.marking.Marking;
import pl.agh.kro.gitmetric.marking.MarkingFactory;

/**
 * @author Tomek
 */
public class ComputeThread extends Thread  {
    
    private String metric;
    private String path;
    private String branch;
    private int minCommit;
    private int maxCommit;
    private Set<String> setExt;
    private JLabel lblFiles;
    private JLabel lblTime;
    
    private JList<String> lstExt;
    private JList<String> lstUsers;
    
    private int maxSize;
    private int minSize;
    
    private JPanel pnlExt;
    private JPanel pnlAuthors;
    private JProgressBar prbCompute;
      
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
       prbCompute.setValue(0);
        Marking marking = MarkingFactory.getMarking(metric);
       
        Git git = GitUtils.getGit(path);
        List<RevCommit> logs = GitUtils.getLogs(path, branch);

        RevCommit firstCommit = logs.get(minCommit);
        RevCommit lastCommit = logs.get(maxCommit);

        // Obtain tree iterators to traverse the tree of the old/new commit
        ObjectReader reader = git.getRepository().newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        try {
            oldTreeIter.reset(reader, firstCommit.getTree());
            newTreeIter.reset(reader, lastCommit.getTree());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        DiffFormatter diffFormatter = GitUtils.prepareDiffFormater(git);
        
        //Set<String> setExt = new HashSet<>(lstExt.getSelectedValuesList());
        try {
            List<DiffEntry> entries = diffFormatter.scan(newTreeIter, oldTreeIter);
            prbCompute.setMaximum(entries.size());
            Map<String, Integer> extsMap = new HashMap<>();
            for (DiffEntry entry : entries) {
                updateInterface(entry, startTime);              
                FileHeader fileHeader = diffFormatter.toFileHeader(entry);
                String ext = getExtension(fileHeader.getNewPath());
                GitUtils.authorsOfFile(marking, path, branch, entry.getNewPath());
                for (HunkHeader hunk : fileHeader.getHunks()) {
                    if (!isValidSize(hunk)) {
                        Utils.addToMap(extsMap, ext, hunk.getNewLineCount());
                    }
                } 
            }

            Utils.fillList(lstUsers,marking.getNames());
            Utils.paintPieChart(pnlAuthors, marking.getUsers(), null);
            
            Utils.fillList(lstExt, extsMap.keySet());
            Utils.paintPieChart(pnlExt, extsMap, setExt);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblTime.setText((System.currentTimeMillis()-startTime)/1000f+"s");
    }

    private String getExtension(String name) {
        String ext = Utils.getExt(name);
        if (ext == null) {
            ext = "none";
        }
        return ext;
    }

    private void updateInterface(DiffEntry entry, long startTime) {
        int value = prbCompute.getValue();
        prbCompute.setValue(value+1);
        lblFiles.setText(entry.getNewPath());
        lblTime.setText((System.currentTimeMillis()-startTime)/1000f+"s");
    }
    
    private boolean isValidSize(HunkHeader hunk) {
        return hunk.getNewLineCount() >= maxSize || hunk.getNewLineCount() <= minSize;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setReposData(String path,String branch) {
        this.path = path;
        this.branch = branch;
    }

    public void setCommits(int minCommit, int maxCommit) {
        this.minCommit = minCommit;
        this.maxCommit = maxCommit;
    }

    public void setSetExt(List<String> setExt) {
        this.setExt = new HashSet<>(setExt);
    }

    public void setLabels(JLabel lblFiles, JLabel lblTime) {
        this.lblFiles = lblFiles;
        this.lblTime=lblTime;
    }

    public void setLists(JList<String> lstExt, JList<String> lstUsers) {
        this.lstExt = lstExt;
        this.lstUsers = lstUsers;
    }

    public void setSizes(int maxSize,int minSize) {
        this.maxSize = maxSize;
        this.minSize = minSize;
    }

    public void setPanels(JPanel pnlAuthors, JPanel pnlExt) {
        this.pnlAuthors = pnlAuthors;
        this.pnlExt = pnlExt;
    }

    public void setPrbCompute(JProgressBar prbCompute) {
        this.prbCompute = prbCompute;
    }
    
    
   
}
