package main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    //=======BUTTONS
    @FXML
    private Button btnPath;
    @FXML
    private Button btnPathSc;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnStart;
    @FXML
    private Button timeTableBtn;
    @FXML
    private Button btnTStart;
    //---------------
    //========TEXT FIELDS
    @FXML
    private TextField pathPython;
    @FXML
    private TextField pathScript;
//    @FXML
//    private ComboBox yyyyStart;
//    @FXML
//    private ComboBox mmStart;
//    @FXML
//    private ComboBox ddStart;
    @FXML
    private ComboBox hhStart;
    @FXML
    private ComboBox wwStart;
    @FXML
    private ComboBox ssStart;
    @FXML
    private TextField freqStart;
    @FXML
    private TextArea logField;

    @FXML
    private TextField yyyyStop;
    @FXML
    private TextField mmStop;
    @FXML
    private TextField ddStop;
    @FXML
    private ComboBox hhStop;
    @FXML
    private ComboBox wwStop;
    @FXML
    private ComboBox ssStop;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private DatePicker datePickerStop;

    //=======STRING's для путей
    private String pyPath;
    private String scPath;

    //=======Переменная для переключения Задать Расписания - Отмена
    int i = 0;

    //TIMER для запуска
    Timer timer = new Timer();
    Timer timerStop = new Timer();

    //=====FILES
    File file1; // Файл интерпретатора питон
    File file2; // Файл к скрипту top_block.py

    //=====PROCESS
    ProcessBuilder procBuilder;
    Process process;

