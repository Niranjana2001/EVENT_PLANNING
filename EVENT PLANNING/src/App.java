import java.sql.*;

import java.util.*;

public class App{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hello");
        System.out.println("Do you want to register or login (Register : 1,Login : 2) : ");
        int a =Integer.parseInt(sc.nextLine());
        String username;
        String eventdate;
        String preferredlocation;
        String preferredcaterer;
        String preferred_decteam;
        if(a==1){
            username=userregistration();
            eventdate=dateavailability(username);
            preferredlocation=locationbooking(username);
            preferredcaterer=caterer(username);
            preferred_decteam=decoration(username);
            
        }else if(a==2){
            username=userLogin();
            eventdate=dateavailability(username);
            preferredlocation=locationbooking(username);
            preferredcaterer=caterer(username);
            preferred_decteam=decoration(username);
        }
        else{
            System.out.println("else is working");
        }
        



    }
    static String userregistration()  {
        String username=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            Scanner sc=new Scanner(System.in);
       
            System.out.println("Enter your details to register:");
            
            // Call the getUserInput() method to get the user input
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
    static String userLogin(){
        String username=null;
       
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter your Username :");
              username=sc.nextLine();
             String[] userDetails = new String[9];
             userDetails[0] = username;
             PreparedStatement stm1 = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
             stm1.setString(1, username);

          ResultSet resultSet = stm1.executeQuery();
            if (resultSet.next()) {
                System.out.println("Enter your Password: ");
                String password = sc.nextLine();
                String storedPassword = resultSet.getString("password");
                if (password.equals(storedPassword)) {
                    System.out.println("Login successful!");

                } else {
                    System.out.println("Incorrect Password");
                    System.out.println("Try again!!!");
                    

                }
                
            } else {
                System.out.println("Username does not exist. Login failed.");
                userregistration();
            }

        }catch(Exception e){
            System.out.println(e);
        }
       return username;
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
            
            
            
            
            // while (resultSet.next()) {
            //     ResultSetMetaData metaData = resultSet.getMetaData();
            //     int columnCount = metaData.getColumnCount();

            // }
            if(count>0){
                System.out.println("Event date not available");
                System.out.println("Sorry for the inconvenience");
            }else{
                locationbooking(username);
            }

            // Statement statement=connection.createStatement();
            // String sql = "INSERT INTO event (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
            // statement.executeUpdate(sql);
            
                      

        }
        
        catch(Exception e){
            System.out.println(e);
        }
        return eventdate;
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
                PreparedStatement prepstatement2=connection.prepareStatement("SELECT DISTINCT location.location_name,location.city FROM location JOIN user ON location.city = user.city WHERE user.city = ?");
                prepstatement2.setString(1,usercity);
                ResultSet rs2=prepstatement2.executeQuery();
                while(rs2.next()){
                    ResultSetMetaData metadata=rs2.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=1;i<column;i++){
                        System.out.println(rs2.getString(i) + "\t");
                    }
            }
            System.out.println("Please select your preferred location : ");
            preferredlocation=sc.nextLine();
            
        }catch(Exception e){
            System.out.println(e);
        }
        return preferredlocation;
            
    }


    static String caterer(String username){
        Scanner sc=new Scanner(System.in);
        System.out.println("Which is your food preference : \n1.Veg \n2.Non Veg \n3.Veg and Non Veg");
        int b = Integer.parseInt(sc.nextLine());
        String preferredcaterer=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prepstatement3=connection.prepareStatement("SELECT city FROM user WHERE username=?");
            prepstatement3.setString(1,username);
            ResultSet rs3=prepstatement3.executeQuery();
            rs3.next();
            String usercity=rs3.getString(1);
            switch(b){
                case 1:
                System.out.println("Case 1 is working");
                PreparedStatement prepstatement4 = connection.prepareStatement("SELECT DISTINCT enterprise_name,rate_per_head FROM caterer WHERE veg=1 AND location=?");
                prepstatement4.setString(1,usercity);
                ResultSet rs4=prepstatement4.executeQuery();
                System.out.println(usercity);
                while(rs4.next()){
                    System.out.println("while loop");
                    ResultSetMetaData metadata=rs4.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=1;i<=column;i++){
                        System.out.println(rs4.getString(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println("Please select your preferred caterer : ");
                preferredcaterer=sc.nextLine();
                break;
                case 2:
                System.out.println("Case 2 is working");
                PreparedStatement prepstatement5 = connection.prepareStatement("SELECT DISTINCT enterprise_name,rate_per_head FROM caterer WHERE non_veg=1 AND location=?");
                prepstatement5.setString(1,usercity);
                ResultSet rs5=prepstatement5.executeQuery();
                while(rs5.next()){
                    ResultSetMetaData metadata=rs5.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=1;i<=column;i++){
                        System.out.println(rs5.getString(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println("Please select your preferred caterer : ");
                preferredcaterer=sc.nextLine();
                break;
                case 3:
                System.out.println("Case 3 is working");
                PreparedStatement prepstatement6 = connection.prepareStatement("SELECT DISTINCT enterprise_name,rate_per_head FROM caterer WHERE veg=1 AND non_veg=0 AND location=?");
                prepstatement6.setString(1,usercity);
                ResultSet rs6=prepstatement6.executeQuery();
                while(rs6.next()){
                    ResultSetMetaData metadata=rs6.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=1;i<=column;i++){
                        System.out.println(rs6.getString(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println("Please select your preferred caterer : ");
                preferredcaterer=sc.nextLine();
                break;
                
            }
            
}
        catch(Exception e){
            System.out.println(e);
        }
        return preferredcaterer;
    }
    static String decoration(String username){
        String preferred_decteam=null;
        try{
            Scanner sc=new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement stm2=connection.prepareStatement("SELECT city FROM user WHERE username=?");
            stm2.setString(1,username);
            ResultSet res=stm2.executeQuery();
            res.next();
            String usercity=res.getString(1);
            PreparedStatement stm3 = connection.prepareStatement("SELECT DISTINCT enterprise_name,rate FROM decoration WHERE location=?");
            stm3.setString(1,usercity);
            ResultSet res1=stm3.executeQuery();
            System.out.println(usercity);

            while(res1.next()){
                System.out.println("while loop");
                ResultSetMetaData metadata=res1.getMetaData();
                int column=metadata.getColumnCount();
                for (int i=1;i<=column;i++){
                    System.out.println(res1.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println("Please select your preferred decoration team : ");
            preferred_decteam=sc.nextLine();
            


        }catch(Exception e){
            System.out.println(e);
        }
        return preferred_decteam;
    }
    static void eventStatus(){
        try{
            Scanner sc=new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement stm8=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
            // String Username=res.getString("username");
            stm8.setString(1,"Username");
            ResultSet rese=stm8.executeQuery();
            rese.next();
            String user_id=rese.getString(1);

            PreparedStatement stm9 = connection.prepareStatement("SELECT * FROM event WHERE user_id=?");
            stm9.setString(1,user_id);
            ResultSet res12=stm9.executeQuery();
            if(res12.next()){
                // displaying event details
                System.out.println("EVENT ID: "+res12.getInt("event_id"));
                System.out.println("EVENT NAME: "+res12.getString("event_name"));
                System.out.println("EVENT DATE: "+res12.getString("event_date"));
                PreparedStatement stm10=connection.prepareStatement("SELECT location_name FROM location WHERE location_id=?");
                int locationId = res12.getInt("location_id");
                 stm10.setString(1, String.valueOf(locationId));
                
                ResultSet res13=stm10.executeQuery();
                if (res13.next()) {
                    String locationName = res13.getString("location_name");
                    System.out.println(" EVENT LOCATION : " + locationName);
                }

                }
                // /check if the caterer has been booked
                boolean catererBooked=false;
                PreparedStatement stmt=connection.prepareStatement("SELECT COUNT(*) FROM event WHERE caterer_id=? AND event_id=?");
                
                 stmt.setString(1,"catererId");
                 stmt.setString(2,"eventId");
                 ResultSet rs14=stmt.executeQuery();
                 if(rs14.next()){
                    int count=rs14.getInt(1);
                    if(count>0){
                        catererBooked=true;
                    }
                 }
                 if(catererBooked){
                    System.out.println("The caterer has been booked");
                    
                 }else{
                    System.out.println("the caterer booking is pending");
                 }


                 boolean decorBooked=false;
                 PreparedStatement stmt1=connection.prepareStatement("SELECT COUNT(*) FROM event WHERE decoration_id=? AND event_id=?");
                  stmt1.setString(1,"decorationId");
                  stmt1.setString(2,"eventId");
                  ResultSet rs15=stmt1.executeQuery();
                  if(rs15.next()){
                     int count=rs15.getInt(1);
                     if(count>0){
                         decorBooked=true;
                     }
                  }
                  if(decorBooked){
                     System.out.println("The decor has been booked");
                     
                  }else{
                     System.out.println("The decor booking is pending");
                  }
                //   Checking payment status
                boolean advancePaid=false;
                boolean finalPaid=false;
                String advRef = res12.getString("adv_ref");
                PreparedStatement stmt2=connection.prepareStatement("SELECT COUNT(*) FROM event WHERE adv_ref=?");
                stmt2.setString(1, advRef);
                ResultSet rs16=stmt2.executeQuery();
                if(rs16.next()){
                    int count=rs16.getInt("adv_ref");
                    if(count>0){
                        advancePaid=true;

                    }

                }
                if(advancePaid){
                    System.out.println("Advance Payment is completed!!");

                }else{
                    System.out.println("Adavance payment is pending...");
                }
                String finalRef = res12.getString("final_ref");
                PreparedStatement stmt3=connection.prepareStatement("SELECT COUNT(*) FROM event WHERE final_ref=?");
                     stmt3.setString(1, finalRef);
                ResultSet rs17=stmt3.executeQuery();
                if(rs17.next()){
                    int count=rs17.getInt("final_ref");
                    if(count>0){
                        finalPaid=true;
                    }
                }
                if(finalPaid){
                    System.out.println("Final payment is completed!!");

                }else{
                    System.out.println("Final Payment is pending....");
                }





        }catch(Exception e){
            System.out.println(e);
        }
        

    }
    
}







        
    


























