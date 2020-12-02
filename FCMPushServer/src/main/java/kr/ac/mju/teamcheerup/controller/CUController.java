package kr.ac.mju.teamcheerup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CUController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
