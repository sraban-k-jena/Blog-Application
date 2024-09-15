package org.jt.tech_trekker.controller;

import static org.jt.tech_trekker.mapper.BlogMapper.*;
import java.util.*;
import org.jt.tech_trekker.constant.BlogCatagory;
import org.jt.tech_trekker.dto.HomePageResponse;
import org.jt.tech_trekker.dto.ViewAllResponse;
import org.jt.tech_trekker.dto.WriterRequest;
import org.jt.tech_trekker.mapper.BlogMapper;
import org.jt.tech_trekker.mapper.WriterMapper;
import org.jt.tech_trekker.model.Blog;
import org.jt.tech_trekker.service.TechTrekkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tech-trekker")
public class BlogController {
    private final TechTrekkerService service;

    @GetMapping({ "/home" })
    public String home(Model model) {

        var recentBlogs = convertBlogsToBasic(service.getTop5Blogs());
        var frontendBlogs = convertBlogsToBasic(service.limitedBlogOfCatagory(BlogCatagory.FRONTEND, 8));
        var backendBlogs = convertBlogsToBasic(service.limitedBlogOfCatagory(BlogCatagory.BACKEND, 6));
        var databaseBlogs = convertBlogsToBasic(service.limitedBlogOfCatagory(BlogCatagory.DATABASE, 6));

        var homePageResponse = new HomePageResponse();
        homePageResponse.setRecentBlogs(recentBlogs);
        homePageResponse.setFrontendBlogs(frontendBlogs);
        homePageResponse.setBackendBlogs(backendBlogs);
        homePageResponse.setDatabaseBlogs(databaseBlogs);

        model.addAttribute("response", homePageResponse);
        return "home";
    }

    @GetMapping("/blog-details")
    public String blogDetails(@RequestParam String id, Model model) {

        var blog = service.getBlogById(id);
        model.addAttribute("blog", BlogMapper.convertBlogToResponse(blog));
        return "blog-details";
    }

    @GetMapping("/view-all")
    public String viewAll(@RequestParam BlogCatagory catagory,
            @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false) String searchTerm,
            Model model) {
        final int limit = 10;
        List<Blog> blogs;
        int totalBlogs;
        if (searchTerm == null) {
            blogs = service.limitedBlogOfCatagory(catagory, pageNum, limit);
            totalBlogs = service.countBlogs(catagory);
        } else {

            blogs = service.blogsOfCatagoryAndTitle(catagory, searchTerm, pageNum, limit);
            totalBlogs = service.countBlogsOfTitle(catagory, searchTerm);
        }

        var blogResponse = blogs.stream().map(BlogMapper::convertBlogToResponse).toList();

        var viewAllResponse = new ViewAllResponse();
        viewAllResponse.setBlogs(blogResponse);
        viewAllResponse.setCurrentPage(pageNum);
        viewAllResponse.setTotalPage(getTotalPage(totalBlogs, limit));

        model.addAttribute("response", viewAllResponse);

        return "view-all";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute WriterRequest request) {

        var writerInfo = WriterMapper.convertRequest(request);
        service.createWriter(writerInfo);
        return "redirect:/tech-trekker/home";
    }

    @PostMapping("/view-all-search")
    public String viewAllSearch(@RequestParam BlogCatagory catagory, @RequestParam String searchTerm) {

        if (searchTerm == null || searchTerm.isEmpty() || searchTerm.isBlank()) {

            return "redirect:/tech-trekker/view-all?catagory=" + catagory;
        }

        return "redirect:/tech-trekker/view-all?catagory=" + catagory + "&searchTerm=" + searchTerm;
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    private int getTotalPage(int totalBlogs, int limit) {

        return (totalBlogs % limit == 0) ? (totalBlogs / limit) : (totalBlogs / limit) + 1;

    }
}
