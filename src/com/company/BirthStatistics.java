package com.company;

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

    public void totalBirths(int year) {
        int total_sum = 0;
        int male_sum = 0;
        int female_sum = 0;
        SEFileUtil seFileUtil = new SEFileUtil(getPathToCSV(year));
        csv.CSVParser parser = seFileUtil.getCSVParser();
        // loop over the data rows in the CSV file
        for (CSVRecord rec : parser) {
            total_sum += Integer.parseInt(rec.get(2));
            if (rec.get(1).equals("M") ) {
                male_sum+= Integer.parseInt(rec.get(2));
            } else {
                female_sum+= Integer.parseInt(rec.get(2));
            }
        }
        System.out.println("total births = "+total_sum);
        System.out.println("female girls = "+female_sum);
        System.out.println("male boys = "+male_sum);
    }
//
//    public int getRank(int year, String name, String gender) {
//
//    }
//
//    public String getName(int year, int rank, String gender) {
//
//    }
//
//    public int yearOfHighestRank(int start_year, int end_year, String name, String gender) {
//
//    }
//
//    public int getAverageRank(int start_year, int end_year, String name, String gender) {
//
//    }
//
//    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
//
//    }

    /**
     * @param args full path to folder of the data // noam
     */
    public static void main(String[] args) {
//        BirthStatistics birthStatistics = new BirthStatistics(args[0]);
        BirthStatistics birthStatistics = new BirthStatistics("src/com/company/data");
        birthStatistics.totalBirths(2010);
//        int rank = birthStatistics.getRank(2010, "Asher", "M");
//        System.out.println("Rank is: " + rank);
//        String name = birthStatistics.getName(2012, 10, "M");
//        System.out.println("Name: " + name);
//        System.out.println(birthStatistics.yearOfHighestRank(1880, 2010, "David", "M"));
//        System.out.println(birthStatistics.yearOfHighestRank(1880, 2014, "Jennifer", "F"));
//        System.out.println(birthStatistics.getAverageRank(1880, 2014, "Benjamin", "M"));
//        System.out.println(birthStatistics.getAverageRank(1880, 2014, "Lois", "F"));
//        System.out.println(birthStatistics.getTotalBirthsRankedHigher(2014, "Draco", "M"));
//        System.out.print(birthStatistics.getTotalBirthsRankedHigher(2014, "Sophia", "F"));
//        System.out.println();
    }


}
