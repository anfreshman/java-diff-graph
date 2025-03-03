package org.accurate.engine;


/**
 * 完成Engine、获取diff
 */
public class AnalyzeEngine {

    /**
     * 对比同一分支的两个commit
     * 获取对应的修改文件
     */
    public void get_commit_diff(String branch, String commit_first, String commit_second){
        // 分支一致，则直接返回无差异
        if(commit_first.equals(commit_second)){
            throw new RuntimeException("无差异") ;
        }
        // 获取对应branch的commit
    }

    /**
     * 查询branch的某个commit，先查询库，没有的话拉取对应commit
     *
     */
    public String get_branch_commit(String branch, String commit_id){
        // 查询数据库里有没有对应的库 - 分支 - commit
        return null;
    }

    // 分析修改文件，确认修改的层级，比如新增类、修改类、删除类、新增方法、修改方法、删除方法、新增字段、修改字段、删除字段
    public void analyze_file(String file_path){
        // 获取文件类型,

        // 获取文件内容
        // 获取文件修改的层级
    }

}
