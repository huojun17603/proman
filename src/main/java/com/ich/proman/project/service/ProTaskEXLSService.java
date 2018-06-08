package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ProTaskEXLSService {

    HttpResponse executeUpload(String projectid, MultipartFile multipartFile);

    void executedown(String projectid, String catalogids, String terminals, HttpServletResponse response);
}
