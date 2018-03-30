package com.ich.proman.base;

import fr.opensagres.xdocreport.converter.BasicURIResolver;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class WordToHtml {

//    public static void main(String[] args) throws Throwable {
//        PoiWord03ToHtml("C:\\word","软件项目管理","doc");
//        PoiWord03ToHtml("C:\\word","U圈U点城市代理商后台系统需求说明书V0.2","doc");
////        PoiWord07ToHtml("C:\\word","软件项目管理","docx");
//        //PoiWord07ToHtml("C:\\word","UU","docx");
//    }

    public static void PoiWord03ToHtml(String path,String filename,String suffix) throws Throwable{
        InputStream input = new FileInputStream(path + File.separator + filename + "." + suffix);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] bytes, PictureType pictureType, String s, float v, float v1) {
                return s;
            }
        });
        File Outfile = new File(path + File.separator + filename);
        if (!Outfile.exists()) Outfile.mkdirs();
        wordToHtmlConverter.processDocument(wordDocument);
        List pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(path + File.separator + filename +  File.separator
                            + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        FileCopyUtils.copy(content.getBytes(),new File(path, filename + File.separator +filename+".html"));
    }

    public static void PoiWord07ToHtml(String path,String filename,String suffix) throws IOException{
        File file = new File(path + File.separator + filename + "." + suffix);
        if (!file.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
            if (file.getName().endsWith(".docx") || file.getName().endsWith(".DOCX")) {
                File Outfile = new File(path + File.separator + filename);
                if (!Outfile.exists()) Outfile.mkdirs();
                //读取文档内容
                InputStream input = new FileInputStream(file);
                XWPFDocument document = new XWPFDocument(input);

                File imageFolderFile = new File(path + File.separator + filename +  File.separator);
                //加载html页面时图片路径
                XHTMLOptions options = XHTMLOptions.create();
                //图片保存文件夹路径
                options.URIResolver( new FileURIResolver(imageFolderFile ) );
                options.setExtractor(new FileImageExtractor(imageFolderFile));
                options.setIgnoreStylesIfUnused(false);
                //options.setFragment(true);
                OutputStream out = new FileOutputStream(new File(path, filename + File.separator +filename+".html"));
                XHTMLConverter.getInstance().convert(document, out, options);
                out.close();
            } else {
                System.out.println("Enter only MS Office 2007+ files");
            }
        }

    }
}
