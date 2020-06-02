package data;

import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.Properties;

public class Ingestingtsdb {
    public static void main(String[] args) throws SQLException, IOException {
        Properties props = new Properties();
        props.put("jdbc.url", "jdbc:postgresql://localhost:5432/tutorial");
        props.put("user", "postgres");
        props.put("password", "java1234");

        Connection c=null;
        try {
             c = DriverManager.getConnection(props.getProperty("jdbc.url"), props);
            System.out.println("Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = "/home/jay/Downloads/datapop.txt";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String query = " insert into newbornsensex (origintime, newborn, city, country)"
                + " values (?, ?, ?, ?)";
        while((line = br.readLine()) != null){
            String [] splitted = line.split("\t");
            System.out.println(splitted[1]+" "+splitted[2]+" "+ splitted[3]+" "+splitted[4]);
            Instant instant = Instant.ofEpochSecond( Long.parseLong(splitted[1] ));
            java.sql.Timestamp ts = java.sql.Timestamp.from( instant );
            PreparedStatement preparedStatement =c.prepareStatement(query);
            preparedStatement.setTimestamp(1,ts);
            //preparedStatement.setString(1, instant.toString());
            preparedStatement.setInt(2, Integer.parseInt(splitted[2]));
            preparedStatement.setString(3, splitted[4]);
            preparedStatement.setString(4, splitted[3]);
            preparedStatement.execute();
        }
        br.close();

        c.close();
    }
}
