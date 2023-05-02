import java.sql.*;
import java.util.*;

public class App{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hello");
        System.out.println("Do you want to register or login (Register : 1,Login : 2) : ");
        int a =Integer.parseInt(sc.nextLine());
        // String username="Niranjana2022";
        String username;
        if(a==1){
            username=userregistration();
            String eventdate=dateavailability(username);
            String preferredlocation=locationbooking(username);
        }else if(a==2){
            // username=userLogin();
            String eventdate=dateavailability(username);
            String preferredlocation=locationbooking(username);
        }else{
            System.out.println("Enter a valid input ,either 1 or 2");
        }
        // String preferredcaterer=caterer(username);
         



    }
    private static String userregistration()  {
        String username=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            Scanner sc=new Scanner(System.in);
       
            System.out.println("Enter your details to register:");
            
            System.out.println("Enter Username: ");
            username = sc.nextLine();
            String[] userDetails = new String[9];
            userDetails[0] = username;
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user WHERE username='" + username + "'";
            ResultSet resultSet = statement.executeQuery(sql);
        

        if(resultSet.next()) {
            System.out.println("Username already exists!!!");
            System.out.println("Enter another username:  ");
            username = sc.nextLine();
            userDetails[0] = username;
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
            userDetails = new String[]{username, password, name, address, city, email, phone_number, adhaar_number, account_number};
            
             sql = "INSERT INTO user (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
           statement.executeUpdate(sql);
           System.out.println("Success");


        }else{
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
            userDetails = new String[]{username, password, name, address, city, email, phone_number, adhaar_number, account_number};
            sql = "INSERT INTO user (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
           statement.executeUpdate(sql);

        }  System.out.println("User " + userDetails[0] + " registered successfully");
        
        //   System.out.println("Success");
            // String username=userDetails[0];
        } catch(Exception e){
            System.out.println(e);
        }
        return username;

    }
    static String locationbooking(String username){
        Scanner sc=new Scanner(System.in);
        String preferredlocation=null; 
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prepstatement1=connection.prepareStatement("SELECT city FROM user WHERE username=?");
            prepstatement1.setString(1,username);
            ResultSet rs1=prepstatement1.executeQuery();
            rs1.next();
            String usercity=rs1.getString(1);
            System.out.println("The following are the location available on the date given : ");
            PreparedStatement prepstatement2=connection.prepareStatement("SELECT location.location_name,location.city FROM location JOIN user ON location.city = user.city WHERE user.city = ?");
            prepstatement2.setString(1,usercity);
            ResultSet rs2=prepstatement2.executeQuery();
            while(rs2.next()){
                ResultSetMetaData metadata=rs2.getMetaData();
                int column=metadata.getColumnCount();
                for (int i=1;i<=column;i++){
                    System.out.println(rs2.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println("Please select your preferred location : ");
            preferredlocation=sc.nextLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return preferredlocation;   
    }

  
    
    static String dateavailability(String username) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Hello, World!");
        System.out.println("Enter the date of the event in the format yyyy-mm-dd");
        String eventdate=sc.nextLine();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            
            PreparedStatement prepstatement = connection.prepareStatement("SELECT COUNT(*) FROM event WHERE event_date=?");
            prepstatement.setString(1,eventdate);
          
            ResultSet rs=prepstatement.executeQuery();
            rs.next();
            int count=rs.getInt(1);
            if(count>0){
                System.out.println("Event date not available");
                System.out.println("Sorry for the inconvenience");
                // dateavailable=false;
            }else{
                System.out.println("Date is available");
                locationbooking(username);
            
        }}
        catch(Exception e){
            System.out.println(e);
        }
        return eventdate;
    }

//     static String caterer(String username){
//         Scanner sc=new Scanner(System.in);
//         System.out.println("Which is your food preference : \n1.Veg \n2.Non Veg \n3.Veg and Non Veg");
//         int b = Integer.parseInt(sc.nextLine());
//         String preferredcaterer=null;
//         try{
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
//             PreparedStatement prepstatement3=connection.prepareStatement("SELECT city FROM user WHERE username=?");
//             prepstatement3.setString(1,username);
//             ResultSet rs3=prepstatement3.executeQuery();
//             rs3.next();
//             String usercity=rs3.getString(1);
//             switch(b){
//                 case 1:
//                 System.out.println("Case 1 is working");
//                 PreparedStatement prepstatement4 = connection.prepareStatement("SELECT enterprise_name,rate_per_head FROM caterer WHERE veg=1 AND non_veg=0 AND location=?");
//                 prepstatement4.setString(1,usercity);
//                 ResultSet rs4=prepstatement4.executeQuery();
//                 System.out.println(usercity);
//                 while(rs4.next()){
//                     ResultSetMetaData metadata=rs4.getMetaData();
//                     int column=metadata.getColumnCount();
//                     for (int i=1;i<=column;i++){
//                         System.out.println(rs4.getString(i) + "\t");
//                     }
//                     System.out.println();
//                 }
//                 System.out.println("Please select your preferred caterer : ");
//                 preferredcaterer=sc.nextLine();
//                 break;
//                 case 2:
//                 System.out.println("Case 2 is working");
//                 PreparedStatement prepstatement5 = connection.prepareStatement("SELECT enterprise_name,rate_per_head FROM caterer WHERE veg=0 AND non_veg=1 AND location=?");
//                 prepstatement5.setString(1,usercity);
//                 ResultSet rs5=prepstatement5.executeQuery();
//                 while(rs5.next()){
//                     ResultSetMetaData metadata=rs5.getMetaData();
//                     int column=metadata.getColumnCount();
//                     for (int i=1;i<=column;i++){
//                         System.out.println(rs5.getString(i) + "\t");
//                     }
//                     System.out.println();
//                 }
//                 System.out.println("Please select your preferred caterer : ");
//                 preferredcaterer=sc.nextLine();
//                 break;
//                 case 3:
//                 System.out.println("Case 3 is working");
//                 PreparedStatement prepstatement6 = connection.prepareStatement("SELECT enterprise_name,rate_per_head FROM caterer WHERE veg=1 AND non_veg=0 AND location=?");
//                 prepstatement6.setString(1,usercity);
//                 ResultSet rs6=prepstatement6.executeQuery();
//                 while(rs6.next()){
//                     ResultSetMetaData metadata=rs6.getMetaData();
//                     int column=metadata.getColumnCount();
//                     for (int i=1;i<=column;i++){
//                         System.out.println(rs6.getString(i) + "\t");
//                     }
//                     System.out.println();
//                 }
//                 System.out.println("Please select your preferred caterer : ");
//                 preferredcaterer=sc.nextLine();
//                 break;
                
//             }
            
// }
//         catch(Exception e){
//             System.out.println(e);
//         }
//         return preferredcaterer;
//     }
    
}







        
    


























