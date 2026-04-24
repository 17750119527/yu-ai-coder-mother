package com.yupi.yuaicodemother.core;

import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;

@SpringBootTest
public class AiCodeGeneratorFacadeTest {
    @Resource
    AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    public void testGenerateAndSaveCode() {
        String userMessage = "请生成一个html页面，内容是“hello world”";
        File file = aiCodeGeneratorFacade.generateAndSaveCode(userMessage, CodeGenTypeEnum.HTML);
        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void testGenerateAndSaveCodeStream() {
        String userMessage = "请生成一个html页面，内容是“hello world”";
        Flux<String> result = aiCodeGeneratorFacade.generateAndSaveCodeStream(userMessage, CodeGenTypeEnum.MULTI_FILE);
        result.subscribe(System.out::println);
    }

}
