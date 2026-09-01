package io.gremstudio.gremdle.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
    private final String name;
    private String role;
    private final Map<String, String> contact = new HashMap<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Map<String, String> getContacts() {
        return contact;
    }

    public void addContract(String contactName, String contactLocation) {
        contact.put(contactName, contactLocation);
    }

    public String formatForFabric() {
        String fName = getName() + ((!getRole().isBlank()) ? (" - " + getRole()) : "");
        List<String> fContacts = new ArrayList<>();
        for (Map.Entry<String, String> entry : getContacts().entrySet()) {
            fContacts.add("\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
        }
        StringBuilder real = new StringBuilder();
        for (int i = 0; i < fContacts.size(); i++) {
            real.append(fContacts);
            if (i + 1 < fContacts.size())
                real.append("\n\t\t");
        }

        String contactsStringified = real.toString();

        return "{\n" +
                "\t\"name\": \"" + fName + "\"\n" +
                "\t\"contact\": {\n" +
                contactsStringified + "\n"
                + "\t}\n}";
    }
}
