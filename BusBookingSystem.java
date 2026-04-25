import java.sql.*;
import java.util.Scanner;

public class BusBookingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DBConnection.createConnection();
            
            System.out.println("=== Online Bus Booking System ===");
            System.out.println("1. View Buses\n2. Book Ticket\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            
            if(choice == 1) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM buses");
                System.out.println("\nBusID | BusName | From | To | Seats | Price");
                while(rs.next()) {
                    System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getInt(5) + " | " + rs.getInt(6));
                }
            }
            else if(choice == 2) {
                System.out.print("Enter Bus ID: ");
                int busId = sc.nextInt();
                System.out.print("Enter Passenger Name: ");
                String name = sc.next();
                System.out.print("Enter Seats to Book: ");
                int seats = sc.nextInt();
                
                PreparedStatement ps = con.prepareStatement("SELECT price, seats_available FROM buses WHERE bus_id = ?");
                ps.setInt(1, busId);
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()) {
                    int price = rs.getInt(1);
                    int available = rs.getInt(2);
                    if(seats <= available) {
                        int total = price * seats;
                        PreparedStatement ps1 = con.prepareStatement("INSERT INTO bookings(passenger_name, bus_id, seats_booked, total_amount) VALUES (?, ?, ?, ?)");
                        ps1.setString(1, name);
                        ps1.setInt(2, busId);
                        ps1.setInt(3, seats);
                        ps1.setInt(4, total);
                        ps1.executeUpdate();
                        
                        PreparedStatement ps2 = con.prepareStatement("UPDATE buses SET seats_available = seats_available - ? WHERE bus_id = ?");
                        ps2.setInt(1, seats);
                        ps2.setInt(2, busId);
                        ps2.executeUpdate();
                        
                        System.out.println("Booking Success! Total Amount: " + total);
                    } else {
                        System.out.println("Sorry, only " + available + " seats available");
                    }
                }
                con.close();
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
