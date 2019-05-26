package com.service.antenna.services;

import com.service.antenna.domain.Task;
import org.apache.commons.io.IOUtils;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Service
public class WordService {
    private final String BORDER_COLOR = "DEE2E6";
    private final String BACKGROUND_COLOR = "F2F2F2";
    private ObjectFactory factory = new ObjectFactory();

    public void createFile(Set<Task> tasks, HttpServletResponse response) throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
        mainDocumentPart.addParagraphOfText("Welcome To Baeldung");

        int writableWidthTwips = wordPackage.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();
        int columnNumber = 2;

        Tbl tbl = TblFactory.createTable(3, 2, writableWidthTwips / columnNumber);
        setTableBorder(tbl);
        List<Object> rows = tbl.getContent();
        for (int i = 0; i < rows.size(); i++) {
            Tr tr = (Tr) rows.get(i);
            List<Object> cells = tr.getContent();
            for (Object cell : cells) {
                Tc td = (Tc) cell;
                if (i % 2 == 0) {
                    setColumnBackground(td);
                }
                td.getContent().add(setText("Foo"));
            }
        }
        mainDocumentPart.getContent().add(tbl);

        File file = new File("welcome.docx");
        wordPackage.save(file);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private P setText(String text) {
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue(text);
        r.getContent().add(t);
        p.getContent().add(r);
        return p;
    }

    private void setTableBorder(Tbl tbl) {
        tbl.setTblPr(new TblPr());

        CTBorder border = new CTBorder();
        border.setColor(BORDER_COLOR);
        border.setSz(new BigInteger("10"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);

        tbl.getTblPr().setTblBorders(borders);
    }

    private void setColumnBackground(Tc td) {
        td.setTcPr(new TcPr());
        CTShd shd = factory.createCTShd();
        shd.setVal(STShd.CLEAR);
        shd.setColor("auto");
        shd.setFill(BACKGROUND_COLOR);
        shd.setThemeFill(STThemeColor.ACCENT_1);
        shd.setThemeFillTint("66");
        td.getTcPr().setShd(shd);
    }
}
