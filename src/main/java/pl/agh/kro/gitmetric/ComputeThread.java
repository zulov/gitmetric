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
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
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
public class ComputeThread extends Thread {

    private String metric;
    private String path;
    private String branch;
    private int minCommit;
    private int maxCommit;
    private Set<String> setExt;
    private Set<String> setFileType;
    private JLabel lblFiles;
    private JLabel lblTime;
    private JLabel lblProgres;

    private JList<String> lstExt;
    private JList<String> lstUsers;
    private JList<String> lstFileType;

    private int maxSize;
    private int minSize;

    private JPanel pnlExt;
    private JPanel pnlAuthors;
    private JPanel pnlFileType;

    private JProgressBar prbCompute;

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        prbCompute.setValue(0);
        Marking marking = MarkingFactory.getMarking(metric);
        Repository repository = GitUtils.getRepository(path);
        ObjectId commitId = GitUtils.getCommitId(repository, branch);
        
        Map<String, Integer> extsMap = new HashMap<>();
        Map<String, Integer> fileTypeMap = new HashMap<>();

        List<RevCommit> logs = GitUtils.getLogs(path, branch);

        RevCommit oldestCommit = logs.get(minCommit);
        RevCommit newestCommit = logs.get(maxCommit);

        // Obtain tree iterators to traverse the tree of the old/new commit
        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        try {
            oldTreeIter.reset(reader, oldestCommit.getTree());
            newTreeIter.reset(reader, newestCommit.getTree());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        DiffFormatter diffFormatter = GitUtils.prepareDiffFormater(repository);
        try {
            List<DiffEntry> entries = diffFormatter.scan(newTreeIter, oldTreeIter);
            prbCompute.setMaximum(entries.size());

            for (DiffEntry entry : entries) {
                updateInterface(entry, startTime);

                FileHeader fileHeader = diffFormatter.toFileHeader(entry);
                String extension = getExtension(fileHeader.getNewPath());
                makeSureMapsAreFilled(extsMap, extension, fileTypeMap, fileHeader);
                
                int lines = GitUtils.linesNumber(repository, commitId, entry.getNewPath());
                
                if (isValid(fileHeader.getPatchType().toString(), extension, lines)) {
                    GitUtils.authorsOfFile(marking, repository, commitId, entry.getNewPath(), lines);
                    Utils.addToMap(fileTypeMap, fileHeader.getPatchType().toString(), lines);
                    for (HunkHeader hunk : fileHeader.getHunks()) {
                        Utils.addToMap(extsMap, extension, hunk.getNewLineCount());
                    }
                }
            }

            updateInterface(marking, extsMap, fileTypeMap);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblTime.setText((System.currentTimeMillis() - startTime) / 1000f + "s");
    }

    private void updateInterface(Marking marking, Map<String, Integer> extsMap, Map<String, Integer> fileTypeMap) {
        Utils.fillList(lstUsers, marking.getNames());
        Utils.paintPieChart(pnlAuthors, marking.getUsers());
        
        Utils.fillList(lstExt, extsMap.keySet());
        Utils.paintPieChart(pnlExt, extsMap);
        
        Utils.fillList(lstFileType, fileTypeMap.keySet());
        Utils.paintPieChart(pnlFileType, fileTypeMap);
    }

    private void makeSureMapsAreFilled(Map<String, Integer> extsMap, String extension, Map<String, Integer> fileTypeMap, FileHeader fileHeader) {
        if(!extsMap.containsKey(extension)){
            extsMap.put(extension,0);
        }
        
        if(!fileTypeMap.containsKey(fileHeader.getPatchType().toString())){
            fileTypeMap.put(fileHeader.getPatchType().toString(),0);
        }
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
        prbCompute.setValue(value + 1);
        lblFiles.setText(entry.getNewPath());
        lblTime.setText((System.currentTimeMillis() - startTime) / 1000f + "s");
        lblProgres.setText((value+1)+"/"+prbCompute.getMaximum());
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setReposData(String path, String branch) {
        this.path = path;
        this.branch = branch;
    }

    public void setCommits(int minCommit, int maxCommit) {
        this.minCommit = minCommit;
        this.maxCommit = maxCommit;
    }

    public void setSetExt(List<String> setExt, List<String> setFileType) {
        this.setExt = new HashSet<>(setExt);
        this.setFileType = new HashSet<>(setFileType);
    }

    public void setLabels(JLabel lblFiles, JLabel lblTime,JLabel lblProgres) {
        this.lblFiles = lblFiles;
        this.lblTime = lblTime;
        this.lblProgres = lblProgres;
    }

    public void setLists(JList<String> lstExt, JList<String> lstUsers, JList<String> lstFileType) {
        this.lstExt = lstExt;
        this.lstUsers = lstUsers;
        this.lstFileType = lstFileType;
    }

    public void setSizes(int maxSize, int minSize) {
        this.maxSize = maxSize;
        this.minSize = minSize;
    }

    public void setPanels(JPanel pnlAuthors, JPanel pnlExt, JPanel pnlFileType) {
        this.pnlAuthors = pnlAuthors;
        this.pnlExt = pnlExt;
        this.pnlFileType = pnlFileType;
    }

    public void setPrbCompute(JProgressBar prbCompute) {
        this.prbCompute = prbCompute;
    }

    private boolean isValid(String fileType, String ext, int lines) {
        if ((!setFileType.contains(fileType) && !setFileType.isEmpty()) || (!setExt.contains(ext) && !setExt.isEmpty()) || lines >= maxSize || lines <= minSize) {
            return false;
        } else {
            return true;
        }
    }

}
