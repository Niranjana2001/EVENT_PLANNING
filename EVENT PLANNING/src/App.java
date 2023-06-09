import java.sql.*;
import java.io.*;
import java.util.*;

public class App{
    private static Scanner sc =new Scanner(System.in);
    public static void main(String[] args){
        
        String filename="Event details.txt";
        System.out.println("\033[31mHello\033[0m");
        String username="";
        
        System.out.println("Are you a user or admin : ");
        String usertype=sc.nextLine();
        if(usertype.equalsIgnoreCase("admin")){
            String adminusername=adminlogin();
            if(adminusername==null){
                System.out.println("------------------");
            }else{
                System.out.println("1.User information \n2.Analysis \n3.Histograms \n4.Event Status");
                int option=Integer.parseInt(sc.nextLine());
                switch(option){
                    case 1 : 
                    userinfo();
                    break;
                    case 2:
                    System.out.println("Most popular locations : ");
                    locationanalysis();
                    System.out.println("Most preferred caterers are : ");
                    catereranalysis();
                    System.out.println("Most preferred decoration teams are : ");
                    decorationteamanalysis();
                    System.out.println("Most popular events are : ");
                    eventtypeanalysis();
                    break;
                    case 3:
                    locationhistogram();
                    catererhistogram();
                    decorationhistogram();
                    eventtypehistogram();
                    break;
                    case 4:
                    eventstatusforadmin();
                    break;
                }
            }
        }else if(usertype.equalsIgnoreCase("user")){
              // Output  prompt for user registration or login
        System.out.println("\033[35mDo you want to register or login (Register : 1,Login : 2) :\033[0m ");
        int a =Integer.parseInt(sc.nextLine());
        if(a==1){
            // Call user registration function
            username=userregistration();
        }
        else if(a==2){
            // Call user login function
            username=userLogin();
            if(username==null){
                // Output  message if login fails 
                System.out.println("\033[36mPassword incorrect.Please try again\033[0m");
        }}
        else{
            // Output  message for invalid input
            System.out.println("\033[32mGive valid number\033[0m");
        }
        // Output a  prompt for event type selection
        System.out.println("\033[31mWhat kind of an event is this : \n1.Wedding \n2.Anniversary Party \n3.Birthday Party\033[0m");
        int option=Integer.parseInt(sc.nextLine());
        String eventtype=eventtype(option);
        System.out.println("\033[30mEnter the number of guests you are expecting :\033[0m ");
        int guest=Integer.parseInt(sc.nextLine());
        System.out.println("\033[34m1.Date Availability \n2. Location Booking \n3.Caterer Booking \n4.Decoration team Booking \n5.Event Status \n6.Cancellation \n7.Most booked locations \nChoose an option\033[0m");
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
            // Call date availability function 
            eventdate=dateavailability(username);
            break;
            case 2:
            System.out.println("\033[33mWhat is the date of your event : \033[0m");
            eventdate=sc.nextLine();
            // Call location booking function
            location_id=locationbooking(username,eventdate);
            if(location_id!=0){
                System.out.println("\033[37mPlease give the reference number of the advance payment(location rate) to proceed further :\033[0m ");
            adv_ref=sc.nextLine(); 
            eventtableupdates(username, eventdate, location_id, adv_ref, eventtype, guest);
            }
            break;
            case 3:
            System.out.println("\033[33mWhat is the date of your event : \033[0m");
            eventdate=sc.nextLine();
            // Call caterer booking function
            caterer_id=caterer(username);
            eventtableupdates(eventdate,username,caterer_id);
            break;
            case 4:
            System.out.println("\033[33mWhat is the date of your event : \033[0m");
            eventdate=sc.nextLine();
            // Call decoration team booking function
            decoration_id=decoration(username);
            System.out.println("\033[34mPlease give the reference number of the final payment :\033[0m ");
            final_ref=sc.nextLine();
            eventtableupdates(eventdate,username, decoration_id, final_ref);
            break;
            case 5:
            // Call eventstatus function
            eventStatus(username, filename, cancellation);
            break;
            case 6:
            // call cancellation function
            cancellation=cancellation(username);
            break;
            case 7:
            // Call analysis function
            analysis(username);
            // default:
            // System.out.println("\033[36mEnter a valid choice\033[0m");
            // break;
        }
        }
      

}
// Eventtype function takes an integer input and returns a string indicating the type of event based on the input option.
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
            System.out.println("\033[36mEnter valid option\033[0m");
            return null;
        }
    }
