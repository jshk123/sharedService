package com.jiang.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description TODO
 * @Created jiang
 */
@Data
public class Pdf2AnyDTO {
    private static final long serialVersionUID = 1L;
    private String description;
    // 对应上传的headPortrait，类型为MultipartFile，上传文件会自动绑定到image属性当中
    private MultipartFile headPortrait;
}
