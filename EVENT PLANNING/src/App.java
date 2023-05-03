import java.sql.*;

import java.util.*;

import com.mysql.cj.log.NullLogger;

public class App{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hello");
        System.out.println("Do you want to register or login (Register : 1,Login : 2) : ");
        int a =Integer.parseInt(sc.nextLine());
        String username;
        String eventdate;
        int location_id;
        int caterer_id;
        int decoration_id;
        String adv_ref=null;
        String final_ref;
        if(a==1){
            username=userregistration();
            eventdate=dateavailability(username);
            location_id=locationbooking(username);
            System.out.println("Please give the reference number of the advance payment(location rate) to proceed further : ");
            adv_ref=sc.nextLine();
            if(adv_ref!=null){
                caterer_id=caterer(username);
                decoration_id=decoration(username);
                System.out.println("Please give the reference number of the final payment : ");
                final_ref=sc.nextLine();
                eventtableupdates(username, eventdate, location_id, caterer_id, decoration_id, adv_ref, final_ref);  
            }else{
                System.out.println("We cannot proceed further without advance payment");
            }
            
        }else if(a==2){
            username=userLogin();
            if(username==null){
                System.out.println("Password incorrect.Please try again");
            }else{
                eventdate=dateavailability(username);
                location_id=locationbooking(username);
                System.out.println("Please give the reference number of the advance payment(location rate) to proceed further : ");
                adv_ref=sc.nextLine();
                if(adv_ref!=null){
                    caterer_id=caterer(username);
                    decoration_id=decoration(username);
                    System.out.println("Please give the reference number of the final payment : ");
                    final_ref=sc.nextLine();
                    eventtableupdates(username, eventdate, location_id, caterer_id, decoration_id, adv_ref, final_ref); 
                }else{
                    System.out.println("We cannot proceed further without advance payment");
                }
                    
                }
                
        }
        else{
            System.out.println("Give valid number");
        }
        



    }
    static String userregistration()  {
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
    static String userLogin(){
        String username=null;
        String password;
        boolean passwordcorrect=false;
        String usernamedupe=null;
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

    static int locationbooking(String username){
        Scanner sc=new Scanner(System.in);
        String preferredlocation=null;
        int location_id=0;
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
            PreparedStatement prep1=connection.prepareStatement("SELECT location_id FROM location WHERE location_name=?");
            prep1.setString(1,preferredlocation);
            ResultSet rset1=prep1.executeQuery();
            rset1.next();
            location_id=rset1.getInt(1);
            
        }catch(Exception e){
            System.out.println(e);
        }
        return location_id;
            
    }
    static int caterer(String username){
        Scanner sc=new Scanner(System.in);
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
    static int decoration(String username){
        String preferred_decteam=null;
        int decoration_id=0;
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

    static void eventtableupdates(String username,String eventdate,int location_id,int caterer_id,int decoration_id,String adv_ref,String final_ref){
        Scanner sc=new Scanner (System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prep5 = connection.prepareStatement("INSERT INTO event (event_name,event_date,guest_number,user_id,location_id,caterer_id,decoration_id,adv_ref,final_ref) VALUES (?, ?, ?, ?,?,?,?,?,?)");
            System.out.println("What kind of an event is this : \n1.Wedding \n2.Anniversary Party \n3.Birthday Party");
            int op=Integer.parseInt(sc.nextLine());
            switch(op){
                case 1:
                prep5.setString(1,"Wedding");
                break;
                case 2:
                prep5.setString(1,"Anniversary Party");
                break;
                case 3:
                prep5.setString(1,"Birthday Party");
                break;
            }
            prep5.setString(2,eventdate);
            System.out.println("How many guests are you expecting : ");
            int guest=Integer.parseInt(sc.nextLine());
            prep5.setInt(3,guest);
            prep5.setInt(5,location_id);
            prep5.setInt(6,caterer_id);
            prep5.setInt(7,decoration_id);
            prep5.setString(8,adv_ref);
            prep5.setString(9,final_ref);
            PreparedStatement prep6=connection.prepareStatement("SELECT user_id FROM user WHERE username=?");
            prep6.setString(1,username);
            ResultSet rset6=prep6.executeQuery();
            rset6.next();
            int user_id=rset6.getInt(1);
            prep5.setInt(4,user_id);
            prep5.executeUpdate();

            
            
        } catch (Exception e) {
            System.out.println(e);
        }
        

    }
    
}







        
    


























