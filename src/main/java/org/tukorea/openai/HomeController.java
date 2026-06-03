package org.tukorea.openai;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Locale locale, Model model) {
        model.addAttribute("title", "스프링 AI 프로그래밍");
        return "home";
    }
}