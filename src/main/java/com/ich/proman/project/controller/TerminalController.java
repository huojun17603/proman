package com.ich.proman.project.controller;

import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.Terminal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TerminalController extends PromanController {

    @RequestMapping("project/terminal/list")
    @ResponseBody
    @Link(name = "终端-列表",code = "admin-project-terminal-list",
            level = Link.LEVEL_NONE,parent = "admin-project-center-index")
    public String list(String callback){
        Map<String,Object> model = getSuccessMap();
        List<Terminal> terminals = findTerminals();
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, terminals.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, terminals);
        return callback(callback, model);
    }

    public static List<Terminal> findTerminals() {
        List<Terminal> terminals = new ArrayList();
        terminals.add(new Terminal("0","接口服务"));
        terminals.add(new Terminal("1","PC-HTML"));
        terminals.add(new Terminal("2","PC-WAP"));
        terminals.add(new Terminal("3","Android"));
        terminals.add(new Terminal("4","IOS"));
        terminals.add(new Terminal("5","微信公众号"));
        terminals.add(new Terminal("6","微信小程序"));
        return terminals;
    }

    public static boolean vryTerminals(String name) {
        for(Terminal terminal : findTerminals()){
            if(name.equals(terminal.getName())){
                return true;
            }
        }
        return false;
    }


}
