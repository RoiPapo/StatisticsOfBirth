package com.company;

import csv.CSVParser;
import csv.CSVRecord;
import csv.SEFileUtil;

import java.io.File;
import java.util.stream.IntStream;

public class BirthStatistics {

    public final String pathToDirCSVs;

    public BirthStatistics (String pathCSVs){
        pathToDirCSVs = pathCSVs;
    }

    /**
     * This method returns the path to the CSV file of the specified year
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
            if (rec.get(1).equals("M")) {
                male_sum += Integer.parseInt(rec.get(2));
            } else {
                female_sum += Integer.parseInt(rec.get(2));
            }
        }
        System.out.println("total births = " + total_sum);
        System.out.println("female girls = " + female_sum);
        System.out.println("male boys = " + male_sum);
    }

    public String getName(int year, int rank, String gender) {
        String name = "NO NAME";
        SEFileUtil seFileUtil = new SEFileUtil(getPathToCSV(year));
        csv.CSVParser parser = seFileUtil.getCSVParser();
        if (gender.equals("M")) {
            rank += getCsvRowOfMostPopularNameByGender(year, gender) - 1;
        }
        for (CSVRecord rec : parser) {
            if (rank == 1) {
                return rec.get(0);
            }
            rank--;
        }

        return name;
    }

    public int getRank(int year, String name, String gender) {
        int line_number = 0;
        int first_male_line = 0;
        int rank = -1;
        SEFileUtil seFileUtil = new SEFileUtil(getPathToCSV(year));
        csv.CSVParser parser = seFileUtil.getCSVParser();
        first_male_line = getCsvRowOfMostPopularNameByGender(year, gender);

        for (CSVRecord rec : parser) {
            line_number++;
            if (gender.equals("M")) {
                if (rec.get(1).equals(gender)) {
                    if (rec.get(0).equals(name)) {
                        rank = line_number - first_male_line + 1;
                    }
                }
            } else {
                if (rec.get(1).equals(gender)) {
                    if (rec.get(0).equals(name)) {
                        rank = line_number;
                    }
                }
            }
        }
        return rank;
    }

    public int yearOfHighestRank(int start_year, int end_year, String name, String gender) {
        int most_popular_year = -1;
        int current_popular_rank = Integer.MAX_VALUE;
        for (int year = start_year; year <= end_year; year++) {
            int curr = getRank(year, name, gender);
            if (current_popular_rank > curr || current_popular_rank == -1) {
                current_popular_rank = curr;
                most_popular_year = year;
            }
        }
        return most_popular_year;

    }

    public double getAverageRank(int start_year, int end_year, String name, String gender) {
        int[] year_list = IntStream.rangeClosed(start_year, end_year).toArray();
        double sum = 0;
        int current_rank = 0;
        double counter = 0;
        for (int year : year_list) {
            current_rank = getRank(year, name, gender);
            if (current_rank > 0) {
                counter++;
                sum += current_rank;
            }
        }
        if (sum > 0) {
            return (sum / counter);
        }
        return -1;
    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int entity_rank = getRank(year, name, gender);
        int sum = 0;
        int i = 1;
        SEFileUtil seFileUtil = new SEFileUtil(getPathToCSV(year));
        csv.CSVParser parser = seFileUtil.getCSVParser();
        int first_line_to_include = getCsvRowOfMostPopularNameByGender(year, gender);
        for (CSVRecord rec : parser) {
            if (first_line_to_include <= i && i < first_line_to_include + entity_rank-1) {
                sum += Integer.parseInt(rec.get(2));
            }
            i++;
        }
        return sum;
    }

    /**
     * @param args full path to folder of the data
     */
    public static void main(String[] args) {
//        BirthStatistics birthStatistics = new BirthStatistics(args[0]);
        BirthStatistics birthStatistics = new BirthStatistics("src/com/company/data");
        birthStatistics.totalBirths(2010);
        int rank = birthStatistics.getRank(2010, "Asher", "M");
        System.out.println("Rank is: " + rank);
        String name = birthStatistics.getName(2012, 10, "M");
        System.out.println("Name: " + name);
        System.out.println(birthStatistics.yearOfHighestRank(1880, 2010, "David", "M"));
        System.out.println(birthStatistics.yearOfHighestRank(1880, 2014, "Jennifer", "F"));
        System.out.println(birthStatistics.getAverageRank(1880, 2014, "Benjamin", "M"));
        System.out.println(birthStatistics.getAverageRank(1880, 2014, "Lois", "F"));
        System.out.println(birthStatistics.getTotalBirthsRankedHigher(2014, "Draco", "M"));
        System.out.print(birthStatistics.getTotalBirthsRankedHigher(2014, "Sophia", "F"));
        System.out.println();
    }


}
