package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Position;
import edu.xidian.sselab.cloudcourse.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

@RequestMapping("/track")
public class TrackController {

    private final PositionRepository repository;

    @Autowired
    public TrackController(PositionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "轨迹重现表格");
        model.addAttribute("condition", new Position());
        return "track";
    }

    @PostMapping("")
    public String post(Model model, Position position) {
        List<Position> PositionList = repository.findAllByPosition(position);
        model.addAttribute("PositionList", PositionList);
        model.addAttribute("title", "轨迹重现表格");
        model.addAttribute("condition", position);
        return "track";
    }


}
