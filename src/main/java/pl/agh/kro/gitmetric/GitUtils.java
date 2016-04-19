package pl.agh.kro.gitmetric;

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
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;

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
    
    public static Git getGit(String repoPath){
        Repository repository = GitUtils.getRepository(repoPath);
        Git git = new Git(repository);
        return git;
    }
    
    public static int countFiles(Repository repository, ObjectId commitID, String name) throws IOException {
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = revWalk.parseCommit(commitID);
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);

        // now try to find a specific file
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            treeWalk.setFilter(PathFilter.create(name));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file "+name);
            }

            ObjectId objectId = treeWalk.getObjectId(0);
            ObjectLoader loader = repository.open(objectId);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            loader.copyTo(stream);

            revWalk.dispose();

            return IOUtils.readLines(new ByteArrayInputStream(stream.toByteArray())).size();
        } 
    }
    
    public static RevTree getTree(Repository repository) throws AmbiguousObjectException, IncorrectObjectTypeException,
            IOException, MissingObjectException {
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);

        // a RevWalk allows to walk over commits based on some filtering
        try (RevWalk revWalk = new RevWalk(repository)) {
            RevCommit commit = revWalk.parseCommit(lastCommitId);

            System.out.println("Time of commit (seconds since epoch): " + commit.getCommitTime());

            // and using commit's tree find the path
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);
            return tree;
        }
    }

    public static void printFile(Repository repository, RevTree tree) throws MissingObjectException,
            IncorrectObjectTypeException, CorruptObjectException, IOException {
        // now try to find a specific file
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);
            treeWalk.setFilter(PathFilter.create("README.md"));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file 'README.md'");
            }

        // FileMode specifies the type of file, FileMode.REGULAR_FILE for normal file, FileMode.EXECUTABLE_FILE for executable bit
    // set
            FileMode fileMode = treeWalk.getFileMode(0);
            ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
            System.out.println("README.md: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode +
                    " size: " + loader.getSize());
        }
    }

    public static void printDirectory(Repository repository, RevTree tree) throws MissingObjectException,
            IncorrectObjectTypeException, CorruptObjectException, IOException {
        // look at directory, this has FileMode.TREE
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            //treeWalk.setFilter(PathFilter.create("src"));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file 'README.md'");
            }

            // FileMode now indicates that this is a directory, i.e. FileMode.TREE.equals(fileMode) holds true
            FileMode fileMode = treeWalk.getFileMode(0);
            System.out.println("src: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode);
        }
    }

    public static String getFileMode(FileMode fileMode) {
        if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
            return "Executable File";
        } else if (fileMode.equals(FileMode.REGULAR_FILE)) {
            return "Normal File";
        } else if (fileMode.equals(FileMode.TREE)) {
            return "Directory";
        } else if (fileMode.equals(FileMode.SYMLINK)) {
            return "Symlink";
        } else {
            // there are a few others, see FileMode javadoc for details
            throw new IllegalArgumentException("Unknown type of file encountered: " + fileMode);
        }
    }

}