// User registration function
    static String userregistration() {
        String username=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            System.out.println("\033[34mEnter your details to register:\033[0m");
            System.out.println("\033[34mEnter Username: \033[0m");
            username = sc.nextLine();
            String[] userDetails = new String[9];
            userDetails[0] = username;
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user WHERE username='" + username + "'";
            ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()) {
            System.out.println("\033[31mUsername already exists!!!\033[0m");
            System.out.println("\033[36mEnter another username: \033[0m ");
            username = sc.nextLine();
            userDetails[0] = username;
            System.out.println("\033[34mEnter password:\033[31m");
            String password=sc.nextLine();
            System.out.println("\033[34mEnter name:\033[0m");
            String name=sc.nextLine();
            System.out.println("\033[34mEnter Address: \033[0m");
            String address=sc.nextLine();
            System.out.println("\033[34mCity : \033[0m");
            String city=sc.nextLine();
            System.out.println("\033[34mEnter Email :\033[0m");
            String email=sc.nextLine();
            System.out.println("\033[34mEnter phoneNumber :\033[0m");
            String phone_number=sc.nextLine();
            System.out.println("\033[34mEnter Adhaar Number :\033[0m");
            String adhaar_number=sc.nextLine();
            System.out.println("\033[34mEnter Account number:\033[0m");
            String account_number=sc.nextLine();
            userDetails = new String[]{username, password, name, address, city, email, phone_number, adhaar_number, account_number};
            
             sql = "INSERT INTO user (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
           statement.executeUpdate(sql);
           System.out.println("Success");
        }else{
            System.out.println("\033[34mEnter password:\033[31m");
            String password=sc.nextLine();
            System.out.println("\033[34mEnter name:\033[0m");
            String name=sc.nextLine();
            System.out.println("\033[34mEnter Address: \033[0m");
            String address=sc.nextLine();
            System.out.println("\033[34mCity : \033[0m");
            String city=sc.nextLine();
            System.out.println("\033[34mEnter Email :\033[0m");
            String email=sc.nextLine();
            System.out.println("\033[34mEnter phoneNumber :\033[0m");
            String phone_number=sc.nextLine();
            System.out.println("\033[34mEnter Adhaar Number :\033[0m");
            String adhaar_number=sc.nextLine();
            System.out.println("\033[34mEnter Account number:\033[0m");
            String account_number=sc.nextLine();
            userDetails = new String[]{username, password, name, address, city, email, phone_number, adhaar_number, account_number};
            
             sql = "INSERT INTO user (username, password,name,address,city,email,phone_number,adhaar_number,account_number) VALUES ('" + userDetails[0] + "', '" + userDetails[1] + "' ,'"+ userDetails[2] +"','"+ userDetails[3] +"','"+ userDetails[4] +"','"+ userDetails[5] +"','"+ userDetails[6] +"','"+ userDetails[7] +"','"+ userDetails[8] +"')";
           statement.executeUpdate(sql);
        }  System.out.println("User " + userDetails[0] + " registered successfully");
        
        } catch(Exception e){
            System.out.println(e);
        }
        return username;
    }
