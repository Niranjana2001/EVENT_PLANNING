import java.sql.*;
import java.io.*;
import java.util.*;

public class App{

    private static Scanner sc =new Scanner(System.in);
    public static void main(String[] args){
        String filename="Event details.txt";
        System.out.println("Hello");
        String username="";
        System.out.println("Do you want to register or login (Register : 1,Login : 2) : ");
        int a =Integer.parseInt(sc.nextLine());
        if(a==1){
            username=userregistration();
        }
        else if(a==2){
            username=userLogin();
            if(username==null){
                System.out.println("Password incorrect.Please try again");
        }}
        else{
            System.out.println("Give valid number");
        }
        System.out.println("What kind of an event is this : \n1.Wedding \n2.Anniversary Party \n3.Birthday Party");
        int option=Integer.parseInt(sc.nextLine());
        String eventtype=eventtype(option);
        System.out.println("Enter the number of guests you are expecting : ");
        int guest=Integer.parseInt(sc.nextLine());
        System.out.println("1.Date Availability \n2. Location Booking \n3.Caterer Booking \n4.Decoration team Booking \n5.Event Status \n6.Cancellation \nChoose an option");
        int choice=Integer.parseInt(sc.nextLine());
        String eventdate="";
        int location_id=0;
        int caterer_id=0;
        int decoration_id=0;
        String adv_ref=null;
        String final_ref;
        boolean cancellation=false;
        switch(choice){
            case 1 : 
            eventdate=dateavailability(username);
            break;
            case 2:
            System.out.println("What is the date of your event : ");
            eventdate=sc.nextLine();
            location_id=locationbooking(username,eventdate);
            if(location_id!=0){
                System.out.println("Please give the reference number of the advance payment(location rate) to proceed further : ");
            adv_ref=sc.nextLine(); 
            eventtableupdates(username, eventdate, location_id, adv_ref, eventtype, guest);
            }
            break;
            case 3:
            System.out.println("What is the date of your event : ");
            eventdate=sc.nextLine();
            caterer_id=caterer(username);
            eventtableupdates(eventdate,username,caterer_id);
            break;
            case 4:
            System.out.println("What is the date of your event : ");
            eventdate=sc.nextLine();
            decoration_id=decoration(username);
            System.out.println("Please give the reference number of the final payment : ");
            final_ref=sc.nextLine();
            eventtableupdates(eventdate,username, decoration_id, final_ref);
            break;
            case 5:
            eventStatus(username, filename, cancellation);
            break;
            case 6:
            cancellation=cancellation(username);
            break;
            default:
            System.out.println("Enter a valid choice");
            break;
        }
}
    static String eventtype(int option){
        String type;
        if(option==1){
            type="Wedding";
            return type;
        }else if(option==2){
            type="Anniversary Party";
            return type;
        }else if(option==3){
            type="Birthday Party";
            return type;
        }else{
            System.out.println("Enter valid option");
            return null;
        }
    }
    static String userregistration()  {
        String username=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            // Scanner sc=new Scanner(System.in);
       
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
    static String userLogin(){
        String username=null;
        String password;
        boolean passwordcorrect=false;
        String usernamedupe=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            // Scanner sc=new Scanner(System.in);
            System.out.println("Enter your Username :");
              username=sc.nextLine();
             String[] userDetails = new String[9];
             userDetails[0] = username;
             PreparedStatement stm1 = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
             stm1.setString(1, username);

          ResultSet resultSet = stm1.executeQuery();
            if (resultSet.next()) {
                String pass;
                System.out.println("Enter your Password: ");
                pass = sc.nextLine();
                String storedPassword = resultSet.getString("password");
                passwordcorrect=passwordverification(pass,storedPassword);
                
            } else {
                System.out.println("Username does not exist.Try again.");
                System.out.println("Enter your correct username : ");
                String username1=sc.nextLine();
                userDetails[0]=username1;
                PreparedStatement stm7 = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
                stm7.setString(1, username1);
                ResultSet resultSet1 = stm7.executeQuery();
                if(resultSet1.next()){
                    String pass1;
                    System.out.println("Enter your Password: ");
                    pass1 = sc.nextLine();
                    String storedPassword1 = resultSet1.getString("password");
                    passwordcorrect = passwordverification(pass1,storedPassword1); 
                    
                }else{
                    System.out.println("The username is wrong again.Please register.");
                    userregistration();
                }  
            }
        }catch(Exception e){
            System.out.println(e);
        }
        if(passwordcorrect==true){
            return username;
        }
        return usernamedupe;  
    }
    static boolean passwordverification(String password1,String password){
        boolean passwordcorrect=false;
        boolean passwordverified=true;
        while(!passwordcorrect){
            if(password1.equals(password)){
                System.out.println("Password correct.Login successful.");
                passwordcorrect=true;
            }else{
                // System.out.println("Password incorrect,please try again.");
                passwordverified=false;
                break;
                
            }   
        }
        return passwordverified;  
    }
    static String dateavailability(String username) {
        // Scanner sc=new Scanner(System.in);
        System.out.println("Enter the date of the event in the format yyyy-mm-dd");
        String eventdate=sc.nextLine();
        String datenotavailable=null;
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
            
            }else{
                datenotavailable=eventdate;
                System.out.println("We have a slot available. You can proceed to checkout the different locations.");
                // locationbooking(username);
            }
        }
        
        catch(Exception e){
            System.out.println(e);
        }
        return datenotavailable;
    }

    static int locationbooking(String username,String event_date){
        // Scanner sc=new Scanner(System.in);
        // String event_date=dateavailability(username);
        int location_id=0;
        if(!event_date.equals("")){
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
                PreparedStatement prepstatement2=connection.prepareStatement("SELECT DISTINCT location.location_name,location.city,location.rate FROM location JOIN user ON location.city = user.city WHERE user.city = ?");
                prepstatement2.setString(1,usercity);
                ResultSet rs2=prepstatement2.executeQuery();
                while(rs2.next()){
                    ResultSetMetaData metadata=rs2.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=1;i<=column;i++){
                        System.out.println(rs2.getString(i) + "\t");
                    }
            }
            System.out.println("Please select your preferred location : ");
            preferredlocation=sc.nextLine();
            System.out.println("Please make an advance payment for booking the location");
            PreparedStatement prep1=connection.prepareStatement("SELECT location_id FROM location WHERE location_name=?");
            prep1.setString(1,preferredlocation);
            ResultSet rset1=prep1.executeQuery();
            rset1.next();
            location_id=rset1.getInt(1);
            
        }catch(Exception e){
            System.out.println(e);
        }
        return location_id;
    }else{
        System.out.println("Please confirm whether the date slot is free before booking the location.");
        return location_id; 
    }
            
    }

    static void eventtableupdates(String username,String eventdate,int location_id,String adv_ref,String event_type,int guest){
        // Scanner sc=new Scanner (System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prep5 = connection.prepareStatement("INSERT INTO event (event_name,event_date,guest_number,user_id,location_id,adv_ref) VALUES (?, ?, ?, ?,?,?)");
            // System.out.println("What kind of an event is this : \n1.Wedding \n2.Anniversary Party \n3.Birthday Party");
            // int op=Integer.parseInt(sc.nextLine());
            // switch(op){
            //     case 1:
            //     prep5.setString(1,"Wedding");
            //     break;
            //     case 2:
            //     prep5.setString(1,"Anniversary Party");
            //     break;
            //     case 3:
            //     prep5.setString(1,"Birthday Party");
            //     break;
            // }
            prep5.setString(1,event_type);
            prep5.setString(2,eventdate);
            // System.out.println("How many guests are you expecting : ");
            // int guest=Integer.parseInt(sc.nextLine());
            prep5.setInt(3,guest);
            PreparedStatement prep6=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
            prep6.setString(1,username);
            ResultSet rset6=prep6.executeQuery();
            rset6.next();
            int user_id=rset6.getInt(1);
            prep5.setInt(4,user_id);
            prep5.setInt(5,location_id);
            prep5.setString(6,adv_ref);
            prep5.executeUpdate();   
        } catch (Exception e) {
            System.out.println(e);
        }
        }
    
    
    
    
    static int caterer(String username){
        // Scanner sc=new Scanner(System.in);
        System.out.println("Which is your food preference : \n1.Veg \n2.Non Veg \n3.Veg and Non Veg");
        int b = Integer.parseInt(sc.nextLine());
        String preferredcaterer=null;
        int caterer_id=0;
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
                System.out.println("The following are the caterers available on the date given : ");
                PreparedStatement prepstatement4 = connection.prepareStatement("SELECT DISTINCT enterprise_name,rate_per_head FROM caterer WHERE veg=1 AND location=?");
                prepstatement4.setString(1,usercity);
                ResultSet rs4=prepstatement4.executeQuery();
                while(rs4.next()){
                    ResultSetMetaData metadata=rs4.getMetaData();
                    int column=metadata.getColumnCount();
                    for (int i=1;i<=column;i++){
                        System.out.println(rs4.getString(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println("Please select your preferred caterer : ");
                preferredcaterer=sc.nextLine();
                PreparedStatement prep2=connection.prepareStatement("SELECT caterer_id FROM caterer WHERE enterprise_name=?");
                prep2.setString(1,preferredcaterer);
                ResultSet rset2=prep2.executeQuery();
                rset2.next();
                caterer_id=rset2.getInt(1);
                break;
                case 2:
                System.out.println("The following are the caterers available on the date given : ");
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
                PreparedStatement prep7=connection.prepareStatement("SELECT caterer_id FROM caterer WHERE enterprise_name=?");
                prep7.setString(1,preferredcaterer);
                ResultSet rset8=prep7.executeQuery();
                rset8.next();
                caterer_id=rset8.getInt(1);
                break;
                case 3:
                System.out.println("The following are the caterers available on the date given : ");
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
                PreparedStatement prep9=connection.prepareStatement("SELECT caterer_id FROM caterer WHERE enterprise_name=?");
                prep9.setString(1,preferredcaterer);
                ResultSet rset10=prep9.executeQuery();
                rset10.next();
                caterer_id=rset10.getInt(1);
                break;
                
            }
            
}
        catch(Exception e){
            System.out.println(e);
        }
        return caterer_id;
    }

    static void eventtableupdates(String eventdate,String username,int caterer_id){
        // Scanner sc=new Scanner (System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prep5 = connection.prepareStatement("UPDATE event SET caterer_id = ? WHERE user_id = ? AND event_date=?");
          
            prep5.setInt(1,caterer_id);
            prep5.setString(3,eventdate);

            PreparedStatement prep6=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
            prep6.setString(1,username);
            ResultSet rset6=prep6.executeQuery();
            rset6.next();
            int user_id=rset6.getInt(1);
            prep5.setInt(2,user_id);
           
            prep5.executeUpdate();   
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    static int decoration(String username){
        String preferred_decteam=null;
        int decoration_id=0;
        try{
            // Scanner sc=new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement stm2=connection.prepareStatement("SELECT city FROM user WHERE username=?");
            stm2.setString(1,username);
            ResultSet res=stm2.executeQuery();
            res.next();
            String usercity=res.getString(1);
            System.out.println("The following are the decoration teams available on the date given : ");
            PreparedStatement stm3 = connection.prepareStatement("SELECT DISTINCT enterprise_name,rate FROM decoration WHERE location=?");
            stm3.setString(1,usercity);
            ResultSet res1=stm3.executeQuery();
            while(res1.next()){
                ResultSetMetaData metadata=res1.getMetaData();
                int column=metadata.getColumnCount();
                for (int i=1;i<=column;i++){
                    System.out.println(res1.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println("Please select your preferred decoration team : ");
            preferred_decteam=sc.nextLine();
            PreparedStatement prep3=connection.prepareStatement("SELECT decoration_id FROM decoration WHERE enterprise_name=?");
            prep3.setString(1,preferred_decteam);
            ResultSet rset4=prep3.executeQuery();
            rset4.next();
            decoration_id=rset4.getInt(1);


        }catch(Exception e){
            System.out.println(e);
        }
        return decoration_id;
    }


    static void eventtableupdates(String eventdate,String username,int decoration_id,String final_ref){
        // Scanner sc=new Scanner (System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prep5 = connection.prepareStatement("UPDATE event SET decoration_id = ?  WHERE user_id = ? AND event_date=?");
            PreparedStatement prepare1=connection.prepareStatement("UPDATE event SET final_ref = ?  WHERE user_id = ? AND event_date=?");
            prepare1.setString(1,final_ref);
            prepare1.setString(3,eventdate);
            PreparedStatement prep6=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
            prep6.setString(1,username);
            ResultSet rset6=prep6.executeQuery();
            rset6.next();
            int user_id=rset6.getInt(1);
            prepare1.setInt(2,user_id);
            prepare1.executeUpdate();
            prep5.setString(3,eventdate);
            prep5.setInt(2,user_id);
            prep5.setInt(1,decoration_id);
            prep5.executeUpdate();   
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    static void eventStatus(String username,String filename,boolean cancellation){
        try{
            // Scanner sc=new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement stm8=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
            stm8.setString(1,username);
            ResultSet rese=stm8.executeQuery();
            rese.next();
            int user_id=rese.getInt("user_id");

            PreparedStatement stm9 = connection.prepareStatement("SELECT * FROM event WHERE user_id=?");
            stm9.setInt(1,user_id);
            ResultSet res12=stm9.executeQuery();
            String locationName="";
            String caterername="";
            String decteamname="";
            String final_ref="";
            if(res12.next()){
                // displaying event details
                System.out.println("EVENT ID: "+res12.getInt("event_id"));
                System.out.println("EVENT NAME: "+res12.getString("event_name"));
                System.out.println("EVENT DATE: "+res12.getString("event_date"));
                PreparedStatement stm10=connection.prepareStatement("SELECT location_name FROM location WHERE location_id=?"); 
                int locationId = res12.getInt("location_id");
                stm10.setInt(1, locationId);
                ResultSet res13=stm10.executeQuery();
                if (res13.next()) {
                    locationName = res13.getString("location_name");
                    System.out.println("EVENT LOCATION : " + locationName);
                }

                }
                // /check if the caterer has been booked
                boolean catererBooked=false;
                PreparedStatement stmt11=connection.prepareStatement("SELECT COUNT(*) FROM event WHERE caterer_id=? AND event_id=?");
                int caterer_id = res12.getInt("caterer_id");
                stmt11.setInt(1, caterer_id);
                stmt11.setInt(2,res12.getInt("event_id"));
                 ResultSet rs14=stmt11.executeQuery();
                 if(rs14.next()){
                    int count=rs14.getInt(1);
                    if(count>0){
                        catererBooked=true;
                    }
                 }
                 if(catererBooked==true){
                    System.out.println("The caterer has been booked");
                    PreparedStatement pres1=connection.prepareStatement("SELECT enterprise_name FROM caterer WHERE caterer_id=?");
                    pres1.setInt(1,caterer_id);
                    ResultSet rest4=pres1.executeQuery();
                    if(rest4.next()){
                        caterername=rest4.getString("enterprise_name");
                        System.out.println("The caterer is : "+caterername);
                    }
                    
                    
                 }else{
                    System.out.println("The caterer booking is pending");
                 }


                 boolean decorBooked=false;
                 PreparedStatement stmt12=connection.prepareStatement("SELECT COUNT(*) FROM event WHERE decoration_id=? AND event_id=?");
                 int decoration_id = res12.getInt("decoration_id");
                stmt12.setInt(1, decoration_id);
                stmt12.setInt(2,res12.getInt("event_id"));
                  ResultSet rs15=stmt12.executeQuery();
                  if(rs15.next()){
                     int count=rs15.getInt(1);
                     if(count>0){
                         decorBooked=true;
                     }
                  }
                  if(decorBooked==true){
                     System.out.println("The decor has been booked");
                     PreparedStatement pres2=connection.prepareStatement("SELECT enterprise_name FROM decoration WHERE decoration_id=?");
                    pres2.setInt(1,decoration_id);
                    ResultSet rest5=pres2.executeQuery();
                    if(rest5.next()){
                        decteamname=rest5.getString("enterprise_name");
                        System.out.println("The decoration team is : "+decteamname);
                    }
                  }else{
                     System.out.println("The decor booking is pending");
                  }
                
                PreparedStatement stmt2=connection.prepareStatement("SELECT user.username, event.adv_ref FROM user INNER JOIN event ON user.user_id = event.user_id WHERE user.user_id = ?");
                stmt2.setInt(1, user_id);
                ResultSet rs16=stmt2.executeQuery();
                if(rs16.next()){
                    System.out.println("The advance payment completed");
                    PreparedStatement pres3=connection.prepareStatement("SELECT adv_ref FROM event WHERE event_id=?");
                    pres3.setInt(1,res12.getInt("event_id"));
                    ResultSet rest6=pres3.executeQuery();
                    if(rest6.next()){
                        String advance=rest6.getString("adv_ref");
                        System.out.println("The reference for advance payment is : "+advance);
                    }

                }else{
                    System.out.println("Advance payment is pending...");
                }
                PreparedStatement stmt3=connection.prepareStatement("SELECT user.username, event.final_ref FROM user INNER JOIN event ON user.user_id = event.user_id WHERE user.user_id = ?");
                stmt3.setInt(1, user_id);
                ResultSet rs17=stmt3.executeQuery();
                // if(rs17.next()){
                    // final_ref=rs17.getString("final_ref");
                // if(!final_ref.equals(null)){
                    
                    // PreparedStatement pres4=connection.prepareStatement("SELECT final_ref FROM event WHERE event_id=?");
                    // pres4.setInt(1,res12.getInt("event_id"));
                    // ResultSet rest7=pres4.executeQuery();
                    if(rs17.next()){
                        String finalpayment=rs17.getString("final_ref");
                        if(!finalpayment.equals(null) && !finalpayment.isEmpty()){
                            System.out.println("Final payment is completed!!");
                            System.out.println("The reference for final payment is : "+finalpayment);
                        }else{
                            System.out.println("Final Payment is pending....");
                        }
                        
                    }
                // }
                
                // }
                
                if(decorBooked==true && catererBooked==true && !locationName.equals("")){
                    try{
                        FileWriter write=new FileWriter(filename,true);
                        write.write("DETAILS OF THE EVENT BOOKED BY "+username+"\n");
                        write.write("Event ID : "+res12.getInt("event_id")+"\n");
                        write.write("Type of Event : "+res12.getString("event_name")+"\n");
                        write.write("Date of Event : "+res12.getString("event_date")+"\n");
                        write.write("Location of the Event : "+locationName+"\n");
                        if(catererBooked=true){
                            write.write("Caterer for the Event : "+caterername+"\n");
                        }
                        if(decorBooked=true){
                            write.write("Decoration team for the event : "+decteamname+"\n");
                        }
                        if(cancellation=true){
                            write.write("The evenet has been cancelled");
                        }
                        write.close();
                    }catch(Exception e){
                        System.out.println(e);
                    }
            }
                }catch(Exception e){
                    System.out.println(e);
                }
                
        
        

    }
    static boolean cancellation(String username){
        // Scanner sc =new Scanner(System.in);
        System.out.println("Press 5 if you wish to cancel the event \nPlease note that upon cancellation only 75% of the payment will be refund to the account number given when the registration was done.");
        int cancel=Integer.parseInt(sc.nextLine());
        boolean cancellation=false;
        if(cancel==5){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                PreparedStatement pstmt=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
                pstmt.setString(1,username);

                ResultSet rest=pstmt.executeQuery();
                rest.next();
                int user_id=rest.getInt("user_id");
                PreparedStatement pstmt1=connection.prepareStatement("SELECT event_id FROM event WHERE user_id=?");
                pstmt1.setInt(1,user_id);
                ResultSet rest1=pstmt1.executeQuery();
                rest1.next();
                int event_id=rest1.getInt("event_id");
                PreparedStatement pstmt2=connection.prepareStatement("DELETE FROM event WHERE event_id=? AND user_id=?");
                pstmt2.setInt(2,user_id);
                pstmt2.setInt(1,event_id);
                // ResultSet rest2=pstmt2.executeUpdate();
                int rowsDeleted = pstmt2.executeUpdate();
                if(rowsDeleted>0){
                    System.out.println("User event information has been deleted successfully.");
                }else{
                    System.out.println("Row not found");
                }
                // pstmt2.executeUpdate();
                System.out.println("The refund process has begun.It will be completed in 5 business days.");
                cancellation=true;
                // rest2.next();

            }catch(Exception e){
                System.out.println(e);
            } 
        }else{
            System.out.println("Thank you for continuing to avail our service.");
        }
        return cancellation;
    }
}







        
    


























