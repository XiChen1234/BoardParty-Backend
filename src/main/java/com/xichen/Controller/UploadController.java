package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Common.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * 文件上传模块
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    // 文件上传目录
    @Value("${file.upload-path}")
    private String UPLOAD_DIR;
    // 图床前缀
    @Value("${file.image-prefix}")
    private String PREFIX;
    // 图片后缀集合
    private static final Set<String> EXTENSION_SET = new HashSet<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp", ".svg", ".ico", ".tiff", ".tif"));


    /**
     * 上传图片文件
     *
     * @param file 文件对象
     * @return 后端存储到服务器中的文件路径+文件名
     */
    @PostMapping("/image")
    public CommonResponse<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // 1. 文件校验检查
        if (file == null || file.isEmpty()) {
            return CommonResponse.fail(ResponseCode.FILE_UPLOAD_FAILED, "无法上传空文件");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return CommonResponse.fail(ResponseCode.FILE_UPLOAD_FAILED, "无法上传非图片文件");
        }
        String filename = file.getOriginalFilename();
        String extension = "";
        if (filename != null) {
            int dotIndex = filename.lastIndexOf('.');
            if (dotIndex <= 0) {
                return CommonResponse.fail(ResponseCode.FILE_UPLOAD_FAILED, "无法上传非图片文件");
            }
            extension = filename.toLowerCase().substring(dotIndex);
            if (!EXTENSION_SET.contains(extension)) {
                return CommonResponse.fail(ResponseCode.FILE_UPLOAD_FAILED, "无法上传非图片文件");
            }
        }

        // 2. 处理文件：UUID
        String newFilename = UUID.randomUUID() + "_" + System.currentTimeMillis() + extension;

        // 3. 检查服务器环境
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean isCreated = uploadDir.mkdirs();
            if (!isCreated) {
                return CommonResponse.fail(ResponseCode.SYSTEM_ERROR, "无法创建上传目录");
            }
        }

        // 4. 文件写入
        File destFile = new File(uploadDir, newFilename);
        try {
            file.transferTo(destFile);
        } catch (Exception e) {
            return CommonResponse.fail(ResponseCode.SYSTEM_ERROR, "文件上传失败");
        }

        String returnFilePath = PREFIX + newFilename;
        return CommonResponse.success(returnFilePath);
    }
}