// User Login function
    static String userLogin(){
        String username=null;
        String password;
        boolean passwordcorrect=false;
        String usernamedupe=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            // Scanner sc=new Scanner(System.in);
            System.out.println("\033[33mEnter your Username :\033[0m");
              username=sc.nextLine();
             String[] userDetails = new String[9];
             userDetails[0] = username;
             PreparedStatement stm1 = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
             stm1.setString(1, username);
          ResultSet resultSet = stm1.executeQuery();
            if (resultSet.next()) {
                String pass;
                System.out.println("\033[35mEnter your Password:\033[0m ");
                pass = sc.nextLine();
                String storedPassword = resultSet.getString("password");
                passwordcorrect=passwordverification(pass,storedPassword);
                
            } else {
                System.out.println("\033[31mUsername does not exist.Try again.\033[0m");
                System.out.println("\033[32mEnter your correct username : \033[0m");
                String username1=sc.nextLine();
                userDetails[0]=username1;
                PreparedStatement stm7 = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
                stm7.setString(1, username1);
                ResultSet resultSet1 = stm7.executeQuery();
                if(resultSet1.next()){
                    String pass1;
                    System.out.println("\033[35mEnter your Password:\033[0m ");
                    pass1 = sc.nextLine();
                    String storedPassword1 = resultSet1.getString("password");
                    passwordcorrect = passwordverification(pass1,storedPassword1); 
                    
                }else{
                    System.out.println("\033[32mThe username is wrong again.Please register.\033[0m");
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
    // Password verification function
    // It is used to verify if the password entered by the user during login matches with the password previously set during registration.
    static boolean passwordverification(String password1,String password){
        boolean passwordcorrect=false;
        boolean passwordverified=true;
        while(!passwordcorrect){
            if(password1.equals(password)){
                System.out.println("\033[36mPassword correct.Login successful.\033[0m");
                passwordcorrect=true;
            }else{
                
                passwordverified=false;
                break;
                
            }   
        }
        return passwordverified;  
    }
// Date availability function for checking the availability of date for the event
    static String dateavailability(String username) {
        // Scanner sc=new Scanner(System.in);
        System.out.println("\033[33mEnter the date of the event in the format yyyy-mm-dd\033[0m");
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
                System.out.println("\033[36mEvent date not available\033[0m");
                System.out.println("\033[36mSorry for the inconvenience\033[0m");

            }else{
                datenotavailable=eventdate;
                System.out.println("\033[34mWe have a slot available. You can proceed to checkout the different locations.\033[0m");
                // locationbooking(username);
            }
        }

        catch(Exception e){
            System.out.println(e);
        }
        return datenotavailable;
    }
    // Location Booking function
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
            System.out.println("\033[32mThe following are the location available on the date given : \033[0m");
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
            System.out.println("\033[32mPlease select your preferred location : \033[0m");
            preferredlocation=sc.nextLine();
            System.out.println("\033[35mPlease make an advance payment for booking the location\033[0m");
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
        System.out.println("\033[31mPlease confirm whether the date slot is free before booking the location.\033[0m");
        return location_id; 
    }

    }
    // Event table Updates function
    // inserts a new row into the "event" table.

    static void eventtableupdates(String username,String eventdate,int location_id,String adv_ref,String event_type,int guest){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            PreparedStatement prep5 = connection.prepareStatement("INSERT INTO event (event_name,event_date,guest_number,user_id,location_id,adv_ref) VALUES (?, ?, ?, ?,?,?)");
            prep5.setString(1,event_type);
            prep5.setString(2,eventdate);
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

        // caterer function
        // to boook the caterer
    
        static int caterer(String username){
           
            System.out.println("\033[34mWhich is your food preference : \n1.Veg \n2.Non Veg \n3.Veg and Non Veg\033[0m");
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
                    System.out.println("\033[32mThe following are the caterers available on the date given :\033[0m ");
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
                    System.out.println("\033[32mPlease select your preferred caterer :\033[0m ");
                    preferredcaterer=sc.nextLine();
                    PreparedStatement prep2=connection.prepareStatement("SELECT caterer_id FROM caterer WHERE enterprise_name=?");
                    prep2.setString(1,preferredcaterer);
                    ResultSet rset2=prep2.executeQuery();
                    rset2.next();
                    caterer_id=rset2.getInt(1);
                    break;
                    case 2:
                    System.out.println("\033[32mThe following are the caterers available on the date given :\033[0m ");
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
                    System.out.println("\033[33mPlease select your preferred caterer :\033[0m ");
                    preferredcaterer=sc.nextLine();
                    PreparedStatement prep7=connection.prepareStatement("SELECT caterer_id FROM caterer WHERE enterprise_name=?");
                    prep7.setString(1,preferredcaterer);
                    ResultSet rset8=prep7.executeQuery();
                    rset8.next();
                    caterer_id=rset8.getInt(1);
                    break;
                    case 3:
                    System.out.println("\033[35mThe following are the caterers available on the date given :\033[0m ");
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
                    System.out.println("\033[34mPlease select your preferred caterer :\033[0m ");
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
        // decoration function
        // for booking the decoration team
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
                System.out.println("\033[35mThe following are the decoration teams available on the date given : \033[0m");
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
                System.out.println("\033[34mPlease select your preferred decoration team : \033[0m");
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
        // event status function
        // for viewing the status of the event
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
                    System.out.println("\033[34mEVENT ID: \033[0m"+res12.getInt("event_id"));
                    System.out.println("\033[34mEVENT NAME:\033[0m "+res12.getString("event_name"));
                    System.out.println("\033[34mEVENT DATE: \033[0m"+res12.getString("event_date"));
                    PreparedStatement stm10=connection.prepareStatement("SELECT location_name FROM location WHERE location_id=?"); 
                    int locationId = res12.getInt("location_id");
                    stm10.setInt(1, locationId);
                    ResultSet res13=stm10.executeQuery();
                    if (res13.next()) {
                        locationName = res13.getString("location_name");
                        System.out.println("\033[34mEVENT LOCATION : \033[0m" + locationName);
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
                        System.out.println("\033[35mThe caterer has been booked\033[0m");
                        PreparedStatement pres1=connection.prepareStatement("SELECT enterprise_name FROM caterer WHERE caterer_id=?");
                        pres1.setInt(1,caterer_id);
                        ResultSet rest4=pres1.executeQuery();
                        if(rest4.next()){
                            caterername=rest4.getString("enterprise_name");
                            System.out.println("\033[33mThe caterer is : \033[0m"+caterername);
                        }
    
    
                     }else{
                        System.out.println("\033[31mThe caterer booking is pending\033[0m");
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
                         System.out.println("\033[35mThe decor has been booked\033[0m");
                         PreparedStatement pres2=connection.prepareStatement("SELECT enterprise_name FROM decoration WHERE decoration_id=?");
                        pres2.setInt(1,decoration_id);
                        ResultSet rest5=pres2.executeQuery();
                        if(rest5.next()){
                            decteamname=rest5.getString("enterprise_name");
                            System.out.println("\033[33mThe decoration team is :\033[0m "+decteamname);
                        }
                      }else{
                         System.out.println("\033[31mThe decor booking is pending\033[0m");
                      }
    
                    PreparedStatement stmt2=connection.prepareStatement("SELECT user.username, event.adv_ref FROM user INNER JOIN event ON user.user_id = event.user_id WHERE user.user_id = ?");
                    stmt2.setInt(1, user_id);
                    ResultSet rs16=stmt2.executeQuery();
                    if(rs16.next()){
                        System.out.println("\033[36mThe advance payment completed\033[0m");
                        PreparedStatement pres3=connection.prepareStatement("SELECT adv_ref FROM event WHERE event_id=?");
                        pres3.setInt(1,res12.getInt("event_id"));
                        ResultSet rest6=pres3.executeQuery();
                        if(rest6.next()){
                            String advance=rest6.getString("adv_ref");
                            System.out.println("\033[33mThe reference for advance payment is :\033[0m "+advance);
                        }
    
                    }else{
                        System.out.println("\033[31mAdvance payment is pending...\033[0m");
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
                                System.out.println("\033[33mFinal payment is completed!!\033[0m");
                                System.out.println("\033[34mThe reference for final payment is : \033[0m"+finalpayment);
                            }else{
                                System.out.println("\033[31mFinal Payment is pending....\033[0m");
                            }
    
                        }
                    // }
    
                    // }
    
                    if(decorBooked==true && catererBooked==true && !locationName.equals("")){
                        try{
                            FileWriter write=new FileWriter(filename,true);
                            write.write("\nDETAILS OF THE EVENT BOOKED BY "+username+"\n");
                            write.write("Event ID : "+res12.getInt("event_id")+"\n");
                            write.write("Type of Event : "+res12.getString("event_name")+"\n");
                            write.write("Date of Event : "+res12.getString("event_date")+"\n");
                            write.write("Location of the Event : "+locationName+"\n");
                            if(catererBooked==true){
                                write.write("Caterer for the Event : "+caterername+"\n");
                            }
                            if(decorBooked==true){
                                write.write("Decoration team for the event : "+decteamname+"\n");
                            }
                            if(cancellation==true){
                                write.write("The event has been cancelled \n");
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
        // cancellation function
        // to cancel the event
        static boolean cancellation(String username){
            // Scanner sc =new Scanner(System.in);
            System.out.println("\033[34mPress 5 if you wish to cancel the event \nPlease note that upon cancellation only 75% of the payment will be refund to the account number given when the registration was done.\033[0m");
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
                    System.out.println("\033[33mThe refund process has begun.It will be completed in 5 business days.\033[0m");
                    cancellation=true;
                    // rest2.next();
    
                }catch(Exception e){
                    System.out.println(e);
                } 
            }else{
                System.out.println("\033[33mThank you for continuing to avail our service.\033[0m");
            }
            return cancellation;
        }
        static void analysis (String username){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                PreparedStatement pstmt8=connection.prepareStatement("SELECT city FROM user WHERE username=?");
                pstmt8.setString(1,username);
                ResultSet restnew=pstmt8.executeQuery();
                restnew.next();
                String user_city=restnew.getString("city");
                PreparedStatement pstmt6=connection.prepareStatement("SELECT l.location_name, COUNT(*) as num_registrations FROM event e JOIN location l ON e.location_id = l.location_id WHERE l.city=? GROUP BY l.location_name ORDER BY num_registrations DESC");
                    pstmt6.setString(1,user_city);
    
                    ResultSet restnew1=pstmt6.executeQuery();
                    
                    while(restnew1.next()){
                        String location=restnew1.getString("location_name");
                        int number=restnew1.getInt("num_registrations");
                        System.out.println("The most popular locations are : ");
                        System.out.println(location +" : "+number);

                    }
                    
           
           
           
            }catch(Exception e){
                System.out.println(e);
            }
        } 
        static String adminlogin(){
            String username=null;
            String password;
            boolean passwordcorrect=false;
            String usernamedupe=null;
            String adminUsername="Event_Planning";
            String adminPassword="1234";
                System.out.println("\033[33mEnter your Username :\033[0m");
                username=sc.nextLine();
                
                 
                if (username.equals(adminUsername)) {
                    String pass;
                    System.out.println("\033[35mEnter your Password:\033[0m ");
                    pass = sc.nextLine();
                    passwordcorrect=passwordverification(pass,adminPassword);
                    if(!passwordcorrect){
                        System.out.println("You have entered the wrong password.Please try again");
                        String pass2=sc.nextLine();
                        boolean passcorrect=passwordverification(pass2, adminPassword);
                        if(!passcorrect){
                            System.out.println("You have entered the wrong password again. Please try later.");
                        }else{
                            username=adminUsername;
                            return username;
                        }
                    }else{
                        username=adminUsername;
                        return username;
                    }
                    
                    
                } else {
                    System.out.println("\033[31mUsername Incorrect. Try again.\033[0m");
                    System.out.println("\033[32mEnter your correct username : \033[0m");
                    String username1=sc.nextLine();
                    
                    if(username1.equals(adminUsername)){
                        String pass1;
                        System.out.println("\033[35mEnter your Password:\033[0m ");
                        pass1 = sc.nextLine();
                        passwordcorrect = passwordverification(pass1,adminPassword); 
                        if(!passwordcorrect){
                            System.out.println("You have entered the wrong password.Please try again");
                            String pass2=sc.nextLine();
                            boolean passcorrect=passwordverification(pass2, adminPassword);
                            if(!passcorrect){
                                System.out.println("You have entered the wrong password again. Please try later.");
                                return username;
                                
                            }else{
                                username=adminUsername;
                                return username;
                            }
                        }else{
                            username=adminUsername;
                            return username;
                        }
                        
                    }else{
                        System.out.println("\033[32mYou have entered wrong username. Try later\033[0m");
                    }  
                }
            
            // if(passwordcorrect==true){
            //     return username;
            // }
            return usernamedupe;  
        }    

        static void userinfo(){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                // create statement
            Statement statement = connection.createStatement();
            
            // execute query
            ResultSet rsnew1 = statement.executeQuery("SELECT * FROM user");
            ResultSetMetaData metaData1 = rsnew1.getMetaData();
            int columnCount = metaData1.getColumnCount();

           // print header row
           System.out.println(" --------------------------------------------------------------------------------------------------------------------------------------------------");
           System.out.format("| %6s | %-13s | %-10s |%-15s |%-10s |%-10s |%-19s |%-10s |%-10s |%-15s |\n",metaData1.getColumnName(1),metaData1.getColumnName(2),metaData1.getColumnName(3),metaData1.getColumnName(4),metaData1.getColumnName(5),metaData1.getColumnName(6),metaData1.getColumnName(7),metaData1.getColumnName(8),metaData1.getColumnName(9),metaData1.getColumnName(10));
           System.out.println(" --------------------------------------------------------------------------------------------------------------------------------------------------");
       // print data rows
       while (rsnew1.next()) {
        String col5Value = rsnew1.getString(5);
    int maxLineWidth = 25; // set the maximum width for each line
    String[] words = col5Value.split("\\s+"); // split the value into individual words
    
    String wrappedValue = "";
    int lineLength = 0;
    for (String word : words) {
        if (lineLength + word.length() + 1 <= maxLineWidth) { // check if the word can fit on the current line
            wrappedValue += word + " ";
            lineLength += word.length() + 1;
        } else {
            wrappedValue += String.format("%n%s ", word); // add a line break and start a new line
            lineLength = word.length() + 1;
        }
    }
               System.out.format("| %7d | %-13s | %-10s |%-15s |%-10s |%-10s |%-19s |%-12s |%-13s |%-15s |\n", rsnew1.getInt(1), rsnew1.getString(2), rsnew1.getString(3),rsnew1.getString(4),wrappedValue.trim(),rsnew1.getString(6),rsnew1.getString(7),rsnew1.getString(8),rsnew1.getString(9),rsnew1.getString(10));
       }
       System.out.println(" --------------------------------------------------------------------------------------------------------------------------------------------------");

            }catch(Exception e){
                System.out.println(e);
            }
            
        }

        static void locationanalysis(){
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
            // create statement
            Statement statement1 = connection.createStatement();
            // execute query
            ResultSet rsnew2 = statement1.executeQuery("SELECT l.location_name, COUNT(*) as num_registrations FROM event e JOIN location l ON e.location_id = l.location_id GROUP BY l.location_name ORDER BY num_registrations DESC");
            ResultSetMetaData metaData2 = rsnew2.getMetaData();
            int columnCount = metaData2.getColumnCount();

            // print header row
            System.out.println(" ------------------------------------------");
            System.out.format("| %-20s | %-15s |\n",metaData2.getColumnName(1),metaData2.getColumnName(2));
            System.out.println(" ------------------------------------------");
            // print data rows
            while (rsnew2.next()) {
                    System.out.format("| %20s | %-17d |\n", rsnew2.getString(1), rsnew2.getInt(2));
            }
            System.out.println(" ------------------------------------------");
            }catch(Exception e){
                System.out.println(e);
            }
            
        }
        static void catereranalysis(){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                // create statement
                Statement statement2 = connection.createStatement();
                // execute query
                ResultSet rsnew3 = statement2.executeQuery("SELECT c.enterprise_name as caterer_name, COUNT(*) as num_registrations FROM event e JOIN caterer c ON e.caterer_id = c.caterer_id GROUP BY c.enterprise_name ORDER BY num_registrations DESC");
                ResultSetMetaData metaData3 = rsnew3.getMetaData();
                int columnCount = metaData3.getColumnCount();
    
                // print header row
                System.out.println(" -----------------------------------------------");
                System.out.format("| %25s | %-17s |\n",metaData3.getColumnName(1),metaData3.getColumnName(2));
                System.out.println(" -----------------------------------------------");
            // print data rows
            while (rsnew3.next()) {
                    System.out.format("| %25s | %-17d |\n", rsnew3.getString(1), rsnew3.getInt(2));
            }
            System.out.println(" -----------------------------------------------");
                }catch(Exception e){
                    System.out.println(e);
                }
        }
        
        static void decorationteamanalysis(){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                // create statement
                Statement statement3 = connection.createStatement();
                // execute query
                ResultSet rsnew4 = statement3.executeQuery("SELECT d.enterprise_name, COUNT(*) as num_registrations FROM event e JOIN decoration d ON e.decoration_id = d.decoration_id GROUP BY d.enterprise_name ORDER BY num_registrations DESC");
                ResultSetMetaData metaData4 = rsnew4.getMetaData();
                int columnCount = metaData4.getColumnCount();
    
                // print header row
                System.out.println(" -----------------------------------------------");
                System.out.format("| %25s | %-17s |\n",metaData4.getColumnName(1),metaData4.getColumnName(2));
                System.out.println(" -----------------------------------------------");
            // print data rows
            while (rsnew4.next()) {
                    System.out.format("| %25s | %-17d |\n", rsnew4.getString(1), rsnew4.getInt(2));
                }
                System.out.println(" -----------------------------------------------");
                }catch(Exception e){
                    System.out.println(e);
                }
        }

        static void eventtypeanalysis(){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                // create statement
                Statement statement8 = connection.createStatement();
                // execute query
                ResultSet rsnew8 = statement8.executeQuery("SELECT e.event_name, COUNT(*) as num_registrations FROM event e GROUP BY e.event_name ORDER BY num_registrations DESC");
                ResultSetMetaData metaData8 = rsnew8.getMetaData();
                int columnCount = metaData8.getColumnCount();
    
                // print header row
                System.out.println(" -----------------------------------------------");
                System.out.format("| %25s | %-17s |\n",metaData8.getColumnName(1),metaData8.getColumnName(2));
                System.out.println(" -----------------------------------------------");
            // print data rows
            while (rsnew8.next()) {
                    System.out.format("| %25s | %-17d |\n", rsnew8.getString(1), rsnew8.getInt(2));
                }
                System.out.println(" -----------------------------------------------");
    
                }catch(Exception e){
                    System.out.println(e);
                }
        }

        static void eventstatusforadmin(){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
                // create statement
            Statement statement7 = connection.createStatement();
            
            // execute query
            ResultSet rsnew7 = statement7.executeQuery("SELECT * FROM event");
            ResultSetMetaData metaData7 = rsnew7.getMetaData();
            int columnCount = metaData7.getColumnCount();

            // print header row
            System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.format("| %6s | %-20s | %-5s |%-5s |%-5s |%-5s |%-5s |%-5s |%-13s |%-13s |\n",metaData7.getColumnName(1),metaData7.getColumnName(2),metaData7.getColumnName(3),metaData7.getColumnName(4),metaData7.getColumnName(5),metaData7.getColumnName(6),metaData7.getColumnName(7),metaData7.getColumnName(8),metaData7.getColumnName(9),metaData7.getColumnName(10));
                System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------");
            // print data rows
            while (rsnew7.next()) {
                    System.out.format("| %8d | %-20s | %-5s |%-12s |%-7s |%-11s |%-10s |%-13s |%-13s |%-13s |\n", rsnew7.getInt(1), rsnew7.getString(2), rsnew7.getString(3),rsnew7.getInt(4),rsnew7.getInt(5),rsnew7.getInt(6),rsnew7.getInt(7),rsnew7.getInt(8),rsnew7.getString(9),rsnew7.getString(10));
            }
            System.out.println(" -------------------------------------------------------------------------------------------------------------------------------------------");
            }catch(Exception e){
                System.out.println(e);
            }
        }
