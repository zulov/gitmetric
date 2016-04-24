package pl.agh.kro.gitmetric.git;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import pl.agh.kro.gitmetric.Main;
import pl.agh.kro.gitmetric.marking.Marking;

public class GitUtils {

    public static Repository getRepository(String path) {
        File repoDir = new File(path);
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder
                    .setGitDir(repoDir)
                    //.readEnvironment() // scan environment GIT_* variables
                    //.findGitDir() // scan up the file system tree
                    .build();
        } catch (IOException ex) {
            Logger.getLogger(GitUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repository;
    }

    public static List<RevCommit> getLogs(String repoPath, String branchName) {
        if (repoPath.isEmpty() || branchName.isEmpty()) {
            return null;
        }
        Repository repository = GitUtils.getRepository(repoPath);
        Git git = new Git(repository);
        Iterable<RevCommit> logs = null;
        try {
            logs = git.log().add(repository.resolve(branchName)).call();
        } catch (RevisionSyntaxException | IOException | GitAPIException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (logs == null) {
            return new ArrayList<>(0);
        }
        List<RevCommit> logsList = new ArrayList<>();
        for (RevCommit rev : logs) {
            logsList.add(rev);
        }
        return logsList;
    }

    public static Git getGit(String repoPath) {
        Repository repository = GitUtils.getRepository(repoPath);
        Git git = new Git(repository);
        return git;
    }

    public static Git getGit(Repository repository) {
        Git git = new Git(repository);
        return git;
    }

    public static int countFiles(Repository repository, ObjectId commitID, String name) throws IOException {
        RevTree tree = getRevTree(repository, commitID);

        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            treeWalk.setFilter(PathFilter.create(name));
            if (!treeWalk.next()) {
                return 0;
            }
            FileMode fileMode = treeWalk.getFileMode(0);
            if (!fileMode.equals(FileMode.REGULAR_FILE)) {
                return 0;
            }

            ObjectId objectId = treeWalk.getObjectId(0);
            ObjectLoader loader = repository.open(objectId);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            loader.copyTo(stream);

            return IOUtils.readLines(new ByteArrayInputStream(stream.toByteArray())).size();
        }
    }

    private static RevTree getRevTree(Repository repository, ObjectId commitID) throws IOException {
        RevWalk revWalk = new RevWalk(repository);
        RevTree tree = revWalk.parseCommit(commitID).getTree();
        revWalk.dispose();
        return tree;
    }

    public static ObjectId getCommitId(Repository repository, String branchName) {
        try {
            ObjectId commitID = repository.resolve(branchName);
            return commitID;
        } catch (RevisionSyntaxException | IOException ex) {
            Logger.getLogger(GitUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void authorsOfFile(Marking marking, Repository repository, ObjectId commitId, String fileName, int lines) {
        System.out.println(fileName);
        try {
            BlameCommand blamer = new BlameCommand(repository)
                    .setStartCommit(commitId).setFilePath(fileName);

            BlameResult blame = blamer.call();

            String lastPerson = "error";
            for (int i = 0; i < lines; i++) {
                try {
                    PersonIdent person = blame.getSourceAuthor(i);
                    lastPerson = person.getName();
                    marking.incMap(person.getName(), 1, blame.getResultContents().getString(i));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    marking.incMap(lastPerson, lines - i - 1, "");
                    break;
                }
            }
        } catch (RevisionSyntaxException | GitAPIException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DiffFormatter prepareDiffFormater(Repository repository) {
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(repository);
        diffFormatter.setContext(0);
        return diffFormatter;
    }

    public static int linesNumber(Repository repository, ObjectId commitID, String newPath) {
        try {
            int lines = GitUtils.countFiles(repository, commitID, newPath);

            return lines;
        } catch (RevisionSyntaxException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
