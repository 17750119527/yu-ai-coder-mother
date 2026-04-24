package com.yupi.yuaicodemother.core.parser;

import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;

/**
 * 代码解析执行器
 * 根据代码生成类型执行相应的解析逻辑
 *
 * @author yupi
 */
public class CodeParserExecutor {
    private static final HtmlCodeParser htmlCodeParser = new HtmlCodeParser();
    private static final MultiFileCodeParser multiFileCodeParser = new MultiFileCodeParser();

    public static Object executeParser(String codeContent, CodeGenTypeEnum codeGenType) {
        return switch (codeGenType) {
            case HTML -> htmlCodeParser.parseCode(codeContent);
            case MULTI_FILE -> multiFileCodeParser.parseCode(codeContent);
            default -> throw new BussinessException(ErrorCode.SYSTEM_ERROR,"不支持的生成类型");
        };
    }
}
