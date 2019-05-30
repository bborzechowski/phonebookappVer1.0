package bb.orzechowski.phonebookapp.commons.xlsCreator;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CreatorXLS<T> {

    private Class<T> tClass;

    public CreatorXLS(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void createFile(List<T> series, String path, String fileName) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //tworzę obiekt reprezentujący cały plik exel
        HSSFWorkbook workbook = new HSSFWorkbook();

        //tworzy arkusz w pliku exel
        HSSFSheet sheet = workbook.createSheet("persons");

        //ustawiam czcionki dla nagłówka
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)14);
        headerFont.setColor(IndexedColors.DARK_GREEN.getIndex());
        //zapisuje styl
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);

        //kolekcja zawierajaca odczytane nazwy pól klasy T (to będą kolumny)
        List<String> columns = new ArrayList<>();

        for(Field f : tClass.getDeclaredFields()){
            columns.add(f.getName());
     //       System.out.println(f.getName());
        }

        //tworzenie nagłówka
        //zapisje do struktury pliku exel odczytane powyżej pola klasy jako nagłówki kolumn
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i <columns.size() ; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(cellStyle); //zapisuje styl do każdej komórki
        }

        //tekst odczyt

        columns.forEach(t -> System.out.println("get"+ t.substring(0,1).toUpperCase() + t.substring(1)));
        
        //zapis danych i wywoływanie metod get
        for (int i = 0; i <series.size() ; i++) {
            HSSFRow row = sheet.createRow(i+1);

            for (int j = 0; j <columns.size() ; j++) {
                HSSFCell cell = row.createCell(j);

                Method method = series.get(i)
                        .getClass()
                        .getMethod("get"+ columns.get(j)
                        .substring(0,1)
                        .toUpperCase() + columns.get(j)
                        .substring(1));

                Object result = method.invoke(series.get(i));
                cell.setCellValue(String.valueOf(result));
            }
        }



        //tworzenie pliku docelowego
        long mills = System.currentTimeMillis();
        String filee = path + fileName + "_" + mills+".xls";

        workbook.write(new File(filee));
        workbook.close();
    }
}
