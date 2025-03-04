package org.accurate.engine;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.IOException;

public class GitEngine {

    public void getBranch(String gitUrl, String branchName, String commitId) throws IOException {
        Git git;
        String[] nameArr = gitUrl.split("/");
        String proName = nameArr[nameArr.length - 2] + "-" + nameArr[nameArr.length - 1].replace(".git", "");
        try {
            git = Git.cloneRepository()
                    .setURI(gitUrl)
                    .setDirectory(new java.io.File(proName))
                    .call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }

        try {

            git.checkout().setName(branchName).call();


            ObjectId commit = git.getRepository().resolve(commitId);
            RevWalk revWalk = new RevWalk(git.getRepository());
            RevCommit commitObj = revWalk.parseCommit(commit);


            ObjectId head = git.getRepository().resolve("HEAD");
            RevCommit headCommit = revWalk.parseCommit(head);


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
            }

            revWalk.close();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        GitEngine gitEngine = new GitEngine();
        try {
            gitEngine.getBranch("https://github.com/eclipse-jgit/jgit.git", "master", "875184297c0c2c08baa25f02ef53de11a6808005");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

