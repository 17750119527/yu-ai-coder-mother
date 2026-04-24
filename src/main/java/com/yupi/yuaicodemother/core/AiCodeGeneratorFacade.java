package com.yupi.yuaicodemother.core;

import com.yupi.yuaicodemother.ai.AiCodeGeneratorService;
import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.ai.model.enums.CodeGenTypeEnum;
import com.yupi.yuaicodemother.core.parser.CodeParserExecutor;
import com.yupi.yuaicodemother.core.saver.CodeFileSaverExecutor;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;

@Service
@Slf4j
public class AiCodeGeneratorFacade {
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    /*
    * 统一入口
    * @param userMessage 用户输入
    * @param codeGenType 代码生成模式
    * @return 文件保存的目录
    * */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenType) {
        if (codeGenType == null){
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        return switch (codeGenType){
            case HTML -> {
                Flux<String> htmlCodeStreame = aiCodeGeneratorService.generateHtmlCodeStreame(userMessage);
                yield processCodeStream(htmlCodeStreame,CodeGenTypeEnum.HTML,null);
            }
            case MULTI_FILE -> {
                Flux<String> multiFileCodeStreame = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
                yield processCodeStream(multiFileCodeStreame,CodeGenTypeEnum.MULTI_FILE,null);
            }
            default -> {
                String errorMessage = "不支持的代码生成模式";
                throw new BussinessException(ErrorCode.PARAMS_ERROR,errorMessage);
            }
        };
    }

    /*
    * 生成HTML模式的代码并保存（流式）
    * @param userMessage 用户输入
    * @return 保存的目录
    * */
    private Flux<String> generateAndSaveHtmlCodeStream(String userMessage){
        Flux<String> result = aiCodeGeneratorService.generateHtmlCodeStreame(userMessage);
        StringBuilder codeBuilder = new StringBuilder();
        return result
                .doOnNext(chunk -> {
            codeBuilder.append(chunk);
        })
                .doOnComplete(() -> {
                    try {
                        String completeHtmlCode = codeBuilder.toString();
                        HtmlCodeResult htmlCodeResult = CodeParser.parseHtmlCode(completeHtmlCode);
                        File file = CodeFileSaver.saveHtmlCodeResult(htmlCodeResult);
                        log.info("保存的目录为：{}", file.getAbsolutePath());
                    }catch (Exception e){
                        log.error("保存代码出错：{}", e.getMessage());
                    }
                });
    }

    /*
    * 生成多文件模式的代码并保存
    * @param userMessage 用户输入
    * @return 文件保存的 目录
    * */
    private Flux<String> generateAndSaveMultiFileCodeStream(String userMessage){
        Flux<String> result = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
        StringBuilder builder = new StringBuilder();
        return result
                .doOnNext(chunk -> {
                    builder.append(chunk);
                })
                .doOnComplete(()->{
                    try {
                        String completeCode = builder.toString();
                        MultiFileCodeResult multiFileCodeResult = CodeParser.parseMultiFileCode(completeCode);
                        File file = CodeFileSaver.saveMultiFileCodeResult(multiFileCodeResult);
                        log.info("保存的目录为：{}", file.getAbsolutePath());
                    }catch (Exception e){
                        log.error("保存代码出错：{}", e.getMessage());
                    }
                });
    }


    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenType) {
        if (codeGenType == null){
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        switch (codeGenType){
            case HTML -> {
                return generateAndSaveHtmlCode(userMessage);
            }
            case MULTI_FILE->{
                return generateAndSaveMultiFileCode(userMessage);
            }
            default->{
                throw new BussinessException(ErrorCode.PARAMS_ERROR);
            }

        }
    }

    private File generateAndSaveHtmlCode(String userMessage) {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode(userMessage);
        return CodeFileSaver.saveHtmlCodeResult(result);
    }

    private File generateAndSaveMultiFileCode(String userMessage) {
        MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode(userMessage);
        return CodeFileSaver.saveMultiFileCodeResult(result);
    }

    /**
     * 通用流式代码处理方法
     *
     * @param codeStream  代码流
     * @param codeGenType 代码生成类型
     * @param appId       应用 ID
     * @return 流式响应
     */
    private Flux<String> processCodeStream(Flux<String> codeStream,CodeGenTypeEnum codeGenType,Long appId){
        StringBuilder builder = new StringBuilder();
        return codeStream
                .doOnNext(chunk -> {
                    builder.append(chunk);
                })
                .doOnComplete(() -> {
                    try {
                        String completeCode = builder.toString();
                        Object parser = CodeParserExecutor.executeParser(completeCode, codeGenType);
                        File file = CodeFileSaverExecutor.executeSaver(completeCode, codeGenType, appId);
                        log.info("保存的目录为：{}", file.getAbsolutePath());
                    } catch (Exception e) {
                        log.error("保存代码出错：{}", e.getMessage());
                    }
                });
    }
}
