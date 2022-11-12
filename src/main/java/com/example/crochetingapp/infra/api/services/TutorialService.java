package com.example.crochetingapp.infra.api.services;

import com.example.crochetingapp.core.Step;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.infra.jpa.JpaTutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
public class TutorialService {
    @Autowired
    JpaTutorialRepository tutorialRepository;

    public Tutorial getTutorialByName(String tutorialName) {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutorialName().equals(tutorialName)) {
                return tutorial;
            }
        }
        return null;
    }

    @RolesAllowed("USER")
    public List<Tutorial> getTutorials() {
        return tutorialRepository.findAll();
    }

    public Step getStep(String tutorialName, int stepNumber) {
        Tutorial tutorial = getTutorialByName(tutorialName);
        if (tutorial == null || stepNumber == 0) {
            return null;
        }
        if (tutorial.getSteps().size() >= stepNumber) {
            return tutorial.getSteps().get(stepNumber - 1);
        }
        return null;
    }
}
