package org.jt.tech_trekker.dto;

import org.springframework.web.multipart.MultipartFile;

public record WriterRequest(

                String WriterName,
                String email,
                String password,
                String WriterProfession,
                MultipartFile profilePicture) {

}
