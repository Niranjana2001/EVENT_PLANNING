import java.sql.*;
import java.util.*;

public class App{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hello");
        System.out.println("Do you want to register or login (Register : 1,Login : 2) : ");
        int a =Integer.parseInt(sc.nextLine());
        if(a==1){
            userregistration();
        }else{
            System.out.println("else is working");
        }
        String eventdate=dateavailability(username);
        String preferredlocation=locationbooking(username);

        



    }
    static void userregistration()  {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            Scanner sc=new Scanner(System.in);
       
            System.out.println("Enter your details to register:");
            
            // Call the getUserInput() method to get the user input
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
            
            Statement statement=connection.createStatement();
            String sql = "INSERT INTO user (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
            statement.executeUpdate(sql);
            
            System.out.println("Success");
        
        } catch(Exception e){
            System.out.println(e);
        }
        
    }
    
  
    
    static String dateavailability(String username) throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("Hello, World!");
        System.out.println("Enter the date of the event in the format yyyy-mm-dd");
        String eventdate=sc.nextLine();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            
            PreparedStatement prepstatement = connection.prepareStatement("SELECT COUNT(*) FROM event WHERE event_date=?");
            prepstatement.setString(1,eventdate);
            // ResultSet resultSet=statement.executeQuery("SELECT * FROM caterer");
            ResultSet rs=prepstatement.executeQuery();
            rs.next();
            int count=rs.getInt(1);
            // boolean dateavailable=true;
            
            
            
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
                System.out.println("Sorry for the inconvenience");
                // dateavailable=false;
            }else{
                locationbooking(username);

            // Statement statement=connection.createStatement();
            // String sql = "INSERT INTO event (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
            // statement.executeUpdate(sql);
            return eventdate;
                      

        }}
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    static String locationbooking(String username){
        Scanner sc=new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prepstatement1=connection.prepareStatement("SELECT city FROM user WHERE username=?");
            prepstatement1.setString(1,username);
            ResultSet rs1=prepstatement1.executeQuery();
            rs1.next();
            String usercity=rs1.getString(0);
            System.out.println("The following are the location available on the date given : ");
                PreparedStatement prepstatement2=connection.prepareStatement("SELECT location.location_name,location.city FROM location JOIN user ON location.city = user.city WHERE user.city = ?");
                prepstatement2.setString(1,usercity);
                ResultSet rs2=prepstatement2.executeQuery();
                while(rs2.next()){
                    ResultSetMetaData metadata=rs2.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=0;i<=column;i++){
                        System.out.println(rs2.getString(i) + "\t");
                    }
            }
            System.out.println("Please select your preferred location : ");
            String preferredlocation=sc.nextLine();
            return preferredlocation;
        }catch(Exception e){
            System.out.println(e);
        }
        
            
    }

}







        
    


























