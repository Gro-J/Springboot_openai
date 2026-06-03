package org.tukorea.openai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/aivision")
public class AiVisionController {

    @Autowired
    private AiVisionService aiVisionService;

    @GetMapping("/")
    public String visionPage() {
        return "vision";
    }

    @PostMapping(
            value = "/analysis",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"
    )
    @ResponseBody
    public String imageAnalysis(
            @RequestParam("message") String question,
            @RequestParam(value = "image", required = false) MultipartFile attach
    ) throws IOException {

        if (attach == null || attach.isEmpty() || attach.getContentType() == null
                || !attach.getContentType().startsWith("image/")) {
            return "올바른 이미지 파일을 올려주세요.";
        }

        if (question == null || question.trim().isEmpty()) {
            question = "이 이미지에 대해 상세히 설명해줘.";
        }

        return aiVisionService.imageAnalysis(
                question,
                attach.getContentType(),
                attach.getBytes()
        );
    }
}