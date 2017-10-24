import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class StudentRecord {

    static String DB_URL = "jdbc:mysql://localhost/test";

    static String USER = "test";
    static String PASS = "password";

    Scanner sc;
    Connection con;
    Statement st;

    String name;
    int roll;
    String dpt;
    int m;

    public StudentRecord() {
        sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL,USER,PASS);
            st = con.createStatement();
        } catch (SQLException e) {
            System.out.println("please check database connection, parameters");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println("please check you have correct CLASSPATH");
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        StudentRecord tt = new StudentRecord();

        int choice;


        while(true) {

            System.out.println("1. New Record \n"
                    + "2. list students \n"
                    + "3. sorted list based on mark \n"
                    + "4. Quit\n");

            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch(choice) {
                case 1:
                    StudentRecord st = new StudentRecord();
                    st.readNewRecord();
                    break;
                case 2:
                    tt.listStudents();
                    break;
                case 3:
                    tt.sortedListStudents();
                    break;
                default:
                    choice = -1;
            }
            if (choice == -1) {
                break;
            }
        }

    }

    void readNewRecord() {
        System.out.println("name: ");
        name = sc.next();
        System.out.println("roll: ");
        roll = sc.nextInt();
        System.out.println("dpt: ");
        dpt = sc.next();
        System.out.println("mark: ");
        m = sc.nextInt();
        insertRecord();
    }

    void insertRecord() {
        String sql = "insert into student values('"+name+"', '"+roll+"','"+dpt+"','"+m+"');";
        try {
            int f = st.executeUpdate(sql);
        } catch(SQLException e) {
            System.out.println("Error in sql");
            return;
        }
    }

    void print() {
        System.out.println("Name: " +name+"\t Roll: " +roll+"\t Dpt:"+dpt+"\t Mark: " +m);
    }

    void listStudents() {
        String sql = "SELECT * FROM student";
        try {
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                StudentRecord st = new StudentRecord();
                st.name = rs.getString("name");
                st.roll = rs.getInt("roll");
                st.dpt = rs.getString("dpt");
                st.m = rs.getInt("m");
                st.print();
            }
        } catch(Exception e) {
            return;
        }
    }

    void sortedListStudents() {
        String sql = "SELECT * FROM student order by m desc";
        try {
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                StudentRecord st = new StudentRecord();
                st.name = rs.getString("name");
                st.roll = rs.getInt("roll");
                st.dpt = rs.getString("dpt");
                st.m = rs.getInt("m");
                st.print();
            }
        } catch(Exception e) {
            return;
        }
    }
}
