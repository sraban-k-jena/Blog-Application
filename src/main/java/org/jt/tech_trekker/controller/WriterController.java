package org.jt.tech_trekker.controller;

import org.jt.tech_trekker.dto.BlogRequest;
import org.jt.tech_trekker.mapper.BlogMapper;
import org.jt.tech_trekker.service.TechTrekkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/writter")
public class WriterController {

    private final TechTrekkerService service;

    @GetMapping("/create-blog")
    public String createBlog() {
        return "create-blog";
    }

    @PostMapping("/create-blog")
    public String addBlog(@ModelAttribute BlogRequest request) {

        var blog = BlogMapper.convertRequest(request);
        service.createBlog(blog, request.banner());
        return "redirect:/tech-trekker/home";
    }
}