//======ПОЛЯ ОБЪЯВЛЕНЫ=========
    // Конструктор не указывается в контроллере, только методы

    public void timeTableBtnClicked(){
        i++;
        if(i%2!=0) {
            hhStart.getItems().addAll(Main.hours);
            hhStop.getItems().addAll(Main.hours);
            wwStart.getItems().addAll(Main.minutes);
            wwStop.getItems().addAll(Main.minutes);
            ssStart.getItems().addAll(Main.seconds);
            ssStop.getItems().addAll(Main.seconds);
            hhStart.setDisable(false);
            hhStart.setValue("14");
            hhStop.setDisable(false);
            hhStop.setValue("14");
            wwStart.setDisable(false);
            wwStart.setValue("00");
            wwStop.setDisable(false);
            wwStop.setValue("00");
            ssStart.setDisable(false);
            ssStart.setValue("00");
            ssStop.setDisable(false);
            ssStop.setValue("00");
            datePickerStart.setDisable(false);
            //datePickerStart.getEditor().appendText(CalendarDefault.dateFormatWithoutMins.format(CalendarDefault.calendar.getTime()));
            datePickerStop.setDisable(false);
            //datePickerStop.getEditor().appendText(CalendarDefault.dateFormatWithoutMins.format(CalendarDefault.calendar.getTime()));
            //timeTableBtn.setDisable(true);
            timeTableBtn.setText("Отменить");

            btnStart.setDisable(true);
            btnSave.setDisable(false);
        }
        if(i%2==0){
            btnStart.setDisable(false);
            timeTableBtn.setText("Задать расписание");

            hhStart.setDisable(true);
            hhStop.setDisable(true);
            wwStart.setDisable(true);
            wwStop.setDisable(true);
            ssStart.setDisable(true);
            ssStop.setDisable(true);
            datePickerStart.setDisable(true);
            datePickerStop.setDisable(true);
            btnSave.setDisable(true);
            btnTStart.setDisable(true);
            logField.appendText("Отмена!");
            timer.cancel();
        }
    }



    // Нажатие кнопки Python Interpreter
    public void btnPathClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Укажите путь к интерпретатору Python 2.7");
        file1 = fileChooser.showOpenDialog(new Stage());
        pyPath = file1.getPath();
        pathPython.setText(pyPath);
        btnPathSc.setDisable(false);
    }
    // Нажатие кнопки Python Script
    public void btnPathScClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Укажите путь скрипту top_block.py");
        file2 = fileChooser.showOpenDialog(new Stage());
        scPath = file2.getPath();
        pathScript.setText(scPath);
        btnStart.setDisable(false);
        timeTableBtn.setDisable(false);
    }


    // Нажатие кнопки запуск скрипта
    public  void btnStartClicked() throws IOException, InterruptedException {
        processStart();
    }

    public void btnSaveClicked() throws IOException {

        try {
            File timeTable = new File("/Users/wolldemurr/Configuration/timetable.txt");
            FileWriter writer = new FileWriter(timeTable);
            writer.append((Date.valueOf(datePickerStart.getValue())).toString().replace("-", " ") + " ")
                    .append(hhStart.getSelectionModel().getSelectedItem().toString() + " ")
                    .append(wwStart.getSelectionModel().getSelectedItem().toString() + " ")
                    .append(ssStart.getSelectionModel().getSelectedItem().toString() + " ")
                    .append(freqStart.getCharacters().toString() + "\n");
            //Добавляем время остановки
            writer.append((Date.valueOf(datePickerStop.getValue())).toString().replace("-", " ") + " ")
                    .append(hhStop.getSelectionModel().getSelectedItem().toString() + " ")
                    .append(wwStop.getSelectionModel().getSelectedItem().toString() + " ")
                    .append(ssStop.getSelectionModel().getSelectedItem().toString() + "\n");
            writer.close();
            logField.appendText("Расписание сохранено\n");
            btnTStart.setDisable(false);
        }catch(Exception e){
            logField.appendText("Вероятно, вы не указали правильно дату\n");
        }
    }

    public void btnTStartClicked(){

        String start;
        String stop;
        ArrayList<String> list = new ArrayList<>();
        try {
            btnStart.setDisable(true);
            pathScript.setDisable(true);
            pathPython.setDisable(true);
            btnPathSc.setDisable(true);
            btnPath.setDisable(true);
            BufferedReader reader = new BufferedReader(new FileReader("/Users/wolldemurr/Configuration/timetable.txt"));

            while(reader.ready()){
                list.add(reader.readLine());
            }
            start = list.get(0);
            stop = list.get(1);
            timerStart(start, stop);
            btnSave.setDisable(true);

        } catch (FileNotFoundException e) {
            logField.appendText("Файл с расписанием не найден");
        } catch (IOException e) {
            logField.appendText(e.toString());
        }
    }

    public void timerStart(String start, String stop){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Calendar calendarStart = new GregorianCalendar();
        Calendar calendarStop = new GregorianCalendar();
        String[] startTime = start.split(" ");
        String[] stopTime = stop.split(" ");
        calendarStart.set(Integer.parseInt(startTime[0]),Integer.parseInt(startTime[1])-1,Integer.parseInt(startTime[2])
               , Integer.parseInt(startTime[3]), Integer.parseInt(startTime[4]), Integer.parseInt(startTime[5]));
        System.out.println("START:" + dateFormat.format(calendarStart.getTime()));

        calendarStop.set(Integer.parseInt(stopTime[0]),Integer.parseInt(stopTime[1])-1,Integer.parseInt(stopTime[2])
                , Integer.parseInt(stopTime[3]), Integer.parseInt(stopTime[4]), Integer.parseInt(stopTime[5]));
        System.out.println("STOP:" + dateFormat.format(calendarStop.getTime()));

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                processStart();
            }
        };
        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                process.destroy();
                logField.appendText("Завершение программы в соответствии с расписанием...");
            }
        };
        try {
            timer.schedule(task, calendarStart.getTime());
        }catch (Exception e){
            logField.appendText("Я не могу возвращаться в прошлое./n" +
                    "Нажмите \"Отмена\" и задайте другое расписание");
            logField.setStyle("-fx-background-color: red");
        }

        try {
            if (calendarStop.before(calendarStop)) {
                throw new Exception("Дата окончания работы программы не может быть до старта...\n" +
                        "Измените дату.");
            }

            else {
                timerStop.schedule(stopTask, calendarStop.getTime());
                logField.appendText("Программа будет запущена в соответсвии с расписанием.");
            }
        }
        catch(Exception e){
            logField.appendText(e.toString());
        }
    }

    //Запуск процесса
    public void processStart(){
        logField.appendText("================START================\n");
        logField.appendText("=====================================\n");
        try{
            //System.out.println(pyPath + " " + scPath);
            //Runtime.getRuntime().exec(pyPath + " " + scPath);
            procBuilder = new ProcessBuilder(pyPath, scPath,freqStart.getCharacters().toString());
            // перенаправляем стандартный поток ошибок на
            // стандартный вывод
            procBuilder.redirectErrorStream(true);
            // запуск программы
            process = procBuilder.start();

            logField.setStyle("-fx-background-color: green");

            // читаем стандартный поток вывода
            // и выводим на экран
            InputStream stdout = process.getInputStream();
            InputStreamReader isrStdout = new InputStreamReader(stdout);
            BufferedReader brStdout = new BufferedReader(isrStdout);

            String line = null;
            while((line = brStdout.readLine()) != null) {
                //System.out.println(line);
                logField.appendText(">>> " + line + "\n");
                if(line.equals("Empty Device Address")){
                    logField.setStyle("-fx-background-color: red");
                    logField.appendText("ERROR >>> " + "Устройство не найдено. Подключите USRP B200 к USB3.\n");
                }
            }
            // ждем пока завершится вызванная программа
            // и сохраняем код, с которым она завершилась в
            // в переменную exitVal
            // необходимо для удержания программы от преждевременного закрытия
            int exitVal = process.waitFor();
        }
        catch (Exception e){
            logField.setStyle("-fx-background-color: red");
            logField.appendText("JAVA ERROR >>> " + e + "\n");
        }
    }

}