package com.alami.app;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.alami.model.Balance;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public  class Task implements Callable<List<String[]>>{
  private String line;
  public Task(String line){
    this.line=line;
  }

  @Override
  public List<String[]> call() throws Exception {
    // TODO Auto-generated method stub
    List<String[]> arr=new ArrayList<String[]>();
    try{
     
      Balance case1=calculateAverage(line);
       arr=ProsesUpdateToAfterEod(case1);
    }catch(IOException e){
        System.out.println(e);
    }
    return arr;
  }


  static Balance calculateAverage(String line){
    long threadID=Thread.currentThread().getId();
    Balance task =new Balance();
    String[] split=line.split(";");
  
    int averageBalance=(Integer.parseInt(split[3])+Integer.parseInt(split[4]))/2;
    task.setId(split[0]);
    task.setAverageBalance(averageBalance);
    task.setNoThread(threadID);
    task.setBalance(Integer.parseInt(split[3]));
    if(task.getBalance()>=100 && task.getBalance()<=150){
      task.setFreeTransfer(5);
    }else if(task.getBalance()>150){
      task.setFreeTransfer(25);
    }
    
    return task;
  }

  static List<String[]> ProsesUpdateToAfterEod(Balance balance)throws IOException{
    List<String[]> data = new ArrayList<String[]>();
    try{
      FileReader filereader = new FileReader("After Eod.csv");
      
      CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
      CSVReader products = new CSVReaderBuilder(filereader).withCSVParser(parser).build();;
      List<String[]> allData = products.readAll();
      
  
      for (String[] row : allData) {
       
          if (row[0].equalsIgnoreCase(balance.getId())){
            String thread=String.valueOf(balance.getNoThread());
              row[7]=String.valueOf(balance.getAverageBalance());
              row[8]=thread;
              row[4]=thread;
              row[10]=thread;
              row[9]=String.valueOf(balance.getFreeTransfer());
              if (Integer.parseInt(row[0])>=1 && Integer.parseInt(row[0])<=100){
                row[3]=String.valueOf(Integer.parseInt(row[3])+10);
                row[5]=thread;
              }
              data.add(row);
          }
         
      }
      
      
    }catch(CsvException e){

    }
    return data;
  }
 
}
