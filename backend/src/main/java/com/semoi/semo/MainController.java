package com.semoi.semo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 주현우
@Controller
@ResponseBody
public class MainController {
    @GetMapping("/main")
    public String index() {
        return "안녕하세요 새모 : 새싹모임에 오신 것을 환영합니다.";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }
}