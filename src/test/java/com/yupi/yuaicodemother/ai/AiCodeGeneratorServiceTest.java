package com.yupi.yuaicodemother.ai;

import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class AiCodeGeneratorServiceTest {
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode(){
        HtmlCodeResult htmlCode = aiCodeGeneratorService.generateHtmlCode("做个程序员鱼皮的博客，不超过 20 行");
        Assertions.assertNotNull(htmlCode);
    }

    @Test
    void generateMultiFileCode(){
        MultiFileCodeResult multiFileCode = aiCodeGeneratorService.generateMultiFileCode("用 Java 创建一个类，类名为 HelloWorld，类中包含一个方法，方法名为 print，方法中打印 Hello World");
        Assertions.assertNotNull(multiFileCode);
    }
}
