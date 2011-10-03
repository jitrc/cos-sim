package ru.cos.nissan.graphs.export;

import ru.cos.sim.meters.framework.TimePeriod;

import java.io.*;
import java.util.List;

public class CSVImportExport {
    public void exportToFile(float[][] data, File toCSVFile) throws IOException {
        OutputStream output = new FileOutputStream(toCSVFile);
        for(float[] datum : data){
            output.write(buildString(datum));
            output.write("\n".getBytes("UTF-8"));
        }
        output.close();
    }

    public void exportToFile(List<float[][]> list, File toCVSFile) throws IOException {
        OutputStream output = new FileOutputStream(toCVSFile);
        for(float[][] data : list){
            for(float[] datum : data){
                output.write(buildString(datum));
                output.write("\n".getBytes("UTF-8"));
            }
            output.write(buildNewLine());
        }
        output.close();
    }

    public void exportToFileRow(float[][] data, File toCSVFile) throws IOException{
        OutputStream output = new FileOutputStream(toCSVFile);
        int arrayLength = data.length;
        if(arrayLength > 0){
            int arrayWidth = data[arrayLength - 1].length;
            for(int k = 0; k < arrayWidth; k++){
                for (float[] aData : data) {
                    output.write(buildLongString(aData, k));
                }
                output.write("\n".getBytes("UTF-8"));
            }
        }
        output.close();
    }

    public void exportToFileRow(List<float[][]> list, File toCSVFile) throws IOException{
        OutputStream output = new FileOutputStream(toCSVFile);
        for(float[][] data : list){
            int arrayLength = data.length;
            if(arrayLength > 0){
                int arrayWidth = data[arrayLength - 1].length;
                for(int k = 0; k < arrayWidth; k++){
                    for(float[] aData : data){
                        output.write(buildLongString(aData, k));
                    }
                    output.write("\n".getBytes("UTF-8"));
                }
            }
            output.write("\n".getBytes("UTF-8"));
        }
        output.close();
    }

    public void exportToFileColumns(float[][] data, TimePeriod timePeriod, File toCSVFile) throws IOException{
        OutputStream output = new FileOutputStream(toCSVFile);
        output.write(buildTimePeriodString(timePeriod));
        output.write("\n".getBytes("UTF-8"));
        
        for(float[] datum : data){
            output.write(buildString(datum));
            output.write("\n".getBytes("UTF-8"));
        }
        output.close();
    }

    public void exportToFileColumns(List<float[][]> list, List<TimePeriod> periods, File toCSVFile) throws IOException{
        OutputStream output = new FileOutputStream(toCSVFile);
        int maxColumnLength = getMaxColumnLength(list);
        int rowLength = getRowLength(list.get(0));

        for(TimePeriod t : periods){
            output.write(buildTimePeriodString(t));
            for(int k = 0; k < rowLength + 1; k++){
                output.write(";".getBytes("UTF-8"));
            }
        }
        output.write("\n".getBytes("UTF-8"));

        for(int i = 0; i < maxColumnLength; i++){
            for(float[][] data : list){
                if(i < data.length){
                    output.write(buildString(data[i]));
                }
                else{
                    for(int k = 0; k < rowLength; k++)
                        output.write(";".getBytes("UTF-8"));
                }
                output.write(";".getBytes("UTF-8"));
            }
            output.write("\n".getBytes("UTF-8"));
        }

        output.close();
    }

    private byte[] buildString(float[] datum) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for(float d : datum){
            builder.append(d)
                    .append(";");
        }
//        builder.append("\n");
        return builder.toString().getBytes("UTF-8");
    }

    private byte[] buildLongString(float[] data, int index) throws UnsupportedEncodingException {
        return (Float.toString(data[index]) + ";").getBytes("UTF-8"); 
    }

    private byte[] buildNewLine() throws UnsupportedEncodingException {
        return "\n".getBytes("UTF-8");
    }

    private byte[] buildTimePeriodString(TimePeriod period) throws UnsupportedEncodingException {
        return (Float.toString(period.getTimeFrom()) + " - " + Float.toString(period.getTimeTo())).getBytes("UTF-8");
    }

    private int getMaxColumnLength(List<float[][]> data){
        int maxColumnLength = 0;
        for(float[][] d : data){
            if(d.length > maxColumnLength)
                maxColumnLength = d.length;
        }
        return maxColumnLength;
    }

    private int getRowLength(float[][] data){
        if(data.length == 0) return 0;
        else return data[0].length;
    }
}
