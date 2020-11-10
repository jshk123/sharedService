package com.jiang.service;

import com.jiang.service.vo.Pdf2AnyVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Pdf2AnyService {
    Pdf2AnyVO pdf2word(MultipartFile file) throws IOException;
}
