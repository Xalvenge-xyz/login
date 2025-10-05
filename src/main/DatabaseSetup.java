package main;

import config.config;

public class DatabaseSetup {

    private config conf;

    public DatabaseSetup(config conf) {
        this.conf = conf;
    }

    public void createTables() {
        // Create accounts table
        String createAccountsTable = "CREATE TABLE IF NOT EXISTS tbl_accounts ("
                + "acc_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "acc_name VARCHAR(255) NOT NULL, "
                + "acc_email VARCHAR(255) UNIQUE NOT NULL, "
                + "acc_pass VARCHAR(255) NOT NULL, "
                + "acc_contact VARCHAR(50), "
                + "acc_role VARCHAR(50) NOT NULL, "
                + "acc_status VARCHAR(50) NOT NULL"
                + ")";

        // Execute the statement
        conf.updateRecord(createAccountsTable);

        System.out.println("✅ Database tables are ready.");
    }
}
