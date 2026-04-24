package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;

public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {
    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        writeToFile("index.html", baseDirPath, result.getHtmlCode());
    }

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void validateResult(HtmlCodeResult result) {
        super.validateResult(result);
        // HTML 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BussinessException(ErrorCode.SYSTEM_ERROR, "HTML 代码不能为空");
        }
    }
}
