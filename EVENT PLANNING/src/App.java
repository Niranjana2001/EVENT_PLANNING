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
}


        
    

























// import java.sql.*;

// public class App {
//     public static void main(String[] args) throws Exception {
        
    
//         System.out.println("Hello, World!");
//         try{
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
//             Statement statement = connection.createStatement();
//             ResultSet resultSet=statement.executeQuery("SELECT * FROM caterer");
//             while (resultSet.next()) {
//                 ResultSetMetaData metaData = resultSet.getMetaData();
//                 int columnCount = metaData.getColumnCount();
//                 for (int i = 1; i <= columnCount; i++) {
//                     System.out.print(resultSet.getString(i) + "\t");
//                 }
//                 System.out.println();
//             }
            

//         }
//         catch(Exception e){
//             System.out.println(e);
//         }
//     }
// }

    

