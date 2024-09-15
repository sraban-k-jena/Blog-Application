package org.jt.tech_trekker.service.impl;

import java.util.List;

import org.jt.tech_trekker.constant.BlogCatagory;
import org.jt.tech_trekker.model.Blog;
import org.jt.tech_trekker.model.WriterInfo;
import org.jt.tech_trekker.repository.BlogRepository;
import org.jt.tech_trekker.repository.WriterInfoRepository;
import org.jt.tech_trekker.service.FileService;
import org.jt.tech_trekker.service.TechTrekkerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TechTrekkerServiceImpl implements TechTrekkerService {
    private final WriterInfoRepository writerRepository;
    private final BlogRepository blogRepository;
    private final FileService fileService;
    private final PasswordEncoder encoder;

    @Override
    public void createWriter(WriterInfo info) {
        var bcrypt = encoder.encode(info.getPassword());
        info.setPassword(bcrypt);
        writerRepository.save(info);
    }

    @Override
    public void createBlog(Blog blog, MultipartFile file) {
        String fileName = fileService.uploadBlogBanner(file);
        blog.setBanner(fileName);
        blogRepository.save(blog);
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getBlogById(String id) {
        return blogRepository.findById(id).orElseThrow();
    }

    @Override
    public byte[] getBanner(String blogId) {
        var blog = getBlogById(blogId);
        var banner = blog.getBanner();
        return fileService.getBlogBanner(banner);
    }

    @Override
    public List<Blog> getTop5Blogs() {
        return blogRepository.findTop5ByOrderByTitle();
    }

    @Override
    public List<Blog> limitedBlogOfCatagory(BlogCatagory catagory, int limit) {

        return blogRepository.findByCatagory(catagory, PageRequest.of(0, limit));
    }

    @Override
    public List<Blog> limitedBlogOfCatagory(BlogCatagory catagory, int page, int limit) {
        return blogRepository.findByCatagory(catagory, PageRequest.of(page - 1, limit));
    }

    @Override
    public int countBlogs(BlogCatagory blogCatagory) {
        return (int) blogRepository.countByCatagory(blogCatagory);
    }

    @Override
    public List<Blog> blogsOfCatagoryAndTitle(BlogCatagory catagory, String title, int page, int limit) {
        return blogRepository.findByCatagoryAndTitleContaining(catagory, title, PageRequest.of(page - 1, limit));
    }

    @Override
    public int countBlogsOfTitle(BlogCatagory blogCatagory, String title) {
        return (int) blogRepository.countByCatagoryAndTitleContaining(blogCatagory, title);
    }

}
