package org.example;

public class Main {

    public static void main(String[] args) {
        String scriptFilePath = "/home/dmytro/IdeaProjects/dz-13/dz-13-db/src/main/java/org/example/create_db.sql";
        DataBaseManager.executeSqlScript(scriptFilePath);
        try {
            DataBaseManager.closeConnection();
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
