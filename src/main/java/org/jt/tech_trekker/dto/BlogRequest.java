package org.jt.tech_trekker.dto;

import org.jt.tech_trekker.constant.BlogCatagory;
import org.springframework.web.multipart.MultipartFile;

public record BlogRequest(
                String title,
                String description,
                BlogCatagory catagory,
                MultipartFile banner) {

}
