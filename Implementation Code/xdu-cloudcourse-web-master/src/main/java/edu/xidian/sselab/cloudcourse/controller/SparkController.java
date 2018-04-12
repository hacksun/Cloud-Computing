package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Data;
import edu.xidian.sselab.cloudcourse.domain.Record;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/spark")
public class SparkController {

    @GetMapping("")
    public String spark(Model model) {
        model.addAttribute("title", "相遇次数统计示例");
        //model.addAttribute("hint", "此为加分项，动手能力强的同学可以尝试完成。");
        return "spark";
    }
    @PostMapping("")
    public String post(Model model, Record record) {
        List<Data> recordList = new ArrayList<>();
        for(Data data:Data.myData)
            recordList.add(data);
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "相遇次数统计示例");
        return "spark";
    }

}

