package com.jiang.service.impl;

import com.jiang.service.Pdf2AnyService;
import com.jiang.service.vo.Pdf2AnyVO;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @Description TODO
 * @Created jiang
 */
@Service
public class Pdf2AnyServiceImpl implements Pdf2AnyService {

    private String docPath = "./doc/";

    @Override
    public Pdf2AnyVO pdf2word(MultipartFile file) throws IOException {
        Pdf2AnyVO result = new Pdf2AnyVO();
        String fileName = file.getOriginalFilename();
        String desPath = fileName.substring(0, fileName.length() - 4) + ".docx";

        File f1 = new File(docPath);
        if (!f1.exists()) f1.mkdirs();

        //加载pdf
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromBytes(file.getBytes());
        //本地文件
        pdf.saveToFile(docPath + desPath, FileFormat.DOCX);

        //重新读取生成的文档
        InputStream is = new FileInputStream(new File(docPath + desPath));
        XWPFDocument document = new XWPFDocument(is);
        //以上Spire.Doc 生成的文件会自带警告信息，这里来删除Spire.Doc 的警告
        document.removeBodyElement(0);
        //输出word内容文件流，新输出路径位置
        OutputStream os = new FileOutputStream(docPath + desPath);
        try {
            document.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }

        result.setFileName(desPath);
        return result;
    }
}
