package com.service.antenna.web;

import com.service.antenna.domain.Task;
import com.service.antenna.payload.ReportRequest;
import com.service.antenna.services.TaskService;
//import com.service.antenna.services.WordService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TaskService taskService;
//    private final WordService wordService;

    @PostMapping
    public ResponseEntity<?> getReport(@RequestBody ReportRequest rep) {
        Set<Task> result = taskService.findAll(rep.getUsers(), rep.getBreakdownType(), rep.getStatus(), rep.getPeriod());
        return ResponseEntity.ok(result);

    }


    @GetMapping("/document")
    public void getWordFile(HttpServletResponse response) throws Docx4JException, IOException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
        mainDocumentPart.addParagraphOfText("Welcome To Baeldung");


//        ObjectFactory factory = new ObjectFactory();
//        P p = setText(factory);
//
//        int writableWidthTwips = wordPackage.getDocumentModel()
//                .getSections().get(0).getPageDimensions().getWritableWidthTwips();
//        int columnNumber = 2;
//
//        Tbl tbl = TblFactory.createTable(3, 2, writableWidthTwips / columnNumber);
//        setTableBorder(tbl);
//        List<Object> rows = tbl.getContent();
//        for (int i = 0; i < rows.size(); i++) {
//            Tr tr = (Tr) rows.get(i);
//            List<Object> cells = tr.getContent();
//            for (Object cell : cells) {
//                Tc td = (Tc) cell;
//                if (i % 2 == 0) {
//                    setBlueBackground(factory, td);
//                }
//                td.getContent().add(p);
//            }
//        }
//        mainDocumentPart.getContent().add(tbl);
//
//        File file = new File("welcome.docx");
//        wordPackage.save(file);
//        try (FileInputStream inputStream = new FileInputStream(file)) {
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
//            IOUtils.copy(inputStream, response.getOutputStream());
//            response.flushBuffer();
//        }

    }

    private P setText(ObjectFactory factory) {
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue("Welcome To Baeldung");
        r.getContent().add(t);
        p.getContent().add(r);
        return p;
    }

    private void setTableBorder(Tbl tbl) {
        tbl.setTblPr(new TblPr());

        CTBorder border = new CTBorder();
        border.setColor("DEE2E6");
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

    private void setBlueBackground(ObjectFactory factory, Tc td) {
        td.setTcPr(new TcPr());
        CTShd shd = factory.createCTShd();
        shd.setVal(STShd.CLEAR);
        shd.setColor("auto");
        shd.setFill("F2F2F2");
        shd.setThemeFill(STThemeColor.ACCENT_1);
        shd.setThemeFillTint("66");
        td.getTcPr().setShd(shd);
    }
}
