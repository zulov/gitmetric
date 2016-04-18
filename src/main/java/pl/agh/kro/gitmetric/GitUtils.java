package pl.agh.kro.gitmetric;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


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

}
