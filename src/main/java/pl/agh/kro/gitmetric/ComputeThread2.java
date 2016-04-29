package pl.agh.kro.gitmetric;

import java.io.IOException;
import java.util.Date;
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
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.Edit.Type;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import pl.agh.kro.gitmetric.git.GitUtils;
import pl.agh.kro.gitmetric.marking.Marking;
import pl.agh.kro.gitmetric.marking.MarkingFactory;

public class ComputeThread2 extends Thread {

    private String metric;
    private String path;
    private String branch;
    private int minCommit;
    private int maxCommit;
    private Set<String> setExt;
    private Set<String> setFileType;
    private Set<String> setUsers;
    private boolean onlyAdditions = false;
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
    private JPanel pnlCommitHistory;

    private JProgressBar prbCompute;

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        prbCompute.setValue(0);
        Marking marking = MarkingFactory.getMarking(metric);
        Repository repository = GitUtils.getRepository(path);
        ObjectId commitId = GitUtils.getCommitId(repository, branch);

        Map<String, Integer> extToLineCountMap = new HashMap<>();
        Map<String, Integer> fileTypeToLineCountMap = new HashMap<>();

        List<RevCommit> logs = GitUtils.getLogs(path, branch);

        RevCommit oldestCommit = logs.get(minCommit);
        System.out.println("Oldest commit message:" + oldestCommit.getShortMessage() + ", commiter: " + oldestCommit.getAuthorIdent().getName());
        RevCommit newestCommit = logs.get(maxCommit);
        System.out.println("Newest commit message:" + newestCommit.getShortMessage() + ", commiter: " + newestCommit.getAuthorIdent().getName());

