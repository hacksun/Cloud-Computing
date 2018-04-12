package edu.xidian.sselab.cloudcourse.controller;

import com.google.gson.Gson;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/filter")
public class FilterController {

    private final RecordRepository repository;

    @Autowired
    public FilterController(RecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "异常数据过滤");
        model.addAttribute("condition", new Record());
        return "record";
    }
    
    @PostMapping("")
    public String post(Model model, Record record) {

          String  redisHost = "192.168.31.10";//192.168.31.71
          int  redisPort = 6379;//端口号
          Jedis jedis = new Jedis(redisHost,redisPort,0);
        List list = new ArrayList(50000);
        Record record1;
        Gson gson = new Gson();
        System.out.println("stary");
        Set<String> records =jedis.smembers("filter");
        System.out.println("end");
        for(String jsonString :records ){
            System.out.println(jsonString);
            record1 = gson.fromJson(jsonString,Record.class);
            System.out.println(record1.getAddress());
            list.add(record1);
        }
        model.addAttribute("recordList", list);
        model.addAttribute("title", "异常数据过滤");
        return "record";
    }
    
}
