package lite.vls.users;

public record UserRecord(
    Long id,
    String name,
    String lastname,
    String phone,
    String email,
    String password
) {
    
}
