package com.komehyo.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


 /* 文件压缩、解压工具类*/
public class ZipUtils {
    private static final int BUFFER_SIZE = 2 * 1024;
    /**
     * 是否保留原来的目录结构
     * true:  保留目录结构;
     * false: 所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    private static final boolean KeepDirStructure = true;
    private static final boolean isDelSrcFile = false;


    /**
     * 压缩成ZIP
     * @param srcDir         压缩 文件/文件夹 路径
     * @param outPathFile    压缩 文件/文件夹 输出路径+文件名 D:/xx.zip
     * @param isDelSrcFile   是否删除原文件: 压缩前文件
     */
    public static void toZip(String srcDir, String outPathFile,boolean isDelSrcFile) throws Exception {
        long start = System.currentTimeMillis();
        FileOutputStream out = null;
        ZipOutputStream zos = null;
        try {
            out = new FileOutputStream(new File(outPathFile));
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            if(!sourceFile.exists()){
                throw new Exception("需压缩文件或者文件夹不存在");
            }
            compress(sourceFile, zos, sourceFile.getName());
            if(isDelSrcFile){
                delDir(srcDir);
            }
            System.out.println("原文件:"+srcDir+". 压缩到:"+outPathFile+"完成. 是否删除原文件:"+isDelSrcFile+". 耗时:"+(System.currentTimeMillis()-start)+"ms. ");
        } catch (Exception e) {
            System.out.println("zip error from ZipUtils: {}. "+e.getMessage());
            throw new Exception("zip error from ZipUtils");
        } finally {
            try {
                if (zos != null) {zos.close();}
                if (out != null) {out.close();}
            } catch (Exception e) {}
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos zip输出流
     * @param name 压缩后的名称
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name)
            throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName());
                    } else {
                        compress(file, zos, file.getName());
                    }
                }
            }
        }
    }

    // 删除文件或文件夹以及文件夹下所有文件
    public static void delDir(String dirPath) throws IOException {
        System.out.println("删除文件开始:"+dirPath+".");
        long start = System.currentTimeMillis();
        try{
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                return;
            }
            if (dirFile.isFile()) {
                dirFile.delete();
                return;
            }
            File[] files = dirFile.listFiles();
            if(files==null){
                return;
            }
            for (int i = 0; i < files.length; i++) {
                delDir(files[i].toString());
            }
            dirFile.delete();
            System.out.println("删除文件:"+dirPath+". 耗时:"+(System.currentTimeMillis()-start)+"ms. ");
        }catch(Exception e){
            System.out.println("删除文件:"+dirPath+". 异常:"+e+". 耗时:"+(System.currentTimeMillis()-start)+"ms. ");
            throw new IOException("删除文件异常.");
        }
    }


     public static void main(String[] args) {
         try {
             toZip("D:\\OneDrive\\图片\\表情\\喜逼", "D:\\OneDrive\\图片\\表情\\test.zip",isDelSrcFile);
//            unZipFiles("D:\\\\apache-maven-3.5.3\\\\maven中央仓库-jar下载.zip","D:\\apache-maven-3.5.3\\maven中央仓库-jar下载");
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}