package projetos.uniClinicas.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    CLINICA("clinica");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
