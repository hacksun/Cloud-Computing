package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Position;
import edu.xidian.sselab.cloudcourse.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import edu.xidian.sselab.cloudcourse.domain.Record;

import java.util.ArrayList;
import java.util.List;

@Controller

@RequestMapping("/position")
public class PositionController {

    private final PositionRepository repository;

    @Autowired
    public PositionController(PositionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String position(Model model) {
        model.addAttribute("title", "轨迹重现");
        model.addAttribute("condition", new Position());
        /*
        Record record1 = new Record();
        record1.setLatitude(116.399);
        record1.setLongitude(39.910);

        Record record2 = new Record();
        record2.setLatitude(116.405);
        record2.setLongitude(39.920);


        Record record3 = new Record();
        record3.setLatitude(116.425);
        record3.setLongitude(39.900);

        List<Record> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);

        model.addAttribute("list",recordList);
        model.addAttribute("condition",new Position());*/
        return "position";
    }

/*    @PostMapping("")
    public String post(Model model, Position position) {
        List<Position> PositionList = repository.findAllByPosition(position);
        model.addAttribute("PositionList", PositionList);
        model.addAttribute("title", "轨迹重现");
        model.addAttribute("condition", position);
        return "position";
    }
    */
    @GetMapping("/id")
    @ResponseBody
    public List<Position> track2(@RequestParam("eid") String id, @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time) {

        //若要测试将下面的注释去掉，这是链接数据库的
        Position record1 = new Position();
        record1.setEid(id);
        record1.setTime1(start_time);
        record1.setTime2(end_time);
        System.out.println(start_time+"  "+end_time+"    ");
        List<Position> recordList = repository.findAllByPosition(record1);

        //这是测试的
      /*  System.out.println(id);
        Record record1 = new Record();
        record1.setLatitude(116.399);
        record1.setLongitude(39.910);

        Record record2 = new Record();
        record2.setLatitude(116.405);
        record2.setLongitude(39.920);


        Record record3 = new Record();
        record3.setLatitude(116.425);
        record3.setLongitude(39.900);

        List<Record> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);*/
        return recordList;
    }

}
