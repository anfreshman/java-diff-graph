package org.accurate.engine;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitEngine {

    private static final Logger log = LoggerFactory.getLogger(GitEngine.class);
    /**
     * 对比同一分支的两个commit
     * 获取对应的修改文件
     */
    public List<String> getDiff(String gitUrl, String branchName, String firstCommit, String secondCommit) throws IOException {
        List<String> result = new ArrayList<>();
        Git git;
        String[] nameArr = gitUrl.split("/");
        String proName = nameArr[nameArr.length - 2] + "-" + nameArr[nameArr.length - 1].replace(".git", "");
        try {
            boolean hasGet = isDirectoryExists(proName);

            if(!hasGet){
                git = Git.cloneRepository()
                        .setURI(gitUrl)
                        .setDirectory(new java.io.File(proName))
                        .call();
            }else {
                git = Git.open(new File(proName));
            }

        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }

        try {

            git.checkout().setName(branchName).call();
            ObjectId firstCommitTree = git.getRepository().resolve(firstCommit);
            RevWalk revWalk = new RevWalk(git.getRepository());
            RevCommit commitObj = revWalk.parseCommit(firstCommitTree);


            ObjectId secondCommitTree = git.getRepository().resolve(secondCommit);
            RevCommit headCommit = revWalk.parseCommit(secondCommitTree);


            CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
            oldTreeParser.reset(git.getRepository().newObjectReader(), commitObj.getTree());
            CanonicalTreeParser newTreeParser = new CanonicalTreeParser();
            newTreeParser.reset(git.getRepository().newObjectReader(), headCommit.getTree());


            java.util.List<DiffEntry> diffs = git.diff()
                    .setNewTree(newTreeParser)
                    .setOldTree(oldTreeParser)
                    .call();


            for (DiffEntry diff : diffs) {
                System.out.println("差异文件: " + diff.getNewPath());
                result.add(diff.getNewPath());
            }

            revWalk.close();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean isDirectoryExists(String directoryName) {
        File directory = new File(directoryName);
        return directory.exists() && directory.isDirectory();
    }


    public static void main(String[] args) {
        GitEngine gitEngine = new GitEngine();
        try {
            gitEngine.getDiff("https://github.com/eclipse-jgit/jgit.git", "master", "9b08cf230d0853873c9caace5cbe35eb19cdef29", "2a3e1191ea8062038a3d11de99d1549678c35d61");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

