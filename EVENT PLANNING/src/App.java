import java.sql.*;
import java.util.*;

public class App{
    
    public static void main(String[] args)  {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            Scanner sc=new Scanner(System.in);
       
            System.out.println("Enter your details to register:");
            
            // Call the getUserInput() method to get the user input
            String[] userDetails = getUserInput(sc);
            
            Statement statement=connection.createStatement();
            String sql = "INSERT INTO user (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
            statement.executeUpdate(sql);
            
            System.out.println("Success");
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    // Create a separate method to get user input
    public static String[] getUserInput(Scanner sc) {
        System.out.println("Enter Username: ");
        String username=sc.nextLine();
        System.out.println("Enter password:");
        String password=sc.nextLine();
        System.out.println("Enter name:");
        String name=sc.nextLine();
        System.out.println("Enter Address: ");
        String address=sc.nextLine();
        System.out.println("City : ");
        String city=sc.nextLine();
        System.out.println("Enter Email :");
        String email=sc.nextLine();
        System.out.println("Enter phoneNumber :");
        String phone_number=sc.nextLine();
        System.out.println("Enter Adhaar Number :");
        String adhaar_number=sc.nextLine();
        System.out.println("Enter Account number:");
        String account_number=sc.nextLine();
        
        String[] userDetails = {username, password, name, address, city, email, phone_number, adhaar_number, account_number};
        
        return userDetails;
    }
    
    static void dateavailability(String[] args) throws Exception {
        Scanner scan=new Scanner(System.in);
        System.out.println("Hello, World!");
        System.out.println("Enter the date of the event in the format yyyy-mm-dd");
        String eventdate=scan.nextLine();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            
            PreparedStatement prepstatement = connection.prepareStatement("SELECT COUNT(*) FROM event WHERE event_date=?");
            prepstatement.setString(1,eventdate);
            // ResultSet resultSet=statement.executeQuery("SELECT * FROM caterer");
            ResultSet rs=prepstatement.executeQuery();
            rs.next();
            int count=rs.getInt(1);
            boolean dateavailable=true;
            // while (resultSet.next()) {
            //     ResultSetMetaData metaData = resultSet.getMetaData();
            //     int columnCount = metaData.getColumnCount();
            //     for (int i = 1; i <= columnCount; i++) {
            //         System.out.print(resultSet.getString(i) + "\t");
            //     }
            //     System.out.println();
            // }
            if(count>0){
                System.out.println("Event date not available");
                dateavailable=false;
            }
                      

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}







        
    


























