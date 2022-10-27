package com.demo.excel.demoexcel.excel;

import com.demo.excel.demoexcel.entity.Usuario;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.usermodel.IndexedColors.*;

public class UserExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Usuario> listUsuarios;

    public UserExcelExporter(List<Usuario> listUsuarios) {
        this.listUsuarios=listUsuarios;
        workbook = new XSSFWorkbook();

    }

    private void createCell(Row row,int columnCount, Object value,CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);
        if(value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine() {
        sheet=workbook.createSheet("Usuarios");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row,0,"INFORMACIÓN DE USUARIOS",style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        font.setFontHeightInPoints((short)(10));

        row=sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        //font.setColor(BLUE.getIndex());
        style.setFont(font);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(LIGHT_YELLOW.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "NOMBRES", style);
        createCell(row, 1, "APELLIDOS", style);
        createCell(row, 2, "EMAIL", style);
        createCell(row, 3, "FECHA", style);
    }

    private void writeDataLines() {
        int rowCount=2;

        CellStyle style=workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);



        for(Usuario usuario:listUsuarios) {
            Row row=sheet.createRow(rowCount++);
            int columnCount=0;
            createCell(row, columnCount++, usuario.getNombre(), style);
            createCell(row, columnCount++, usuario.getApellido(), style);
            createCell(row, columnCount++, usuario.getEmail(), style);
            createCell(row, columnCount++, usuario.getFecha(), style);
        }
    }

    public ByteArrayInputStream exportExcel() throws  IOException{
        writeHeaderLine();
        writeDataLines();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());

    }

    public ByteArrayInputStream exportExcel(List<Usuario> usuarios) throws IOException {
        String[] columns = {"NOMBRE", "APELLIDO", "EMAIL", "FECHA"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.getSheet("Usuarios");
        sheet.autoSizeColumn(columns.length);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(BLUE.getIndex());


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);

        //   HEADER

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(cellStyle);
        }

        CellStyle cellStyleData = workbook.createCellStyle();
        cellStyleData.setDataFormat(creationHelper.createDataFormat().getFormat("#"));

        int rowIndex = 1;

        for(Usuario usuario: usuarios){
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(usuario.getNombre());
            row.createCell(1).setCellValue(usuario.getApellido());
            row.createCell(2).setCellValue(usuario.getEmail());
            row.createCell(3).setCellValue(usuario.getFecha());

            rowIndex++;
        }

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());



    }
}
