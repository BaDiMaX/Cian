import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ResultsData {
    private String url;
    private String title;
    private String adress;
    private List<String> metro = new ArrayList<String>();
    private String price;
    private String dopInfo;
    private String telephone;
    private List<String> infoTh = new ArrayList<String>();
    private List<String> infoTd = new ArrayList<String>();
    private List<String> infoDescription = new ArrayList<String>();
    private String view;
    private ArrayList<String> cell1 = new ArrayList<String>();
    private ArrayList<String> cell2 = new ArrayList<String>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<String> getMetro() {
        return metro;
    }

    public void setMetro(List<String> metro) {
        this.metro = metro;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDopInfo() {
        return dopInfo;
    }

    public void setDopInfo(String dopInfo) {
        this.dopInfo = dopInfo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getInfoTh() {
        return infoTh;
    }

    public void setInfoTh(List<String> infoTh) {
        this.infoTh = infoTh;
    }

    public List<String> getInfoTd() {
        return infoTd;
    }

    public void setInfoTd(List<String> infoTd) {
        this.infoTd = infoTd;
    }

    public List<String> getInfoDescription() {
        return infoDescription;
    }

    public void setInfoDescription(List<String> infoDescription) {
        this.infoDescription = infoDescription;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public ArrayList<String> getCell1() {
        return cell1;
    }

    public void setCell1(ArrayList<String> cell1) {
        this.cell1 = cell1;
    }

    public ArrayList<String> getCell2() {
        return cell2;
    }

    public void setCell2(ArrayList<String> cell2) {
        this.cell2 = cell2;
    }

    public void addCell1(){
        if(url != null) {
            cell1.add("Ссылка");
        }
        if(title!=null) {
            cell1.add("Кр. Описание");
        }
        if(adress != null) {
            cell1.add("Адресс");
        }
        if(!metro.isEmpty()) {
            cell1.add("Метро");
        }
        if(price!=null) {
            cell1.add("Цена");
        }
        if(dopInfo != null) {
            cell1.add("Доп.Инф.");
        }
        if(telephone!=null) {
            cell1.add("Телефон");
        }
        if(!infoTh.isEmpty()) {
            cell1.addAll(infoTh);
        }
        if(!infoDescription.isEmpty()) {
            cell1.add("Имеется");
        }
        if(view!=null) {
            cell1.add("Описание");
        }
        cell1.add("-----");

    }
    public void addCell2(){
        if(url != null) {
            cell2.add(url);
        }
        if(title != null) {
            cell2.add(title);
        }
        if(adress != null) {
            cell2.add(adress);
        }
        if(!metro.isEmpty()) {
            String sMetro = "";
            for (int i = 0; i < metro.size(); i++) {
                if((i+1)!=metro.size()){
                    sMetro+=metro.get(i)+", ";
                }else{
                    sMetro+=metro.get(i);
                }
            }
            cell2.add(sMetro);
        }
        if(price!=null) {
            cell2.add(price);
        }
        if(dopInfo!=null) {
            cell2.add(dopInfo);
        }
        if(telephone!=null) {
            cell2.add(telephone);
        }
        if(!infoTd.isEmpty()) {
            if (infoTh.size() > infoTd.size()) {
                cell2.add("-");
            }
            cell2.addAll(infoTd);
        }
        if(!infoDescription.isEmpty()) {
            String des = "";
            for (int i = 0; i < infoDescription.size(); i++) {
                if((i+1)!=infoDescription.size()){
                    des+=infoDescription.get(i)+", ";
                }else{
                    des+=infoDescription.get(i);
                }
            }
            cell2.add(des);
        }
        if(view!=null) {
            cell2.add(view);
        }
        cell2.add("-----");
    }

    public void newFileCreate(ArrayList<String> cell1, ArrayList<String> cell2, String numResult) {

        List<String> dataList1 = cell1;
        List<String> dataList2 = cell2;
        if(!dataList1.isEmpty()&&!dataList2.isEmpty()&&dataList1.size()==dataList2.size()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Результат");
            for (int i = 0; i < dataList1.size(); i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(dataList1.get(i));
                row.createCell(1).setCellValue(dataList2.get(i));
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            try (FileOutputStream out = new FileOutputStream(new File("result_"+numResult+".xls"))) {
                workbook.write(out);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Excel файл успешно создан!");
        } else {
            System.out.println("Нет результатов");
        }
    }
}
