package org.dto;

import lombok.Data;

@Data
public class project {
    // 项目id，自动生成的唯一主键
    private String projcetId;

    // 项目名，git名
    private String projectName;

    // 项目url
    private String gitUrl;

    // 项目分支
    private String branch;

    // 项目commit
    private String commit;

}
