package com.alami.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;


public class App 
{
    public static void main( String[] args )throws InterruptedException,IOException,CsvException,Exception
    {
        ExecutorService pool=Executors.newFixedThreadPool(4);
        List<Future<List<String[]>>> listOfFutures = new ArrayList<Future<List<String[]>>>();
        FileReader is = new FileReader("Before Eod.csv");
        BufferedReader br = null;
        br = new BufferedReader(is);
        int count=0;
        String line;
            while((line=br.readLine())!=null){
                if (count==0){
                    count++;
                    continue;
                }
            Future< List<String[]>> future=pool.submit(new Task(line));
            listOfFutures.add(future);
            }   

        List<String[]> data = new ArrayList<String[]>();

        for (Future<List<String[]>> future : listOfFutures) {

            List<String[]> t=future.get();

            data.add(t.get(0));
        }
        UpdateCsv(data);
        pool.shutdown(); 
    }
  static void UpdateCsv(List<String[]> arr)throws IOException{

    try{
      FileWriter outputfile = new FileWriter("After Eod.csv");

      String[] header = { "id","Nama","Age","Balanced","No 2b Thread-No","No 3 Thread-No","Previous Balanced","Average Balanced","No 1 Thread-No","Free Transfer","No 2a Thread-No"};
     

      CSVWriter writer = new CSVWriter(outputfile,';',
                                  CSVWriter.NO_QUOTE_CHARACTER,
                                  CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                  CSVWriter.DEFAULT_LINE_END);
                                  writer.writeNext(header);                     
      writer.writeAll(arr);

      writer.close();
    }catch(IOException e){
            System.out.println(e);
    }
    
  }

}
