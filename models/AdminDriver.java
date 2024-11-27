package models;

import java.io.*;
import java.util.*;

public class AdminDriver extends Driver {
    private Admin admin;
    private Scanner scanner;

    public AdminDriver(Admin admin, Scanner scanner) {
        super(admin.getAkun());
        this.admin = admin;
        this.scanner = scanner;
    }
}
