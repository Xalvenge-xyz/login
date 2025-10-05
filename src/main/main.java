package main;

import java.util.Scanner;
import java.util.Map;
import java.util.List;
import config.config;

public class main {

    static config conf = new config();
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        DatabaseSetup dbSetup = new DatabaseSetup(conf);
        dbSetup.createTables();

        boolean mainLoop = true;
        while (mainLoop) {
            System.out.println("== Welcome to LogiTrak ==");
            System.out.println("== Select Account ==");
            System.out.println("1. Customer Portal");
            System.out.println("2. Staff Portal");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int resp = Integer.parseInt(sc.nextLine());

            switch (resp) {
                case 1:
                    CustomerPortal(sc);
                    break;
                case 2:
                    StaffSelectionMenu(sc);
                    break;
                case 3:
                    mainLoop = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }

    // ===== STAFF SELECTION MENU =====
    public static void StaffSelectionMenu(Scanner sc) {
        boolean loop = true;
        while (loop) {
            System.out.println("\n== Staff Portal ==");
            System.out.println("1. Login as Staff");
            System.out.println("2. Login as Admin");
            System.out.println("3. Login as Super Admin");
            System.out.println("4. Register Staff or Admin");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    staffPortal(sc);
                    break;
                case 2:
                    adminPortal(sc);
                    break;
                case 3:
                    superAdminPortal(sc);
                    break;
                case 4:
                    staffRegister(sc);
                    break;
                case 5:
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== CUSTOMER PORTAL =====
    public static void CustomerPortal(Scanner sc) {
        boolean loop = true;
        while (loop) {
            System.out.println("\n== LogiTrak Customer Portal ==");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    customerLogin(sc);
                    break;
                case 2:
                    customerRegister(sc);
                    break;
                case 3:
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== CUSTOMER LOGIN =====
    private static void customerLogin(Scanner sc) {
        System.out.println("\n== Customer Login ==");
        Map<String, Object> user = authenticate(sc);
        if (user != null && "Customer".equals(user.get("acc_role"))) {
            String name = (String) user.get("acc_name");
            System.out.println("Login successful! Welcome, " + name + " (" + user.get("acc_role") + ").");
            customerDashboard(sc, user);
        } else {
            System.out.println("Invalid Customer credentials or account not approved.");
        }
    }

    // ===== STAFF PORTAL =====
    public static void staffPortal(Scanner sc) {
        System.out.println("\n== Staff Login ==");
        Map<String, Object> user = authenticate(sc);
        if (user != null && "Staff".equals(user.get("acc_role"))) {
            String name = (String) user.get("acc_name");
            System.out.println("Login successful! Welcome, " + name + " (" + user.get("acc_role") + ").");
            staffDashboard(sc, user);
        } else {
            System.out.println("Invalid Staff credentials or account not approved.");
        }
    }

    // ===== ADMIN PORTAL =====
    public static void adminPortal(Scanner sc) {
        System.out.println("\n== Admin Login ==");
        Map<String, Object> user = authenticate(sc);
        if (user == null || !"Admin".equals(user.get("acc_role"))) {
            System.out.println("Invalid Admin credentials or account not approved.");
            return;
        }

        String name = (String) user.get("acc_name");
        System.out.println("Login successful! Welcome, " + name + " (" + user.get("acc_role") + ").");

        boolean loop = true;
        while (loop) {
            System.out.println("\n== Admin Dashboard ==");
            System.out.println("1. Approve Staff Accounts");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    approveAccounts(sc, "Staff");
                    break;
                case 2:
                    loop = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== SUPER ADMIN PORTAL =====
    public static void superAdminPortal(Scanner sc) {
        System.out.println("\n== Super Admin Login ==");
        Map<String, Object> user = authenticate(sc);
        if (user == null || !"Super Admin".equals(user.get("acc_role"))) {
            System.out.println("Invalid Super Admin credentials or account not approved.");
            return;
        }

        String name = (String) user.get("acc_name");
        System.out.println("Login successful! Welcome, " + name + " (" + user.get("acc_role") + ").");

        boolean loop = true;
        while (loop) {
            System.out.println("\n== Super Admin Dashboard ==");
            System.out.println("1. Approve Admin Accounts");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    approveAccounts(sc, "Admin");
                    break;
                case 2:
                    loop = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== CUSTOMER DASHBOARD =====
    private static void customerDashboard(Scanner sc, Map<String, Object> user) {
        String name = (String) user.get("acc_name");
        boolean loop = true;
        while (loop) {
            System.out.println("\n== Customer Dashboard - Welcome, " + name + " ==");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");
            System.out.print("Choice: ");

            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1:
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + user.get("acc_email"));
                    System.out.println("Contact: " + user.get("acc_contact"));
                    break;
                case 2:
                    loop = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== STAFF DASHBOARD =====
    private static void staffDashboard(Scanner sc, Map<String, Object> user) {
        String name = (String) user.get("acc_name");
        boolean loop = true;
        while (loop) {
            System.out.println("\n== Staff Dashboard - Welcome, " + name + " ==");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");
            System.out.print("Choice: ");

            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1:
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + user.get("acc_email"));
                    System.out.println("Contact: " + user.get("acc_contact"));
                    break;
                case 2:
                    loop = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===== AUTHENTICATE (Returns user map if credentials valid and approved, else null; no printing here) =====
    private static Map<String, Object> authenticate(Scanner sc) {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String qry = "SELECT * FROM tbl_accounts WHERE acc_email = ? AND acc_pass = ?";
        List<Map<String, Object>> result = conf.fetchRecords(qry, email, password);

        if (result.isEmpty()) {
            System.out.println("Invalid email or password.");
            return null;
        }

        Map<String, Object> user = result.get(0);
        String status = (String) user.get("acc_status");

        if (!"Approved".equalsIgnoreCase(status)) {
            System.out.println("Your account is currently " + status + ". Please wait for approval.");
            return null;
        }

        return user;
    }

    // ===== CUSTOMER REGISTRATION (Auto-approved, then dashboard) =====
    private static void customerRegister(Scanner sc) {
        System.out.println("\n===== Customer Registration =====");

        System.out.print("Enter Full Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String role = "Customer";
        String status = "Approved";

        // Check if email already exists
        String checkQuery = "SELECT * FROM tbl_accounts WHERE acc_email = ?";
        List<Map<String, Object>> existing = conf.fetchRecords(checkQuery, email);

        if (!existing.isEmpty()) {
            System.out.println("Email already exists. Try another one.");
            return;
        }

        // Insert new user
        String insertQuery = "INSERT INTO tbl_accounts (acc_name, acc_email, acc_pass, acc_contact, acc_role, acc_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.updateRecord(insertQuery, name, email, password, contact, role, status);

        // Auto-login to dashboard
        String fetchQry = "SELECT * FROM tbl_accounts WHERE acc_email = ? AND acc_pass = ?";
        List<Map<String, Object>> res = conf.fetchRecords(fetchQry, email, password);
        if (!res.isEmpty()) {
            Map<String, Object> user = res.get(0);
            System.out.println("Registration successful! You are now logged in as " + name + " (" + role + ").");
            customerDashboard(sc, user);
        } else {
            System.out.println("Registration successful, but login failed. Please login manually.");
        }
    }

    // ===== STAFF/ADMIN REGISTRATION (Pending approval) =====
    private static void staffRegister(Scanner sc) {
        System.out.println("\n===== Staff/Admin Registration =====");

        System.out.print("Enter Full Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        // Choose role (Staff or Admin only)
        System.out.println("Select Role:");
        System.out.println("1. Staff (requires Admin approval)");
        System.out.println("2. Admin (requires Super Admin approval)");
        System.out.print("Enter choice (1-2): ");
        int roleChoice = Integer.parseInt(sc.nextLine());

        String role = "";
        String status = "Pending Approval";

        switch (roleChoice) {
            case 1:
                role = "Staff";
                break;
            case 2:
                role = "Admin";
                break;
            default:
                System.out.println("Invalid choice. Registration canceled.");
                return;
        }

        // Check if email already exists
        String checkQuery = "SELECT * FROM tbl_accounts WHERE acc_email = ?";
        List<Map<String, Object>> existing = conf.fetchRecords(checkQuery, email);

        if (!existing.isEmpty()) {
            System.out.println("Email already exists. Try another one.");
            return;
        }

        // Insert new user
        String insertQuery = "INSERT INTO tbl_accounts (acc_name, acc_email, acc_pass, acc_contact, acc_role, acc_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.updateRecord(insertQuery, name, email, password, contact, role, status);

        System.out.println("Registration successful! Your account is pending approval by " +
                (role.equals("Staff") ? "Admin." : "Super Admin."));
    }

    // ===== APPROVE ACCOUNTS =====
    private static void approveAccounts(Scanner sc, String roleToApprove) {
        // Fetch pending accounts
        String qry = "SELECT * FROM tbl_accounts WHERE acc_role = ? AND acc_status = 'Pending Approval'";
        List<Map<String, Object>> pending = conf.fetchRecords(qry, roleToApprove);

        if (pending.isEmpty()) {
            System.out.println("No " + roleToApprove + " accounts pending approval.");
            return;
        }

        System.out.println("\nPending " + roleToApprove + " Accounts:");

        for (Map<String, Object> acc : pending) {
            String id = acc.get("acc_id").toString();
            String name = (String) acc.get("acc_name");
            String email = (String) acc.get("acc_email");
            System.out.println("- " + name + " (ID: " + id + ", Email: " + email + ")");
            System.out.print("Approve this account? (y/n): ");
            String resp = sc.nextLine();
            if (resp.equalsIgnoreCase("y")) {
                String updateQry = "UPDATE tbl_accounts SET acc_status = 'Approved' WHERE acc_id = ?";
                conf.updateRecord(updateQry, id);
                System.out.println("" + name + " (" + roleToApprove + ") approved successfully.");
            } else {
                System.out.println("Skipped.");
            }
            System.out.println(); // Spacing
        }
    }
}
