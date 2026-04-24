package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;

public class MultiCodeFileSaverTemplate extends CodeFileSaverTemplate<MultiFileCodeResult> {
    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        writeToFile("index.html", baseDirPath, result.getHtmlCode());
        writeToFile("index.css", baseDirPath, result.getCssCode());
        writeToFile("index.js", baseDirPath, result.getJsCode());
    }

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }

    @Override
    protected void validateResult(MultiFileCodeResult result) {
        super.validateResult(result);
        // 至少要有 HTML 代码，CSS 和 JS 可以为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BussinessException(ErrorCode.SYSTEM_ERROR, "HTML代码内容不能为空");
        }
    }
}
