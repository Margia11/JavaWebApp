package it.plantict.officeolympics.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @RequestMapping(method = RequestMethod.GET, path = "/dummy")
    public String dummy() throws Exception {
        return "ciaooooooooo";
    }

}
