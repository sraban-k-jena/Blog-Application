package org.jt.tech_trekker.service;

import java.util.*;

import org.jt.tech_trekker.constant.BlogCatagory;
import org.jt.tech_trekker.model.Blog;
import org.jt.tech_trekker.model.WriterInfo;
import org.springframework.web.multipart.MultipartFile;

public interface TechTrekkerService {
    void createWriter(WriterInfo info);

    void createBlog(Blog blog, MultipartFile file);

    List<Blog> getBlogs();

    Blog getBlogById(String id);

    byte[] getBanner(String blogId);

    List<Blog> getTop5Blogs();

    List<Blog> limitedBlogOfCatagory(BlogCatagory catagory, int limit);

    List<Blog> limitedBlogOfCatagory(BlogCatagory catagory, int page, int limit);

    int countBlogs(BlogCatagory blogCatagory);

    List<Blog> blogsOfCatagoryAndTitle(BlogCatagory catagory, String title, int page, int limit);

    int countBlogsOfTitle(BlogCatagory blogCatagory, String title);
}
