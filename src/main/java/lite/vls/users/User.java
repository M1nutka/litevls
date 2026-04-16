package lite.vls.users;

public record User(
    Long id,
    String name,
    String lastname,
    String phone,
    String email,
    String password
) {
    
}
