public class User {

    private int id;
    private String name;
    private Login login;
    private long token;
    private String email;
    private Adress adress;
    private String phone;
    private String website;
    private Company company;

    public User() {

    }

    public User(int id, String name, Login login, String email, Adress adress, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.token = (long) (Math.random() * 100000);
        this.email = email;
        this.adress = adress;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + "\nNombre: " + this.name + "\nLogin: " + this.login.toString() + "\nToken: " + this.token + "\nEmail: " + this.email + "\nDirección:" + this.adress.toString() + "\nTelefono: " + this.phone + "\nDirección web: " + this.website + "\nCompañía:" + this.company.toString();
    }

    public String data() {
        return "Nombre de usuario: " + this.login.getUsername() + "\nToken: " + this.token;
    }

    public boolean validate(Login login) {
        return this.login.getUsername().toUpperCase().equals(login.getUsername().toUpperCase()) && this.login.getPassword().equals(login.getPassword());
    }

    public boolean validateToken(long token) {
        return this.token == token;
    }

}
