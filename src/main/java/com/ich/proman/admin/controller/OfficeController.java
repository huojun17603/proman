package com.ich.proman.admin.controller;

import com.ich.core.http.controller.CoreController;
import com.ich.proman.base.WordToHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.file.Paths;

@Controller
public class OfficeController extends CoreController {

    @Value("${UPLOAD_ROOT}")
    private String UPLOAD_ROOT;

    private final ResourceLoader resourceLoader;

    @Autowired
    public OfficeController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(method = RequestMethod.GET, value = "file/{times}/{filename}/office.html")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String times, @PathVariable String filename) {
        try {
            File html = new File(UPLOAD_ROOT + File.separator + "file" + File.separator + times + File.separator + filename + File.separator + filename + ".html");
            if(!html.exists()){
                File file = new File(UPLOAD_ROOT + File.separator + "file" + File.separator + times + File.separator + filename + ".doc");
                if(!file.exists()){
                    return ResponseEntity.notFound().build();
                }
                WordToHtml.PoiWord03ToHtml(UPLOAD_ROOT + File.separator + "file" + File.separator + times, filename,"doc");
            }
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(UPLOAD_ROOT + File.separator + "file" + File.separator + times + File.separator + filename, filename + ".html").toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } catch (Throwable throwable) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "file/{times}/{filename}/{img:.+}")
    @ResponseBody
    public ResponseEntity<?> getPicture(@PathVariable String times,@PathVariable String filename,@PathVariable String img) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(UPLOAD_ROOT  + File.separator + "file" + File.separator + times + File.separator + filename, img).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
