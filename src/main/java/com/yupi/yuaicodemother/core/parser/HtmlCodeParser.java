package com.yupi.yuaicodemother.core.parser;

import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlCodeParser implements CodeParser<HtmlCodeResult> {
    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    @Override
    public HtmlCodeResult parseCode(String codeContent) {
        HtmlCodeResult htmlCodeResult = new HtmlCodeResult();
        //提取html代码
        String htmlCode = extractHtmlCode(codeContent);
        if (htmlCode != null && !htmlCode.trim().isEmpty()){
            htmlCodeResult.setHtmlCode(htmlCode.trim());
        }else {
            htmlCodeResult.setHtmlCode(codeContent.trim());
        }
        return htmlCodeResult;
    }

    private String extractHtmlCode(String codeContent) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(codeContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
