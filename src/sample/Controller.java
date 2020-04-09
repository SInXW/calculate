package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import po.Fraction;
import po.Result;
import service.GenerateExp;
import util.CalculateUtil;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    @FXML
    public TableColumn c1;
    public TableColumn c2;
    @FXML
    public AnchorPane main_frm;
    public Button input_file;
    public TextField question_num_input;
    public TextField question_range_input;
    public CheckBox add_select;
    public CheckBox subtract_select;
    public CheckBox multi_select;
    public CheckBox division_select;
    public Button generate_btn;
    public TableColumn question_result;
    public TableColumn question;
    public TableColumn question_id;
    public TableView<Result> question_table;
    public TextField question_exp_output;
    public TextField question_answer_output;
    public TextField question_input;
    public TextField answer_input;
    public TableColumn correct_column;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    /**生成题目
     *@author InX
     */
    public void generateQuestion(ActionEvent actionEvent) throws Exception {
        //获取输入
        int questionNum = Integer.parseInt(question_num_input.getText());
        int questionRange = Integer.parseInt(question_range_input.getText());
        GenerateExp generateExp = new GenerateExp();
        //检测运算类型
        List<Integer> opList = new ArrayList<>();
        if (add_select.isSelected()){
            opList.add(1);
        }
        if (subtract_select.isSelected()){
            opList.add(2);
        }
        if (multi_select.isSelected()){
            opList.add(3);
        }
        if (division_select.isSelected()){
            opList.add(4);
        }
        generateExp.generateOp(opList);
        //将题目刷新到表格
        ObservableList<Result> data = FXCollections.observableArrayList(generateExp.generateExpList(questionRange, questionNum, opList));
        question_table.setItems(data);
        question.setCellValueFactory(new PropertyValueFactory<Result, String>("expression"));
        question_id.setCellValueFactory(new PropertyValueFactory<Result, String>("id"));
        question_result.setCellValueFactory(new PropertyValueFactory<Result, String>("result"));
    }

    /**将题目写出
     *@author InX
     */
    public void outputQuestion(ActionEvent actionEvent) throws IOException {
        //获取表格里的数据
        ObservableList<Result> data = question_table.getItems();
        //写出操作
        File file = new File("Exercises.txt");
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("Exercises.txt"));
        question_exp_output.setText(file.getAbsolutePath());
        for(int i = 0; i < data.size(); i++){
            writer.write((i+1)+"、"+data.get(i).getExpression()+"\r\n");
        }
        writer.close();
    }

    /**将答案写出
     *@author InX
     */
    public void outputAnswer(ActionEvent actionEvent) throws IOException {
        //获取表格里的数据
        ObservableList<Result> data = question_table.getItems();
        File file = new File("Answers.txt");
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("Answers.txt"));
        question_answer_output.setText(file.getAbsolutePath());
        for(int i = 0; i < data.size(); i++){
            writer.write((i+1)+"、"+data.get(i).getResult()+"\r\n");
        }
        writer.close();
    }


    /**导入问题
     *@author SXH
     */
    public String inputQuestion(ActionEvent actionEvent) throws IOException {
        //获取路径并以字符串形式返回
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        question_input.setText(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**导入答案
     *@author SXH
     */
    public String inputAnswer(ActionEvent actionEvent) throws IOException {
        //获取路径并以字符串形式返回
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        answer_input.setText(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**将答案、问题文件导入，并刷新到列表，同时验证正确与否
     *@author InX
     */
    public void inputAll(ActionEvent actionEvent) throws Exception {
        //读取问题文件
        String questionPath = question_input.getText();
        File questionFile = new File(questionPath);
        FileReader questionReader = new FileReader(questionFile);
        BufferedReader questionBufferedReader = new BufferedReader(questionReader);
        //读取答案文件
        String answerPath = answer_input.getText();
        File answerFile = new File(answerPath);
        FileReader answerReader = new FileReader(answerFile);
        BufferedReader answerBufferedReader = new BufferedReader(answerReader);
        //将问题与答案关联，以供表格输出
        List<Result> resultList = new ArrayList<>();
        String exp = "";
        String ans = "";
        int i = 1;
        while((exp = questionBufferedReader.readLine()) != null && (ans = answerBufferedReader.readLine()) != null){
            Result result = new Result();
            exp = exp.substring(exp.indexOf("、")+1,exp.length());
            ans = ans.substring(ans.indexOf("、")+1,ans.length());

            result.setExpression(exp);
            result.setResult(ans);
            result.setId(i++);
            result.setCorrect(result.getResult().equals(Fraction.resultFormat(CalculateUtil.calculate(result.getExpression()))));
            resultList.add(result);
        }
        //将问题与答案刷新到表格
        ObservableList<Result> data = FXCollections.observableArrayList(resultList);
        question_table.setItems(data);
        question.setCellValueFactory(new PropertyValueFactory<Result, String>("expression"));
        question_result.setCellValueFactory(new PropertyValueFactory<Result, String>("result"));
        question_id.setCellValueFactory(new PropertyValueFactory<Result, String>("id"));
        correct_column.setCellValueFactory(new PropertyValueFactory<Result, String>("correct"));

        questionBufferedReader.close();
        answerBufferedReader.close();
        questionReader.close();
        answerReader.close();
    }

    /**导出检测文件
     *@author SXH
     */
    public void checkAnswer(ActionEvent actionEvent) throws Exception {
        ObservableList<Result> data = question_table.getItems();
        //检测的相关变量
        StringBuilder correct = new StringBuilder();
        StringBuilder wrong = new StringBuilder();
        int correctNum = 0;
        int wrongNum = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).isCorrect()){
                correct.append(","+(i+1));
                correctNum++;
            }else{
                wrong.append(","+(i+1));
                wrongNum++;
            }
        }
        correct.replace(0,1,"");
        wrong.replace(0,1,"");
        String coresult = "Correct: "+correctNum+"("+correct+")";
        String wrresult = "Wrong："+wrongNum+"("+wrong+")";

        //写出检测文件
        File file = new File("Grade.txt");
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("Grade.txt"));
        System.out.println(coresult);
        writer.write(coresult+"\r\n");
        writer.write(wrresult);
        writer.close();
    }


}
