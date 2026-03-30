package com.cesco.sys.common;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: navy
 * @Date: 2022/11/21
 * @Description:
 **/
public class FileDown {
    private static final Logger logger = LoggerFactory.getLogger(FileDown.class);

    private static MimetypesFileTypeMap mimetypesFileTypeMap;

    /**
     * http fileStream download
     *
     * @param urlStr
     * @param request
     * @param response
     * @param fileName
     * @return
     */
    public static void downloadHttpFile(String urlStr, HttpServletRequest request, HttpServletResponse response, String fileName) {
        ServletOutputStream out = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //fileStream 받아오기
            inputStream = conn.getInputStream();
            //리스트 생성
            byte[] getData = FileDown.inputStreamToByte(inputStream);
            // 다운로드
            out = response.getOutputStream();
            long contentLength = getData.length;
            FileDown.setResponse(fileName, contentLength, request, response);
            out.write(getData);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException("다운로드 실페!");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //File、FileInputStream
    public static byte[] inputStreamToByte(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("문서 전환 실페!");
        }
    }

    /**
     * 다운 fileStream
     *
     * @param file
     * @param request
     * @param response
     * @param fileName
     * @return
     */
    public static void downloadFile(File file, HttpServletRequest request, HttpServletResponse response, String fileName) {
        if (file != null && file.exists() && file.length() > 0L) {
            try {
                RandomAccessFile randomFile = new RandomAccessFile(file, "r");
                Throwable var5 = null;

                Object var54;
                try {
                    ServletOutputStream out = response.getOutputStream();
                    Throwable var7 = null;

                    try {
                        long contentLength = randomFile.length();
                        String range = request.getHeader("Range");
                        long start = 0L;
                        long end = 0L;
                        if (range != null && range.startsWith("bytes=")) {
                            String[] values = range.split("=")[1].split("-");
                            start = Long.parseLong(values[0]);
                            if (values.length > 1) {
                                end = Long.parseLong(values[1]);
                            }
                        }

                        int requestSize;
                        if (end != 0L && end > start) {
                            requestSize = Long.valueOf(end - start + 1L).intValue();
                        } else {
                            requestSize = 2147483647;
                        }

                        FileDown.setResponse(fileName, contentLength, request, response);

                        randomFile.seek(start);

                        byte[] buffer;
                        for (int needSize = requestSize; needSize > 0; needSize -= buffer.length) {
                            buffer = new byte[1024];
                            int len = randomFile.read(buffer);
                            if (needSize < buffer.length) {
                                out.write(buffer, 0, needSize);
                            } else {
                                out.write(buffer, 0, len);
                                if (len < buffer.length) {
                                    break;
                                }
                            }
                        }

                        out.flush();
                        var54 = null;
                    } catch (Throwable var47) {
                        var7 = var47;
                        throw var47;
                    } finally {
                        if (out != null) {
                            if (var7 != null) {
                                try {
                                    out.close();
                                } catch (Throwable var46) {
                                    var7.addSuppressed(var46);
                                }
                            } else {
                                out.close();
                            }
                        }

                    }
                } catch (Throwable var49) {
                    var5 = var49;
                    throw var49;
                } finally {
                    if (randomFile != null) {
                        if (var5 != null) {
                            try {
                                randomFile.close();
                            } catch (Throwable var45) {
                                var5.addSuppressed(var45);
                            }
                        } else {
                            randomFile.close();
                        }
                    }

                }
            } catch (IOException var51) {
                logger.debug(var51.getMessage(), var51);
                throw new RuntimeException(var51.getMessage());
            }
        } else {
            throw new RuntimeException("文件为空或不存在！");
        }
    }

    /**
     * @param fileName
     * @param contentLength
     * @param request
     * @param response
     * @return
     */
    public static void setResponse(String fileName, long contentLength, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType(FileDown.getContentType("0.jpg"));
            boolean isPreview = "preview".equalsIgnoreCase(request.getParameter("source"));
            response.addHeader("Content-Disposition", (!isPreview ? "attachment; " : "") + "filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Accept-Ranges", "bytes");

            String range = request.getHeader("Range");
            if (range == null) {
                response.setHeader("Content-Length", String.valueOf(contentLength));
            } else {
                response.setStatus(206);
                long requestStart = 0L;
                long requestEnd = 0L;
                String[] ranges = range.split("=");
                if (ranges.length > 1) {
                    String[] rangeDatas = ranges[1].split("-");
                    requestStart = Long.parseLong(rangeDatas[0]);
                    if (rangeDatas.length > 1) {
                        requestEnd = Long.parseLong(rangeDatas[1]);
                    }
                }

                long length = 0L;
                if (requestEnd > 0L) {
                    length = requestEnd - requestStart + 1L;
                    response.setHeader("Content-Length", String.valueOf(length));
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                } else {
                    length = contentLength - requestStart;
                    response.setHeader("Content-Length", String.valueOf(length));
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1L) + "/" + contentLength);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("response响应失败!");
        }
    }

    public static String getContentType(String fileName) {
        if (mimetypesFileTypeMap == null) {
            mimetypesFileTypeMap = new MimetypesFileTypeMap();
        }

        return mimetypesFileTypeMap.getContentType(fileName);
    }

    /**
     * 디스크에 자원문서여러개 압축해서 보내기
     * @param pathList
     * @param request
     * @param response
     * @throws Exception
     */
    public static void zipDirFileToFile(List<Map<String, String>> pathList, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 设置response参数并且获取ServletOutputStream
            ZipArchiveOutputStream zous = getServletOutputStream(response);

            for (Map<String, String> map : pathList) {
                String fileName = map.get("name");
                File file = new File(map.get("path"));
                InputStream inputStream = new FileInputStream(file);
                setByteArrayOutputStream(fileName, inputStream, zous);
            }
            zous.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 인터넷 url자원문서여러개 압축해서 보내기
     * @param pathList
     * @param request
     * @param response
     * @throws Exception
     */
    public static void zipUrlToFile(List<Map<String, String>> pathList, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // response ServletOutputStream
            ZipArchiveOutputStream zous = getServletOutputStream(response);

            for (Map<String, String> map : pathList) {
                String fileName = map.get("name");
                InputStream inputStream = getInputStreamFromUrl(map.get("path"));
                setByteArrayOutputStream(fileName, inputStream, zous);
            }
            zous.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * response ServletOutputStream
     * @param response
     */
    private static ZipArchiveOutputStream getServletOutputStream(HttpServletResponse response) throws Exception {

        String outputFileName = "문서" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
        response.reset();
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        ServletOutputStream out = response.getOutputStream();

        ZipArchiveOutputStream zous = new ZipArchiveOutputStream(out);
        zous.setUseZip64(Zip64Mode.AsNeeded);
        return zous;
    }

    private static void setByteArrayOutputStream(String fileName, InputStream inputStream, ZipArchiveOutputStream zous) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        byte[] bytes = baos.toByteArray();

        //파일명 설정
        ArchiveEntry entry = new ZipArchiveEntry(fileName);
        zous.putArchiveEntry(entry);
        zous.write(bytes);
        zous.closeArchiveEntry();
        baos.close();
    }

    /**
     *  인터넷 주소 통해 파일 InputStream 얻어오기
     *
     * @param path 주소
     * @return
     */
    private static InputStream getInputStreamFromUrl(String path) {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