        prbCompute.setMaximum(minCommit - maxCommit);
        System.out.println("minCommit: " + minCommit + ", maxCommit: " + maxCommit);
        for (int i = minCommit - 1; i >= maxCommit; i--) {
            RevCommit currentCommit = logs.get(i);
            RevCommit nextCommit = logs.get(i + 1);
            PersonIdent author = currentCommit.getAuthorIdent(); //jest autor kodu
            Date date = currentCommit.getAuthorIdent().getWhen();//jest data, ale nie wiem czy poprawna
            
            CanonicalTreeParser currTreeIter = new CanonicalTreeParser();
            CanonicalTreeParser nextTreeIter = new CanonicalTreeParser();
            try {
                ObjectReader reader = repository.newObjectReader();
                currTreeIter.reset(reader, currentCommit.getTree());
                nextTreeIter.reset(reader, nextCommit.getTree());

                DiffFormatter diffFormatter = GitUtils.prepareDiffFormater(repository);
                List<DiffEntry> entries = diffFormatter.scan(nextTreeIter, currTreeIter);
                for (DiffEntry diffEntry : entries) {
                    int lines = GitUtils.linesNumber(repository, nextCommit.copy(), diffEntry.getNewPath()); //jest wielkość pliku
                    FileHeader fileHeader = diffFormatter.toFileHeader(diffEntry);
                    String extension = getExtension(fileHeader.getNewPath());   //jest rozszerzenie 
                    String fileType = fileHeader.getPatchType().toString(); //jest typ pliku
                    
                    makeSureMapsAreFilled(extToLineCountMap, fileTypeToLineCountMap, fileHeader);

                    if (isValid(author.getName(), fileType, extension, lines)) {
                        for (HunkHeader hunk : fileHeader.getHunks()) {
                            if (fileType.equals("BINARY")) {
                                int linesNumber = GitUtils.linesNumber(repository, commitId, diffEntry.getNewPath());
                                marking.incMap(author.getName(), linesNumber, "none", date);
                                Utils.addToMap(fileTypeToLineCountMap, fileType, linesNumber);
                                Utils.addToMap(extToLineCountMap, extension, linesNumber);
                            } else {
                                EditList editList = hunk.toEditList();
                                for (Edit edit : editList) {
                                    if ((!onlyAdditions && edit.getType() == Type.REPLACE) || (edit.getType() == Type.INSERT)) {
                                        for (int j = edit.getBeginB(); j < edit.getEndB(); j++) {
                                            String line = getLineFromFile(j, hunk.getFileHeader().getNewPath(), currentCommit, repository);
                                            if (marking.incMap(author.getName(), 1, line, date)) {
                                                Utils.addToMap(fileTypeToLineCountMap, fileType, 1);
                                                Utils.addToMap(extToLineCountMap, extension, 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateInterface(currentCommit, startTime);
        }
        updateInterface(marking, extToLineCountMap, fileTypeToLineCountMap);
        lblTime.setText((System.currentTimeMillis() - startTime) / 1000f + "s");
    }

    private void updateInterface(Marking marking, Map<String, Integer> extsMap, Map<String, Integer> fileTypeMap) {
        Utils.fillList(lstUsers, marking.getNames());
        Utils.paintPieChart(pnlAuthors, marking.getUsers());

        Utils.fillList(lstExt, extsMap.keySet());
        Utils.paintPieChart(pnlExt, extsMap);

        Utils.fillList(lstFileType, fileTypeMap.keySet());
        Utils.paintPieChart(pnlFileType, fileTypeMap);

        Utils.paintTimeSeries(pnlCommitHistory, marking.getUserToHistory());
    }

    private void makeSureMapsAreFilled(Map<String, Integer> extsMap, Map<String, Integer> fileTypeMap, FileHeader fileHeader) {
        String extension = getExtension(fileHeader.getNewPath());
        if (!extsMap.containsKey(extension)) {
            extsMap.put(extension, 0);
        }

        if (!fileTypeMap.containsKey(fileHeader.getPatchType().toString())) {
            fileTypeMap.put(fileHeader.getPatchType().toString(), 0);
        }
    }

    private String getExtension(String name) {
        String ext = Utils.getExt(name);
        if (ext == null) {
            ext = "none";
        }
        return ext;
    }

    private void updateInterface(RevCommit commit, long startTime) {
        int value = prbCompute.getValue();
        prbCompute.setValue(value + 1);
        lblFiles.setText(commit.getShortMessage());
        lblTime.setText((System.currentTimeMillis() - startTime) / 1000f + "s");
        lblProgres.setText((value + 1) + "/" + prbCompute.getMaximum());
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

    public void setSets(List<String> setExt, List<String> setFileType, List<String> setUsers) {
        this.setExt = new HashSet<>(setExt);
        this.setFileType = new HashSet<>(setFileType);
        this.setUsers = new HashSet<>(setUsers);
    }

    public void setLabels(JLabel lblFiles, JLabel lblTime, JLabel lblProgres) {
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

    public void setPanels(JPanel pnlAuthors, JPanel pnlExt, JPanel pnlFileType, JPanel pnlCommitHistory) {
        this.pnlAuthors = pnlAuthors;
        this.pnlExt = pnlExt;
        this.pnlFileType = pnlFileType;
        this.pnlCommitHistory = pnlCommitHistory;
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

    //tego się nie da użyć, bo badamy tylko diffy między ostatnim a pierwszym commitem, a diff może zawierać kilku autorów
    private boolean isValid(String user, String fileType, String ext, int lines) {
        if ((!(setUsers.isEmpty() || setUsers.contains(user))) || (!setFileType.contains(fileType) && !setFileType.isEmpty()) || (!setExt.contains(ext) && !setExt.isEmpty()) || lines >= maxSize || lines <= minSize) {
            return false;
        } else {
            return true;
        }
    }

    private int getLineCount(Edit edit, boolean onlyAdditions) {
        if ((!onlyAdditions && edit.getType() == Type.REPLACE) || (edit.getType() == Type.INSERT)) {
            return edit.getLengthB();
        }
        return 0;
    }

    private String getLineFromFile(int j, String newPath, RevCommit nextCommit, Repository repository) {
        RawText r = GitUtils.getFileFromCommit(repository, nextCommit.copy(), newPath);
        return r.getString(j);
    }

}
