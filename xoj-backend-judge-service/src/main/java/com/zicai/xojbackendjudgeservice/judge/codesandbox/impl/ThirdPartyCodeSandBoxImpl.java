package com.zicai.xojbackendjudgeservice.judge.codesandbox.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.zicai.xojbackendmodel.model.codesandbox.SubmissionRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Component
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {

    private static final String API_URL = "http://81.71.102.131:2358/submissions/?base64_encoded=false&wait=true";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        if (executeCodeRequest == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "请求参数为空");
        }

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String sourceCode = executeCodeRequest.getCode();
            int languageId = 62;  // jdk 13
            List<String> inputList = executeCodeRequest.getInputList();
            List<String> outputList = new ArrayList<>();

            for (String stdin : inputList) {
                SubmissionRequest submissionRequest = new SubmissionRequest(sourceCode, languageId, stdin);
                String jsonRequest = objectMapper.writeValueAsString(submissionRequest);

                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(API_URL);
                httpPost.setHeader("Content-Type", "application/json");

                StringEntity entity = new StringEntity(jsonRequest);
                httpPost.setEntity(entity);

                CloseableHttpResponse response = httpClient.execute(httpPost);
                HttpEntity responseEntity = response.getEntity();
                String jsonResponse = EntityUtils.toString(responseEntity);

                log.info("Response: " + jsonResponse);

                if (response.getStatusLine().getStatusCode() == 200) {
                    // 解析JSON响应并添加到输出列表
                    ExecuteCodeResponse responseObj = objectMapper.readValue(jsonResponse, ExecuteCodeResponse.class);
//                    outputList.add(responseObj.getOutput());
                } else {
                    log.error("Error: Received status code " + response.getStatusLine().getStatusCode() + " with message " + jsonResponse);
//                    throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "Judge0 server error");
                }

                response.close();
                httpClient.close();
            }

            executeCodeResponse.setOutputList(outputList);

        } catch (JsonProcessingException e) {
            log.error("JSON processing error", e);
//            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON processing error", e);
        } catch (IOException e) {
            log.error("I/O error occurred while executing code", e);
//            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "I/O error", e);
        } catch (Exception e) {
            log.error("Exception occurred while executing code", e);
//            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "Execution error", e);
        }
        return executeCodeResponse;
    }
}
