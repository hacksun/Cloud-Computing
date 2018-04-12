package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.repository.KafkaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaRecordRepository repository;
    @Autowired
    public KafkaController(KafkaRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "kafka过滤");
        model.addAttribute("condition", new Record());
        return "kafka";
    }
    @PostMapping("")
        public String post(Model model, Record record) {
            List<Record> recordList = repository.findAllByRecord(record);
            System.out.println(recordList);
            model.addAttribute("recordList", recordList);
            model.addAttribute("title", "kafka过滤");
            model.addAttribute("condition", record);
            return "kafka";
        }
    }

