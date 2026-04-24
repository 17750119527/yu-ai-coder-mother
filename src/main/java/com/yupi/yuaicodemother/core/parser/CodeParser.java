package com.yupi.yuaicodemother.core.parser;

public interface CodeParser<T> {
    /*
    * 解析代码内容
    * @param codeContent 代码内容
    * @return 解析结果
    * */
    T parseCode(String codeContent);
}