static void locationhistogram(){
    int[] locationCount = new int[100]; 
        String[] locationNames = new String[100]; 
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
    
        String query = "SELECT event.event_id, location.location_name " +
                       "FROM event " +
                       "JOIN location " +
                       "ON event.location_id = location.location_id";
    
        
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
    
        
        
        int count = 0;
        while (resultSet.next()) {
            String locationName = resultSet.getString("location_name");
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (locationNames[i].equals(locationName)) {
                    locationCount[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                locationNames[count] = locationName;
                locationCount[count] = 1;
                count++;
            }
        }
    
        // print the histogram
        System.out.println("");
        System.out.println("-------------------");
        System.out.println("Location Histogram :");
        System.out.println("-------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-20s",locationNames[i]+":" );
            for (int j = 0; j < locationCount[i]; j++) {
                System.out.print(" | ");
            }
            System.out.println( " ");
        }
        
    
    } catch (Exception e) {
        System.out.println(e);
    }
   

}
static void catererhistogram(){

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
    
        String query = "SELECT event.event_id, caterer.enterprise_name " +
                       "FROM event " +
                       "JOIN caterer " +
                       "ON event.caterer_id = caterer.caterer_id";
    
        
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
    
        
        int[] catererCount = new int[100]; 
        String[] catererNames = new String[100]; 
        int count = 0;
        while (resultSet.next()) {
            String catererName = resultSet.getString("enterprise_name");
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (catererNames[i].equals(catererName)) {
                    catererCount[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                catererNames[count] = catererName;
                catererCount[count] = 1;
                count++;
            }
        }
    
        // print the histogram
        System.out.println("");
        System.out.println("------------------");
        System.out.println("Caterer Histogram :");
        System.out.println("------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-20s",catererNames[i]+":" );
            for (int j = 0; j < catererCount[i]; j++) {
                System.out.print(" | ");
            }
            System.out.println( " ");
        }
    
    } catch (Exception e) {
        System.out.println(e);
    }
    

}
static void decorationhistogram(){

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
    
        String query = "SELECT event.event_id, decoration.enterprise_name " +
                       "FROM event " +
                       "JOIN decoration " +
                       "ON event.decoration_id = decoration.decoration_id";
    
        
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
    
        
        int[] decorCount = new int[100]; 
        String[] decorNames = new String[100]; 
        int count = 0;
        while (resultSet.next()) {
            String decorName = resultSet.getString("enterprise_name");
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (decorNames[i].equals(decorName)) {
                    decorCount[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                decorNames[count] = decorName;
                decorCount[count] = 1;
                count++;
            }
        }
    
        // print the histogram
        System.out.println("");
        System.out.println("---------------------");
        System.out.println("Decoration Histogram :");
        System.out.println("---------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-23s ",decorNames[i]+ ":" );
            for (int j = 0; j < decorCount[i]; j++) {
                System.out.print(" | ");
            }
            System.out.println( " ");
        }
    
    } catch (Exception e) {
        System.out.println(e);
    }
    

}
static void eventtypehistogram(){
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/event_planning","root","mysql1234");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT event_name FROM event ");
        int[] eventCount = new int[100]; 
        String[] eventNames = new String[100]; 
        int count = 0;
        while (rs.next()) {
            String eventName = rs.getString("event_name");
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (eventNames[i].equals(eventName)) {
                    eventCount[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                eventNames[count] = eventName;
                eventCount[count] = 1;
                count++;
            }
        }
    
        // print the histogram
        System.out.println("");
        System.out.println("----------------------");
        System.out.println("Event Type Histogram :");
        System.out.println("----------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-20s ",eventNames[i]+ ":" );
            for (int j = 0; j < eventCount[i]; j++) {
                System.out.print(" | ");
            }
            System.out.println( " ");
        }
    
       
    
    } catch (Exception e) {
        System.out.println(e);
    }
    
}


    }