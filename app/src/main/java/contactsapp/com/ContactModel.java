package contactsapp.com;

public class ContactModel {
    String name, phone, email;
    int image;

    public ContactModel(String name, String phone, String email, int image) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public int getImage() {
        return image;
    }
}