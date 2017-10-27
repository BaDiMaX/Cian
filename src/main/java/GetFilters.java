import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GetFilters {

    public ArrayList<ArrayList<String>> filtersArray = new ArrayList<ArrayList<String>>();

    private ArrayList<String> filter;

    public ArrayList<ArrayList<String>> getFiltersArray() {
        return filtersArray;
    }
    public GetFilters(){

    }
    public void getFiltersData(String fileName) {
        InputStream inputStream = null;
        HSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream(fileName);
            workBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            if(row.getRowNum() > 0) {
                filter = new ArrayList<String>();
                for (int i = 1; i < 8; i++) {
                    Cell cell = row.getCell(i);
                    if(cell != null && !(cell.getCellTypeEnum()==CellType.BLANK)){
                        filter.add(""+cell);
                    }else{
                        filter.add("empty");
                    }
                }
                filtersArray.add(filter);
            }
        }
    }

}
