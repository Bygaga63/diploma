package com.service.antenna.services;

import com.service.antenna.domain.Address;
import com.service.antenna.domain.BreakdownType;
import com.service.antenna.domain.Task;
import com.service.antenna.domain.User;
import com.service.antenna.payload.AddressResponse;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {
    private final String BORDER_COLOR = "DEE2E6";
    private final String BACKGROUND_COLOR = "F2F2F2";
    private ObjectFactory factory = new ObjectFactory();

    public void createFile(List<Task> tasks, HttpServletResponse response) throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Отчет");

        List<List<TableFields>> list = convertTaskToMap(tasks);
        list.forEach(tableFields -> createTaskTable(wordPackage, mainDocumentPart, tableFields, 2));

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

    public void createAddressFile(List<AddressResponse> taskAddress, HttpServletResponse response) throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Отчет");

        List<List<String>> list = convertAddressToMap(taskAddress);
        createAddressTable(wordPackage, mainDocumentPart, list, 3);

        File file = new File("address.docx");
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

    private void createAddressTable(WordprocessingMLPackage wordPackage, MainDocumentPart mainDocumentPart, List<List<String>> list, int columnNumber) {
        int writableWidthTwips = wordPackage.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();

        Tbl tbl = TblFactory.createTable(list.size(), columnNumber, writableWidthTwips / columnNumber);
        setTableBorder(tbl);
        List<Object> rows = tbl.getContent();
        for (int i = 0; i < rows.size(); i++) {
            Tr tr = (Tr) rows.get(i);
            List<Object> cells = tr.getContent();
            for (int j = 0; j < cells.size(); j++) {
                Tc td = (Tc) cells.get(j);
                td.getContent().add(setText(list.get(i).get(j)));
            }
        }
        mainDocumentPart.getContent().add(tbl);
        mainDocumentPart.addParagraphOfText("");

    }

    private void createTaskTable(WordprocessingMLPackage wordPackage, MainDocumentPart mainDocumentPart, List<TableFields> taskToMap, int columnNumber) {
        int writableWidthTwips = wordPackage.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();

        Tbl tbl = TblFactory.createTable(taskToMap.size(), columnNumber, writableWidthTwips / columnNumber);
        setTableBorder(tbl);
        List<Object> rows = tbl.getContent();
        for (int i = 0; i < rows.size(); i++) {
            Tr tr = (Tr) rows.get(i);
            List<Object> cells = tr.getContent();
            for (int j = 0; j < cells.size(); j++) {
                Tc td = (Tc) cells.get(j);
                if (j % 2 == 0) {
                    td.getContent().add(setText(taskToMap.get(i).getKey()));
                } else {
                    td.getContent().add(setText(taskToMap.get(i).getValue()));
                }

            }
        }
        mainDocumentPart.getContent().add(tbl);
        mainDocumentPart.addParagraphOfText("");
    }

    private List<List<String>> convertAddressToMap(List<AddressResponse> addressResponses) {
        List<List<String>> result = new ArrayList<>();
        result.add(Arrays.asList("ID", "Адрес", "ФИО"));
        addressResponses.forEach(a -> {
            List<String> addressFields = new ArrayList<>();

            addressFields.add(String.valueOf(a.getId()));
            addressFields.add(a.getStreet() + " " + a.getHouse() + " " + a.getFlatNumber());
            addressFields.add(a.getFullName());

            result.add(addressFields);
        });
        return result;
    }

    private List<List<TableFields>> convertTaskToMap(List<Task> tasks) {
        List<List<TableFields>> result = new ArrayList<>();

        tasks.forEach(task -> {
            List<TableFields> taskFields = new ArrayList<>();
            StringBuilder addressString = new StringBuilder();
            Address address = task.getCustomer().getAddress();
            if (address != null) {
                if (StringUtils.hasText(address.getStreet())) {
                    addressString.append(address.getStreet()).append(" ");
                }
                if (StringUtils.hasText(address.getHouse())) {
                    addressString.append(address.getHouse()).append(" ");
                }
                if (StringUtils.hasText(address.getFlatNumber())) {
                    addressString.append(address.getFlatNumber());
                }
            }
            taskFields.add(new TableFields("Мастер", task.getUsers().stream().map(User::getFullName).collect(Collectors.joining(","))));
            taskFields.add(new TableFields("Дата создания", task.getCreateAt().toString()));
            taskFields.add(new TableFields("Типы поломок", task.getBreakdownType().stream().map(BreakdownType::getType).collect(Collectors.joining(","))));
            taskFields.add(new TableFields("ФИО заказчика", task.getCustomer() != null ? task.getCustomer().getFullName() : ""));
            taskFields.add(new TableFields("Телефон", task.getCustomer() != null ? task.getCustomer().getPhone() : ""));
            taskFields.add(new TableFields("Адрес", addressString.length() > 0 ? addressString.toString() : ""));
            taskFields.add(new TableFields("Статус", task.getStatus().getName()));
            result.add(taskFields);
        });


        return result;
    }

    private P setText(String text) {
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue(text);
        r.getContent().add(t);
        p.getContent().add(r);

        PPr paragraphProperties = factory.createPPr();
        Jc justification = factory.createJc();
        justification.setVal(JcEnumeration.CENTER);
        paragraphProperties.setJc(justification);

        p.setPPr(paragraphProperties);
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

    @Data
    private class TableFields {
        private final String key;
        private final String value;
    }


}
