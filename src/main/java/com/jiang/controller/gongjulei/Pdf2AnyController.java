package com.jiang.controller.gongjulei;

import com.jiang.controller.dto.Pdf2AnyDTO;
import com.jiang.service.Pdf2AnyService;
import com.jiang.service.vo.BaseVO;
import com.jiang.service.vo.Pdf2AnyVO;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;

/**
 * @Description TODO
 * @Created jiang
 */
@Api(tags = "PDF转其它格式")
@Controller
public class Pdf2AnyController {
    @Autowired
    private Pdf2AnyService pdf2AnyService;
    // 下载文件路径
    private String docPath = "./doc/";


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/gj")
    public String pdf2word() {
        return "gj";
    }


    // 上传文件会自动绑定到MultipartFile中
    @PostMapping(value = "/gj/pdf2word")
    public String upload(HttpServletRequest request,
                         @ModelAttribute Pdf2AnyDTO pdf2AnyDTO,
                         Model model) throws Exception {
        // 如果文件不为空，写入上传路径
        if (!pdf2AnyDTO.getHeadPortrait().isEmpty()) {
            String fileName = pdf2AnyDTO.getHeadPortrait().getOriginalFilename();
            if (!fileName.endsWith(".pdf")) {
                BaseVO error = BaseVO.error("非pdf文件!");
                model.addAttribute("error", error);
                return "error";
            }
            long size = pdf2AnyDTO.getHeadPortrait().getSize();
            if (size >= 1024 * 1024 * 1) {
                BaseVO error = BaseVO.error("文件大于1M了!");
                model.addAttribute("error", error);
                return "error";
            }
            Pdf2AnyVO pdf2AnyVO = pdf2AnyService.pdf2word(pdf2AnyDTO.getHeadPortrait());
            model.addAttribute("ok", pdf2AnyVO);
            return "success";
        }
        return "error";
    }


    @RequestMapping(value = "/gj/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam("filename") String filename,
                                           @RequestHeader("User-Agent") String userAgent) throws Exception {


        // 构建File
        File file = new File(docPath + filename);
        // ok表示Http协议中的状态 200
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        // 内容长度
        builder.contentLength(file.length());
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用URLDecoder.decode对文件名进行解码
        filename = URLEncoder.encode(filename, "UTF-8");
        // 设置实际的响应文件名，告诉浏览器文件要用于【下载】、【保存】attachment 以附件形式
        // 不同的浏览器，处理方式不同，要根据浏览器版本进行区别判断
        if (userAgent.indexOf("MSIE") > 0) {
            // 如果是IE，只需要用UTF-8字符集进行URL编码即可
            builder.header("Content-Disposition", "attachment; filename=" + filename);
        } else {
            // 而FireFox、Chrome等浏览器，则需要说明编码的字符集
            // 注意filename后面有个*号，在UTF-8后面有两个单引号！
            builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(file));
    }


}
