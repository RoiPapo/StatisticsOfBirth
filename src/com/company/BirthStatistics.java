package com.company;

import csv.CSVParser;
import csv.CSVRecord;
import csv.SEFileUtil;

import java.io.File;

public class BirthStatistics {

    public final String pathToDirCSVs;

    public BirthStatistics(String pathCSVs) {
        pathToDirCSVs = pathCSVs;
    }

    /**
     * This method returns the path to the CSV file of the specified year
     *
     * @param year
     * @return
     */
    private String getPathToCSV(int year) {
        File[] csvFiles = new File(pathToDirCSVs).listFiles();
        for (File csvF : csvFiles) {
            if (csvF.getName().contains(Integer.toString(year))) {
                return csvF.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * This method returns the row number in the CSV file of the most popular name by the given gender
     *
     * @param year
     * @param gender
     * @return
     */
    private int getCsvRowOfMostPopularNameByGender(int year, String gender) {
        int rank = -1;
        SEFileUtil seFileUtil = new SEFileUtil(getPathToCSV(year));
        for (CSVRecord record : seFileUtil.getCSVParser()) {
            String currGender = record.get(1);
            if (currGender.equals(gender)) {
                rank = (int) record.getRecordNumber();
                break;
            }
        }
        return rank;
    }

    public int totalBirths(int year){

    }

    public int getRank(int year, String name, String gender){

    }

    public String getName(int year, int rank, String gender){

    }

    public int yearOfHighestRank(int start_year, int end_year, String name, String gender){

    }

    public int getAverageRank(int start_year, int end_year, String name, String gender){

    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender){

    }

    public static void main(String[] args) {
        BirthStatistics birthStatistics = new BirthStatistics(args[0]);
        System.out.println("hello world");
        birthStatistics.totalBirths(2010);
        int rank = birthStatistics.getRank(2010, "Asher", "M");
        System.out.println("Rank is: " + rank);
        String name = birthStatistics.getName(2012, 10, "M");
        System.out.println("Name: " + name);
        System.out.println(birthStatistics.yearOfHighestRank(1880, 2014,"David", "M"));
        System.out.println(birthStatistics.yearOfHighestRank(1880, 2014,"Jennifer", "F"));
        System.out.println(birthStatistics.getAverageRank(1880, 2014, "Benjamin", "M"));
        System.out.println(birthStatistics.getAverageRank(1880,2014, "Lois", "F"));
        System.out.println(birthStatistics.getTotalBirthsRankedHigher(2014, "Draco", "M"));
        System.out.print(birthStatistics.getTotalBirthsRankedHigher(2014, "Sophia", "F"));
    }


}
