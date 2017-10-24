import com.sun.javafx.css.StyleCacheEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CianSearch {
    private WebDriver driver;
    private Actions builder;
    private String url = "https://cian.ru/";
    private List<String> roomsDeselect;
    private List<String> filtersList;
    private GetFilters filters;
    private ResultsData resultsData;

    private String dealType;
    private  String offerType;
    private  List<String> rooms = new ArrayList<String>();
    private  String bedRooms;
    private String valueFrom;
    private String valueTo;
    private  String adress;


    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        builder = new Actions(driver);
        roomsDeselect = new ArrayList<String>();
        filtersList = new ArrayList<String>();
        filters = new GetFilters();
        openUrl(url);
    }

    @Test
    public void searchInCian() throws Exception {

            filters.getFiltersData("list1.xls");
            setValues(filters.getFiltersArray().get(0));
            seeArray(filters.getFiltersArray().get(0));
            firstFiltrTest(dealType, offerType, roomsDeselect, rooms, bedRooms, valueFrom, valueTo, adress);
            seeResults(2, "filtr1");
            if(filters.getFiltersArray().size() > 1) {
                for (int i = 1; i < filters.getFiltersArray().size()-1; i++) {
                   setValues(filters.getFiltersArray().get(i));
                   seeArray(filters.getFiltersArray().get(i));
                   Thread.sleep(1000);
                   othwerFiltrs(dealType, offerType, roomsDeselect, rooms, bedRooms, valueFrom, valueTo, adress);
                   seeResults(1, "filtr"+(i+1));
                }
           }
    }

    @After
    public void stop() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }


    //временные
    public void seeArray(List<String> list){
        for (String s: list) {
            System.out.println(s);
        }
    }
    public void seeResults(int countResults, String resultsName) throws InterruptedException {
        resultsData = new ResultsData();
        if(!listResult().isEmpty()){
            for (int i = 0; i < countResults; i++){
                getInfoOfResults(i);
            }
            createFileResults(resultsName);
        }else{
            System.out.println("Нет результатов");
        }

    }
    public void setData(){
        resultsData.setUrl(getUrl());
        resultsData.setTitle(getTitle());
        resultsData.setAdress(getAdress());
        resultsData.setMetro(getMetro());
        resultsData.setPrice(getPrice());
        resultsData.setDopInfo(getDopInfo());
        resultsData.setTelephone(getTelephone());
        resultsData.setInfoTh(getInfoTh());
        resultsData.setInfoTd(getInfoTd());
        resultsData.setInfoDescription(getInfoDescription());
        resultsData.setView(getView());
        resultsData.addCell1();
        resultsData.addCell2();
    }
    public void createFileResults(String resultsName){
        ArrayList<String> list1 = resultsData.getCell1();
        ArrayList<String> list2 = resultsData.getCell2();
        resultsData.newFileCreate(list1, list2, resultsName);
    }
    public void setValues(ArrayList<String> list){
         this.dealType = list.get(0);
        this.offerType = list.get(1);
        rooms.clear();
         for(String s: list.get(2).split(", ")) {
             this.rooms.add(s);
         }
        this.bedRooms = list.get(3);
        this.valueFrom = list.get(4);
        this.valueTo = list.get(5);
        this.adress = list.get(6);


    }
    //тесты
    public void firstFiltrTest(String operation, String object, List<String> roomDeselect, List<String> roomsSelect, String bedrooms, String valueFrom, String valueTo, String adress) throws InterruptedException {
        if(object != "empty") {
            selectOperation();
            chooseOperation(operation);
        }
        if(object != "empty") {
            selectObject();
            chooseObject(object);
        }
        if(roomsSelect.get(0).contains("empty")){
            roomsSelect.clear();
        }
        if(!roomsSelect.isEmpty()) {
                selectNumberRooms();
                chooseNumberRooms(roomDeselect, roomsSelect);
                roomDeselect.clear();
                roomsDeselect.addAll(roomsSelect);
        }else if(object.contains("Квартира")){
            selectNumberRooms();
            roomsDeselect.clear();
        }
        if(valueFrom != "empty") {
            setValueFrom(valueFrom);
        }else{
            clearFilterInput("от");
        }
        if(valueTo != "empty") {
            setValueTo(valueTo);
        }else{
            clearFilterInput("до");
        }
        if(adress !="empty") {
            setAddress(adress);
        }
        if(bedrooms !="empty" && operation.contains("Посуточно")){
            selectBedrooms();
            chooseBebrooms(bedrooms);
        }else if(object.contains("Дом")&& operation.contains("Посуточно")){
            selectBedrooms();
            chooseBebrooms("Не важно");
        }
        pushButtonFind();

    }
    public void getInfoOfResults(int numResult) throws InterruptedException {
        Thread.sleep(2000);
        chooseResult(numResult);
        switchToNewWindow(1);
        seePhotos();
        showTelephone();
//        System.out.println(getUrl());
//        System.out.println(getTitle());
//        System.out.println(getAdress());
//        seeArray(getMetro());
//        System.out.println(getPrice());
//        System.out.println(getDopInfo());
//        System.out.println(getTelephone());
//        seeArray(getInfoTh());
//        seeArray(getInfoTd());
//        seeArray(getInfoDescription());
//        System.out.println(getView());
        setData();
        closeSwitchWindow();
        closeWindow();
    }
    public void othwerFiltrs(String operation, String object, List<String> roomDeselect, List<String> roomsSelect, String bedrooms, String valueFrom, String valueTo, String adress) throws InterruptedException {
        //goToUp();
        if(operation !="empty") {
            clickButtonDealType();
            chooseOperation(operation);
        }
        if(object !="empty") {
            clickButtonOfferType();
            chooseNewOfferType(object);
        }
        if(roomsSelect.get(0).contains("empty")){
            roomsSelect.clear();
        }
        if(!roomsSelect.isEmpty()) {
            clickButtonRooms();
            chooseNumberRooms(roomDeselect, roomsSelect);
            clickButtonRooms();
            roomsDeselect.clear();
            roomsDeselect.addAll(roomsSelect);
        }else if(object.contains("Квартира")){
            clickButtonRooms();
            chooseNumberRooms(roomDeselect, roomsSelect);
            clickButtonRooms();
            roomsDeselect.clear();
        }
        if(bedrooms !="empty" && operation.contains("Посуточно")){
            clickButtonBedrooms();
            chooseBebrooms(bedrooms);
        }else if(object.contains("Дом")&& operation.contains("Посуточно")){
            clickButtonBedrooms();
            chooseBebrooms("Не важно");
        }
        if(valueFrom !="empty") {
            setValueFrom(valueFrom);
        }else{
            clearFilterInput("от");
        }
        if(valueTo !="empty") {
            setValueTo(valueTo);
        }else{
            clearFilterInput("до");
        }
        if(adress != "empty") {
            setNewAddress(adress);
        }
        clickButtonSearch();

    }

    //словарь
    public void openUrl(String url){
        driver.get(url);
    }
    public void selectOperation(){
        findFiltrButton("Купить");
    }
    public void chooseOperation(String cOperation){
        findFiltrDiv(cOperation);
    }
    public void selectObject(){
        findFiltrButton("Квартиру");
    }
    public void chooseObject(String cObject){
        findFiltrSpan(cObject);
    }
    public void selectNumberRooms(){
        findFiltrButton("1, 2 комн.");
        findFiltrLabel("1-комнатная");
        findFiltrLabel("2-комнатная");
    }
    public void chooseNumberRooms(List<String> roomsDeselect, List<String> roomsSelect) throws InterruptedException {
        if(!roomsDeselect.isEmpty()){
            for(String s: roomsDeselect){
                findFiltrLabel(s);
                Thread.sleep(1000);
            }
        }
        if(!roomsSelect.isEmpty()){
            for (String s: roomsSelect){
                findFiltrLabel(s);
                Thread.sleep(1000);
            }
        }
    }

    public void selectBedrooms() {
        findFiltrButton("Спален");
    }

    public void chooseBebrooms(String bedrooms){
        findFiltrDiv(bedrooms);
    }
    public void setValueFrom(String valueFrom) throws InterruptedException {
        findFilterInput("от", valueFrom);
    }
    public void setValueTo(String valueTo) throws InterruptedException {
        findFilterInput("до",valueTo);
    }
    public void setAddress(String address) throws InterruptedException {
        findFilterInputPlace(address);
    }
    public void pushButtonFind() throws InterruptedException {
        Thread.sleep(1000);
        findFiltrButton("Найти");
    }
    public void chooseResult(int number){
        listResult().get(number).click();
    }
    public void switchToNewWindow(int numOfWindow){
        ArrayList tabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window((String) tabs.get(numOfWindow));
    }
    public void closeSwitchWindow(){
        driver.close();
        ArrayList tabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window((String) tabs.get(0));

    }
    public  void clickButtonBedrooms() throws InterruptedException {
        findNewFilterButton("bedrooms");
    }
    public void clickButtonDealType() throws InterruptedException {
        findNewFilterButton("dealType");
    }
    public void clickButtonOfferType() throws InterruptedException {
        findNewFilterButton("offerType");
    }
    public void clickButtonRooms() throws InterruptedException {
        findNewFilterButton("rooms");
    }
    public void chooseNewOfferType(String offerType){
        findFiltrButton(offerType);
        try {
            WebElement element = driver.findElement(By.xpath("//div[@class='popupContent--1FjLB']"));
            if(element.isEnabled()){
                clickButtonOfferType();
            }
        } catch (Exception e) {
        }
    }
    public void setNewAddress(String address) throws InterruptedException {
        findNewFilterInputPlace(address);
    }

    //описание
    public void showTelephone() throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='cf_offer_show_phone cf_offer_show_phone--under_price ']/button[@class='cf_offer_button']")).click();
        driver.findElement(By.xpath("//div[@class='cf_offer_show_phone']/button[@data-ga-offer-phone-type='Open_card']")).click();
    }
    public void seePhotos() throws InterruptedException {

        List<WebElement> listph = driver.findElements(By.xpath("//div[@class='fotorama__nav-wrap']/div/div/div/div"));
        builder.moveToElement(listph.get(0)).build().perform();
        if(listph.size()> 1) {
            for (int i = 0; i < listph.size(); i++) {
                Thread.sleep(1000);
                while (true) {
                    try{
                    WebElement webElement = driver.findElement(By.xpath("//div[@class='fotorama__arr fotorama__arr--next']"));
                    builder.moveToElement(webElement).build().perform();
                    webElement.click();
                        break;
                    }catch (Exception e){
                    }
                }

            }
        }
    }
    public List<WebElement> listResult(){
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='wrapper--1Z8Nz'][1]//a[text()='Подробнее']"));
        return list;
    }
    public void clickButtonSearch(){
        driver.findElement(By.xpath("//div[@data-mark='searchButton']/button")).click();
    }

    public void findFiltrButton(String button){
        driver.findElement(By.xpath("//button[text()='" + button + "']")).click();
    }
    public void findFiltrDiv(String div){
        driver.findElement(By.xpath("//div[text()='" + div + "']")).click();
    }
    public void findFiltrSpan(String span){
        driver.findElement(By.xpath("//span[text()='" + span + "']")).click();
    }
    public void findFiltrLabel(String label){
        driver.findElement(By.xpath("//label[text()='" + label + "']")).click();

    }
    public void findFilterInput(String input, String string) throws InterruptedException {
        WebElement inp = driver.findElement(By.xpath("//input[@placeholder='" + input + "']"));
        inp.clear();
        Thread.sleep(1000);
        inp.sendKeys(string);
    }
    public void clearFilterInput(String input) throws InterruptedException {
        WebElement inp = driver.findElement(By.xpath("//input[@placeholder='" + input + "']"));
        inp.sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
    }

    public void findNewFilterInputPlace(String address) throws InterruptedException {
        WebElement place = driver.findElement(By.xpath("//input[@class='_3uYBcpTy2fYzFl_O _1NLO4lEF2p-oalza input--37E4Y']"));
        place.clear();
        Thread.sleep(1000);
        place.sendKeys(address);
        while(true) {
            try {
                WebElement element = driver.findElement(By.xpath("//div[@class='list-container--Afb5W']//ul/li[position()=1]"));
                if (element.isDisplayed()) {
                    builder.moveToElement(element).build().perform();
                    Thread.sleep(2000);
                    element.click();
                    break;
                }
            }catch (Exception e){

            }
        }
    }

    public void findFilterInputPlace(String address) throws InterruptedException {
        WebElement place = driver.findElement(By.xpath("//input[@id='c_filters-suggest_input']"));
        place.clear();
        Thread.sleep(1000);
        place.sendKeys(address);
        while(true) {
            try {
                WebElement element = driver.findElement(By.xpath("//div[@role='search']/div/div/div/div/div[position()=1]"));
                if (element.isDisplayed()) {
                    builder.moveToElement(element).build().perform();
                    Thread.sleep(1000);
                    element.click();
                    break;
                }
            }catch (Exception e){

            }
        }
    }
    public void findNewFilterButton(String tagValue) throws InterruptedException {

        WebElement element = driver.findElement(By.xpath("//button[@data-mark='" + tagValue + "']"));
        builder.moveToElement(element).build().perform();
        Thread.sleep(1000);
        element.click();
    }

    //сбор данных
    public String getUrl(){
        return  driver.getCurrentUrl();
    }
    public String getTitle(){
        return driver.findElement(By.xpath("//div[@class='object_descr_title']")).getText();
    }
    public String getAdress(){
        return driver.findElement(By.xpath("//h1[@class='object_descr_addr']")).getText();
    }
    public List<String> getMetro(){

        List<WebElement> list = driver.findElements(By.xpath("//p[@class='objects_item_metro_prg']"));
        List<String> listm = new ArrayList<String>();
        for (WebElement webElement: list) {
            listm.add(webElement.getText());
        }
        return  listm;
    }
    public String getPrice(){
        return driver.findElement(By.xpath("//div[@class='object_descr_price']")).getText();
    }
    public String getDopInfo(){
        return driver.findElement(By.xpath("//div[@style='position: relative']/div//div[3]")).getText();
    }
    public List<String> getInfoTh(){
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='clearfix']//table/tbody/tr/th"));
        List<String> listth = new ArrayList<String>();
        for (WebElement webElement: list) {
            listth.add(webElement.getText());
        }
        return  listth;
    }
    public List<String> getInfoTd(){
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='clearfix']//table/tbody/tr/td"));
        List<String> listtd = new ArrayList<String>();
        for (WebElement webElement: list) {
            listtd.add(webElement.getText());
        }
        return  listtd;
    }
    public String getView(){
        return driver.findElement(By.xpath("//div[@class='object_descr_text']")).getText();
    }
    public String getTelephone(){
        return driver.findElement(By.xpath("//div[@class='cf_offer_show_phone-number cf_offer_show_phone-number--under_price']")).getText();
    }
    public List<String> getInfoDescription(){
        List<WebElement> list = driver.findElements(By.xpath("//div[@itemprop='description']/div/ul/li"));
        List<String> listdescr = new ArrayList<String>();
        for (WebElement webElement: list) {
            listdescr.add(webElement.getText());
        }
        return listdescr;
    }
    public void goToUp(){
        WebElement element = driver.findElement(By.xpath("//span[@class='c-header-logo']"));
        builder.moveToElement(element).build().perform();
    }
    public void closeWindow(){
        try {
            WebElement element = driver.findElement(By.xpath("//div[@class='button--3JzvW']"));
            if(element.isEnabled()){
                element.click();
            }
        } catch (Exception e) {

        }
    }
}
