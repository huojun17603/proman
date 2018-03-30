package com.ich.proman.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketController {


    @RequestMapping(value="/pushVideoListToWeb",method= RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public Map<String,Object> pushVideoListToWeb(@RequestBody Map<String,Object> param) {
        Map<String,Object> result =new HashMap<String,Object>();

        return result;
    }
}
