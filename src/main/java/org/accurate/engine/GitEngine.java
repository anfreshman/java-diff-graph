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
    // 使用jgit获取远程仓库代码
    public void getBranch(String gitUrl, String branchName, String commitId) throws IOException {
        Git git;
        String[] nameArr = gitUrl.split("\\\\");
        String proName = nameArr[nameArr.length - 2] + nameArr[nameArr.length - 1];
        try {
            git = Git.cloneRepository()
                    .setURI(gitUrl)
                    .setDirectory(new java.io.File(proName))
                    .call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        // 获取指定分支的commit


    }

}
