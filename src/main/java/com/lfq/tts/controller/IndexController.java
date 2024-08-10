package com.lfq.tts.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @作者 lfq
 * @DATE 2024/8/10  22:24
 * current year
 **/
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
