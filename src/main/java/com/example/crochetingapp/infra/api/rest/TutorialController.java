package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.Step;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.infra.api.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tutorials")
public class TutorialController {
    @Autowired
    TutorialService tutorialService;

    @GetMapping("/")
    public List<Tutorial> getTutorials() {
        return tutorialService.getTutorials();
    }

    @GetMapping("/{tutorialName}")
    public Tutorial getTutorial(@Valid @PathVariable("tutorialName") String tutorialName) {
        return tutorialService.getTutorialByName(tutorialName);
    }

    @GetMapping("/{tutorialName}/{stepNumber}")
    public Step getTutorialStep(@Valid @PathVariable("tutorialName") String tutorialName, @Valid @PathVariable("stepNumber") int stepNumber) {
        return tutorialService.getStep(tutorialName, stepNumber);
    }
}
