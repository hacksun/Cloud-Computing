package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.MeetRecord;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.repository.MeetRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/meetcount")
public class meetcountController {

    private final MeetRecordRepository repository;

    @Autowired
    public meetcountController(MeetRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "相遇次数统计");
        model.addAttribute("condition", new Record());
        return "meetcount";
    }

    @PostMapping("")
    public String post(Model model, MeetRecord meetrecord) {
        List<MeetRecord> recordList = repository.findAllByMRecord(meetrecord);
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "相遇次数统计");
        model.addAttribute("condition", meetrecord);
        return "meetcount";
    }

}
