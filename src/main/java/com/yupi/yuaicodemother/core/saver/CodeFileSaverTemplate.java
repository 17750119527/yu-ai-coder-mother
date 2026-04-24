package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;
import com.yupi.yuaicodemother.constant.AppConstant;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;

import java.io.File;
import java.nio.charset.StandardCharsets;

public abstract class CodeFileSaverTemplate<T> {
    /*
    * 保存路径
    * */
    private static final String FILE_SAVE_ROOT_DIR = AppConstant.CODE_OUTPUT_ROOT_DIR;


    /**
     * 模板方法：保存代码的标准流程
     *
     * @param result 代码结果对象
     * @param appId 应用 ID
     * @return 保存的目录
     */
    public final File saveCode(T result, Long appId){
        //1. 校验结果
        validateResult(result);
        //2. 构建文件唯一路径
        String baseDirPath = buildUniqueDir(appId);
        //3. 保存文件(具体实现交给子类)
        saveFiles(result,baseDirPath);
        return new File(baseDirPath);
    }

    /*
    * 保存文件
    * @param fileName 文件名
    * @param dirpath 文件保存的目录
    * @param content 文件内容
    * */
    public final void writeToFile(String fileName,String dirpath,String content){
        if (StrUtil.isBlank(content)){
            String filePath = dirpath + File.separator + fileName;
            FileUtil.writeString(content,filePath, StandardCharsets.UTF_8);
        }
    }

    /*
    * 校验结果
    * @param result 待校验的代码结果对象
    * */
    protected void validateResult(T result){
        if (result == null){
            throw new BussinessException(ErrorCode.SYSTEM_ERROR,"代码参数不能为空");
        }
    }

    /**
     * 构建文件的唯一路径：tmp/code_output/bizType_雪花 ID
     *
     * @param appId 应用 ID
     * @return 目录路径
     */
    protected String buildUniqueDir(Long appId) {
        if (appId == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "应用 ID 不能为空");
        }
        String codeType = getCodeType().getValue();
        String uniqueDirName = StrUtil.format("{}_{}", codeType, appId);
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /**
     * 保存文件（具体实现交给子类）
     *
     * @param result      代码结果对象
     * @param baseDirPath 基础目录路径
     */
    protected abstract void saveFiles(T result, String baseDirPath);

    /**
     * 获取代码生成类型
     *
     * @return 代码生成类型枚举
     */
    protected abstract CodeGenTypeEnum getCodeType();
}
