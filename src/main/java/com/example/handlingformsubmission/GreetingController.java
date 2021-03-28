package com.example.handlingformsubmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

    private final DynamoDBEnhanced dynamoDBEnhanced;

    private final PublishTextSMS publishTextSMS;

    @Autowired
    public GreetingController(DynamoDBEnhanced dynamoDBEnhanced, PublishTextSMS publishTextSMS) {
        this.dynamoDBEnhanced = dynamoDBEnhanced;
        this.publishTextSMS = publishTextSMS;
    }

    @GetMapping("/")
    public String greetingForm(Model model){
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting){
        dynamoDBEnhanced.injectDynamoItem(greeting);
        publishTextSMS.sendMessage(greeting.getId());
        return "result";
    }
}


