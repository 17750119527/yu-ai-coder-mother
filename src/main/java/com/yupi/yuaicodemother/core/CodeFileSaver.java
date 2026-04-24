package com.yupi.yuaicodemother.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import cn.hutool.core.io.FileUtil;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;


import java.io.File;
import java.nio.charset.StandardCharsets;

public class CodeFileSaver {
    /*
    * 文件保存根目录
    * */
    private static final String FILE_SAVEROOT_DIR  = System.getProperty("user.dir")+"/tmp/code_output";

    /*
    * 构建文件的唯一路径  tmp/output/bizType_雪花ID
    * @param bizType 代码生成类型
    *
    * */
    private static String buildUniqueDir(String bizType){
        String uniqueDirName = StrUtil.format("{}_{}", bizType, IdUtil.getSnowflakeNextIdStr());
        String dirPath = FILE_SAVEROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /*
    * 保存单个文件
    * @param dirPath 文件保存的目录
    * @param fileName 文件名
    * @param content 文件内容
    * */
    private static void writeToFile(String dirPath, String fileName, String content){
        String filepath = dirPath + File.separator + fileName;
        FileUtil.writeString(content, filepath, StandardCharsets.UTF_8);
    }

    /*
    * 保存html文件
    * @param htmlCodeResult html代码结果
    * */
    public static File saveHtmlCodeResult(HtmlCodeResult htmlCodeResult){
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.HTML.getValue());
        writeToFile(baseDirPath, "index.html", htmlCodeResult.getHtmlCode());
        return new File(baseDirPath);
    }

    /*
    * 保存多文件
    * */
    public static File saveMultiFileCodeResult(MultiFileCodeResult multiFileCodeResult){
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.MULTI_FILE.getValue());
        writeToFile(baseDirPath, "index.html", multiFileCodeResult.getHtmlCode());
        writeToFile(baseDirPath, "index.css", multiFileCodeResult.getCssCode());
        writeToFile(baseDirPath, "index.js", multiFileCodeResult.getJsCode());
        return new File(baseDirPath);
    }

}
